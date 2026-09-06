package br.com.fiap.clyvo_companion.model.enums;

/**
 * Perfis de acesso persistidos em TB_CC_USUARIO.TP_PERFIL.
 * O Spring Security espera a autoridade no formato ROLE_TUTOR / ROLE_VETERINARIO.
 */
public enum PerfilUsuario {

    TUTOR,
    VETERINARIO;

    public String asRole() {
        return "ROLE_" + name();
    }

    public static PerfilUsuario fromValor(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Perfil não informado");
        }
        try {
            return PerfilUsuario.valueOf(valor.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "Perfil inválido: " + valor + ". Valores aceitos: TUTOR, VETERINARIO");
        }
    }
}
