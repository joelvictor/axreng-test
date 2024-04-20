package com.axreng.backend.api.controller.request;

public class TermRequest {

    private String keyword;

    public TermRequest() {}

    public TermRequest(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
