package br.com.fiap.guilda.service;

import br.com.fiap.guilda.model.Missao;
import br.com.fiap.guilda.repository.MissaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissaoService {

    private final MissaoRepository repository;

    public MissaoService(MissaoRepository repository) {
        this.repository = repository;
    }

    public List<Missao> listarTodas() {
        return repository.findAll();
    }

    public Missao salvar(Missao missao) {
        return repository.save(missao);
    }

    public Missao buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
