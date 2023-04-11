package com.emiary.service;

import com.emiary.domain.Diaries;

import java.util.List;

public interface GalleryService {
    List<Diaries> getAIImg(String keyword, String yearAndMonth, String username);

    String getKeyword(String username);
}
