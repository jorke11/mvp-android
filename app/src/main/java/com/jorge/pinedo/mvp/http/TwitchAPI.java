package com.jorge.pinedo.mvp.http;

import com.jorge.pinedo.mvp.http.twitch.Twich;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TwitchAPI {

    @GET("games/top")
    Call<Twich> getTopGames(@Header("Client-Id") String client_id);

    @GET("games/top")
    Observable<Twich> getTopGamesObservable(@Header("Client-Id") String client_id);

}
