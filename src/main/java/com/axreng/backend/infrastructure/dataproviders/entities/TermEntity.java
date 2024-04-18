package com.axreng.backend.infrastructure.dataproviders.entities;

import com.axreng.backend.domain.term.StatusTerm;

import java.util.Set;

public class TermEntity {

    private String id;

    private String keyword;

    private StatusTerm status;

    private Set<String> urls;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
    }

}
