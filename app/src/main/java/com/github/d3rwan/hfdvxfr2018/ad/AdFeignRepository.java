package com.github.d3rwan.hfdvxfr2018.ad;

import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface AdFeignRepository extends AdRepository {
    @Override
    @RequestLine("GET /api/ad/{city}")
    List<Ad> findAdsForCity(@Param("city") String city);
}