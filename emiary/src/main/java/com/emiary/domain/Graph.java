package com.emiary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Graph {

    String yearAndMonth;
    int countPerMonth;
    double emotionScore;
    String yearAndMonthAndDay;
}
