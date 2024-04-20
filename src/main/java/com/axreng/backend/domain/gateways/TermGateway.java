package com.axreng.backend.domain.gateways;

import com.axreng.backend.domain.exception.NotFoundException;
import com.axreng.backend.domain.term.Term;

public interface TermGateway {

    Term save(Term term);

    Term findTermById(String id) throws NotFoundException;
}
