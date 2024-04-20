package com.axreng.backend.application.usecase.listurl;

import com.axreng.backend.api.controller.response.TermResponse;
import com.axreng.backend.domain.exception.EmptyTermIdException;
import com.axreng.backend.domain.exception.InvalidTermIdSizeException;
import com.axreng.backend.domain.exception.NotFoundException;

public interface ListUrlByTermUseCase {
    TermResponse listUrlByTerm(String id) throws EmptyTermIdException, InvalidTermIdSizeException, NotFoundException;
}
