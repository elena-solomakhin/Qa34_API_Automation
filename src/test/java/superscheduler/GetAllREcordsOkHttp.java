package superscheduler;

import com.google.gson.Gson;

import dtoSuper.ErrorDtoSS;
import dtoSuper.GetAllRecords;
import dtoSuper.PeriodDtoSS;
import dtoSuper.RecordDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
public class GetAllREcordsOkHttp {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();
//    String token2 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im1vYUBnbWFpbC5jb20ifQ.FdyRws90Gn4IbInlRWOYZO3VnFZUw4zYJHI42vuSKHI";
    @Test
    public void getAllRecordsSuccess() throws IOException {

        PeriodDtoSS periodDto= PeriodDtoSS.builder().monthFrom(1).monthTo(10).yearFrom(2022).yearTo(2022).build();
        RequestBody body = RequestBody.create(gson.toJson(periodDto),JSON);
        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/records")
                .post(body)
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(),200);

        GetAllRecords recordsDto= gson.fromJson(response.body().string(), GetAllRecords.class);

        List<RecordDTO> list = recordsDto.getRecords();
        for(RecordDTO recordDto:list){
            System.out.println(recordDto.toString());
        }
        }



    @Test
    public void getAllRecordsWrongToken() throws IOException {
        PeriodDtoSS periodDto= PeriodDtoSS.builder().monthFrom(1).monthTo(10).yearFrom(2022).yearTo(2022).build();
        RequestBody body = RequestBody.create(gson.toJson(periodDto),JSON);
        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/records")
                .post(body)
                .addHeader("Authorization", "hiuo897")
                .build();
        Response response = client.newCall(request).execute();
        ErrorDtoSS errorDtoSS = gson.fromJson(response.body().string(), ErrorDtoSS.class);
        Assert.assertEquals(errorDtoSS.getMessage(), "Wrong authorization format");
        Assert.assertEquals(errorDtoSS.getCode(), 401);


    }
}

