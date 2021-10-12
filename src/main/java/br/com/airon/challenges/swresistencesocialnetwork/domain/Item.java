package br.com.airon.challenges.swresistencesocialnetwork.domain;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private int quantidadePontos;

}
