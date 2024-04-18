package com.axreng.backend.application.useCase.searchUrl;

import com.axreng.backend.api.controller.request.TermRequest;
import com.axreng.backend.api.controller.response.TermResponse;
import com.axreng.backend.domain.exception.EmptyKeywordException;
import com.axreng.backend.domain.exception.InvalidKeywordSizeException;

public interface SearchUrlByTermUseCase {
    TermResponse search(TermRequest termRequest) throws EmptyKeywordException, InvalidKeywordSizeException;
}
