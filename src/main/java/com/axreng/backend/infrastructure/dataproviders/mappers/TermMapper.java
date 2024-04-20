package com.axreng.backend.infrastructure.dataproviders.mappers;

import com.axreng.backend.domain.term.Term;
import com.axreng.backend.infrastructure.dataproviders.entities.TermEntity;

import java.util.Objects;

public class TermMapper {

    public TermEntity toTermEntity(final Term term) {
        TermEntity termEntity = null;
        if (Objects.nonNull(term)) {
            termEntity = new TermEntity();
            termEntity.setId(term.getId());
            termEntity.setKeyword(term.getKeyword());
            termEntity.setStatus(term.getStatus());
            termEntity.setUrls(term.getUrls());
        }
        return termEntity;
    }

    public Term toTerm(final TermEntity termEntity) {
        Term term = null;
        if (Objects.nonNull(termEntity)) {
            term = new Term();
            term.setId(termEntity.getId());
            term.setKeyword(termEntity.getKeyword());
            term.setStatus(termEntity.getStatus());
            term.setUrls(termEntity.getUrls());
        }
        return term;
    }

}
