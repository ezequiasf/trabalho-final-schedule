package com.dbccompany.kafkareceita.service;

import com.dbccompany.kafkareceita.dataTransfer.InfoBuyDTO;
import com.dbccompany.kafkareceita.dataTransfer.LogDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProducerService {
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(InfoBuyDTO buyDTO) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(buyDTO);
        send(message, "recipe-buy");
    }

    public void sendMessage(LogDTO logDTO) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(logDTO);
        send(message, "recipe-log");
    }

    public void send(String messageString, String topic) {
        Message<String> message = MessageBuilder.withPayload(messageString)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString())
                .build();

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(message);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult result) {
                log.info(" Log enviado para o kafka com o texto: {} ", messageString);
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error(" Erro ao publicar duvida no kafka com a mensagem: {}", messageString, ex);
            }
        });
    }
}
