package superscheduler;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.AuthResponceDto;
import dto.ErrorDto;
import dtoSuper.AuthRequestDtoSS;
import dtoSuper.AuthResponceDtoSS;
import dtoSuper.ErrorDtoSS;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTestOkHttp {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Test
    public void loginSuccess() throws IOException {
        AuthRequestDtoSS auth = AuthRequestDtoSS.builder().email("noa@gmail.com").password("Nnoa12345$").build();
        RequestBody requestBody = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        AuthResponceDtoSS responceDtoSS = gson.fromJson(response.body().string(), AuthResponceDtoSS.class);
        responceDtoSS.getToken();
        System.out.println("token" + responceDtoSS.getToken());
        //Assert.assertTrue(ErrorDtoSS.getMessage().contains("Wrong email format!"));
    }
    @Test
    public void loginWrongPassword() throws IOException {
        AuthRequestDtoSS auth = AuthRequestDtoSS.builder().email("noa@gmail.com").password("Mmmma12345").build();
        RequestBody requestBody = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 401);

        ErrorDtoSS errorDtoSS = gson.fromJson(response.body().string(), ErrorDtoSS.class);

        Assert.assertEquals(errorDtoSS.getCode(), 401);
        Assert.assertEquals(errorDtoSS.getMessage(), "Wrong email or password");
        Assert.assertTrue(errorDtoSS.getMessage().contains("Wrong email o"));

    }
//    eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo

}
