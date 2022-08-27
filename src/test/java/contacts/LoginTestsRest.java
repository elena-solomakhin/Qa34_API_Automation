package contacts;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.AuthResponceDto;
import dto.ErrorDto;
import dtoSuper.AuthRequestDtoSS;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class LoginTestsRest {
    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contacts-telran.herokuapp.com";
        RestAssured.basePath = "api";
    }

    @Test
    public void loginSuccess() throws IOException {
        AuthRequestDtoSS auth = AuthRequestDtoSS.builder().email("noa@gmail.com").password("Nnoa12345$").build();
        AuthResponceDto responceDto = given()
                .body(auth)
                .contentType("application/json")
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .as(AuthResponceDto.class);
        System.out.println(responceDto.getToken());
    }

    @Test
    public void loginWrongEmail() {
        AuthRequestDtoSS auth = AuthRequestDtoSS.builder().email("noagmail.com").password("Nnoa12345$").build();
        ErrorDto errorDto = given()
                .body(auth)
                .contentType(ContentType.JSON) // == .contentType("application/json")
                .post("login")
                .then()
                .assertThat().statusCode(400)
                .extract()
                .as(ErrorDto.class);
        Assert.assertEquals(errorDto.getMessage(), "Wrong email format! Example: name@mail.com");//assert from test NG on ok-http
    }

    @Test
    public void loginWrongEmaiCheck() {
        AuthRequestDtoSS auth = AuthRequestDtoSS.builder().email("noagmail.com").password("Nnoa12345$").build();
given()
        .body(auth)
        .contentType(ContentType.JSON)
        .when()
        .post("login")
        .then()
        .assertThat().statusCode(400)
        .assertThat().body("message",containsString("Wrong email format! Example: name@mail.com"))
        .assertThat().body("details",containsString("uri=/api/login"));

    }
}