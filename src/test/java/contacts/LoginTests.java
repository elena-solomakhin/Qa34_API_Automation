package contacts;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTests {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
OkHttpClient client =new OkHttpClient();
    @Test
    public void loginSuccess() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("noa@gmail.com").password("Nnoa12345$").build();
        RequestBody requestBody = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

 Response response= client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
    }
    @Test
    public void loginUnsuccess1() throws IOException {
        AuthRequestDto auth= AuthRequestDto.builder().email("miagmail.com").password("Mmia12345$").build();
        RequestBody requestBody= RequestBody.create(gson.toJson(auth),JSON);

        Request request= new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

       Response response= client.newCall(request).execute();
       Assert.assertFalse(response.isSuccessful());
       Assert.assertEquals(response.code(),400);

    }
    @Test
    public void loginUnsuccess2() throws IOException {
        AuthRequestDto auth= AuthRequestDto.builder().email("noa@gmail.com").password("Mmia12345$").build();
        RequestBody requestBody= RequestBody.create(gson.toJson(auth),JSON);

        Request request= new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response= client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),401);

    }
    @Test
    public void loginUnsuccess3() throws IOException {
        AuthRequestDto auth= AuthRequestDto.builder().email("noa@gmail.com").password("Mm").build();
        RequestBody requestBody= RequestBody.create(gson.toJson(auth),JSON);

        Request request= new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response= client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);

    }

}