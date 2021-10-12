package br.com.airon.challenges.swresistencesocialnetwork.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.sql.Date;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of={"id"})
@NoArgsConstructor
public class MarcacaoTraidor {

    public MarcacaoTraidor(MarcacaoTraidorId id){
        this.id = id;
    }

    @EmbeddedId
    private MarcacaoTraidorId id;

    private Date dataMarcacao = new Date(new java.util.Date().getTime());

}
