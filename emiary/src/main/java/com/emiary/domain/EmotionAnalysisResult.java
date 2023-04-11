package com.emiary.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmotionAnalysisResult {
	public double score;
    private String noun;
    private List<String> wordsForAi;

}
