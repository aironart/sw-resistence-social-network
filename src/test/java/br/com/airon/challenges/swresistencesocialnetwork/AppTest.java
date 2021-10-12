package br.com.airon.challenges.swresistencesocialnetwork;

import br.com.airon.challenges.swresistencesocialnetwork.domain.Localizacao;
import br.com.airon.challenges.swresistencesocialnetwork.domain.Rebelde;
import br.com.airon.challenges.swresistencesocialnetwork.exceptions.BusinessException;
import br.com.airon.challenges.swresistencesocialnetwork.request.CriaRebeldeRequest;
import br.com.airon.challenges.swresistencesocialnetwork.request.ItemInventarioBaseRequest;
import br.com.airon.challenges.swresistencesocialnetwork.request.LocalizacaoBaseRequest;
import br.com.airon.challenges.swresistencesocialnetwork.request.NegociacaoRequest;
import br.com.airon.challenges.swresistencesocialnetwork.service.NegociacaoService;
import br.com.airon.challenges.swresistencesocialnetwork.service.RebeldeService;
import br.com.airon.challenges.swresistencesocialnetwork.service.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class AppTest {

    @Autowired
    private RebeldeService rebeldeService;

    @Autowired
    private NegociacaoService negociacaoService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ObjectMapper mapper;

    public void criaRebelde(int rebeldeNum){
        String json = "{\n" +
                "  \"genero\": \"FEMININO\",\n" +
                "  \"idade\": 30,\n" +
                "  \"itensInventario\": [\n" +
                "    {\n" +
                "      \"idItem\": 1,\n" +
                "      \"quantidade\": 3\n" +
                "    },\n" +
                "    {\n" +
                "      \"idItem\": 4,\n" +
                "      \"quantidade\": 15\n" +
                "    },\n" +
                "    {\n" +
                "      \"idItem\": 3,\n" +
                "      \"quantidade\": 6\n" +
                "    }\n" +
                "  ],\n" +
                "  \"localizacao\": {\n" +
                "    \"latitude\": 150,\n" +
                "    \"longitude\": 150,\n" +
                "    \"nomeGalaxia\": \"Terra\"\n" +
                "  },\n" +
                "  \"nome\": \"Rebelde " + rebeldeNum + "\"\n" +
                "}";

        try {
            CriaRebeldeRequest criaRebeldeRequest = mapper.readValue(json, CriaRebeldeRequest.class);
            Rebelde rebelde = rebeldeService.criaRebelde(criaRebeldeRequest);
            assertTrue(criaRebeldeRequest.getNome().equalsIgnoreCase(rebelde.getNome()));
        } catch (Exception e){
            fail();
        }
    }

    @Test
    public void testApp() {
        t1_criaRebeldeTeste();
        t2_findById();
        t3_findAll();
        t4_marcaTraidor();
        t5_atualizaLocalizacaoRebelde();
        t6_negociacaoEntreRebeldes();
        t7_retornaPorcentagemTraidores();
        t8_retornaPorcentagemNaoTraidores();
        t9_retornaPontosPerdidosDevidoTraidores();
        t10_retornaListaMediaTipoRecursoPorRebelde();
    }

    public void t1_criaRebeldeTeste(){
        criaRebelde(1);
        criaRebelde(2);
        criaRebelde(3);
        criaRebelde(4);
    }

    public void t2_findById(){
        Long id = 1l;
        Rebelde rebelde = rebeldeService.findById(id);
        assertTrue(rebelde.getNome().equalsIgnoreCase("Rebelde 1"));
    }

    public void t3_findAll(){
        var rebeldes = rebeldeService.findAll();
        assertTrue(rebeldes.size() > 0);
    }

    public void t4_marcaTraidor(){

        Long id = 1l;
        Rebelde rebelde = rebeldeService.findById(id);

        id = 2l;
        Rebelde traidor = rebeldeService.findById(id);

        rebeldeService.marcaRebeldeTraidor(rebelde, traidor);

        id = 3l;
        rebelde = rebeldeService.findById(id);

        rebeldeService.marcaRebeldeTraidor(rebelde, traidor);

        id = 4l;
        rebelde = rebeldeService.findById(id);

        rebeldeService.marcaRebeldeTraidor(rebelde, traidor);

        id = 2l;
        traidor = rebeldeService.findById(id);

        assertTrue(traidor.isTraidor());
    }

    public void t5_atualizaLocalizacaoRebelde(){

        Long id = 1l;
        Rebelde rebelde = rebeldeService.findById(id);

        Localizacao localizacao = rebelde.getLocalizacao();
        var localizacaoBaseRequest =
                LocalizacaoBaseRequest.builder()
                        .latitude(localizacao.getLatitude())
                        .longitude(localizacao.getLongitude())
                        .nomeGalaxia("Marte")
                        .build();

        rebeldeService.atualizaLocalizacaoRebelde(rebelde, localizacaoBaseRequest);

        rebelde = rebeldeService.findById(id);
        assertTrue(rebelde.getLocalizacao().getNomeGalaxia().equalsIgnoreCase("Marte"));
    }

    public void t6_negociacaoEntreRebeldes() {

        var idRebelde1 = 1l;
        var itensRebelde1 = new ArrayList<ItemInventarioBaseRequest>();
        itensRebelde1.add(new ItemInventarioBaseRequest(1l,1));

        var idRebelde2 = 2l;
        var itensRebelde2 = new ArrayList<ItemInventarioBaseRequest>();
        itensRebelde2.add(new ItemInventarioBaseRequest(4l,4));

        try{
            negociacaoService.realizaNegociacao(
                    NegociacaoRequest.builder()
                            .idRebelde1(idRebelde1)
                            .idRebelde2(idRebelde2)
                            .itensRebelde1(itensRebelde1)
                            .itensRebelde2(itensRebelde2)
                            .build()
            );
            fail();
        } catch (BusinessException e){
            assertTrue(e != null && e.getListaErros().size() == 1);
        }

        idRebelde2 = 3l;

        try{
            negociacaoService.realizaNegociacao(
                    NegociacaoRequest.builder()
                            .idRebelde1(idRebelde1)
                            .idRebelde2(idRebelde2)
                            .itensRebelde1(itensRebelde1)
                            .itensRebelde2(itensRebelde2)
                            .build()
            );
        } catch (BusinessException e){
            e.printStackTrace();
            fail();
        }
    }

    public void t7_retornaPorcentagemTraidores(){
        val porcentagemTraidores = reportService.retornaPorcentagemTraidores();
        assertTrue(25d == porcentagemTraidores);
    }

    public void t8_retornaPorcentagemNaoTraidores(){
        val porcentagemNaoTraidores = reportService.retornaPorcentagemNaoTraidores();
        assertTrue(75d == porcentagemNaoTraidores);
    }

    public void t9_retornaPontosPerdidosDevidoTraidores(){
        val pontosPerdidosDevidoTraidores = reportService.retornaPontosPerdidosDevidoTraidores();
        assertTrue(39l == pontosPerdidosDevidoTraidores);
    }

    public void t10_retornaListaMediaTipoRecursoPorRebelde(){
        val retornaListaMediaTipoRecursoPorRebelde = reportService.retornaListaMediaTipoRecursoPorRebelde();
        assertTrue(3 == retornaListaMediaTipoRecursoPorRebelde.size());

        val arma = retornaListaMediaTipoRecursoPorRebelde.stream().filter(el -> el.getIdItem().longValue() == 1l).findFirst().get();
        assertTrue(3 == arma.getQuantidade());

        val agua = retornaListaMediaTipoRecursoPorRebelde.stream().filter(el -> el.getIdItem().longValue() == 3l).findFirst().get();
        assertTrue(6 == agua.getQuantidade());

        val comida = retornaListaMediaTipoRecursoPorRebelde.stream().filter(el -> el.getIdItem().longValue() == 4l).findFirst().get();
        assertTrue(15 == comida.getQuantidade());
    }

}
