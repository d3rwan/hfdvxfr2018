package com.github.d3rwan.hfdvxfr2018.ad;

import java.util.List;

public interface AdRepository {
    List<Ad> findAdsForCity(String city);
}
