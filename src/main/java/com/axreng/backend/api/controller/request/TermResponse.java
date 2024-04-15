package com.axreng.backend.api.controller.request;

import java.util.UUID;

public class TermResponse {

    private String id;

    public TermResponse() {}

    public TermResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
