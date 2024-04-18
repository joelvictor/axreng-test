package com.axreng.backend.application.useCase.crawler.impl;

import com.axreng.backend.application.useCase.crawler.CrawlerUseCase;
import com.axreng.backend.domain.gateways.TermGateway;
import com.axreng.backend.domain.term.StatusTerm;
import com.axreng.backend.domain.term.Term;
import com.axreng.backend.infrastructure.dataproviders.TermDataProvider;

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

    private final static String baseUrl = "http://hiring.axreng.com/";
    private Queue<String> queue = new LinkedList<>();
    private Set<String> visitedUrls = new HashSet<>();
    private TermGateway termGateway;
    private int workingThreads = 0;
    private Term term;

    public CrawlerUseCaseImpl(Term term) {
        this.termGateway = new TermDataProvider();
        this.term = term;
    }

    private void crawl() {
        Long startTime = System.currentTimeMillis();
        queue.add(baseUrl);
        term.setUrls(new HashSet<>());
        OUTER_LOOP: while(true) {
            String nextUrl;
            synchronized(this) {
                while(queue.isEmpty()) {
                    if(workingThreads == 0 && queue.isEmpty()) { // new code
                        break OUTER_LOOP;
                    }
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                nextUrl = queue.poll();
                workingThreads++;  // new code
            }
            Set<String> urls = extractURLsFromPage(nextUrl);

            synchronized(this) {
                for(String newUrl: urls) {
                    if(!visitedUrls.contains(newUrl)) {
                        queue.offer(newUrl);
                        visitedUrls.add(newUrl);
                    }
                }
                workingThreads--;  // new code
                notifyAll();
            }
        }
        term.setStatus(StatusTerm.DONE);
        termGateway.save(term);
        System.out.println((System.currentTimeMillis() - startTime) / 1000);
    }

    private Set<String> extractURLsFromPage(String url) {
        Set<String> newUrls = new HashSet<>();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    StringBuffer buffer = new StringBuffer();
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    reader.close();
                    String content = buffer.toString();
                    if (buffer.length() > 0 && buffer.toString().contains(term.getKeyword())) {
                        term.getUrls().add(url);
                        termGateway.save(term);
                    }
                    newUrls = extractLinksFromPage(content);
                    newUrls.removeIf(newUrl -> visitedUrls.contains(newUrl));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newUrls;
    }

    private Set<String> extractLinksFromPage(String content) {
        Set<String> links = new HashSet<>();
        Matcher matcher = Pattern.compile("(?i)<a\\s+[^>]*?href=\"([^\"]*)\"").matcher(content.toString());
        while (matcher.find()) {
            String link = matcher.group(1);
            if (!link.startsWith("http")) {
                String pattern = "(?:\\.\\./|\\.\\/)";
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(link);
                link = m.replaceAll("");
                System.out.println("link "+ link + "  full "+ baseUrl+link);
                links.add(baseUrl+link);
            }
        }
        return links;
    }

    @Override
    public void run() {
        crawl();
    }
}
