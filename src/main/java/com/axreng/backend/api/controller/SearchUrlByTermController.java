package com.axreng.backend.api.controller;

import com.axreng.backend.api.controller.request.TermRequest;
import com.axreng.backend.api.controller.response.ErrorMessage;
import com.axreng.backend.application.usecase.searchurl.SearchUrlByTermUseCase;
import com.axreng.backend.domain.exception.EmptyKeywordException;
import com.axreng.backend.domain.exception.InvalidDomainException;
import com.axreng.backend.domain.exception.InvalidKeywordSizeException;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public class SearchUrlByTermController implements Route {

    private final SearchUrlByTermUseCase searchUrlByTermUseCase;
    private final Gson gson;

    public SearchUrlByTermController(SearchUrlByTermUseCase searchUrlByTermUseCase, Gson gson) {
        this.searchUrlByTermUseCase = searchUrlByTermUseCase;
        this.gson = gson;
    }

    public Object handle(Request request, Response response) {
        try {
            response.type("application/json");
            TermRequest termRequest = gson.fromJson(request.body(), TermRequest.class);
            return gson.toJson(searchUrlByTermUseCase.search(termRequest));
        } catch (EmptyKeywordException | InvalidKeywordSizeException e) {
            response.status(400);
            return gson.toJson(new ErrorMessage(e.getMessage()));
        } catch (InvalidDomainException e) {
            response.status(500);
            return gson.toJson(new ErrorMessage(e.getMessage()));
        }
    }

}
