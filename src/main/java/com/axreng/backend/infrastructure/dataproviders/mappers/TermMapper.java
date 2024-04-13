package com.axreng.backend.infrastructure.dataproviders.mappers;

import com.axreng.backend.domain.term.Term;
import com.axreng.backend.infrastructure.dataproviders.entities.TermEntity;

public class TermMapper {

    public TermEntity toTermEntity(final Term term) {
        TermEntity termEntity = new TermEntity();
        termEntity.setId(term.getId());
        termEntity.setKeyword(term.getKeyword());
        termEntity.setStatus(term.getStatus());
        termEntity.setUrls(term.getUrls());
        return termEntity;
    }

    public Term toTerm(final TermEntity termEntity) {
        Term term = new Term();
        term.setId(termEntity.getId());
        term.setKeyword(termEntity.getKeyword());
        term.setStatus(termEntity.getStatus());
        term.setUrls(termEntity.getUrls());
        return term;
    }


}
