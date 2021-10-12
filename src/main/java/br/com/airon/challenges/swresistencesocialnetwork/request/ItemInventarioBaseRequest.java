package br.com.airon.challenges.swresistencesocialnetwork.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel(description = "Representa um item de inventário do rebelde")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemInventarioBaseRequest {

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "Código identificador do Item", required = true)
    private Long idItem;

    @Min(1)
    @ApiModelProperty(value = "Quantidade de itens", required = true)
    private int quantidade;

}
