package br.com.fiap.clyvo_companion.security;

import br.com.fiap.clyvo_companion.exception.BusinessRuleException;
import br.com.fiap.clyvo_companion.model.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetAcessoPolicy {

    public void garantirPetDoTutor(Pet pet, UsuarioDetails usuario) {
        if (usuario == null || !usuario.isTutor()) {
            return;
        }
        if (!pet.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            throw new BusinessRuleException("Você só pode registrar logs de saúde dos seus próprios pets");
        }
    }
}
