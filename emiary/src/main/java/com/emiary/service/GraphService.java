package com.emiary.service;


import com.emiary.domain.Graph;

import java.util.List;

public interface GraphService {
    List<Graph> barFunction(String presentYear, String username);

    List<Graph> lineFunction(String presentMonth, String presentYear, String username);

    List<Graph> monthlyLineFunction(String presentYear, String username);

    List<Graph> doughnutFunction(String givenMonth, String username);


}
