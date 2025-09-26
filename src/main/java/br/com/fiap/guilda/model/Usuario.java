package br.com.fiap.guilda.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer avaliacao;
    private Integer moedas;

    // adicione este campo na classe Usuario
    @Column(name = "external_id", unique = true)
    private String externalId;

    @OneToOne
    @JoinColumn(name = "missao_ativa_id")
    private Missao missaoAtiva;

    @OneToOne
    @JoinColumn(name = "missao_criada_id")
    private Missao missaoCriada;
}
