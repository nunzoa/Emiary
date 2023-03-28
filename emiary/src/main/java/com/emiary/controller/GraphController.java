package com.emiary.controller;

import com.emiary.domain.Graph;
import com.emiary.service.GraphService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public List<Graph> bar(@AuthenticationPrincipal UserDetails userDetails){

        log.debug("바 그래프 실행되냐??");
        List<Graph> graph = graphService.countDiaryForGraph(userDetails.getUsername());

        return graph;
    }

    @ResponseBody
    @GetMapping("line")
    public List<Graph> line(String presentMonth, @AuthenticationPrincipal UserDetails userDetails){
        log.debug("이번달은? {} ", presentMonth);
        List<Graph> barGraph = graphService.lineFunction(presentMonth, userDetails.getUsername());

        return barGraph;
    }
}
