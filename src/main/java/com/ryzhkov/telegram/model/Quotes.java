package com.ryzhkov.telegram.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quotes {
    private Integer id;
    private String language_code;
    private String url;
    private String content;
    private Originator originator;
    private List<String> tags;
}
