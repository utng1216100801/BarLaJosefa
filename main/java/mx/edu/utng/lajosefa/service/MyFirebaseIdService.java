package mx.edu.utng.lajosefa.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import mx.edu.utng.lajosefa.Common;

/**
 * Created by Diana Manzano on 29/03/2018.
 */

public class MyFirebaseIdService extends FirebaseInstanceIdService {
    //public static final String TAG = "NOTICIAS";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log.d(TAG,"Token"+token);
        Common.currentToken = refreshedToken;

    }
}
