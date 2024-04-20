package com.axreng.backend.application.usecase.crawler.impl;

import com.axreng.backend.config.DomainConfig;
import com.axreng.backend.domain.exception.InvalidDomainException;
import com.axreng.backend.domain.gateways.TermGateway;
import com.axreng.backend.domain.term.Term;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.mockito.Mockito.*;

class CrawlerUseCaseImplTest {

    @Mock
    private TermGateway termGateway;

    @Mock
    private DomainConfig domainConfig;

    @Mock
    private Term term;

    @Mock
    private HttpURLConnection httpURLConnection;

    private CrawlerUseCaseImpl crawlerUseCase;

    @BeforeEach
    public void setUp() throws InvalidDomainException {
        MockitoAnnotations.openMocks(this);
        when(domainConfig.getBaseUrl()).thenReturn("http://www.example.com/");
        crawlerUseCase = new CrawlerUseCaseImpl(termGateway, domainConfig, term);
    }

    @Test
    void testCrawl_Successful() throws IOException {
        when(term.getKeyword()).thenReturn("testKeyword");
        when(httpURLConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(httpURLConnection.getInputStream()).thenReturn(getClass().getResourceAsStream("/testHtml.html"));
        crawlerUseCase.run();

        verify(termGateway, times(1)).save(term);
    }

}
