package br.com.airon.challenges.swresistencesocialnetwork.validator;

import br.com.airon.challenges.swresistencesocialnetwork.exceptions.BusinessError;
import br.com.airon.challenges.swresistencesocialnetwork.exceptions.BusinessException;
import br.com.airon.challenges.swresistencesocialnetwork.request.CriaRebeldeRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CriaRebeldeValidator implements BusinessValidator<CriaRebeldeRequest> {

    @Autowired
    private LocalizacaoBaseValidator localizacaoBaseValidator;

    @Autowired
    private ItemInventarioBaseValidator criaRebeldeItemValidator;

    @Override
    public void validate(CriaRebeldeRequest requestBody) throws BusinessException {
        var listaErros = new ArrayList<BusinessError>();

        if (requestBody == null) {
            listaErros.add(
                    BusinessError.builder()
                            .message("Request Body is null")
                            .build());
            throw new BusinessException(listaErros);
        }

        if (StringUtils.isBlank(requestBody.getNome())) {
            listaErros.add(
                    BusinessError.builder()
                            .message("Favor preencher o nome")
                            .build());
        }

        if (requestBody.getGenero() == null) {
            listaErros.add(
                    BusinessError.builder()
                            .message("Favor preencher o genero")
                            .build());
        }

        if (requestBody.getIdade() < 0) {
            listaErros.add(
                    BusinessError.builder()
                            .message("Favor a idade com um valor positivo")
                            .build());
        }

        if (requestBody.getItensInventario() == null
                || requestBody.getItensInventario().size() == 0) {
            listaErros.add(
                    BusinessError.builder()
                            .message("Necessário possuir ao menos um item no inventário")
                            .build());
        } else {
            requestBody.getItensInventario().forEach(el -> {
                try {
                    criaRebeldeItemValidator.validate(el);
                } catch (BusinessException e) {
                    listaErros.addAll(e.getListaErros());
                }
            });
        }

        try {
            localizacaoBaseValidator.validate(requestBody.getLocalizacao());
        } catch (BusinessException e) {
            listaErros.addAll(e.getListaErros());
        }

        if (listaErros.size() > 0) {
            throw new BusinessException(listaErros);
        }

    }
}
