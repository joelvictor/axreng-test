package com.axreng.backend.application.useCase.listurl.impl;

import com.axreng.backend.api.controller.request.TermRequest;
import com.axreng.backend.api.controller.response.TermResponse;
import com.axreng.backend.application.validator.ListTermValidation;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class ListUrlByTermUseCaseImplTest {
    @Mock
    ListTermValidation listTermValidation;
    @InjectMocks
    ListUrlByTermUseCaseImpl listUrlByTermUseCaseImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


}
