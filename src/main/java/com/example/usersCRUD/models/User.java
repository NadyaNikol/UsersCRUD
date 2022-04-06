package com.example.usersCRUD.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
@NoArgsConstructor
public class User {
    @Id
    private long id;
    private String name;
    private String email;

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";
}