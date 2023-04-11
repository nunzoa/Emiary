package com.emiary.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.emiary.util.PapagoTranslate;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class ImageGenerationService {

    public final String REST_API_KEY = "${64f70d198c9261dcdd9023dcff52737f}";
    public final String T2I_URL = "https://api.kakaobrain.com/v1/inference/karlo/t2i";

    public void generateImage( String text, int diary_id ) throws IOException {
        log.debug("이미지 서비스 들어옴 텍스트 : {}, 다이어리번호 : {}",text,diary_id);
        
        String translatedword = PapagoTranslate.post(text);
        log.debug("번역되서 온 데이터 : {}", translatedword );
        
       
            // JSON 문자열을 Java 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(translatedword);

            // "translatedText" 필드의 값을 뽑아내기
            String translatedText = jsonNode.get("message").get("result").get("translatedText").asText();
            System.out.println("Translated Text: " + translatedText);
      
        
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + "64f70d198c9261dcdd9023dcff52737f");
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        String requestBody = objectMapper.writeValueAsString(Collections.singletonMap("prompt",
                Collections.singletonMap("text", translatedText)));
        log.debug(requestBody);
        
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(T2I_URL, HttpMethod.POST, requestEntity, String.class);
        String responseJson = responseEntity.getBody();
        
        log.debug("responseJson!!! {}", responseJson);
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ImageResponse response = mapper.readValue(responseJson, ImageResponse.class);
        String base64ImageString = response.getImages().get(0).getImage();
        log.debug("base64 : {}", base64ImageString);
        
        // Base64 이미지 문자열을 byte 배열로 디코딩
        byte[] decodedBytes = Base64.getDecoder().decode(base64ImageString);
        if(decodedBytes!=null) {
        	log.debug("바이트배열 있다");
        }
        
     // jpg 파일로 저장
        File path = new File(".");
        log.debug("현재경로:{}", path.getCanonicalPath());
        //C:\Java\workspace\emiary\staic
        //C:\\Java\\workspace\\emiary\\src\\main\\resources\\static\\aiimages/
        
        String filePath = "C:/AIimages/" + diary_id + ".jpg";
        File jpgFile = new File(filePath);
        try (FileOutputStream fos = new FileOutputStream(jpgFile)) {
            fos.write(decodedBytes);
        }

        System.out.println("jpg 파일이 저장되었습니다.");
        
        /////////////////////////////////////////////////////
        
        // BufferedImage 로 변환
//        ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
//        BufferedImage bufferedImage = ImageIO.read(bis);
        
        
//        if(bufferedImage==null) {
//        	log.debug("버퍼드 이미지 없다.");
//        }
        // BufferedImage 를 jpg 형식으로 저장
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write(bufferedImage, "jpg", baos);
//        baos.flush();
//        byte[] jpgBytes = baos.toByteArray();
//        if(jpgBytes==null) {
//        	log.debug("jpg소스 없다1");
//        }
//        baos.close();
        
     // 저장할 파일 경로와 파일 이름
//        String filePath = "static/aiimages/test1.jpg";

        // jpgBytes를 파일로 저장
//        try (FileOutputStream fos = new FileOutputStream(filePath)) {
//        	if(jpgBytes==null) {
//            	log.debug("jpg소스 없다2");
//            }
//            fos.write(jpgBytes);
//            fos.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if (jpgBytes == null) {
//            throw new IllegalArgumentException("이미지를 생성할 수 없습니다.");
//        }
//        
        
        // 클라우디너리에 이미지 업로드
//        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
//                "cloud_name", "ddwvhzk2g",
//                "api_key", "179111432387239",
//                "api_secret", "KorgnmN6ui2hBmmYFT33SigsFkc"));
//        Map<String, String> uploadResult = cloudinary.uploader().upload("https://upload.wikimedia.org/wikipedia/commons/a/ae/Olympic_flag.jpg", ObjectUtils.asMap("myfolder", "my_folder_name"));

        // 업로드된 이미지 URL 반환
//        return uploadResult.get("url");
    }
//
//
   private static class ImageResponse {
        private java.util.List<ImageData> images;

        public java.util.List<ImageData> getImages() {
            return images;
        }

        public void setImages(java.util.List<ImageData> images) {
            this.images = images;
        }
    }

   private static class ImageData {
        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}

