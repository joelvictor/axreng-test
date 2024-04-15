package com.axreng.backend.api.controller;

import com.axreng.backend.application.useCase.searchUrl.SearchUrlByTermUseCase;
import com.axreng.backend.domain.exception.EmptyKeywordException;
import com.axreng.backend.domain.exception.InvalidKeywordSizeException;
import spark.Request;
import spark.Response;
import spark.Route;

public class SearchUrlByTermController implements Route {

    private SearchUrlByTermUseCase searchUrlByTermUseCase;

    public SearchUrlByTermController(SearchUrlByTermUseCase searchUrlByTermUseCase) {
        this.searchUrlByTermUseCase = searchUrlByTermUseCase;
    }

    public Object handle(Request request, Response response) {
        try {
            return searchUrlByTermUseCase.search(request.attribute("keyword"));
        } catch (EmptyKeywordException | InvalidKeywordSizeException e) {
            response.status(400);
            return e.getMessage();
        }
    }

}
