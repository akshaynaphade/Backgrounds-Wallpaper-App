package com.preciousstudio.Listeners;

import com.preciousstudio.backgrounds.Models.SearchApiResponse;

public interface SearchResponseListener {
    void onFetch(SearchApiResponse response, String message);
    void onError(String message);
}
