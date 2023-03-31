package com.emiary.graphTest;

import com.emiary.domain.Graph;
import com.emiary.service.GraphService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class GraphTest {

    @Autowired
    GraphService graphService;

}
