package com.dbccompany.kafkareceita.dataTransfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDTO {
    @NotBlank(message = "O nome do produto deve ser informado.")
    @Size(min = 2, max = 40, message = "O nome do produto deve estar entre 2 e 15 caracteres.")
    private String productName;

    @Digits(integer = 6, fraction = 2)
    @DecimalMin(value = "0.0", message = "Não é permitido números negativos.")
    private Double price;

    @NotBlank(message = "A descrição do produto deve ser informado.")
    @Size(min = 2, max = 40, message = "O nome do produto deve estar entre 2 e 15 caracteres.")
    private String description;

    @NotNull(message = "O estoque deve ser informado.")
    private Integer stock;
}

