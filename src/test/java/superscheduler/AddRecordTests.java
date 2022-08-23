package superscheduler;

import com.google.gson.Gson;
import dto.ErrorDto;
import dtoSuper.DateDto;
import dtoSuper.ErrorDtoSS;
import dtoSuper.RecordDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddRecordTests {
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";


    @Test
    public void addRecordSuccess() throws IOException {
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

        RequestBody body=RequestBody.create(gson.toJson(recordDTO),JSON);
        Request request=new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/record")
                .post(body)
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);

        RecordDTO record = gson.fromJson(response.body().string(), RecordDTO.class);

        System.out.println("Id: " + record.getId());

        Assert.assertNotEquals(recordDTO.getId(), record.getId());

    }
    @Test
    public void addRecordUnSuccessNullToken() throws IOException {
        RecordDTO recordDTO = RecordDTO.builder()
                .breaks(3)
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

        RequestBody body=RequestBody.create(gson.toJson(recordDTO),JSON);
        Request request=new Request.Builder()
                .url("https://super-scheduler-app.herokuapp.com/api/record")
                .post(body)
                .addHeader("Authorization","")
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 401);

//        RecordDTO record = gson.fromJson(response.body().string(), RecordDTO.class);
        ErrorDtoSS errorDtoSS=gson.fromJson(response.body().string(), ErrorDtoSS.class);
        Assert.assertEquals(errorDtoSS.getMessage(),"Wrong authorization format");

    }
}
