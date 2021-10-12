package br.com.airon.challenges.swresistencesocialnetwork.request;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ApiModel(description = "Representa uma negociação entre rebeldes")
/**
 * Representa uma negociação entre rebeldes
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NegociacaoRequest {

    @NotNull
    @Min(1)
    private Long idRebelde1;

    @NotNull
    private List<ItemInventarioBaseRequest> itensRebelde1;

    @NotNull
    @Min(1)
    private Long idRebelde2;

    @NotNull
    private List<ItemInventarioBaseRequest> itensRebelde2;

}

