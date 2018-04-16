package mx.edu.utng.lajosefa;

import mx.edu.utng.lajosefa.remote.APIService;
import mx.edu.utng.lajosefa.remote.RetrofitClient;

/**
 * Created by Diana Manzano on 29/03/2018.
 */

public class Common {
    public static String currentToken = "";

    private static String baseUrl = "https://fcm.googleapis.com/";

    public static APIService getFCMClient(){
        return RetrofitClient.getClient(baseUrl).create(APIService.class);
    }
}
