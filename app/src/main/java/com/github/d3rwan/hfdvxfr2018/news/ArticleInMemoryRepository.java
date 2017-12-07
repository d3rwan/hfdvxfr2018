package com.github.d3rwan.hfdvxfr2018.news;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleInMemoryRepository implements ArticleRepository {

    private final Map<String, List<Article>> articlesByCity;

    public ArticleInMemoryRepository(Map<String, List<Article>> articleByCity) {
        this.articlesByCity = articleByCity;
    }

    @Override
    public List<Article> findLastArticleForCity(String city) {
        return articlesByCity.getOrDefault(city, new ArrayList<>());
    }
}
