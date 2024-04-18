package com.axreng.backend.api.controller;

import com.axreng.backend.api.controller.request.TermRequest;
import com.axreng.backend.api.controller.response.TermResponse;
import com.axreng.backend.application.useCase.searchUrl.SearchUrlByTermUseCase;
import com.axreng.backend.domain.exception.EmptyKeywordException;
import com.axreng.backend.domain.exception.InvalidKeywordSizeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spark.Request;
import spark.Response;

import static org.mockito.Mockito.*;

class SearchUrlByTermControllerTest {
    @Mock
    SearchUrlByTermUseCase searchUrlByTermUseCase;
    @InjectMocks
    SearchUrlByTermController searchUrlByTermController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandle() throws Exception {
        TermResponse termResponse = new TermResponse("123");
        when(searchUrlByTermUseCase.search(new TermRequest())).thenReturn(termResponse);
        Request request = mock(Request.class);
        request.attribute("keyword", "keyword");
        Response response = mock(Response.class);
        Object result = searchUrlByTermController.handle(request, response);
        Assertions.assertEquals(termResponse, result);
    }

    @Test
    public void testEmptyKeyword() throws Exception {
        when(searchUrlByTermUseCase.search(new TermRequest())).thenThrow(new EmptyKeywordException());
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        when(searchUrlByTermController.handle(request, response)).thenThrow(new EmptyKeywordException());
        searchUrlByTermController.handle(request, response);
        verify(response).status(400);
    }

    @Test
    public void testKeywordSizeLess4() throws Exception {
        TermRequest termRequest = new TermRequest();
        termRequest.setKeyword("xyz");
        when(searchUrlByTermUseCase.search(termRequest)).thenThrow(new EmptyKeywordException());
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        when(searchUrlByTermController.handle(request, response)).thenThrow(new InvalidKeywordSizeException());
        searchUrlByTermController.handle(request, response);
        verify(response).status(400);
    }
}
