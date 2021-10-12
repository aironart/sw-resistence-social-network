package br.com.airon.challenges.swresistencesocialnetwork.factory;

import br.com.airon.challenges.swresistencesocialnetwork.domain.ItemInventario;
import br.com.airon.challenges.swresistencesocialnetwork.domain.Rebelde;
import br.com.airon.challenges.swresistencesocialnetwork.request.CriaRebeldeRequest;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RebeldeFactory {

    public Rebelde create(CriaRebeldeRequest criaRebeldeRequest){
        var rebelde = Rebelde.builder()
                .genero(criaRebeldeRequest.getGenero())
                .idade(criaRebeldeRequest.getIdade())
                .nome(criaRebeldeRequest.getNome())
                .traidor(false)
                .build();

//        rebelde.setLocalizacao(localizacaoFactory.create(criaRebeldeRequest.getLocalizacao(), rebelde));

        return rebelde;
    }

}
