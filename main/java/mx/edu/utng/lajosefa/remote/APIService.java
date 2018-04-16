package mx.edu.utng.lajosefa.remote;

import mx.edu.utng.lajosefa.Entity.MyResponse;
import mx.edu.utng.lajosefa.Entity.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Diana Manzano on 29/03/2018.
 */

public interface APIService {
    @Headers({"Content-Type:application/json",
            "Authorization:key=AAAAXg_EUXc:APA91bHCxXkMYriayVbtaxjtdbjs2hKtL4_4_7W5IcWNUxErWk25FTSjVc61epLMV7JLHqBF7jrBn0qXdDmRH30pUDPHHA53CM7f3Ju4wOgnl23mNf0Nd4KKkKP5FwyAptHPtZVTk5Hw"})
    @POST("/fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
