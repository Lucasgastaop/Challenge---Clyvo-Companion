package br.com.fiap.clyvo_companion.service;

import br.com.fiap.clyvo_companion.exception.BusinessRuleException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Limites físicos das métricas de saúde. Valores fora da faixa são rejeitados antes da persistência.
 */
@Component
public class MetricaSaudeValidator {

    private static final Map<String, Limite> LIMITES = new LinkedHashMap<>();

    static {
        LIMITES.put("peso", new Limite(new BigDecimal("0.10"), new BigDecimal("120.00"), "kg"));
        LIMITES.put("alimentacao", new Limite(new BigDecimal("1"), new BigDecimal("5000"), "g"));
        LIMITES.put("exercicio", new Limite(BigDecimal.ZERO, new BigDecimal("480"), "min"));
        LIMITES.put("temperatura", new Limite(new BigDecimal("30.00"), new BigDecimal("45.00"), "°C"));
        LIMITES.put("frequencia cardiaca", new Limite(new BigDecimal("30"), new BigDecimal("300"), "bpm"));
    }

    public void validar(String metrica, BigDecimal valor) {
        if (metrica == null || metrica.isBlank() || valor == null) {
            throw new BusinessRuleException("Métrica e valor são obrigatórios");
        }

        String chave = normalizar(metrica);
        Limite limite = LIMITES.get(chave);
        if (limite == null) {
            throw new BusinessRuleException(
                    "Métrica não suportada: " + metrica + ". Use: " + String.join(", ", LIMITES.keySet()));
        }
        if (valor.compareTo(limite.min()) < 0 || valor.compareTo(limite.max()) > 0) {
            throw new BusinessRuleException(
                    "Valor de " + chave + " fora dos limites ("
                            + limite.min() + " a " + limite.max() + " " + limite.unidade() + ")");
        }
    }

    public Set<String> metricasPermitidas() {
        return LIMITES.keySet();
    }

    public Map<String, String> opcoesFormulario() {
        Map<String, String> opcoes = new LinkedHashMap<>();
        opcoes.put("peso", "Peso (kg) — 0,10 a 120");
        opcoes.put("alimentacao", "Alimentação (g) — 1 a 5000");
        opcoes.put("exercicio", "Exercício (min) — 0 a 480");
        opcoes.put("temperatura", "Temperatura (°C) — 30 a 45");
        opcoes.put("frequencia cardiaca", "Frequência cardíaca (bpm) — 30 a 300");
        return opcoes;
    }

    public String normalizar(String metrica) {
        return metrica.toLowerCase().trim().replace('_', ' ');
    }

    private record Limite(BigDecimal min, BigDecimal max, String unidade) {
    }
}
