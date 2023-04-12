package com.emiary.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

@Slf4j
public class InstagramParser {


    public String getValue(String keyword) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://43.201.101.83:8000/search/");
        httpPost.setHeader("Content-type", "application/json");

        // JSON 객체 생성
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonObject = objectMapper.createObjectNode();
        ((ObjectNode) jsonObject).put("keyword", keyword);

        StringEntity stringEntity = new StringEntity(objectMapper.writeValueAsString(jsonObject), "UTF-8");
        httpPost.setEntity(stringEntity);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        httpResponse.setHeader("Content-type", "application/json");
//        httpResponse.setHeader("Cross-Origin-Resource-Policy", "same-origin");


        String responseBody = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");


        return responseBody;
    }



}
