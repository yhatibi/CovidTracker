package com.company.roomlogin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.company.roomlogin.model.AutenticacionManager;
import com.company.roomlogin.model.Usuario;

public class AutenticacionViewModel extends AndroidViewModel {

    enum EstadoDeLaAutenticacion {
        NO_AUTENTICADO,
        AUTENTICADO,
        AUTENTICACION_INVALIDA
    }

    enum EstadoDelRegistro {
        INICIO_DEL_REGISTRO,
        NOMBRE_NO_DISPONIBLE,
        REGISTRO_COMPLETADO
    }

    MutableLiveData<EstadoDeLaAutenticacion> estadoDeLaAutenticacion = new MutableLiveData<>(EstadoDeLaAutenticacion.NO_AUTENTICADO);
    MutableLiveData<EstadoDelRegistro> estadoDelRegistro = new MutableLiveData<>(EstadoDelRegistro.INICIO_DEL_REGISTRO);
    MutableLiveData<Usuario> usuarioAutenticado = new MutableLiveData<>();

    AutenticacionManager autenticacionManager;

    public AutenticacionViewModel(@NonNull Application application) {
        super(application);
        autenticacionManager = new AutenticacionManager(application);
    }


    void iniciarSesion(String username, String password){
        autenticacionManager.iniciarSesion(username, password, new AutenticacionManager.IniciarSesionCallback() {
            @Override
            public void cuandoUsuarioAutenticado(Usuario usuario) {
                usuarioAutenticado.postValue(usuario);
                estadoDeLaAutenticacion.postValue(EstadoDeLaAutenticacion.AUTENTICADO);
            }

            @Override
            public void cuandoAutenticacionNoValida() {
                estadoDeLaAutenticacion.postValue(EstadoDeLaAutenticacion.AUTENTICACION_INVALIDA);
            }
        });
    }

    void iniciarRegistro(){
        estadoDelRegistro.postValue(EstadoDelRegistro.INICIO_DEL_REGISTRO);
    }

    void crearCuentaEIniciarSesion(String username, String password, String biography){
        autenticacionManager.crearCuenta(username, password, biography, new AutenticacionManager.RegistrarCallback() {
            @Override
            public void cuandoRegistroCompletado() {
                estadoDelRegistro.postValue(EstadoDelRegistro.REGISTRO_COMPLETADO);
                iniciarSesion(username, password);
            }

            @Override
            public void cuandoNombreNoDisponible() {
                estadoDelRegistro.postValue(EstadoDelRegistro.NOMBRE_NO_DISPONIBLE);
            }
        });
    }

    void cerrarSesion(){
        usuarioAutenticado.postValue(null);
        estadoDeLaAutenticacion.postValue(EstadoDeLaAutenticacion.NO_AUTENTICADO);
    }
}
