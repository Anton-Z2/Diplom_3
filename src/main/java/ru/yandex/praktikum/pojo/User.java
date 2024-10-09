package ru.yandex.praktikum.pojo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
    private String email;
    private String password;
    private String name;
}
