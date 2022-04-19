package com.dbccompany.trabalhofinalmod5.entity;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private String objectId;
    private String username;
    private String password;
    private String email;
    private Integer age;
    private boolean isactive;
}
