package br.com.fiap.guilda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.guilda.model.Missao;

public interface MissaoRepository extends JpaRepository<Missao, Long> {
}
