package com.emiary.service;


import com.emiary.domain.Graph;

import java.util.List;

public interface GraphService {
    List<Graph> countDiaryForGraph(String username);

    List<Graph> lineFunction(String presentMonth, String presentYear, String username);

    List<Graph> radarFunction(String presentMonth, String username);

    List<Graph> monthlyLineFunction(String presentYear, String username);
}
