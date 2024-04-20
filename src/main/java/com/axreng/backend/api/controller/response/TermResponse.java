package com.axreng.backend.api.controller.response;

import com.axreng.backend.domain.term.Term;

import java.util.Objects;
import java.util.Set;

public class TermResponse {

    private String id;

    private String status;

    private Set<String> urls;

    public TermResponse() {}

    public TermResponse(String id) {
        this.id = id;
    }

    public TermResponse(Term term) {
        if (Objects.nonNull(term)) {
            this.id = term.getId();
            this.status = Objects.nonNull(term.getStatus()) ? term.getStatus().getDescricao() : null;
            this.urls = term.getUrls();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }
}
