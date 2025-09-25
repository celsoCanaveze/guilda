package br.com.fiap.guilda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.guilda.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
