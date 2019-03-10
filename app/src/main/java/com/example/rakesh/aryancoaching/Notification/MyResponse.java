package com.example.rakesh.aryancoaching.Notification;

import java.util.List;

public class MyResponse {
    public long multicast_id;
    public int success;
    public int faliure;
    public int canonical_ids;
    public List<Result> results;


    public MyResponse() {

    }
    public MyResponse(long multicast_id,int success, int faliure, int canonical_ids, List<Result> results)
    {
        this.multicast_id = multicast_id;
        this.success = success;
        this.faliure = faliure;
        this.canonical_ids = canonical_ids;
        this.results = results;
    }

    public long getMulticast_id() {
        return multicast_id;
    }

    public int getCanonical_ids() {
        return canonical_ids;
    }

    public int getFaliure() {
        return faliure;
    }

    public List<Result> getResults() {
        return results;
    }

    public int getSuccess() {
        return success;
    }
}
