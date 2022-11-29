package site.hobbyup.class_final_back.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;

public class DecodeUtil {
    private static FileOutputStream fos;

    public static String saveDecodingImage(String encodeFile) throws IOException {

        // base64 디코딩
        // byte[] stringToByte = encodeFile.getBytes(); // 문자열을 바이트로 변환
        byte[] decodeByte = Base64.decodeBase64(encodeFile);

        // 이미지 이름
        String fileName = UUID.randomUUID().toString();
        String filePath = "C:\\Temp\\upload\\" + fileName + ".jpg";

        // 이미지 저장
        fos = new FileOutputStream(filePath); // 현위치에 path명으로 파일생성
        fos.write(decodeByte); // 파일에 buffer의 모든 내용 출력
        fos.close();
        return filePath;
    }
}
