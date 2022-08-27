package superscheduler;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.ErrorDto;
import dtoSuper.AuthRequestDtoSS;
import dtoSuper.AuthResponceDtoSS;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class LoginTestSSRest {
    @BeforeMethod
    public void preCondition(){
        RestAssured.baseURI="https://super-scheduler-app.herokuapp.com";
        RestAssured.basePath="api";
    }
    @Test
    public void loginSuccess() {
    AuthRequestDtoSS auth = AuthRequestDtoSS.builder().email("moa@gmail.com").password("Nnoa12345$").build();
        AuthResponceDtoSS responceDtoSS=given()
        .body(auth)
        .contentType("application/json")
        .when()
        .post("login")
        .then()
        .assertThat().statusCode(200)
        .extract()
        .as(AuthResponceDtoSS.class);
        System.out.println(responceDtoSS.getToken());
    }
    @Test
    public void loginUnSuccessWrongPassword() {
        AuthRequestDtoSS auth = AuthRequestDtoSS.builder().email("moa@gmail.com").password("nnnnnN12345$").build();
        ErrorDto errorDto =given()
                .body(auth)
                .contentType(ContentType.JSON)
                .post("login")
                .then()
                .assertThat().statusCode(401)
                .extract()
                .as(ErrorDto.class);
        Assert.assertEquals(errorDto.getMessage(), "Wrong email or password");//assert from test NG on ok-http


    }
//token= "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im1vYUBnbWFpbC5jb20ifQ.FdyRws90Gn4IbInlRWOYZO3VnFZUw4zYJHI42vuSKHI"
}
