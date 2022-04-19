package com.dbccompany.kafkareceita.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class UserEntity {
    @Id
    private String objectId;
    private String name;
    private String password;
    private String email;
    private boolean active;
}
