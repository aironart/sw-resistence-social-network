package br.com.airon.challenges.swresistencesocialnetwork.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Localizacao implements Serializable {

    @Id
    @JsonIgnore
    private Long rebeldeId;

    private String nomeGalaxia;

    private int latitude;

    private int longitude;
}
