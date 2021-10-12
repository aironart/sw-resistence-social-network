package br.com.airon.challenges.swresistencesocialnetwork.factory;

import br.com.airon.challenges.swresistencesocialnetwork.domain.Localizacao;
import br.com.airon.challenges.swresistencesocialnetwork.domain.Rebelde;
import br.com.airon.challenges.swresistencesocialnetwork.request.LocalizacaoBaseRequest;
import org.springframework.stereotype.Component;

@Component
public class LocalizacaoFactory {

    public Localizacao create(LocalizacaoBaseRequest localizacaoBaseRequest, Rebelde rebelde) {
        return Localizacao.builder()
                .latitude(localizacaoBaseRequest.getLatitude())
                .longitude(localizacaoBaseRequest.getLongitude())
                .nomeGalaxia(localizacaoBaseRequest.getNomeGalaxia())
                .rebeldeId(rebelde.getId())
                .build();
    }

}
