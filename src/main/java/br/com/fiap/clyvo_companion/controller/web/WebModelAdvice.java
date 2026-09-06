package br.com.fiap.clyvo_companion.controller.web;

import br.com.fiap.clyvo_companion.security.UsuarioDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(basePackages = "br.com.fiap.clyvo_companion.controller.web")
public class WebModelAdvice {

    @ModelAttribute("usuarioLogado")
    public UsuarioDetails usuarioLogado(@AuthenticationPrincipal UsuarioDetails usuario) {
        return usuario;
    }

    @ModelAttribute("isTutor")
    public boolean isTutor(@AuthenticationPrincipal UsuarioDetails usuario) {
        return usuario != null && usuario.isTutor();
    }

    @ModelAttribute("isVeterinario")
    public boolean isVeterinario(@AuthenticationPrincipal UsuarioDetails usuario) {
        return usuario != null && usuario.isVeterinario();
    }
}
