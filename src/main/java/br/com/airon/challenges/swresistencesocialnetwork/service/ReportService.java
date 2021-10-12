package br.com.airon.challenges.swresistencesocialnetwork.service;

import br.com.airon.challenges.swresistencesocialnetwork.report.MediaTipoRecursoPorRebeldeReport;
import br.com.airon.challenges.swresistencesocialnetwork.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public double retornaPorcentagemTraidores(){
        return reportRepository.retornaPorcentagemTraidores();
    }

    public double retornaPorcentagemNaoTraidores(){
        return reportRepository.retornaPorcentagemNaoTraidores();
    }

    public List<MediaTipoRecursoPorRebeldeReport> retornaListaMediaTipoRecursoPorRebelde() {
        return reportRepository.retornaListaMediaTipoRecursoPorRebelde();
    }

    public long retornaPontosPerdidosDevidoTraidores() {
        return reportRepository.retornaPontosPerdidosDevidoTraidores();
    }
}
