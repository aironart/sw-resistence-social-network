package br.com.airon.challenges.swresistencesocialnetwork.factory;

import br.com.airon.challenges.swresistencesocialnetwork.domain.ItemInventario;
import br.com.airon.challenges.swresistencesocialnetwork.domain.ItemInventarioId;
import br.com.airon.challenges.swresistencesocialnetwork.domain.Rebelde;
import br.com.airon.challenges.swresistencesocialnetwork.repository.ItemRepository;
import br.com.airon.challenges.swresistencesocialnetwork.request.ItemInventarioBaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemInventarioFactory {

    @Autowired
    private ItemRepository itemRepository;

    public ItemInventario create (ItemInventarioBaseRequest criaRebeldeItemRequest, Rebelde rebelde){
        var itemInventarioId = ItemInventarioId.builder()
                .item(itemRepository.getById(criaRebeldeItemRequest.getIdItem()))
                .rebelde(rebelde)
                .build();
        return ItemInventario.builder()
                .quantidade(criaRebeldeItemRequest.getQuantidade())
                .id(itemInventarioId)
                .build();
    }
}
