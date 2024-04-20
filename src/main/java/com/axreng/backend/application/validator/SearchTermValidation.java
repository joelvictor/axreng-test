package com.axreng.backend.application.validator;

import com.axreng.backend.domain.exception.EmptyKeywordException;
import com.axreng.backend.domain.exception.InvalidKeywordSizeException;
import com.axreng.backend.domain.term.Term;
import spark.utils.StringUtils;

public class SearchTermValidation {

    public void validate(Term term) throws EmptyKeywordException, InvalidKeywordSizeException {
        if (StringUtils.isEmpty(term.getKeyword())) {
            throw new EmptyKeywordException();
        } else if (term.getKeyword().length() < 4 || term.getKeyword().length() > 32) {
            throw new InvalidKeywordSizeException();
        }
    }

}
