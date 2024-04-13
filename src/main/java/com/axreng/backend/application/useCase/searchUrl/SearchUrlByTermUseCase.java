package com.axreng.backend.application.useCase.searchUrl;

import com.axreng.backend.api.controller.request.TermResponse;

public interface SearchUrlByTermUseCase {
    TermResponse search(String keyword);
}
