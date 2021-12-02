package com.ryzhkov.telegram.model;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private String name;
    private String age;
    private String town;
    private List<Quotes> quotes;
}
