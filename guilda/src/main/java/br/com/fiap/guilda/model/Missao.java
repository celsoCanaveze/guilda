package br.com.fiap.guilda.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "missoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Missao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private String prazo;
    private Integer recompensa;
}
