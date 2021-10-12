package br.com.airon.challenges.swresistencesocialnetwork.validator;

import br.com.airon.challenges.swresistencesocialnetwork.exceptions.BusinessError;
import br.com.airon.challenges.swresistencesocialnetwork.exceptions.BusinessException;
import br.com.airon.challenges.swresistencesocialnetwork.request.LocalizacaoBaseRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class LocalizacaoBaseValidator implements BusinessValidator<LocalizacaoBaseRequest>{

    @Override
    public void validate(LocalizacaoBaseRequest localizacao) throws BusinessException {

        var listaErros = new ArrayList<BusinessError>();

        if (localizacao == null) {
            listaErros.add(
                    BusinessError.builder()
                            .message("Favor preencher a localização")
                            .build());
        } else {
            if (StringUtils.isBlank(localizacao.getNomeGalaxia())) {
                listaErros.add(
                        BusinessError.builder()
                                .message("Favor preencher o nome da Galáxia na localização")
                                .build());
            }
            if (localizacao.getLongitude() < 0) {
                listaErros.add(
                        BusinessError.builder()
                                .message("Favor preencher a longitude da localização")
                                .build());
            }
            if (localizacao.getLatitude() < 0) {
                listaErros.add(
                        BusinessError.builder()
                                .message("Favor preencher a latitude da localização")
                                .build());
            }
        }

        if (listaErros.size() > 0) {
            throw new BusinessException(listaErros);
        }
    }
}
