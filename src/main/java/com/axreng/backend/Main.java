package com.axreng.backend;

import com.axreng.backend.config.DependencyInjector;

import static spark.Spark.get;
import static spark.Spark.post;

public class Main {


    public static void main(String[] args) {
        DependencyInjector injector = new DependencyInjector();
        get("/crawl/:id", injector.getListUrlByTermController());
        post("/crawl", injector.getSearchUrlByTermController());
    }

}
