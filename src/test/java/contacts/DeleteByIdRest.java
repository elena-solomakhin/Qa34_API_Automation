package contacts;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.ContactDto;
import dto.ErrorDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class DeleteByIdRest {
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";
int id;
    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contacts-telran.herokuapp.com";
        RestAssured.basePath = "api";

        int i = (int) (System.currentTimeMillis() / 1000) % 3600;
        ContactDto contactDto = ContactDto.builder()
                .name("Lena")
                .lastName("Popova")
                .email("lena" + i + "@gmail.com")
                .address("JJJ")
                .description("he")
                .phone("097860" + i)
                .build();
         id= given()
                .header("Authorization", token)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .when()
                .post("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().path("id"); //vitagivat'
//        System.out.println(id);
    }
@Test
public void deletedByIdSuccess(){
        given()
                .header("Authorization", token)
                .when()
                .delete("contact/"+id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("status",containsString("Contact was deleted!"));

    }
    @Test
    public void deletedByWrongAuth(){
        given()
                .header("Authorization", "ooolllllen")
                .when()
                .delete("contact/"+id)
                .then()
                .assertThat().body("message",containsString("Wrong token format!"))
                .assertThat().statusCode(401);

    }

    @Test
    public void deletedByWrongFormatId(){
       ErrorDto errorDto= given()
                .header("Authorization", token)
                .when()
                .delete("contact/-3")
                .then()
                .assertThat().body("message",containsString("not found!"))
    .assertThat().statusCode(404)
        .extract().as(ErrorDto.class);
        System.out.println(errorDto.getMessage());
    }
}
