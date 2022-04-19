package com.dbccompany.kafkareceita.dataTransfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BuyDTO {
    private String userId;
    private String productId;
    private Integer qntItens;
    private String cardNumber;
    private String expirationDate;
    private String cvv;
}
