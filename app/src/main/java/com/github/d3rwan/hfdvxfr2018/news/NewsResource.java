package com.github.d3rwan.hfdvxfr2018.news;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
public class NewsResource {
    private final NewsService newsService;

    public NewsResource(NewsService newsService) {
        this.newsService = newsService;
    }

    @RequestMapping("/{city}")
    public News getNewsForCity(@PathVariable("city") String city) {
        return newsService.getNewsForCity(city);
    }
}
