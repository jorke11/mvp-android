package com.jorge.pinedo.mvp;

import com.jorge.pinedo.mvp.login.LoginActivityMVP;
import com.jorge.pinedo.mvp.login.LoginActivityPresenter;
import com.jorge.pinedo.mvp.login.User;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class PresenterUnitTest {

    LoginActivityPresenter presenter;
    User user;

    LoginActivityMVP.Model mockedModel;
    LoginActivityMVP.View mockedView;

    @Before
    public void init(){
        mockedModel = mock(LoginActivityMVP.Model.class);
        mockedView = mock(LoginActivityMVP.View.class);

        user=new User("jorge","pinedo");

        //when(mockedModel.getUser()).thenReturn(user);
        //when(mockedView.getFirtsName()).thenReturn(user.getFirtsName());
        //when(mockedView.getLastName()).thenReturn(user.getLastName());

        presenter = new LoginActivityPresenter(mockedModel);
        presenter.setView(mockedView);
    }

    @Test
    public void noExistInteractionView(){
        presenter.getCurrentUser();
        //verifyNoMoreInteractions(mockedView);
        verify(mockedView,times(1)).showUserNotAvailable();
    }

    @Test
    public void loadUserFromTheRepoWhenValidUserIsPresent(){
        when(mockedModel.getUser()).thenReturn(user);
        presenter.getCurrentUser();

        //Comprobamos la interacturacion con el modelo de datos
        verify(mockedModel,times(1)).getUser();

        //Comprobamos la interacturacion con la vista
        verify(mockedView,times(1)).setFirstName("jorge");
        verify(mockedView,times(1)).setLastName("pinedo");
        verify(mockedView,never()).showUserNotAvailable();
    }

    @Test
    public void showErrorMessageWhenUserIsNull(){
        when(mockedModel.getUser()).thenReturn(null);
        presenter.getCurrentUser();

        verify(mockedModel,times(1)).getUser();

        //Comprobamos la interacturacion con la vista
        verify(mockedView,never()).setFirstName("jorge");
        verify(mockedView,never()).setLastName("pinedo");
        verify(mockedView,times(1)).showUserNotAvailable();

    }

    @Test
    public void createErrorMessageIfAnyFieldsEmpty(){
        //Primera prueba colocnando el firt_name vacio
        when(mockedView.getFirtsName()).thenReturn("");
        presenter.loginButtonClicked();

        verify(mockedView,times(1)).getFirtsName();
        verify(mockedView,never()).getLastName();
        verify(mockedView,times(1)).showInputError();

        //segunda prueba, colocando un valor a firts_name y dejando last_name vacio
        when(mockedView.getFirtsName()).thenReturn(user.getFirtsName());
        when(mockedView.getLastName()).thenReturn("");
        presenter.loginButtonClicked();

        //el metodo se llama dos veces
        verify(mockedView,times(2)).getFirtsName();
        verify(mockedView,times(1)).getLastName();
        verify(mockedView,times(2)).showInputError();

    }
    @Test
    public void saveValidUser(){
        when(mockedView.getFirtsName()).thenReturn("jorge");
        when(mockedView.getLastName()).thenReturn("pinedo");
        presenter.loginButtonClicked();

        verify(mockedView,times(1)).getFirtsName();
        verify(mockedView,times(2)).getLastName();

        verify(mockedModel,times(1)).createUser("jorge","pinedo");
        verify(mockedView,times(1)).showUserSaved();

    }

}
