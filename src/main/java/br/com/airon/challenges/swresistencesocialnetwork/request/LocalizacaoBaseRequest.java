package br.com.airon.challenges.swresistencesocialnetwork.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel("Localizacao de um rebelde")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalizacaoBaseRequest {

    @NotNull
    @ApiModelProperty(value = "Nome da Galaxia da Localização do Rebelde", required = true)
    private String nomeGalaxia;

    @NotNull
    @Min(0)
    @ApiModelProperty(value = "Latitude da Localização do Rebelde", required = true)
    private int latitude;

    @NotNull
    @Min(0)
    @ApiModelProperty(value = "Longitude da Localização do Rebelde", required = true)
    private int longitude;

}
