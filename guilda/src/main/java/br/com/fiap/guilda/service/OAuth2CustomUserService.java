package br.com.fiap.guilda.service;

import br.com.fiap.guilda.model.Missao;
import br.com.fiap.guilda.model.Usuario;
import br.com.fiap.guilda.repository.UsuarioRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class OAuth2CustomUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
    private final UsuarioRepository usuarioRepository;

    public OAuth2CustomUserService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oauth2User = delegate.loadUser(userRequest);
        Map<String, Object> attrs = oauth2User.getAttributes();

        // Tentar extrair um identificador único do provedor
        String externalId = getAttribute(attrs, "sub", "id", "node_id"); // sub (OIDC), id (github), node_id fallback
        if (externalId == null) {
            // fallback para email ou login
            externalId = getAttribute(attrs, "email", "login");
        }

        // Nome legível
        String nome = getAttribute(attrs, "name", "login", "preferred_username", "email");
        if (nome == null) nome = "Usuário-" + (externalId != null ? externalId : "unk");

        // Localiza ou cria usuário no banco
        Usuario usuario = findOrCreateUsuario(externalId, nome);

        // Podemos adicionar atributos extras se quisermos (ex: id interno)
        // retornamos um DefaultOAuth2User reusando o principal original (roles não estão sendo mapeadas aqui)
        return new DefaultOAuth2User(oauth2User.getAuthorities(), oauth2User.getAttributes(), "name");
    }

    private String getAttribute(Map<String, Object> attrs, String... keys) {
        for (String k : keys) {
            Object v = attrs.get(k);
            if (v != null) return v.toString();
        }
        return null;
    }

    private Usuario findOrCreateUsuario(String externalId, String nome) {
        // Procuramos por algum campo que possamos mapear.
        // Se quiser, adicione uma coluna "external_id" em Usuario e reescreva persistência.
        // Aqui faço busca simples por nome como fallback (melhore para production).
        Optional<Usuario> maybe = usuarioRepository.findAll()
                .stream()
                .filter(u -> u.getNome() != null && u.getNome().equals(nome))
                .findFirst();

        if (maybe.isPresent()) return maybe.get();

        Usuario novo = new Usuario();
        novo.setNome(nome);
        novo.setAvaliacao(0);
        novo.setMoedas(0);
        // missaoAtiva e missaoCriada podem ficar null inicialmente
        return usuarioRepository.save(novo);
    }
}
