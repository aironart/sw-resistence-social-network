package br.com.airon.challenges.swresistencesocialnetwork.repository;

import br.com.airon.challenges.swresistencesocialnetwork.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório da tabela Item
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
