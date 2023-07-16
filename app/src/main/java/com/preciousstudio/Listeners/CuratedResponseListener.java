package com.preciousstudio.Listeners;

import com.preciousstudio.backgrounds.Models.CuratedApiResponse;

public interface CuratedResponseListener {

    void onFetch(CuratedApiResponse response,String message);
    void onError(String message);
}
