package ru.spartars.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auto {

    private String id;
    private String name;
    private String description;
    private String image;

    public Auto(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
