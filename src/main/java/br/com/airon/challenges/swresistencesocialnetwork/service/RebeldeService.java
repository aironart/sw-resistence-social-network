package br.com.airon.challenges.swresistencesocialnetwork.service;

import br.com.airon.challenges.swresistencesocialnetwork.domain.ItemInventario;
import br.com.airon.challenges.swresistencesocialnetwork.domain.MarcacaoTraidor;
import br.com.airon.challenges.swresistencesocialnetwork.domain.MarcacaoTraidorId;
import br.com.airon.challenges.swresistencesocialnetwork.domain.Rebelde;
import br.com.airon.challenges.swresistencesocialnetwork.exceptions.BusinessError;
import br.com.airon.challenges.swresistencesocialnetwork.exceptions.BusinessException;
import br.com.airon.challenges.swresistencesocialnetwork.factory.ItemInventarioFactory;
import br.com.airon.challenges.swresistencesocialnetwork.factory.LocalizacaoFactory;
import br.com.airon.challenges.swresistencesocialnetwork.factory.RebeldeFactory;
import br.com.airon.challenges.swresistencesocialnetwork.repository.ItemInventarioRepository;
import br.com.airon.challenges.swresistencesocialnetwork.repository.LocalizacaoRepository;
import br.com.airon.challenges.swresistencesocialnetwork.repository.MarcacaoTraidorRepository;
import br.com.airon.challenges.swresistencesocialnetwork.repository.RebeldeRepository;
import br.com.airon.challenges.swresistencesocialnetwork.request.CriaRebeldeRequest;
import br.com.airon.challenges.swresistencesocialnetwork.request.LocalizacaoBaseRequest;
import br.com.airon.challenges.swresistencesocialnetwork.validator.CriaRebeldeValidator;
import br.com.airon.challenges.swresistencesocialnetwork.validator.LocalizacaoBaseValidator;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RebeldeService {

    @Autowired
    private RebeldeRepository rebeldeRepository;

    @Autowired
    private CriaRebeldeValidator criaRebeldeValidator;

    @Autowired
    private RebeldeFactory rebeldeFactory;

    @Autowired
    private MarcacaoTraidorRepository marcacaoTraidorRepository;

    @Autowired
    private LocalizacaoBaseValidator localizacaoBaseValidator;

    @Autowired
    private LocalizacaoFactory localizacaoFactory;

    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    @Autowired
    private ItemInventarioFactory itemInventarioFactory;

    @Autowired
    private ItemInventarioRepository itemInventarioRepository;

    /**
     * Cria o rebelde na base de dados
     *
     * @param criaRebeldeRequest
     * @throws BusinessException
     */
    public Rebelde criaRebelde(CriaRebeldeRequest criaRebeldeRequest) throws BusinessException {
        criaRebeldeValidator.validate(criaRebeldeRequest);

        var rebelde = rebeldeFactory.create(criaRebeldeRequest);
        rebelde = rebeldeRepository.save(rebelde);

        var localizacao = localizacaoFactory.create(criaRebeldeRequest.getLocalizacao(),rebelde);
        localizacaoRepository.save(localizacao);

        var listaItensInventario = criaRebeldeRequest.getItensInventario();
        val itens = new ArrayList<ItemInventario>();
        val rebeldeFinal = rebelde;
        listaItensInventario.forEach(itemInventario -> {
            var itemInventarioToSave = itemInventarioFactory.create(itemInventario, rebeldeFinal);
            itens.add(itemInventarioToSave);
            itemInventarioRepository.save(itemInventarioToSave);
        });

        rebeldeFinal.setItens(itens);

        return rebeldeFinal;
    }

    /**
     * Retorna todos os rebeldes
     * @return
     */
    public List<Rebelde> findAll() {
        return rebeldeRepository.findAll();
    }

    /**
     * Retorna um rebelde específico
     */
    public Rebelde findById(Long id){
        var opt = rebeldeRepository.findById(id);
        if(opt.isPresent()){
            return opt.get();
        } else {
            throw new EntityNotFoundException("Não encontrado rebelde para o ID " + id + ".");
        }
    }


    /**
     * Valida os parametros de entrada se estão preenchidos
     *
     * @param autor
     * @param traidor
     * @throws BusinessException
     */
    private void validaMarcacaoTraidor(Rebelde autor, Rebelde traidor) throws BusinessException {
        var listaErros = new ArrayList<BusinessError>();

        if (autor == null || autor.getId() == null || autor.getId().longValue() <= 0) {
            listaErros.add(new BusinessError("Autor da sinalização de rebelde traidor não preenchido"));
        }

        if (traidor == null || traidor.getId() == null || traidor.getId().longValue() <= 0) {
            listaErros.add(new BusinessError("Traidor da sinalização de rebelde traidor não preenchido"));
        }

        if (listaErros.size() > 0) {
            throw new BusinessException(listaErros);
        }
    }

    /**
     * Método para marcar um rebelde como traidor
     * <br>Este rebelde só será marcado como traidor se for sinalizado por 3 rebeldes
     *
     * @param autor
     * @param traidor
     * @throws BusinessException
     */
    public void marcaRebeldeTraidor(Rebelde autor, Rebelde traidor) throws BusinessException {
        this.validaMarcacaoTraidor(autor, traidor);

        var marcacoes = this.marcacaoTraidorRepository.findByIdTraidor(traidor);

        if (marcacoes.size() >= 2) {
            traidor.setTraidor(true);
            rebeldeRepository.save(traidor);
        }

        var marcacao = new MarcacaoTraidor(new MarcacaoTraidorId(autor, traidor));
        if (marcacoes.contains(marcacao)) {
            return;
        }

        marcacaoTraidorRepository.save(marcacao);
    }

    /**
     * Atualiza a localização do Rebelde
     *
     * @param rebelde
     * @param localizacaoBaseRequest
     * @throws BusinessException
     */
    public void atualizaLocalizacaoRebelde(Rebelde rebelde, LocalizacaoBaseRequest localizacaoBaseRequest) throws BusinessException {
        this.localizacaoBaseValidator.validate(localizacaoBaseRequest);

        if (rebelde == null) {
            throw new BusinessException("Rebelde não preenchido");
        }

        var localizacao = localizacaoFactory.create(localizacaoBaseRequest, rebelde);

        localizacaoRepository.save(localizacao);
    }
}
