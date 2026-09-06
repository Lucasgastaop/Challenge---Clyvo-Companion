package br.com.fiap.clyvo_companion.security;

import br.com.fiap.clyvo_companion.model.Usuario;
import br.com.fiap.clyvo_companion.model.enums.PerfilUsuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Projeção de {@link Usuario} para o Spring Security.
 * Evita persistir a entidade JPA na sessão HTTP.
 */
public class UsuarioDetails implements UserDetails {

    private final Long idUsuario;
    private final String nomeUsuario;
    private final String email;
    private final String senha;
    private final String tpPerfil;

    public UsuarioDetails(Long idUsuario, String nomeUsuario, String email, String senha, String tpPerfil) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.senha = senha;
        this.tpPerfil = tpPerfil;
    }

    public static UsuarioDetails from(Usuario usuario) {
        return new UsuarioDetails(
                usuario.getIdUsuario(),
                usuario.getNomeUsuario(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getTpPerfil());
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getTpPerfil() {
        return tpPerfil;
    }

    public boolean isTutor() {
        return PerfilUsuario.TUTOR.name().equalsIgnoreCase(tpPerfil);
    }

    public boolean isVeterinario() {
        return PerfilUsuario.VETERINARIO.name().equalsIgnoreCase(tpPerfil);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(PerfilUsuario.fromValor(tpPerfil).asRole()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
