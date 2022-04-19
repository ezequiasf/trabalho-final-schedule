package com.dbccompany.trabalhofinalmod5.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserUpdateDTO {
    @NotBlank(message = "A senha deve ser informada.")
    @Size(min = 5, max = 10, message = "A senha deve ter entre 5 e 10 caracteres.")
    private String password;

    @Email
    @NotBlank(message = "O email deve ser informado.")
    private String email;

    private Integer age;

    private Boolean isactive;
}
