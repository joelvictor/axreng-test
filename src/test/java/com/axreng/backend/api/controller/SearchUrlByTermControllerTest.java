package com.axreng.backend.api.controller;

import com.axreng.backend.api.controller.request.TermResponse;
import com.axreng.backend.application.useCase.searchUrl.SearchUrlByTermUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spark.Request;

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
        when(searchUrlByTermUseCase.search(anyString())).thenReturn(new TermResponse());
        Request request = mock(Request.class);
        request.attribute("keyword", "xyz");
        Object result = searchUrlByTermController.handle(null, null);
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }
}
