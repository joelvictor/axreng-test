package com.axreng.backend.infrastructure.dataproviders;

import com.axreng.backend.domain.exception.NotFoundException;
import com.axreng.backend.domain.gateways.TermGateway;
import com.axreng.backend.domain.term.Term;
import com.axreng.backend.infrastructure.dataproviders.entities.TermEntity;
import com.axreng.backend.infrastructure.dataproviders.mappers.TermMapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TermDataProvider implements TermGateway {

    private static ConcurrentHashMap<String, TermEntity> terms;
    private final TermMapper termMapper = new TermMapper();

    @Override
    public Term save(Term term) {
        TermEntity termEntity = termMapper.toTermEntity(term);
        getTerms().put(termEntity.getId(), termEntity);
        return termMapper.toTerm(termEntity);
    }

    @Override
    public Term findTermById(String id) throws NotFoundException {
        TermEntity termEntity = getTerms().get(id);
        if (termEntity == null) {
            throw new NotFoundException();
        }
        return termMapper.toTerm(termEntity);
    }

    public static Map<String, TermEntity> getTerms() {
        if (terms == null) {
            terms = new ConcurrentHashMap<>();
        }
        return terms;
    }

}
