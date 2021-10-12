package br.com.airon.challenges.swresistencesocialnetwork.controller;

import br.com.airon.challenges.swresistencesocialnetwork.report.MediaTipoRecursoPorRebeldeReport;
import br.com.airon.challenges.swresistencesocialnetwork.service.ReportService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @ApiOperation("Retorna Porcentagem de Traidores")
    @GetMapping(value = "/retorna_porcentagem_traidores", produces = "text/plain")
    public double retornaPorcentagemTraidores(){
        return reportService.retornaPorcentagemTraidores();
    }

    @ApiOperation("Retorna Porcentagem de Não Traidores")
    @GetMapping(value = "/retorna_porcentagem_nao_traidores", produces = "text/plain")
    public double retornaPorcentagemNaoTraidores(){
        return reportService.retornaPorcentagemNaoTraidores();
    }

    @ApiOperation("Retorna Porcentagem de Não Traidores")
    @GetMapping(value = "/retorna_lista_media_tipo_recurso_por_rebelde", produces = "application/json")
    public List<MediaTipoRecursoPorRebeldeReport> retornaListaMediaTipoRecursoPorRebelde() {
        return reportService.retornaListaMediaTipoRecursoPorRebelde();
    }

    @ApiOperation("Retorna pontos perdidos devido a traidores")
    @GetMapping(value = "/retorna_pontos_perdidos_devido_traidores", produces = "text/plain")
    public long retornaPontosPerdidosDevidoTraidores(){
        return reportService.retornaPontosPerdidosDevidoTraidores();
    }

}
