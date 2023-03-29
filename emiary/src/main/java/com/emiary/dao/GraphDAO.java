package com.emiary.dao;

import com.emiary.domain.Graph;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GraphDAO {
    List<Graph> barFunction(Map<String, String> map);
    List<Graph> lineFunction(Map<String, String> map);
    List<Graph> monthlyLineFunction(Map<String, String> map);
    List<Graph> doughnutFunction(Map<String, String> map);

}
