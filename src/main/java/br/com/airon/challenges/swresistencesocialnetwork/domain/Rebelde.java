package br.com.airon.challenges.swresistencesocialnetwork.domain;

import br.com.airon.challenges.swresistencesocialnetwork.enums.GeneroEnum;
import lombok.*;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.InheritanceType.JOINED;

/**
 * Classe que representa a entidade Rebelde
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"id", "nome", "traidor"})
@Entity
@Builder
@EqualsAndHashCode(of={"id"})
//@IdClass(IdPk.class)
public class Rebelde implements Serializable {

    /**
     * Identificador da tabela
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Nome do rebelde
     */
    @Column(name="nome", length = 100, nullable = false)
    private String nome;

    /**
     * Idade do Rebelde
     */
    private int idade;

    /**
     * Gênero do Rebelde
     */
    private GeneroEnum genero;

    /**
     * Indicador se o Rebelde é ou não um traidor
     */
    private boolean traidor;

    /**
     * Indica a localização do Rebelde
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Localizacao localizacao;

    /**
     * Itens que o Rebelde possui no inventário
     */
    @OneToMany(targetEntity = ItemInventario.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "rebelde_id", referencedColumnName = "id")
//    @JoinFormula("select ii from ItemInventario ii where ii.id.rebelde.id = id")
    private List<ItemInventario> itens = new ArrayList();


}
