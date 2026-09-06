package br.com.fiap.clyvo_companion.security;

import br.com.fiap.clyvo_companion.exception.BusinessRuleException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioAutenticadoService {

    public Optional<UsuarioDetails> getUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        if (authentication.getPrincipal() instanceof UsuarioDetails usuario) {
            return Optional.of(usuario);
        }
        return Optional.empty();
    }

    public UsuarioDetails exigirUsuarioLogado() {
        return getUsuarioLogado()
                .orElseThrow(() -> new BusinessRuleException("Usuário não autenticado"));
    }
}
