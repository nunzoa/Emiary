package com.emiary.controller;

import com.emiary.domain.Graph;
import com.emiary.service.GraphService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("graph")
public class GraphController {

    @Autowired
    GraphService graphService;

    @GetMapping("home")
    public String graph(){
        return "graphView/graph";
    }

    @ResponseBody
    @GetMapping("bar")
    public List<Graph> bar(String presentYear, @AuthenticationPrincipal UserDetails userDetails){
        log.debug("바그래프 {}", presentYear);
        List<Graph> graph = graphService.barFunction(presentYear, userDetails.getUsername());
        return graph;
    }

    @ResponseBody
    @GetMapping("line")
    public List<Graph> line(String presentMonth, String presentYear, @AuthenticationPrincipal UserDetails userDetails){
        log.debug("line +그래프 {}", presentMonth);
        log.debug("가지? presentMonth : {} presentYear : {} ", presentMonth, presentYear);
        List<Graph> lineGraph = graphService.lineFunction(presentMonth, presentYear, userDetails.getUsername());
        log.debug("이거 값이 없어? {}", lineGraph);
        return lineGraph;
    }

    @ResponseBody
    @GetMapping("monthlyLine")
    public List<Graph> monthlyLine(String presentYear, @AuthenticationPrincipal UserDetails userDetails){

        List<Graph> radarGraph = graphService.monthlyLineFunction(presentYear, userDetails.getUsername());
        return radarGraph;
    }


    @ResponseBody
    @GetMapping("doughnut")
    public List<Graph> doughnutFunction(String givenMonth, @AuthenticationPrincipal UserDetails userDetails){
        log.debug("givenMonth : {}", givenMonth);
        List<Graph> doughnutGraph = graphService.doughnutFunction(givenMonth, userDetails.getUsername());
        return doughnutGraph;
    }
}
