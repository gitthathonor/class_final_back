package site.hobbyup.class_final_back.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import site.hobbyup.class_final_back.config.dummy.DummyEntity;
import site.hobbyup.class_final_back.config.enums.UserEnum;
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.category.CategoryRepository;
import site.hobbyup.class_final_back.dto.user.UserReqDto.JoinReqDto;

@Sql("classpath:db/truncate.sql") // 롤백 대신 사용 (auto_increment 초기화 + 데이터 비우기)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class UserApiControllerTest extends DummyEntity {
  private static final String APPLICATION_JSON_UTF8 = "application/json; charset=utf-8";
  private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded; charset=utf-8";

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper om;

  @Autowired
  private CategoryRepository categoryRepository;

  @BeforeEach
  public void setUp() {

    Category beauty = categoryRepository.save(newCategory("뷰티"));
    Category sports = categoryRepository.save(newCategory("스포츠"));
    Category dance = categoryRepository.save(newCategory("댄스"));
    Category music = categoryRepository.save(newCategory("음악"));
    Category art = categoryRepository.save(newCategory("미술"));
    Category crafts = categoryRepository.save(newCategory("공예"));
    Category game = categoryRepository.save(newCategory("게임"));
    Category others = categoryRepository.save(newCategory("기타"));

  }

  @Test
  public void join_test() throws Exception {
    // given
    List<Long> categoryIds = new ArrayList<>();
    categoryIds.add(1L);
    categoryIds.add(2L);
    categoryIds.add(3L);

    JoinReqDto joinReqDto = new JoinReqDto();
    joinReqDto.setUsername("ssar");
    joinReqDto.setPassword("1234");
    joinReqDto.setEmail("ssar@nate.com");
    joinReqDto.setPhoneNum("01011112222");
    joinReqDto.setRole(UserEnum.USER);
    joinReqDto.setCategoryIds(categoryIds);
    String requestBody = om.writeValueAsString(joinReqDto);

    // when
    ResultActions resultActions = mvc
        .perform(post("/api/join").content(requestBody)
            .contentType(APPLICATION_JSON_UTF8));
    String responseBody = resultActions.andReturn().getResponse().getContentAsString();
    System.out.println("디버그 : " + responseBody);

    // then
    resultActions.andExpect(status().isCreated());
    resultActions.andExpect(jsonPath("$.data.username").value("ssar"));
    resultActions.andExpect(jsonPath("$.data.interestList[0].name").value("뷰티"));
  }

  // @Test
  // public void deleteById_test() throws Exception {
  // // given
  // Long id = 1L;

  // // when
  // ResultActions resultActions = mvc
  // .perform(delete("/api/user/" + id)
  // .contentType(APPLICATION_JSON_UTF8));
  // String responseBody =
  // resultActions.andReturn().getResponse().getContentAsString();
  // System.out.println("디버그 : " + responseBody);

  // // then
  // resultActions.andExpect(status().isOk());
  // }
}
