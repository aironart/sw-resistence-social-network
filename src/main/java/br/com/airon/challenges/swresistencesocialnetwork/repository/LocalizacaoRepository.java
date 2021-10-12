package br.com.airon.challenges.swresistencesocialnetwork.repository;

import br.com.airon.challenges.swresistencesocialnetwork.domain.Localizacao;
import br.com.airon.challenges.swresistencesocialnetwork.domain.Rebelde;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalizacaoRepository extends JpaRepository<Localizacao, Rebelde> {
}
