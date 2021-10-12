package br.com.airon.challenges.swresistencesocialnetwork.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Chave Primária da tabela de ItemInventario
 */
@Getter
@Setter
@Embeddable
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemInventarioId implements Serializable {

    /**
     * Identificador do rebelde
     */
    @ManyToOne
    @JoinColumn(name = "rebelde_id")
    @JsonIgnore
    private Rebelde rebelde;

    /**
     * Identificador do item
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

}
