package br.com.airon.challenges.swresistencesocialnetwork.repository;

import br.com.airon.challenges.swresistencesocialnetwork.domain.Item;
import br.com.airon.challenges.swresistencesocialnetwork.domain.ItemInventario;
import br.com.airon.challenges.swresistencesocialnetwork.domain.ItemInventarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório da tabela ItemInventario
 */
@Repository
public interface ItemInventarioRepository extends JpaRepository<ItemInventario, ItemInventarioId> {

}
