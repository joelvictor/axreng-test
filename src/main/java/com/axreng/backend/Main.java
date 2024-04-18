package com.axreng.backend;

import com.axreng.backend.api.controller.ListUrlByTermController;
import com.axreng.backend.api.controller.SearchUrlByTermController;
import com.axreng.backend.application.useCase.searchUrl.impl.SearchUrlByTermUseCaseImpl;

import static spark.Spark.get;
import static spark.Spark.post;

public class Main {

    public static void main(String[] args) {

//        get("/crawl/:id", ListUrlByTermController.get);
        post("/crawl", new SearchUrlByTermController((new SearchUrlByTermUseCaseImpl())));
    }

    public static class MyCrawler {
    }
}
