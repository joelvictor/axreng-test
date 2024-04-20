package com.axreng.backend.domain.term;

public enum StatusTerm {

    ACTIVE("active"),
    DONE("done");

    private final String descricao;

    StatusTerm(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
