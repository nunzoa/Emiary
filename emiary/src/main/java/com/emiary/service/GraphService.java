package com.emiary.service;


import com.emiary.domain.Graph;

import java.util.List;

public interface GraphService {
    List<Graph> countDiaryForGraph(String username);

    List<Graph> lineFunction(String presentMonth, String username);
}
