package backendClip.baclend.service;

import backendClip.baclend.dto.CompanyDTO;
import backendClip.baclend.entity.CompanyEntity;
import backendClip.baclend.repository.CompanyRepository;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static backendClip.baclend.dto.CompanyDTO.convertEntityToDto;

@Service
public class CompanyService {
  private final CompanyRepository companyRepository;

  public CompanyService(CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }


  public List<CompanyDTO> getCompanies(String position) {
    List<CompanyEntity> companyEntityList = companyRepository.findByRecruitPosition(position);
    return change(companyEntityList);
  }

  private List<CompanyDTO> change(List<CompanyEntity> companyEntityList) {
    List<CompanyDTO> companyDTOList = new ArrayList<>();
    for ( CompanyEntity entity : companyEntityList){
      CompanyDTO dto = convertEntityToDto(entity);
      companyDTOList.add(dto);
    }
    return companyDTOList;
  }

  public List<CompanyDTO> crollingAndSave() {
    String companyname;
    String recruitPosition;
    String reward;
    String source;
    String workDetail = "";

    Path path = Paths.get(System.getProperty("user.dir"),"src/main/resources/chromedriver.exe");
    // WebDriver 경로 설정
    System.setProperty("webdriver.chrome.driver",path.toString());
    // WebDriver 옵션 설정
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--start-maximized"); //전체화면으로 실행
    options.addArguments("--diable-popup-blocking"); //팝업 무시
    options.addArguments("--disable-default-apps"); //기본앱 사용안함
    // WebDriver 객체 생성
    ChromeDriver driver = new ChromeDriver(options);

    driver.get("https://www.wanted.co.kr/wdlist/518?country=kr&job_sort=job.recommend_order&years=-1&locations=all");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    /*crolling*/
    List<WebElement> elements = driver.findElements(By.cssSelector("li.Card_Card__WdaEk > div"));
    List<CompanyDTO> jobCards = new ArrayList<>(); // 여기에 크롤링한 데이터를 저장

    // 각 elements에 대하여
    for (WebElement jobCardElement : elements) {
      WebElement outside = jobCardElement.findElement(By.cssSelector("a"));
      companyname = outside.getAttribute("data-company-name");
      recruitPosition = outside.getAttribute("data-position-name");
      source = outside.getAttribute("href");

      WebElement fortheReward = jobCardElement.findElement(By.cssSelector("a > div > div > span"));
      reward = fortheReward.getText();

      driver.get(source);

      List<WebElement> insideElements1 = driver.findElements(By.cssSelector("article.JobDescription_JobDescription__dq8G5 > div > div"));
      for (WebElement insideDivElement : insideElements1) {
        String one = insideDivElement.findElement(By.cssSelector("div > p > span")).getText();
        workDetail += one;
      }

      // CompanyDTO 객체 생성 및 jobCards 리스트에 추가
      CompanyDTO dto = new CompanyDTO();
      dto.setCompanyname(companyname);
      dto.setRecruitPosition(recruitPosition);
      dto.setReward(reward);
      dto.setSource(source);
      dto.setWorkDetail(workDetail);
      jobCards.add(dto);
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
