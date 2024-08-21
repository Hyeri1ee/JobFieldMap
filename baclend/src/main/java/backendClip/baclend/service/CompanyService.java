package backendClip.baclend.service;

import backendClip.baclend.dto.CompanyDTO;
import backendClip.baclend.dto.LocationDTO;
import backendClip.baclend.entity.CompanyEntity;
import backendClip.baclend.repository.CompanyRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Request;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;

import static backendClip.baclend.dto.CompanyDTO.convertEntityToDto;
import static org.apache.el.util.MessageFactory.get;

@Service
public class CompanyService {
  private final CompanyRepository companyRepository;

  public CompanyService(CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }

  public CompanyDTO getCompany(String position) {
    CompanyEntity company = companyRepository.findByRecruitPosition(position);
    return change(company);
  }

  private CompanyDTO change(CompanyEntity companyEntityList) {
      CompanyDTO dto = convertEntityToDto(companyEntityList);
    return dto;
  }

  public List<CompanyDTO> crollingAndSave() {
    String companyname;
    String recruitPosition;
    String reward;
    String source;
    String workDetail = "";
    Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/chromedriver.exe");
    System.setProperty("webdriver.chrome.driver", path.toString());
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--start-maximized");
    options.addArguments("--disable-popup-blocking");
    options.addArguments("--disable-default-apps");
    ChromeDriver driver = new ChromeDriver(options);

    driver.get("https://www.wanted.co.kr/wdlist/518?country=kr&job_sort=job.recommend_order&years=-1&locations=all");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    List<CompanyDTO> jobCards = new ArrayList<>();

    while (true) {
      try {
        List<WebElement> elements = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("li.Card_Card__WdaEk > div"))
        );

        for (int i = 0; i < 8; i++) {
          driver.get("https://www.wanted.co.kr/wdlist/518?country=kr&job_sort=job.recommend_order&years=-1&locations=all");
          wait = new WebDriverWait(driver, Duration.ofSeconds(20));
          elements = wait.until(
                  ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("li.Card_Card__WdaEk > div"))
          );

          WebElement jobCardElement = elements.get(i);

          int retryCount = 0;
          while (retryCount < 3) {
            try {
              StringBuilder workDetailBuilder = new StringBuilder();


              WebElement outside = jobCardElement.findElement(By.cssSelector("a"));
              companyname = outside.getAttribute("data-company-name");
              recruitPosition = outside.getAttribute("data-position-name");
              source = outside.getAttribute("href");

              WebElement fortheReward = jobCardElement.findElement(By.cssSelector("a > div > div > span"));
              reward = fortheReward.getText();

              driver.get(source);
              wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("article.JobDescription_JobDescription__dq8G5 > div > div")));

              List<WebElement> insideElements1 = driver.findElements(By.cssSelector("article.JobDescription_JobDescription__dq8G5 > div > div"));
              for (WebElement insideDivElement : insideElements1) {
                String one = insideDivElement.findElement(By.cssSelector("div > p > span")).getText();
                workDetailBuilder.append(one);
              }
              workDetail = workDetailBuilder.length() > 2000 ? workDetailBuilder.substring(0, 2000) : workDetailBuilder.toString();


              WebElement locationInfo = driver.findElement(By.cssSelector("article.JobWorkPlace_JobWorkPlace__Q6Gml > div > div.JobWorkPlace_JobWorkPlace__map__location____MvP > span"));
              String locationName = locationInfo.getText();


              CompanyDTO dto = new CompanyDTO();
              dto.setCompanyname(companyname);
              dto.setRecruitPosition(recruitPosition);
              dto.setReward(reward);
              dto.setSource(source);
              dto.setWorkDetail(workDetail);
              dto.setLocationName(locationName);
              jobCards.add(dto);

              // 브라우저 뒤로가기
              driver.navigate().back();
              // 페이지가 로드될 때까지 대기
              wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("li.Card_Card__WdaEk > div")));

              // 작업 성공 시 루프 종료
              break;

            } catch (StaleElementReferenceException e) {
              retryCount++;
              if (retryCount >= 3) {
                throw e;  // 재시도 횟수를 초과하면 예외를 던짐
              }
            }
          }
        }

        break;

      } catch (StaleElementReferenceException | NoSuchElementException e) {
        break;
      }
    }

    // 데이터베이스에 저장
    for (CompanyDTO dto : jobCards) {
      CompanyEntity entity = convertDtoToEntity(dto);
      companyRepository.save(entity);
    }

    driver.quit();
    return jobCards;
  }

  private CompanyEntity convertDtoToEntity(CompanyDTO dto) {
    CompanyEntity entity = new CompanyEntity();
    entity.setCompanyname(dto.getCompanyname());
    entity.setRecruitPosition(dto.getRecruitPosition());
    entity.setReward(dto.getReward());
    entity.setSource(dto.getSource());
    entity.setWorkDetail(dto.getWorkDetail());
    entity.setLocationName(dto.getLocationName());
    return entity;
  }

  public String getLongLatitude(String locationName) {
    String clientId = "m270k2o4ho"; //애플리케이션 클라이언트 아이디
    String clientSecret = "KxmfW01FTozjnUADLsT3G1zaKJX21SLYPRJUVSxL"; //애플리케이션 클라이언트 시크릿

    String text = null;
    try {
      text = URLEncoder.encode(locationName, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("검색어 인코딩 실패",e);
    }
    String apiURL = "https://openapi.naver.com/v1/search/kin.json?query=" + text + "&display=1";    // JSON 결과
    Map<String, String> requestHeaders = new HashMap<>();
    requestHeaders.put("X-Naver-Client-Id", clientId);
    requestHeaders.put("X-Naver-Client-Secret", clientSecret);
    String responseBody = get(apiURL,requestHeaders);


    return responseBody;
//    // 응답 처리
//    if (responseBody.getStatusCode() == HttpStatus.OK) {
//      try {
//        // JSON 파싱
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode root = objectMapper.readTree(response.getBody());
//
//        // 첫 번째 item에서 mapx, mapy 추출
//        JsonNode item = root.path("items").get(0);
//        Long longtitude = (long) item.path("mapx").asDouble();
//        Long latitude = (long) item.path("mapy").asDouble();
//
//        // DTO 생성 및 반환
//        LocationDTO locationDTO = new LocationDTO();
//        locationDTO.setLongtitude(longtitude);
//        locationDTO.setLatitude(latitude);
//        return locationDTO;
//
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
//    }

    //return null; // 오류 시 null 반환
  }
}
