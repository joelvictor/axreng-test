package com.axreng.backend.infrastructure.dataproviders.entities;

import com.axreng.backend.domain.term.StatusTerm;

import java.util.List;
import java.util.UUID;

public class TermEntity {

    private UUID id;

    private String keyword;

    private StatusTerm status;

    private List<String> urls;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public StatusTerm getStatus() {
        return status;
    }

    public void setStatus(StatusTerm status) {
        this.status = status;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

}
