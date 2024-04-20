package com.axreng.backend.api.controller;


import com.axreng.backend.api.controller.response.ErrorMessage;
import com.axreng.backend.application.usecase.listurl.ListUrlByTermUseCase;
import com.axreng.backend.application.usecase.listurl.impl.ListUrlByTermUseCaseImpl;
import com.axreng.backend.domain.exception.EmptyTermIdException;
import com.axreng.backend.domain.exception.InvalidDomainException;
import com.axreng.backend.domain.exception.InvalidTermIdSizeException;
import com.axreng.backend.domain.exception.NotFoundException;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;


public class ListUrlByTermController implements Route {

    private final ListUrlByTermUseCase listUrlByTermUseCase;
    private final Gson gson;

    public ListUrlByTermController(ListUrlByTermUseCaseImpl listUrlByTermUseCase, Gson gson) {
        this.listUrlByTermUseCase = listUrlByTermUseCase;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) {
        try {
            response.type("application/json");
            return gson.toJson(listUrlByTermUseCase.listUrlByTerm(request.params("id")));
        } catch (EmptyTermIdException | InvalidTermIdSizeException e) {
            response.status(400);
            return gson.toJson(new ErrorMessage(e.getMessage()));
        } catch (NotFoundException e) {
            response.status(404);
            return gson.toJson(new ErrorMessage(e.getMessage()));
        } catch (InvalidDomainException e) {
            response.status(500);
            return gson.toJson(new ErrorMessage(e.getMessage()));
        }
    }
}
