package br.com.airon.challenges.swresistencesocialnetwork.repository;

import br.com.airon.challenges.swresistencesocialnetwork.domain.Rebelde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório da entidade Rebelde
 */
@Repository
public interface RebeldeRepository extends JpaRepository<Rebelde, Long> {
}
