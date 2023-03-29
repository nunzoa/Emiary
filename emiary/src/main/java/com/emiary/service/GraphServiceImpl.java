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
    public List<Graph> barFunction(String presentYear, String username) {
        Map<String, String> map = new HashMap<>();
        map.put("presentYear", presentYear);
        map.put("username", username);
        List<Graph> graph = graphDAO.barFunction(map);
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
    public List<Graph> doughnutFunction(String givenMonth, String username) {
        Map<String, String> map = new HashMap<>();
        map.put("givenMonth", givenMonth);
        map.put("username", username);
        List<Graph> graph = graphDAO.doughnutFunction(map);
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
