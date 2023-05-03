package com.example.gradeCourse.entity;

import lombok.Data;

@Data
public class Role {

    private String id;
    private String name;

    public Role(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
