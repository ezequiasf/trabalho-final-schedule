package com.dbccompany.kafkareceita.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "product")
public class ProductEntity {
    @Id
    private String objectId;
    private String productName;
    private Double price;
    private String description;
    private Integer stock;
}
