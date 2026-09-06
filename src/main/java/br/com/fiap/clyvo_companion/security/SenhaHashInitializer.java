package br.com.fiap.clyvo_companion.security;

import br.com.fiap.clyvo_companion.model.Usuario;
import br.com.fiap.clyvo_companion.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Recodifica senhas em texto puro (cargas Flyway ou base Oracle da sprint anterior) para BCrypt.
 */
@Component
public class SenhaHashInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(SenhaHashInitializer.class);

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public SenhaHashInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        usuarioRepository.findAll().stream()
                .filter(this::senhaEmTextoPuro)
                .forEach(this::codificar);
    }

    private boolean senhaEmTextoPuro(Usuario usuario) {
        String senha = usuario.getSenha();
        return senha != null && !senha.startsWith("$2a$") && !senha.startsWith("$2b$");
    }

    private void codificar(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
        log.debug("Senha recodificada para o usuário {}", usuario.getEmail());
    }
}
