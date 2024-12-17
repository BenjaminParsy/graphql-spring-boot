package com.benjamin.parsy.lpgraphql.author;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity(name = "author")
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "firstname", nullable = false)
    private String firstname;

    @NotEmpty
    @Column(name = "lastname", nullable = false)
    private String lastname;

}
