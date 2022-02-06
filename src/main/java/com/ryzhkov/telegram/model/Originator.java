package com.ryzhkov.telegram.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "originators")
@Entity
@ToString
public class Originator {
    @Id
    private Long id;
    private String name;
}
