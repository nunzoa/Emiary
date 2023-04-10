package com.emiary.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;

public class ImageUtils {
    
    // 이미지를 Base64 문자열로 인코딩하는 함수
    public static String imageToString(BufferedImage img, String format) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(img, format, os);
        String encodedImage = Base64.getEncoder().encodeToString(os.toByteArray());
        return encodedImage;
    }

    // Base64 문자열을 이미지로 디코딩하는 함수
    public static BufferedImage stringToImage(String base64String) throws IOException {
        byte[] bytes = Base64.getDecoder().decode(base64String);
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        BufferedImage img = ImageIO.read(bis);
        return img;
    }
    
    public static void main(String[] args) throws IOException {
        // 이미지 파일 불러오기
        File file = new File("my_image.png");
        BufferedImage img = ImageIO.read(file);

        // 이미지를 Base64 인코딩하기
        String imgBase64 = imageToString(img, "PNG");

        // Base64 인코딩된 값을 디코딩해 이미지로 변환하기
        BufferedImage image = stringToImage(imgBase64);
    }
}

