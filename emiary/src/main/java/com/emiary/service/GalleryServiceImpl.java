package com.emiary.service;

import com.emiary.dao.GalleryDAO;
import com.emiary.domain.Diaries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GalleryServiceImpl implements GalleryService {


    @Autowired
    GalleryDAO galleryDAO;

    @Override
    public List<Diaries> getAIImg(String keyword, String yearAndMonth, String username) {
        Map<String, String> map = new HashMap<>();
        map.put("yearAndMonth", yearAndMonth);
        map.put("username", username);
        map.put("keyword", keyword);
        List<Diaries> diaries = galleryDAO.getAIImg(map);
        return diaries;
    }

    @Override
    public String getKeyword(String username) {
        return galleryDAO.getKeyword(username);
    }
}
