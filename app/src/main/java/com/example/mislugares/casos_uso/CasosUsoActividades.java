package com.example.mislugares.casos_uso;

import android.app.Activity;
import android.content.Intent;

import com.example.mislugares.presentacion.AcercaDeActivity;
import com.example.mislugares.presentacion.MapaActivity;
import com.example.mislugares.presentacion.PreferenciasActivity;

public class CasosUsoActividades {

   protected Activity actividad;

   public CasosUsoActividades(Activity actividad) {
      this.actividad = actividad;
   }

   public void lanzarAcercaDe() {
      actividad.startActivity(
         new Intent(actividad, AcercaDeActivity.class));
   }

   public void lanzarPreferencias(int codidoSolicitud) {
      actividad.startActivityForResult(
         new Intent(actividad, PreferenciasActivity.class), codidoSolicitud);
   }

   public void lanzarMapa() {
      actividad.startActivity(
              new Intent(actividad, MapaActivity.class));
   }
}