package br.com.airon.challenges.swresistencesocialnetwork.domain;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemInventario {

    @EmbeddedId
    private ItemInventarioId id = new ItemInventarioId();

    private int quantidade;

}
