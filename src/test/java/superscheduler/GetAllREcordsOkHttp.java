package superscheduler;

import com.google.gson.Gson;

import dtoSuper.ErrorDtoSS;
import dtoSuper.GetAllRecords;

import dtoSuper.RecordDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetAllREcordsOkHttp {
    Gson gson = new Gson();

    OkHttpClient client = new OkHttpClient();
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";

    @Test
    public void getRecordsSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/recordsPeriod")
                .get()
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 200);

        GetAllRecords records = gson.fromJson(response.body().string(), GetAllRecords.class);
        List<RecordDTO> all = records.getRecords();
        for (RecordDTO dto : all) {
            System.out.println(dto.toString());
            System.out.println("******");
        }

    }

    @Test
    public void getAllRecordsWrongToken() throws IOException {
        Request request = new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/recordsPeriod")
                .get()
                .addHeader("Authorization", "hiuo897")
                .build();
        Response response = client.newCall(request).execute();
        ErrorDtoSS errorDtoSS = gson.fromJson(response.body().string(), ErrorDtoSS.class);
        Assert.assertEquals(errorDtoSS.getMessage(), "Wrong authorization format");
        Assert.assertEquals(errorDtoSS.getCode(), 401);


    }
}

