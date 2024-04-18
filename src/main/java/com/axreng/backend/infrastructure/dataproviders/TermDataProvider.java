package com.axreng.backend.infrastructure.dataproviders;

import com.axreng.backend.domain.gateways.TermGateway;
import com.axreng.backend.domain.term.Term;
import com.axreng.backend.infrastructure.dataproviders.entities.TermEntity;
import com.axreng.backend.infrastructure.dataproviders.mappers.TermMapper;

import java.util.concurrent.ConcurrentHashMap;

public class TermDataProvider implements TermGateway {

    private ConcurrentHashMap<String, TermEntity> terms;
    private final TermMapper termMapper = new TermMapper();

    @Override
    public Term save(Term term) {
        TermEntity termEntity = termMapper.toTermEntity(term);
        getTerms().put(termEntity.getId(), termEntity);
        return termMapper.toTerm(termEntity);
    }

    public ConcurrentHashMap<String, TermEntity> getTerms() {
        if (terms == null) {
            terms = new ConcurrentHashMap<>();
        }
        return terms;
    }

}
