package com.axreng.backend.domain.gateways;

import com.axreng.backend.api.controller.response.TermResponse;
import com.axreng.backend.domain.term.Term;

public interface TermGateway {

    Term save(Term term);

    Term getTermById(String id);
}
