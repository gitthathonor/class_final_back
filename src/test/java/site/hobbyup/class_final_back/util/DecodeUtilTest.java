package site.hobbyup.class_final_back.util;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@Sql("classpath:db/truncate.sql") // 롤백 대신 사용 (auto_increment 초기화 + 데이터 비우기)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class DecodeUtilTest extends DecodeUtil {

    @Test
    public void saveDecodingImage_test() throws IOException {
        // given
        String encodeFile = "";

        // when
        String filePath = saveDecodingImage(encodeFile);
        System.out.println("테스트 : " + filePath);

    }

}
