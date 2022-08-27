package superscheduler;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dtoSuper.DateDto;
import dtoSuper.ErrorDtoSS;
import dtoSuper.RecordDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AddRecordTestSSrest {
    String token= "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im1vYUBnbWFpbC5jb20ifQ.FdyRws90Gn4IbInlRWOYZO3VnFZUw4zYJHI42vuSKHI";
    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://super-scheduler-app.herokuapp.com";
        RestAssured.basePath = "api";
    }

    @Test
    public void AddNewRecordsSuccess() {
        RecordDTO recordDTO = RecordDTO.builder()
                .breaks(2)
                .currency("Currency")
                .date(DateDto.builder()
                        .dayOfMonth(5)
                        .dayOfWeek("2")
                        .month(9)
                        .year(2022)
                        .build())
                .hours(3)
//              .id(0)
                .timeFrom("18:00")
                .timeTo("21:00")
                .title("Meeting")
                .type("type")
                .totalSalary(500)
                .wage(40)
                .build();

      RecordDTO record1=given()
                .header("Authorization", token)
                .body(recordDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("record")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .as(RecordDTO.class);


    }
    @Test
    public void AddNewRecordsSuccess2() {
        RecordDTO recordDTO = RecordDTO.builder()
                .breaks(2)
                .currency("Currency")
                .date(DateDto.builder()
                        .dayOfMonth(5)
                        .dayOfWeek("2")
                        .month(9)
                        .year(2022)
                        .build())
                .hours(3)
//              .id(0)
                .timeFrom("18:00")
                .timeTo("21:00")
                .title("Meeting")
                .type("type")
                .totalSalary(500)
                .wage(40)
                .build();

       int id=given()
                .header("Authorization", token)
                .body(recordDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("record")
                .then()
                .assertThat().statusCode(200)
                 .assertThat().body("id",is(not(equalTo(recordDTO.getId()))))
                .extract().path("id"); //vitagivat'
        System.out.println("id: "+id);

    }
    @Test
    public void AddUnSuccessWrongAuth() {
        RecordDTO recordDTO = RecordDTO.builder()
                .breaks(2)
                .currency("Currency")
                .date(DateDto.builder()
                        .dayOfMonth(5)
                        .dayOfWeek("2")
                        .month(9)
                        .year(2022)
                        .build())
                .hours(3)
//              .id(0)
                .timeFrom("18:00")
                .timeTo("21:00")
                .title("Meeting")
                .type("type")
                .totalSalary(500)
                .wage(40)
                .build();

        ErrorDtoSS errorDtoSS =given()
                .header("Authorization", "")
                .body(recordDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("record")
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("message",containsString("Wrong authorization format"))
                .extract().as(ErrorDtoSS.class); //vitagivat'


    }

}