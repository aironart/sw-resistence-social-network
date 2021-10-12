package br.com.airon.challenges.swresistencesocialnetwork.request;

import br.com.airon.challenges.swresistencesocialnetwork.enums.GeneroEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Body para criação de um rebelde")
public class CriaRebeldeRequest {

    @NotNull
    @ApiModelProperty(value = "Nome do rebelde", required = true)
    private String nome;

    @Min(1)
    @ApiModelProperty(value = "Idade do Rebelde", required = true)
    private int idade;

    @NotNull
    @ApiModelProperty(value = "Genero do Rebelde", required = true)
    private GeneroEnum genero;

    @NotNull
    @ApiModelProperty(value = "Localização do Rebelde", required = true)
    private LocalizacaoBaseRequest localizacao;

    @NotNull(message = "Ao menos um item é necessário")
    @ApiModelProperty(value = "Itens do inventário do rebelde (necessário ao menos um item)", required = true)
    private List<ItemInventarioBaseRequest> itensInventario;
}
