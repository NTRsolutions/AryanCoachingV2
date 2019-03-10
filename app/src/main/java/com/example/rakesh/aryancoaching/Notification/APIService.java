package com.example.rakesh.aryancoaching.Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAve19pgg:APA91bEwmo5WyaACAiV3zDhNMX69X3umIHP0FEHu5-11N9VT1UE7aVSkJTyjY8IrSSjRbhFDkfM-ZOBnxJo2fET6YJeNcsDzJMg91iEI2qspuzUJDm6mLXfk8urIABcK6C6o2vMlV5ui",

    })
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);


}
