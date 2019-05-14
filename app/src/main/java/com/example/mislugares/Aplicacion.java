package com.example.mislugares;

import android.app.Application;

import com.example.mislugares.datos.LugaresBD;
import com.example.mislugares.modelo.GeoPunto;
import com.example.mislugares.presentacion.AdaptadorLugaresBD;

public class Aplicacion extends Application {

    public LugaresBD lugares;
    public AdaptadorLugaresBD adaptador;
    public GeoPunto posicionActual = new GeoPunto(0.0, 0.0);

    @Override public void onCreate() {
        super.onCreate();
        lugares = new LugaresBD(this);
        adaptador = new AdaptadorLugaresBD(lugares, lugares.extraeCursor());
    }
   /* @Override public void onCreate() {
        super.onCreate();
        lugares.añadeEjemplos();
    }*/


/*    public LugaresLista getLugares() {
        return lugares;
    }*/
}
