package com.jorge.pinedo.mvp.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jorge.pinedo.mvp.R;
import com.jorge.pinedo.mvp.http.TwitchAPI;
import com.jorge.pinedo.mvp.http.twitch.Game;
import com.jorge.pinedo.mvp.http.twitch.Twich;
import com.jorge.pinedo.mvp.root.App;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements LoginActivityMVP.View{

    @Inject
    LoginActivityMVP.Presenter presenter;


    @BindView(R.id.firstName)
    EditText firstName;
    @BindView(R.id.lastName)
    EditText lastName;
    @BindView(R.id.button)
    Button button;

    @Inject
    TwitchAPI twitchAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        ((App)getApplication()).getComponent().inject(this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loginButtonClicked();
            }
        });

        //Ejemplo de llamada
        /*Call<Twich> call= twitchAPI.getTopGames("ant1d2avs7zwowjkbdms1nwo1h0h78");

        call.enqueue(new Callback<Twich>() {
            @Override
            public void onResponse(Call<Twich> call, Response<Twich> response) {
                List<Game> topGame=response.body().getGame();
                for (Game game:topGame){
                    System.out.println(game.getName());
                }

            }

            @Override
            public void onFailure(Call<Twich> call, Throwable t) {
                    t.printStackTrace();
            }
        });*/

         twitchAPI.getTopGamesObservable("ant1d2avs7zwowjkbdms1nwo1h0h78").flatMap(new Function<Twich, Observable<Game>>() {
             @Override
             public Observable<Game> apply(Twich twich){
                 return Observable.fromIterable(twich.getGame());
             }
         }).flatMap(new Function<Game,Observable<String>>(){
            @Override
            public Observable<String> apply(Game game){
                return Observable.just(game.getName());
            }

        }).subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
             @Override
             public void onSubscribe(Disposable d) {

             }

             @Override
             public void onNext(String name) {
                 System.out.println(name);
             }

             @Override
             public void onError(Throwable e) {
                e.printStackTrace();
             }

             @Override
             public void onComplete() {

             }
         });

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.getCurrentUser();
    }

    @Override
    public String getFirtsName() {
        return this.firstName.getText().toString();
    }

    @Override
    public String getLastName() {
        return this.lastName.getText().toString();
    }

    @Override
    public void showUserNotAvailable() {
        Toast.makeText(this,"Error el usuario no esta dispojible",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showInputError() {
        Toast.makeText(this,"Error, el Nombre ni el apellido no pueden estar vacios",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUserSaved() {
        Toast.makeText(this,"Usuario guardado correctamente",Toast.LENGTH_LONG).show();
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName.setText(lastName);
    }
}
