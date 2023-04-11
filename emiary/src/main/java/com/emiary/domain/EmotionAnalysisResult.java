package com.emiary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmotionAnalysisResult {
	public double score;
    private String noun;
    private List<String> wordsForAi;
}
