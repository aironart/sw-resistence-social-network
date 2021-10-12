package br.com.airon.challenges.swresistencesocialnetwork.validator;

import br.com.airon.challenges.swresistencesocialnetwork.exceptions.BusinessError;
import br.com.airon.challenges.swresistencesocialnetwork.exceptions.BusinessException;
import br.com.airon.challenges.swresistencesocialnetwork.repository.ItemRepository;
import br.com.airon.challenges.swresistencesocialnetwork.request.ItemInventarioBaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ItemInventarioBaseValidator implements BusinessValidator<ItemInventarioBaseRequest>{

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void validate(ItemInventarioBaseRequest requestBody) throws BusinessException {
        var listaErros = new ArrayList<BusinessError>();

        if (requestBody == null) {
            listaErros.add(
                    BusinessError.builder()
                            .message("Favor preencher o item de inventário")
                            .build());
        } else {
            if (requestBody.getIdItem() == null || requestBody.getIdItem().longValue() <= 0) {
                listaErros.add(
                        BusinessError.builder()
                                .message("Favor preencher o código do item")
                                .build());
            } else {
                try {
                    var item = itemRepository.findById(requestBody.getIdItem());
                    if(!item.isPresent()){
                        listaErros.add(
                                BusinessError.builder()
                                        .message("Não foi encontrado item para o identificador " + requestBody.getIdItem())
                                        .build());
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    listaErros.add(
                            BusinessError.builder()
                                    .message("Não foi encontrado item para o identificador " + requestBody.getIdItem())
                                    .build());
                }
            }
            if (requestBody.getQuantidade() <= 0) {
                listaErros.add(
                        BusinessError.builder()
                                .message("Favor preencher a quantidade")
                                .build());
            }
        }

        if (listaErros.size() > 0) {
            throw new BusinessException(listaErros);
        }

    }
}
