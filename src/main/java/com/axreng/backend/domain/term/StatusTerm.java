package com.axreng.backend.domain.term;

public enum StatusTerm {

    ACTIVE("active"),
    DONE("done");

    private final String status;

    StatusTerm(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
