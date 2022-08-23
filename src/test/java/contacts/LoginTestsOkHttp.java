package contacts;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.AuthResponceDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTestsOkHttp {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Test
    public void loginSuccess() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("noa@gmail.com").password("Nnoa12345$").build();
        RequestBody requestBody = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        AuthResponceDto responceDto = gson.fromJson(response.body().string(), AuthResponceDto.class);
        responceDto.getToken();
        System.out.println("token" + responceDto.getToken());
    }

    @Test
    public void loginUnsuccess1() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("miagmail.com").password("Mmia12345$").build();
        RequestBody requestBody = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);
        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);

        Assert.assertEquals(errorDto.getCode(), 400);
        Assert.assertEquals(errorDto.getMessage(), "Wrong email format! Example: name@mail.com");
        Assert.assertTrue(errorDto.getMessage().contains("Wrong email format!"));
    }

    @Test
    public void loginWrongPassword() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("noa@gmail.com").password("Mmia12345").build();
        RequestBody requestBody = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);

        Assert.assertEquals(errorDto.getCode(), 400);
        Assert.assertEquals(errorDto.getMessage(), "Password must contain at least one special symbol from ['$','~','-','_']!");
        Assert.assertTrue(errorDto.getMessage().contains("Password must contain "));

    }
    @Test
    public void loginPasswordNotReally() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("noa@gmail.com").password("Mmjkk12345@").build();
        RequestBody requestBody = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);

        Assert.assertEquals(errorDto.getMessage(), "Password must contain at least one special symbol from ['$','~','-','_']!");
//        Assert.assertTrue(errorDto.getMessage().contains("Password must contain "));
        Assert.assertEquals(response.code(), 400);
        Assert.assertEquals(errorDto.getCode(), 400);

    }
    @Test
    public void loginUnsuccess3() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().email("noa@gmail.com").password("Mm").build();
        RequestBody requestBody = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);

        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);

        Assert.assertEquals(errorDto.getCode(), 400);
        Assert.assertEquals(errorDto.getMessage(), "Password length need be 8 or more symbols");
//        Assert.assertTrue(errorDto.getMessage().contains("Password must contain "));
    }

}