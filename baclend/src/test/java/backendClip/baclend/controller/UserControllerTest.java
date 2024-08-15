package backendClip.baclend.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
  @Autowired
  ObjectMapper mapper;

  @Autowired
  MockMvc mvc;

  private static final String BASE_URL = "/api/v1";

  @Test
  @DisplayName("저장 테스트")
  void save_test() throws Exception {
    //given
    String title = "Test title";
    String content = "Test content";
    String author = "gorany";
    //when
    /**
     * Object를 JSON으로 변환
     * */
    String body = mapper.writeValueAsString(
            PostsSaveRequestDto.builder().author(author).content(content).title(title).build()
    );
    //then
    mvc.perform(post(BASE_URL + "/posts")
                    .content(body) //HTTP Body에 데이터를 담는다
                    .contentType(MediaType.APPLICATION_JSON) //보내는 데이터의 타입을 명시
            )
            .andExpect(status().isOk())
            .andExpect(content().string("1"));
  }
}