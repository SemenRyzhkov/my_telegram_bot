package com.ryzhkov.telegram.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
@ToString
public class Quotes {
    @Id
    private Long id;
    private String language_code;
    private String content;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "originator_id", referencedColumnName = "id")
    private Originator originator;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
