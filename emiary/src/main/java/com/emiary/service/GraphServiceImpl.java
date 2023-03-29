package com.emiary.service;

import com.emiary.dao.GraphDAO;
import com.emiary.domain.Graph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GraphServiceImpl implements GraphService{

    @Autowired
    GraphDAO graphDAO;


    @Override
    public List<Graph> countDiaryForGraph(String username) {
        List<Graph> graph = graphDAO.countDiaryForGraph(username);
        return graph;
    }

    @Override
    public List<Graph> lineFunction(String presentMonth, String presentYear, String username) {
        Map<String, String> map = new HashMap<>();
        map.put("presentYear", presentYear);
        map.put("presentMonth", presentMonth);
        map.put("username", username);
        List<Graph> graph = graphDAO.lineFunction(map);
        return graph;
    }

    @Override
    public List<Graph> radarFunction(String presentMonth, String username) {
        Map<String, String> map = new HashMap<>();
        map.put("presentMonth", presentMonth);
        map.put("username", username);
        List<Graph> graph = graphDAO.radarFunction(map);
        return graph;
    }

    @Override
    public List<Graph> monthlyLineFunction(String presentYear, String username) {
        Map<String, String> map = new HashMap<>();
        map.put("presentYear", presentYear);
        map.put("username", username);
        List<Graph> graph = graphDAO.monthlyLineFunction(map);
        return graph;
    }
}
