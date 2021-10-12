package br.com.airon.challenges.swresistencesocialnetwork.domain;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class MarcacaoTraidorId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Rebelde autor;

    @ManyToOne
    @JoinColumn(name = "traidor_id")
    private Rebelde traidor;

}
