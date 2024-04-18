package com.axreng.backend.api.controller;

import com.axreng.backend.api.controller.request.TermRequest;
import com.axreng.backend.application.useCase.searchUrl.SearchUrlByTermUseCase;
import com.axreng.backend.domain.exception.EmptyKeywordException;
import com.axreng.backend.domain.exception.InvalidKeywordSizeException;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public class SearchUrlByTermController implements Route {

    private SearchUrlByTermUseCase searchUrlByTermUseCase;
    private Gson gson = new Gson();


    public SearchUrlByTermController(SearchUrlByTermUseCase searchUrlByTermUseCase) {
        this.searchUrlByTermUseCase = searchUrlByTermUseCase;
    }

    public Object handle(Request request, Response response) {
        try {
            response.type("application/json");
            TermRequest termRequest = gson.fromJson(request.body(), TermRequest.class);
            return gson.toJson(searchUrlByTermUseCase.search(termRequest));
        } catch (EmptyKeywordException | InvalidKeywordSizeException e) {
            response.status(400);
            return e.getMessage();
        }
    }

}
