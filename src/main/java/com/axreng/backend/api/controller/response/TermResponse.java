package com.axreng.backend.api.controller.response;

import com.axreng.backend.domain.term.StatusTerm;
import com.axreng.backend.domain.term.Term;

import java.util.HashSet;
import java.util.Set;

public class TermResponse {

    private String id;

    private StatusTerm status;

    private Set<String> urls = new HashSet<>();

    public TermResponse() {}

    public TermResponse(String id) {
        this.id = id;
    }

    public TermResponse(Term term) {
        this.id = term.getId();
        this.status = term.getStatus();
        this.urls = term.getUrls();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StatusTerm getStatus() {
        return status;
    }

    public void setStatus(StatusTerm status) {
        this.status = status;
    }

    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }
}
