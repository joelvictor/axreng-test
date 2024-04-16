package com.axreng.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebsiteCrawler {

    private final String keyword;
    private final int threadPoolSize;
    private final static String baseUrl = "http://hiring.axreng.com/";
    private final Map<String, Set<String>> results;
    private Deque<String> urlsToVisit = new ConcurrentLinkedDeque<>();
    private Set<String> visitedUrls = Collections.synchronizedSet(new HashSet<>());

    public WebsiteCrawler(String keyword, int threadPoolSize) {
        this.keyword = keyword;
        this.threadPoolSize = threadPoolSize;
        this.results = new HashMap<>();
    }

    public Map<String, Set<String>> extractURLs() throws IOException, InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        results.put(baseUrl, new HashSet<>());
        List<Future<Map<String, Set<String>>>> futures = new LinkedList<>();


        urlsToVisit.add(baseUrl);

        while (!urlsToVisit.isEmpty()) {
            String currentUrl = urlsToVisit.poll();

            if (visitedUrls.contains(currentUrl)) {
                continue;
            }

            visitedUrls.add(currentUrl);
            Future<Map<String, Set<String>>> future = executorService.submit(() -> extractURLsFromPage(currentUrl));
            futures.add(future);
        }

        // Aguardar a conclusão de todas as tarefas
        for (Future<Map<String, Set<String>>> future : futures) {
            Map<String, Set<String>> partialResults = future.get();
            mergeResults(partialResults);
        }

        executorService.shutdown();

        return results;
    }

    private Set<String> extractLinksFromPage(String content) throws IOException {
        Set<String> links = new HashSet<>();
        Matcher matcher = Pattern.compile("(?i)<a\\s+[^>]*?href=\"([^\"]*)\"").matcher(content.toString());
        while (matcher.find()) {
            String link = matcher.group(1);
            if (!link.startsWith("http")) {
//                System.out.println("link "+ link);
                links.add(baseUrl+link);
            }
        }
        return links;
    }

    private Map<String, Set<String>> extractURLsFromPage(String url) throws IOException {
        visitedUrls.add(url);
        Map<String, Set<String>> partialResults = new HashMap<>();

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
                if (buffer.length() > 0 && buffer.toString().contains(keyword)) {
                    results.get(baseUrl).add(url);
                }
                Set<String> newUrls = extractLinksFromPage(content);
                for (String newUrl : newUrls) {
                    if (!visitedUrls.contains(newUrl)) {
                        extractURLsFromPage(newUrl);
                    }
                }
            }
        }

        return partialResults;
    }

    private void mergeResults(Map<String, Set<String>> partialResults) {
        for (String parentUrl : partialResults.keySet()) {
            Set<String> urls = partialResults.get(parentUrl);

            results.putIfAbsent(parentUrl, new HashSet<>());
            results.get(parentUrl).addAll(urls);
        }
    }

    public static void main(String[] args) {

        String keyword = "teste";
        int threadPoolSize = Runtime.getRuntime().availableProcessors() / 2;

        WebsiteCrawler extractor = new WebsiteCrawler(keyword, threadPoolSize);

        try {
            Map<String, Set<String>> results = extractor.extractURLs();

            // Exibir os resultados
            for (Map.Entry<String, Set<String>> entry : results.entrySet()) {
                System.out.println("Site Pai: " + entry.getKey());
                for (String url : entry.getValue()) {
                    System.out.println("\tURL com Palavra-chave: " + url);
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro durante a extração de URLs: " + e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
