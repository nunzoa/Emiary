package com.emiary.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import com.emiary.domain.EmotionAnalysisResult;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class EmotionAnalyzer {

	// 감정사전 파일의 경로를 지정
    private static final String SENTIWORD_DICT_PATH = "sentiword_info.csv";


    /**
     * "sentiword_info.csv"의 데이터를 MAP형태로 저장
     * @return 맵에 담긴 감성사전 데이터들
     */
    private static Map<String, Double> loadSentiWordDict() {
        Map<String, Double> dict = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(SENTIWORD_DICT_PATH),  "EUC-KR"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 3) {
                    String rootword = fields[1];
                    double score = Double.parseDouble(fields[2]);
                    dict.put(rootword, score);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dict;
    }

    /**
     * 빈출단어 반환하기
     * @param nouns 형태소분석으로 정리된 명사List
     * @return String 빈출단어
     */
    public static String findMostFrequentString(List<String> nouns) {
        // 리스트 길이가 1일 경우 바로 리턴
        //if (nouns.size() == 1) {
        //     return nouns.get(0);
        // }
        System.out.print("\n 형태소 리스트 길이 : " + nouns.size() + "\n");
        Map<String, Integer> counts = new HashMap<>();

        // 각 문자열의 카운트 계산
        for (String s : nouns) {
            if (counts.containsKey(s)) {
                counts.put(s, counts.get(s) + 1);
            } else {
                counts.put(s, 1);
            }
        }

        int maxCount = 0;
        List<String> maxCountStrings = new ArrayList<>();

        // 가장 큰 카운트값 찾기
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            int count = entry.getValue();
            if (count > maxCount) {
                maxCount = count;
                maxCountStrings.clear();
                maxCountStrings.add(entry.getKey());
            } else if (count == maxCount) {
                maxCountStrings.add(entry.getKey());
            }
        }

        // 가장 큰 카운트값을 가진 문자열 중 첫 번째 반환
        return maxCountStrings.get(0);
    }

    /**
     * komoran 형태소분석 API로 다이어리 본문을 각 각의 품사별로 분리 한후 감정사전
     * 데이터가 담긴 dict MAP과 매핑하여 각 단어의 점수를 scores Arraylist에 저
     * 장후 list길이로 나누어 평균감정지수를 계산.
     * @param content 일기의 본문 파리미터
     * @return double형의 평균감정지수를 리턴(최저-2 최고 2, 소숫점 둘째자리 까지)
     */
    public static EmotionAnalysisResult analyzeEmotion(String content) {
        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
        Map<String, Double> sentiWordDict = loadSentiWordDict();
        //log.debug("감정사전데이터 : {}",sentiWordDict);
        EmotionAnalysisResult result = new EmotionAnalysisResult();
        //명사 리스트
        List<String> nouns = new ArrayList<>();
        // 빈출 단어값
        //String noun="";

        List<Token> tokens = komoran.analyze(content).getTokenList();
        log.debug("토큰 : {}",tokens.toString());
        List<Double> scores = new ArrayList<>();

        for (Token token : tokens) {
            String pos = token.getPos();
            //log.debug("pos : {}",pos);
            String word = token.getMorph();
            //log.debug("Morph : {}",word);
            if (pos.startsWith("NNG") || pos.startsWith("V") || pos.startsWith("VA")) {
                if (sentiWordDict.containsKey(word)) {
                    double score = sentiWordDict.get(word);
                    scores.add(score);
                    log.debug("계산되기전 원소들 : {}",scores);
                }

            }
            if (pos.startsWith("NNG") ) {
                nouns.add(word);
            }

        }
        System.out.print(" 이거 : " + nouns);
        // 명사리스트가 비었거나 비교할 데이터가 없을 때 빈문자열 처리
        if (nouns.isEmpty() || nouns.size()==1) {
            result.setNoun("");
        }else  {
            // 빈출단어 도출후 EmotionAnalysisResult 객체에 저장
            String noun = findMostFrequentString(nouns);
            result.setNoun(noun);
            List<String> wordsForAi = wordsForAi(nouns);
            result.setWordsForAi(wordsForAi);
        }


        // 형태소와 감정사전 매핑 결과가 없거나 형태소가 너무 적을 경우 감정점수 10으로 리턴
        if (scores.isEmpty() || scores.size()<10) {
            result.setScore(10);
            //result.setNoun(noun);
            return  result;
        } else {
            double sum = scores.stream().mapToDouble(Double::doubleValue).sum();
            double avg = sum / scores.size();

            BigDecimal bd = BigDecimal.valueOf(avg);
            BigDecimal roundedValue = bd.setScale(2, RoundingMode.HALF_UP);
            double score = roundedValue.doubleValue();
            result.setScore(score);
            //     if (avg >= 0.5) {
            //       return "positive";
            //    } else if (avg <= -0.5) {
            //       return "negative";
            //    } else {
            //       return "neutral";
            //     }
        }
        return result;
    }

    public static List<String> wordsForAi(List<String> nouns) {
        // 단어 별 빈도수를 저장할 Map 생성
        Map<String, Integer> wordFrequency = new HashMap<>();
        // 단어 별 빈도수 카운트
        for (String word : nouns) {
            if (wordFrequency.containsKey(word)) {
                wordFrequency.put(word, wordFrequency.get(word) + 1);
            } else {
                wordFrequency.put(word, 1);
            }
        }
        System.out.print("워드프리퀀시 맵: " + wordFrequency);

        // 빈도수를 기준으로 내림차순으로 정렬된 단어 리스트 생성
        List<String> sortedWords = new ArrayList<>(wordFrequency.keySet());
        System.out.print("소티드워즈 : " + sortedWords);
        Collections.sort(sortedWords, (word1, word2) -> wordFrequency.get(word2) - wordFrequency.get(word1));
        System.out.print("반복수로 내림차순된 결과 : " + sortedWords);
        // 상위 5개의 단어를 반환
        return sortedWords.subList(0, Math.min(6, sortedWords.size()));
    }
}

