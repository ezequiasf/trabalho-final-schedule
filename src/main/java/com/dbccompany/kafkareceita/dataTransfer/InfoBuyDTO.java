package com.dbccompany.kafkareceita.dataTransfer;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoBuyDTO {
    private String username;
    private String email;
    private String productName;
    private double price;
    private Integer qntItens;
    private String cardNumber;
    private String expirationDate;
    private String cvv;
}
