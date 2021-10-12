package br.com.airon.challenges.swresistencesocialnetwork.service;

import br.com.airon.challenges.swresistencesocialnetwork.domain.Item;
import br.com.airon.challenges.swresistencesocialnetwork.domain.ItemInventario;
import br.com.airon.challenges.swresistencesocialnetwork.domain.ItemInventarioId;
import br.com.airon.challenges.swresistencesocialnetwork.domain.Rebelde;
import br.com.airon.challenges.swresistencesocialnetwork.exceptions.BusinessException;
import br.com.airon.challenges.swresistencesocialnetwork.repository.ItemInventarioRepository;
import br.com.airon.challenges.swresistencesocialnetwork.repository.RebeldeRepository;
import br.com.airon.challenges.swresistencesocialnetwork.request.ItemInventarioBaseRequest;
import br.com.airon.challenges.swresistencesocialnetwork.request.NegociacaoRequest;
import br.com.airon.challenges.swresistencesocialnetwork.validator.NegociacaoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NegociacaoService {

    @Autowired
    private RebeldeRepository rebeldeRepository;

    @Autowired
    private NegociacaoValidator negociacaoValidator;

    @Autowired
    private ItemInventarioRepository itemInventarioRepository;

    public void realizaNegociacao(NegociacaoRequest negociacaoRequest) throws BusinessException {
        negociacaoValidator.validate(negociacaoRequest);

        var rebelde1 = rebeldeRepository.findById(negociacaoRequest.getIdRebelde1()).get();
        var rebelde2 = rebeldeRepository.findById(negociacaoRequest.getIdRebelde2()).get();

        var itensInventariosDeletarBanco = new ArrayList<ItemInventario>();

        negociacaoRequest.getItensRebelde1().forEach(
                itemInventarioBaseRequest -> {
                    efetivaNegociacaoAdicionarItem(itemInventarioBaseRequest, rebelde2);
                    efetivaNegociacaoRemoveItem(itemInventarioBaseRequest, rebelde1, itensInventariosDeletarBanco);
                }
        );

        negociacaoRequest.getItensRebelde2().forEach(
                itemInventarioBaseRequest -> {
                    efetivaNegociacaoAdicionarItem(itemInventarioBaseRequest, rebelde1);
                    efetivaNegociacaoRemoveItem(itemInventarioBaseRequest, rebelde2, itensInventariosDeletarBanco);
                }
        );

        rebeldeRepository.save(rebelde1);
        rebeldeRepository.save(rebelde2);

        itemInventarioRepository.deleteAll(itensInventariosDeletarBanco);

    }

    private void efetivaNegociacaoAdicionarItem(ItemInventarioBaseRequest itemInventarioBaseRequest,
                                                Rebelde rebeldeDestino){
        var itensInventarioRebelde = rebeldeDestino.getItens();
        var opt = itensInventarioRebelde.stream().filter(
                it -> it.getId().getItem().getId().longValue() == itemInventarioBaseRequest.getIdItem().longValue()
        ).findFirst();

        if(opt.isPresent()){
            var itemInventario = opt.get();
            itemInventario.setQuantidade(itemInventario.getQuantidade() + itemInventarioBaseRequest.getQuantidade());
        } else {
            var itemInventario = new ItemInventario();
            itemInventario.setId(ItemInventarioId.builder()
                    .item(Item.builder()
                            .id(itemInventarioBaseRequest.getIdItem())
                            .build())
                    .rebelde(rebeldeDestino)
                    .build());
            itemInventario.setQuantidade(itemInventarioBaseRequest.getQuantidade());
            itensInventarioRebelde.add(itemInventario);
        }
    }

    private void efetivaNegociacaoRemoveItem(ItemInventarioBaseRequest itemInventarioBaseRequest,
                                                Rebelde rebeldeOrigem,
                                             List<ItemInventario> itensInventariosDeletarBanco){
        var itensInventarioRebelde = rebeldeOrigem.getItens();
        var opt = itensInventarioRebelde.stream().filter(
                it -> it.getId().getItem().getId().longValue() == itemInventarioBaseRequest.getIdItem().longValue()
        ).findFirst().get();

        if(itemInventarioBaseRequest.getQuantidade() == opt.getQuantidade()){
            itensInventarioRebelde.remove(opt);
            itensInventariosDeletarBanco.add(opt);
        } else {
            opt.setQuantidade(opt.getQuantidade() - itemInventarioBaseRequest.getQuantidade());
        }
    }




}
