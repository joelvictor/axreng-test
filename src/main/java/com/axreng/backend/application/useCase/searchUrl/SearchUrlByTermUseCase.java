package com.axreng.backend.application.useCase.searchUrl;

import com.axreng.backend.api.controller.request.TermResponse;
import com.axreng.backend.domain.exception.EmptyKeywordException;
import com.axreng.backend.domain.exception.InvalidKeywordSizeException;

public interface SearchUrlByTermUseCase {
    TermResponse search(String keyword) throws EmptyKeywordException, InvalidKeywordSizeException;
}
