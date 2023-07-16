package com.preciousstudio;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.preciousstudio.Listeners.CuratedResponseListener;
import com.preciousstudio.Listeners.SearchResponseListener;
import com.preciousstudio.backgrounds.Models.CuratedApiResponse;
import com.preciousstudio.backgrounds.Models.SearchApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.pexels.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getCuratedWallpapers(CuratedResponseListener listener, String page) {
        CallWallpaperList callWallpaperList = retrofit.create(CallWallpaperList.class);
        Call<CuratedApiResponse> call = callWallpaperList.getWallpapers(page, "7");

        call.enqueue(new Callback<CuratedApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<CuratedApiResponse> call, @NonNull Response<CuratedApiResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(context, "An Error Occured",Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(@NonNull Call<CuratedApiResponse> call, @NonNull Throwable t) {

                listener.onError(t.getMessage());
            }
        });
    }
    public void searchCuratedWallpapers(SearchResponseListener listener, String page, String query) {
        CallWallpaperListSearch callWallpaperListSearch = retrofit.create(CallWallpaperListSearch.class);
        Call<SearchApiResponse> call = callWallpaperListSearch.searchWallpapers(query, page, "7");

        call.enqueue(new Callback<SearchApiResponse>() {
            @Override
            public void onResponse(Call<SearchApiResponse> call, Response<SearchApiResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(context, "An Error Occured",Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<SearchApiResponse> call, Throwable t) {

                listener.onError(t.getMessage());
            }
        });
    }






    private interface CallWallpaperList{
        @Headers({
                "Accept: application/json",
                "Authorization: POStyUO3fc0Ol2Obhe3aYfaxckcKiJrZRKYhikN0pp0BaUtHphCObY8o"
        })
        @GET("curated/")
        Call<CuratedApiResponse> getWallpapers(
                @Query("page") String page,
                @Query("per_page") String per_page
        );
    }


    private interface CallWallpaperListSearch{
        @Headers({
                "Accept: application/json",
                "Authorization: POStyUO3fc0Ol2Obhe3aYfaxckcKiJrZRKYhikN0pp0BaUtHphCObY8o"
        })
        @GET("search")
        Call<SearchApiResponse> searchWallpapers(
                @Query("query") String query,
                @Query("page") String page,
                @Query("per_page") String per_page
        );
    }
}
