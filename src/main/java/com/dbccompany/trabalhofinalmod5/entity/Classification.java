package com.dbccompany.trabalhofinalmod5.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Classification {
    private String authorClass;
    private Double rating;
    private String coment;
}
