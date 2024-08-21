package backendClip.baclend.service;

import backendClip.baclend.dto.CompanyDTO;
import backendClip.baclend.entity.CompanyEntity;
import backendClip.baclend.repository.CompanyRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static backendClip.baclend.dto.CompanyDTO.convertEntityToDto;

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

              CompanyDTO dto = new CompanyDTO();
              dto.setCompanyname(companyname);
              dto.setRecruitPosition(recruitPosition);
              dto.setReward(reward);
              dto.setSource(source);
              dto.setWorkDetail(workDetail);
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
    return entity;
  }
}
