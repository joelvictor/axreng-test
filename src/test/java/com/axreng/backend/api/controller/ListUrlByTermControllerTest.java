package com.axreng.backend.api.controller;

import com.axreng.backend.application.useCase.listurl.ListUrlByTermUseCase;
import com.axreng.backend.application.useCase.searchUrl.SearchUrlByTermUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spark.Request;
import spark.Response;

import static org.mockito.Mockito.mock;

class ListUrlByTermControllerTest {

    @Mock
    ListUrlByTermUseCase listUrlByTermUseCase;

    @InjectMocks
    ListUrlByTermController listUrlByTermController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandle() throws Exception {
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        Object result = listUrlByTermController.handle(request, response);
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}
