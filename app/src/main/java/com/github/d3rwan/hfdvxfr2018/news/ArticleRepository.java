package com.github.d3rwan.hfdvxfr2018.news;

import java.util.List;

public interface ArticleRepository {
    List<Article> findLastArticleForCity(String city);
}
