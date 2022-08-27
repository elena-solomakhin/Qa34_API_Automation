package superscheduler;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dtoSuper.ErrorDtoSS;
import dtoSuper.GetAllRecords;
import dtoSuper.PeriodDtoSS;
import dtoSuper.RecordDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class GetAllRecordsRest {
    String token= "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im1vYUBnbWFpbC5jb20ifQ.FdyRws90Gn4IbInlRWOYZO3VnFZUw4zYJHI42vuSKHI";
    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://super-scheduler-app.herokuapp.com";
        RestAssured.basePath = "api";
    }

    @Test
    public void GetAllRecordsSuccess() {
        PeriodDtoSS periodDto = PeriodDtoSS.builder()
                .monthFrom(9)
                .monthTo(10)
                .yearFrom(2022)
                .yearTo(2022)
                .build();
        GetAllRecords getList= given()
                .header("Authorization", token)
                .body(periodDto)
                .contentType(ContentType.JSON)
                .when()
                .post("records")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .as(GetAllRecords.class);
//                .extract().path("contacts");
//
        for (RecordDTO recordDTO: getList.getRecords()){
            System.out.println(recordDTO.toString());
            System.out.println("**********");
        }

    }
    @Test
    public void GetAllRecordsWrongPeriod() {
        PeriodDtoSS periodDto = PeriodDtoSS.builder()
                .monthFrom(59)
                .monthTo(10)
                .yearFrom(2022)
                .yearTo(2022)
                .build();
        ErrorDtoSS errorDtoSS = given()
                .header("Authorization", token)
                .body(periodDto)
                .contentType(ContentType.JSON)
                .when()
                .post("records")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message",containsString("Wrong month period! Month from and to need be in range 1-12"))
                .extract().as(ErrorDtoSS.class);

//
//


    }
    }
