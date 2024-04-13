package com.axreng.backend.infrastructure.dataproviders;

import com.axreng.backend.domain.gateways.TermGateway;
import com.axreng.backend.domain.term.Term;
import com.axreng.backend.infrastructure.dataproviders.entities.TermEntity;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TermDataProvider implements TermGateway {

    private ConcurrentHashMap<UUID, TermEntity> terms = new ConcurrentHashMap<>();

    @Override
    public Term save(Term term) {

        return null;
    }
}
