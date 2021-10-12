package br.com.airon.challenges.swresistencesocialnetwork.repository;

import br.com.airon.challenges.swresistencesocialnetwork.report.MediaTipoRecursoPorRebeldeReport;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ReportRepository {

    @Autowired
    private EntityManager em;

    private static final String SELECT_COUNT_REBELDE = " select count(r) from Rebelde r ";

    public long retornaQuantidadeRebeldes() {
        StringBuilder sql = new StringBuilder();
        sql.append(SELECT_COUNT_REBELDE);

        return (long) em.createQuery(sql.toString()).getSingleResult();
    }

    public long retornaQuantidadeRebeldesNaoTraidores() {
        StringBuilder sql = new StringBuilder();
        sql.append(SELECT_COUNT_REBELDE);
        sql.append(" where r.traidor = false");

        return (long) em.createQuery(sql.toString()).getSingleResult();
    }

    public double retornaPorcentagemTraidores() {
        var totalRebeldes = this.retornaQuantidadeRebeldes();

        val sql = new StringBuilder(SELECT_COUNT_REBELDE);
        sql.append(" where r.traidor = true ");

        val totalTraidores = (long) em.createQuery(sql.toString()).getSingleResult();

        val quantidadeTraidores = totalTraidores * 100 / totalRebeldes;
        return quantidadeTraidores;
    }

    public double retornaPorcentagemNaoTraidores() {
        var totalRebeldes = this.retornaQuantidadeRebeldes();

        val sql = new StringBuilder(SELECT_COUNT_REBELDE);
        sql.append(" where r.traidor = false ");

        val totalNaoTraidores = (long) em.createQuery(sql.toString()).getSingleResult();

        val quantidadeNaoTraidores = totalNaoTraidores * 100 / totalRebeldes;
        return quantidadeNaoTraidores;
    }

    public List<MediaTipoRecursoPorRebeldeReport> retornaListaMediaTipoRecursoPorRebelde() {
        val totalRebeldes = this.retornaQuantidadeRebeldesNaoTraidores();

        val sql = new StringBuilder();
        sql.append(" select ");
        sql.append(" new br.com.airon.challenges.swresistencesocialnetwork.report.MediaTipoRecursoPorRebeldeReport");
        sql.append("(it.id as idItem, it.nome as denItem, sum(ii.quantidade) / " + totalRebeldes + " as quantidade) ");
        sql.append(" from ItemInventario ii ");
        sql.append(" inner join ii.id.item it ");
        sql.append(" inner join ii.id.rebelde re ");
        sql.append(" where re.traidor = false ");
        sql.append(" group by it.id, it.nome ");
        sql.append(" order by it.id ");

        var retorno = em.createQuery(sql.toString(), MediaTipoRecursoPorRebeldeReport.class).getResultList();
        return retorno;

    }

    public long retornaPontosPerdidosDevidoTraidores() {
        val totalRebeldes = this.retornaQuantidadeRebeldes();

        val sql = new StringBuilder();
        sql.append(" select sum(ii.quantidade * it.quantidadePontos)  ");
        sql.append(" from ItemInventario ii ");
        sql.append(" inner join ii.id.item it ");
        sql.append(" inner join ii.id.rebelde re ");
        sql.append(" where re.traidor = true ");

        var retorno = (long) em.createQuery(sql.toString()).getSingleResult();
        return retorno;
    }


}
