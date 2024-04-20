package com.axreng.backend.application.usecase.crawler.impl;

import com.axreng.backend.application.usecase.crawler.CrawlerUseCase;
import com.axreng.backend.config.DomainConfig;
import com.axreng.backend.domain.gateways.TermGateway;
import com.axreng.backend.domain.term.StatusTerm;
import com.axreng.backend.domain.term.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlerUseCaseImpl implements CrawlerUseCase {

    private static final Logger logger = LoggerFactory.getLogger(CrawlerUseCaseImpl.class);
    private final String baseUrl;
    private final Queue<String> queue;
    private final Set<String> visitedUrls;
    private final TermGateway termGateway;
    private final Term term;

    public CrawlerUseCaseImpl(TermGateway termGateway, DomainConfig domainConfig, Term term) {
        this.termGateway = termGateway;
        this.term = term;
        this.queue = new LinkedList<>();
        this.visitedUrls = new HashSet<>();
        baseUrl = domainConfig.getBaseUrl();
    }

    @Override
    public void run() {
        crawl();
    }

    private void crawl() {
        long startTime = System.currentTimeMillis();
        queue.add(baseUrl);
        term.setUrls(new HashSet<>());

        while (!queue.isEmpty()) {
            String nextUrl = queue.poll();
            Set<String> urls = extractURLsFromPage(nextUrl);
            queueNewUrls(urls);
        }

        term.setStatus(StatusTerm.DONE);
        termGateway.save(term);

        long elapsedTimeSeconds = (System.currentTimeMillis() - startTime) / 1000;
        logger.info("Crawling {} completed in {} seconds.", baseUrl, elapsedTimeSeconds);
    }

    private void queueNewUrls(Set<String> urls) {
        synchronized (this) {
            for (String newUrl : urls) {
                if (!visitedUrls.contains(newUrl)) {
                    queue.offer(newUrl);
                    visitedUrls.add(newUrl);
                }
            }
        }
    }

    private Set<String> extractURLsFromPage(String url) {
        Set<String> newUrls = new HashSet<>();
        try {
            logger.info("Extracting URLs from {}", url);
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder buffer = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                reader.close();
                String content = buffer.toString();

                containsKeyword(url, content);

                newUrls = extractLinksFromPage(content);
                newUrls.removeIf(visitedUrls::contains);
            }
        } catch (IOException e) {
            logger.error("Error while extracting URLs from {}", url, e);
        }
        return newUrls;
    }

    private void containsKeyword(String url, String content) {
        if (StringUtils.isNotEmpty(content) && content.contains(term.getKeyword())) {
            term.getUrls().add(url);
            termGateway.save(term);
            logger.info("Keyword {} found in {}", term.getKeyword(), url);
        }
    }

    private Set<String> extractLinksFromPage(String content) {
        Set<String> links = new HashSet<>();
        Matcher matcher = Pattern.compile("(?i)<a\\s+[^>]*?href=\"([^\"]*)\"").matcher(content);
        while (matcher.find()) {
            String link = matcher.group(1);
            if (!link.startsWith("http") && !link.startsWith("mailto") && !link.startsWith("ftp")) {
                String pattern = "\\.\\./|\\./";
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(link);
                link = m.replaceAll("");
                links.add(baseUrl + link);
                logger.info("Found link: {}", link);
            }
        }
        return links;
    }

}
