package br.com.airon.challenges.swresistencesocialnetwork.validator;

import br.com.airon.challenges.swresistencesocialnetwork.domain.Item;
import br.com.airon.challenges.swresistencesocialnetwork.domain.ItemInventario;
import br.com.airon.challenges.swresistencesocialnetwork.domain.Rebelde;
import br.com.airon.challenges.swresistencesocialnetwork.exceptions.BusinessError;
import br.com.airon.challenges.swresistencesocialnetwork.exceptions.BusinessException;
import br.com.airon.challenges.swresistencesocialnetwork.repository.ItemRepository;
import br.com.airon.challenges.swresistencesocialnetwork.repository.RebeldeRepository;
import br.com.airon.challenges.swresistencesocialnetwork.request.ItemInventarioBaseRequest;
import br.com.airon.challenges.swresistencesocialnetwork.request.NegociacaoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe responsável para fazer a validação das negociações entre os rebeldes
 */
@Component
public class NegociacaoValidator implements BusinessValidator<NegociacaoRequest> {

    @Autowired
    private RebeldeRepository rebeldeRepository;

    @Autowired
    private ItemInventarioBaseValidator itemInventarioBaseValidator;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void validate(NegociacaoRequest requestBody) throws BusinessException {

        var itens = new HashMap<Long, Item>();
        var rebeldes = new HashMap<Long, Rebelde>();

        var listaErros = new ArrayList<BusinessError>();

        validaRebelde(requestBody.getIdRebelde1(), rebeldes, listaErros, 1);

        if (rebeldes.containsKey(requestBody.getIdRebelde2())) {
            listaErros.add(
                    new BusinessError("Não é possível fazer uma negociação para o mesmo rebelde"));
            throw new BusinessException(listaErros);
        }

        validaRebelde(requestBody.getIdRebelde2(), rebeldes, listaErros, 2);

        validaQuantidadeItens(listaErros, requestBody.getItensRebelde1(), 1);
        validaQuantidadeItens(listaErros, requestBody.getItensRebelde2(), 2);
        disparaExcecaoSeContemErros(listaErros);

        var itensNegociacao = requestBody.getItensRebelde1();
        var numRebelde = 1;
        validaItens(itens, listaErros, itensNegociacao, numRebelde);

        itensNegociacao = requestBody.getItensRebelde2();
        numRebelde = 2;
        validaItens(itens, listaErros, itensNegociacao, numRebelde);

        disparaExcecaoSeContemErros(listaErros);

        ajustaItensNegociacao(requestBody.getItensRebelde1());
        ajustaItensNegociacao(requestBody.getItensRebelde2());

        numRebelde = 1;
        validaItensRebeldeContraNegociacao(
                requestBody.getItensRebelde1(),
                rebeldes.get(requestBody.getIdRebelde1()).getItens(),
                listaErros,
                numRebelde
        );

        numRebelde = 2;
        validaItensRebeldeContraNegociacao(
                requestBody.getItensRebelde2(),
                rebeldes.get(requestBody.getIdRebelde2()).getItens(),
                listaErros,
                numRebelde
        );

        disparaExcecaoSeContemErros(listaErros);

        var somatoriaPontosRebelde1 = calculaSomatoriaPontos(requestBody.getItensRebelde1(), itens);
        var somatoriaPontosRebelde2 = calculaSomatoriaPontos(requestBody.getItensRebelde2(), itens);

        if(somatoriaPontosRebelde1 != somatoriaPontosRebelde2){
            listaErros.add(new BusinessError("Somatória de pontos da negociação precisam ser iguais"));
        }

        disparaExcecaoSeContemErros(listaErros);
    }

    private int calculaSomatoriaPontos(List<ItemInventarioBaseRequest> itensNegociacao, Map<Long, Item> itens) {
        return itensNegociacao.stream().map((item) -> {
            var it = itens.get(item.getIdItem());
            return it.getQuantidadePontos() * item.getQuantidade();
        }).collect(Collectors.summingInt(Integer::intValue));
    }

    private void ajustaItensNegociacao(List<ItemInventarioBaseRequest> itensNegociacao) {
        var itensNegociacaoRemover = new ArrayList<ItemInventarioBaseRequest>();

        itensNegociacao.sort(Comparator.comparing(ItemInventarioBaseRequest::getIdItem));
        Long lastId = Long.MIN_VALUE;

        for (int i = 0; i < itensNegociacao.size(); i++) {
            var itemNegociacao = itensNegociacao.get(i);
            if (lastId.equals(itemNegociacao.getIdItem())) {
                var itemNegociacaoAntigo = itensNegociacao.get(i - 1);
                itemNegociacaoAntigo.setQuantidade(itemNegociacaoAntigo.getQuantidade() + itemNegociacao.getQuantidade());
                itensNegociacaoRemover.add(itemNegociacao);
            } else {
                lastId = itemNegociacao.getIdItem();
            }
        }

        itensNegociacaoRemover.forEach(item -> itensNegociacao.remove(item));
    }

    private void validaItensRebeldeContraNegociacao(List<ItemInventarioBaseRequest> itensNegociacao,
                                                    List<ItemInventario> itensRebelde,
                                                    ArrayList<BusinessError> listaErros,
                                                    int numRebelde) {
        itensNegociacao.forEach(itemNegociacao -> {
            Optional<ItemInventario> itemInventarioOptional = itensRebelde.stream().filter(itemRebelde ->
                    itemNegociacao.getIdItem().equals(itemRebelde.getId().getItem().getId())
            ).findFirst();
            if (!itemInventarioOptional.isPresent()) {
                listaErros.add(new BusinessError("Rebelde " + numRebelde
                        + " não possui o item " + itemNegociacao.getIdItem()
                        + " para negociação."));
            } else {
                ItemInventario itemInventario = itemInventarioOptional.get();
                if (itemNegociacao.getQuantidade() > itemInventario.getQuantidade()) {
                    listaErros.add(new BusinessError("Rebelde " + numRebelde
                            + " não possui a quantidade suficiente do item " + itemNegociacao.getIdItem()
                            + " para negociação."));
                }
            }

        });
    }

    private void validaItens(HashMap<Long, Item> itens, ArrayList<BusinessError> listaErros, List<ItemInventarioBaseRequest> itensNegociacao, int numRebelde) {
        itensNegociacao.forEach(item -> {
            if (!itens.containsKey(item.getIdItem())) {
                var itemValidado = validaItemExiste(listaErros, item.getIdItem(), "Item do rebelde " + numRebelde + " não existe -> " + item.getIdItem());
                if (itemValidado != null) {
                    itens.put(item.getIdItem(), itemValidado);
                }
            }
        });
    }

    private void disparaExcecaoSeContemErros(List<BusinessError> listaErros) {
        if (listaErros.size() > 0) {
            throw new BusinessException(listaErros);
        }
    }

    private void validaQuantidadeItens(List<BusinessError> listaErros, List<ItemInventarioBaseRequest> itensInventario, int numRebelde) {
        if (itensInventario == null || itensInventario.size() == 0) {
            listaErros.add(new BusinessError("Lista de Itens a negociar do rebelde " + numRebelde + " não preenchida."));
        }
    }

    private void validaRebelde(Long idRebelde, HashMap<Long, Rebelde> rebeldes, ArrayList<BusinessError> listaErros, int numRebelde) {
        var rebelde = validaRebeldeExiste(listaErros, idRebelde, "Código do rebelde " + numRebelde + " não foi preenchido ou rebelde não encontrado");
        if (rebelde != null) {
            if(rebelde.isTraidor()){
                throw new BusinessException("Rebelde código " + numRebelde + " é um traidor, não pode fazer negociações");
            }
            rebeldes.put(rebelde.getId(), rebelde);
        } else {
            throw new BusinessException(listaErros);
        }
    }

    private Item validaItemExiste(ArrayList<BusinessError> listaErros, Long idItem, String message) {
        try {
            var item = itemRepository.findById(idItem);
            if(!item.isPresent()){
                listaErros.add(
                        new BusinessError(message));
            }else{
                return item.get();
            }
        } catch (Exception e) {
            listaErros.add(
                    new BusinessError(message));
        }
        return null;
    }

    private Rebelde validaRebeldeExiste(ArrayList<BusinessError> listaErros, Long idRebelde2, String s) {
        try {
            var rebeldeOptional = rebeldeRepository.findById(idRebelde2);
            if (!rebeldeOptional.isPresent()) {
                listaErros.add(
                        new BusinessError(s));
            } else {
                return rebeldeOptional.get();
            }
        } catch (Exception e) {
            listaErros.add(
                    new BusinessError(s));
        }
        return null;
    }
}
