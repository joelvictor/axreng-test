package com.axreng.backend.application.validator;

import com.axreng.backend.domain.exception.EmptyTermIdException;
import com.axreng.backend.domain.exception.InvalidTermIdSizeException;
import spark.utils.StringUtils;

public class ListTermValidation {

    public void validateId(String id) throws EmptyTermIdException, InvalidTermIdSizeException {
        if (StringUtils.isEmpty(id)) {
            throw new EmptyTermIdException();
        } else if (id.length() == 8) {
            throw new InvalidTermIdSizeException();
        }
    }

}
