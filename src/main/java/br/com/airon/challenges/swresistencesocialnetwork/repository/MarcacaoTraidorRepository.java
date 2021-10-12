package br.com.airon.challenges.swresistencesocialnetwork.repository;

import br.com.airon.challenges.swresistencesocialnetwork.domain.MarcacaoTraidor;
import br.com.airon.challenges.swresistencesocialnetwork.domain.MarcacaoTraidorId;
import br.com.airon.challenges.swresistencesocialnetwork.domain.Rebelde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repositório para a tabela MarcacaoTraidor
 */
public interface MarcacaoTraidorRepository extends JpaRepository<MarcacaoTraidor, MarcacaoTraidorId> {

    @Query("select m from MarcacaoTraidor m where m.id.traidor = ?1")
    public List<MarcacaoTraidor> findByIdTraidor(Rebelde traidor);

}
