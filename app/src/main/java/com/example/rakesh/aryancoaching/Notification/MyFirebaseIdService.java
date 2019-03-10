package com.example.rakesh.aryancoaching.Notification;

import com.example.rakesh.aryancoaching.Notification.Common;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
String refreshedToken = FirebaseInstanceId.getInstance().getToken();
Common.currentToken = refreshedToken;
    }
}
