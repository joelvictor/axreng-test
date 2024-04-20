package com.axreng.backend.infrastructure.dataproviders.mappers;

import com.axreng.backend.domain.term.StatusTerm;
import com.axreng.backend.domain.term.Term;
import com.axreng.backend.infrastructure.dataproviders.entities.TermEntity;
import com.axreng.backend.infrastructure.dataproviders.mappers.TermMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class TermMapperTest {

    private TermMapper termMapper = new TermMapper();
    private Term term;
    private TermEntity termEntity;

    @BeforeEach
    public void setUp() {
        term = new Term();
        term.setId("testId");
        term.setKeyword("testKeyword");
        term.setStatus(StatusTerm.ACTIVE);
        term.setUrls(new HashSet<>(Arrays.asList("http://example.com")));

        termEntity = new TermEntity();
        termEntity.setId("testId");
        termEntity.setKeyword("testKeyword");
        termEntity.setStatus(StatusTerm.ACTIVE);
        termEntity.setUrls(new HashSet<>(Arrays.asList("http://example.com")));
    }

    @Test
    void testToTermEntity() {
        TermEntity termEntity = termMapper.toTermEntity(term);

        assertNotNull(termEntity);
        assertEquals(term.getId(), termEntity.getId());
        assertEquals(term.getKeyword(), termEntity.getKeyword());
        assertEquals(term.getStatus(), termEntity.getStatus());
        assertEquals(term.getUrls(), termEntity.getUrls());
    }

    @Test
    void testToTerm() {
        Term term = termMapper.toTerm(termEntity);

        assertNotNull(term);
        assertEquals(termEntity.getId(), term.getId());
        assertEquals(termEntity.getKeyword(), term.getKeyword());
        assertEquals(termEntity.getStatus(), term.getStatus());
        assertEquals(termEntity.getUrls(), term.getUrls());
    }

    @Test
    void testNullConversions() {
        assertNull(termMapper.toTermEntity(null));
        assertNull(termMapper.toTerm(null));
    }
}
