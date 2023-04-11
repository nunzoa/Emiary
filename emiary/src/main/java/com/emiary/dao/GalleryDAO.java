package com.emiary.dao;


import com.emiary.domain.Diaries;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GalleryDAO {
    List<Diaries> getAIImg(Map<String, String> map);
}
