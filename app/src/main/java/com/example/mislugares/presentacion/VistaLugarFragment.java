package com.example.mislugares.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mislugares.Aplicacion;
import com.example.mislugares.R;
import com.example.mislugares.casos_uso.CasosUsoLugarFecha;
import com.example.mislugares.datos.LugaresBD;
import com.example.mislugares.modelo.Lugar;

import java.text.DateFormat;
import java.util.Date;

import androidx.fragment.app.Fragment;

public class VistaLugarFragment extends Fragment {

   final static int RESULTADO_EDITAR = 1;
   final static int RESULTADO_GALERIA = 2;
   final static int RESULTADO_FOTO = 3;
   private ImageView foto;
   private Uri uriUltimaFoto;

   private LugaresBD lugares;
   private AdaptadorLugaresBD adaptador;

   private CasosUsoLugarFecha usoLugar;
   /*private*/public int pos;
   public int _id = -1;

   private Lugar lugar;
   private View v;

   @Override public View onCreateView(LayoutInflater inflador,
                            ViewGroup contenedor, Bundle savedInstanceState) {
      setHasOptionsMenu(true);
      View vista = inflador.inflate(R.layout.vista_lugar, contenedor, false);
      return vista;
   }

   @Override public void onActivityCreated(Bundle state) {
      super.onActivityCreated(state);

      lugares = ((Aplicacion) getActivity().getApplication()).lugares;
      adaptador = ((Aplicacion) getActivity().getApplication()).adaptador;
      usoLugar = new CasosUsoLugarFecha(getActivity(), this, lugares, adaptador);

      v = getView();
      foto = v.findViewById(R.id.foto);

      v.findViewById(R.id.barra_mapa).setOnClickListener(
              new View.OnClickListener() { public void onClick(View view) {
                 usoLugar.verMapa(lugar); }  });
      v.findViewById(R.id.barra_url).setOnClickListener(
              new View.OnClickListener() { public void onClick(View view) {
                 usoLugar.verPgWeb(lugar); }  });
      v.findViewById(R.id.barra_telefono).setOnClickListener(
              new View.OnClickListener() {public void onClick(View view) {
                 usoLugar.llamarTelefono(lugar);  }  });
      v.findViewById(R.id.camara).setOnClickListener(
              new View.OnClickListener() {public void onClick(View view) {
                 uriUltimaFoto = usoLugar.tomarFoto(RESULTADO_FOTO);  }  });
      v.findViewById(R.id.galeria).setOnClickListener(
              new View.OnClickListener() {public void onClick(View view) {
                 usoLugar.ponerDeGaleria(RESULTADO_GALERIA);  }  });
      v.findViewById(R.id.eliminar_foto).setOnClickListener(
              new View.OnClickListener() {public void onClick(View view) {
                 usoLugar.ponerFoto(pos, "", foto);  }  });
      v.findViewById(R.id.icono_hora).setOnClickListener(
              new View.OnClickListener() {
                 public void onClick(View view) { usoLugar.cambiarHora(pos); } });
      v.findViewById(R.id.hora).setOnClickListener(
              new View.OnClickListener() {
                 public void onClick(View view) { usoLugar.cambiarHora(pos); } });
      v.findViewById(R.id.icono_fecha).setOnClickListener(
              new View.OnClickListener() {
                 public void onClick(View view) { usoLugar.cambiarFecha(pos); } });
      v.findViewById(R.id.fecha).setOnClickListener(
              new View.OnClickListener() {
                 public void onClick(View view) { usoLugar.cambiarFecha(pos); } });
      Bundle extras = getActivity().getIntent().getExtras();
      if (extras != null)
           pos = extras.getInt("pos", 0);
      else pos = 0;
      _id = adaptador.idPosicion(pos);
      actualizaVistas();
   }

   public void actualizaVistas() {
      //v = getView();
      if (adaptador.getItemCount() == 0) return;
      lugar = adaptador.lugarPosicion(pos);
      TextView nombre = v.findViewById(R.id.nombre);
      nombre.setText(lugar.getNombre());
      ImageView logo_tipo = v.findViewById(R.id.logo_tipo);
      logo_tipo.setImageResource(lugar.getTipo().getRecurso());
      TextView tipo = v.findViewById(R.id.tipo);
      tipo.setText(lugar.getTipo().getTexto());
      TextView direccion = v.findViewById(R.id.direccion);
      direccion.setText(lugar.getDireccion());
      if (lugar.getTelefono() == 0) {
         v.findViewById(R.id.telefono).setVisibility(View.GONE);
      } else {
         v.findViewById(R.id.telefono).setVisibility(View.VISIBLE);
         TextView telefono = v.findViewById(R.id.telefono);
         telefono.setText(Integer.toString(lugar.getTelefono()));
      }
      TextView url = v.findViewById(R.id.url);
      url.setText(lugar.getUrl());
      TextView comentario = v.findViewById(R.id.comentario);
      comentario.setText(lugar.getComentario());
      TextView fecha = v.findViewById(R.id.fecha);
      fecha.setText(DateFormat.getDateInstance().format(
              new Date(lugar.getFecha())));
      TextView hora = v.findViewById(R.id.hora);
      hora.setText(DateFormat.getTimeInstance().format(
              new Date(lugar.getFecha())));
      RatingBar valoracion = v.findViewById(R.id.valoracion);
      valoracion.setOnRatingBarChangeListener(null);  //<<<<<<<<<<<<<<<<<<
      valoracion.setRating(lugar.getValoracion());
      valoracion.setOnRatingBarChangeListener(
              new RatingBar.OnRatingBarChangeListener() {
                 @Override
                 public void onRatingChanged(RatingBar ratingBar,
                                             float valor, boolean fromUser) {
                    lugar.setValoracion(valor);
                    usoLugar.actualizaPosLugar(pos, lugar);
                    pos = adaptador.posicionId(_id);
                 }
              });
      usoLugar.visualizarFoto(lugar, foto);
   }

   @Override /*protected*/ public void onActivityResult(int requestCode, int resultCode,
                                                        Intent data) {
      //super.onActivityResult(requestCode,resultCode, data);

      if (requestCode == RESULTADO_EDITAR) {
         //int id = adaptador.idPosicion(pos);
         lugar = lugares.elemento(_id);
         //adaptador.cursor = lugares.extraeCursor();
         pos = adaptador.posicionId(_id);
         actualizaVistas();
      } else if (requestCode == RESULTADO_GALERIA) {
         if (resultCode == Activity.RESULT_OK) {
            usoLugar.ponerFoto(pos, data.getDataString(), foto);
         } else {
            Toast.makeText(getActivity(), "Foto no cargada", Toast.LENGTH_LONG).show();
         }
      } else if (requestCode == RESULTADO_FOTO) {
         if (resultCode == Activity.RESULT_OK && uriUltimaFoto != null) {
            lugar.setFoto(uriUltimaFoto.toString());
            usoLugar.ponerFoto(pos, lugar.getFoto(), foto);
         } else {
            Toast.makeText(getActivity(), "Error en captura", Toast.LENGTH_LONG).show();
         }
      }
   }

   @Override
   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      inflater.inflate(R.menu.vista_lugar, menu);
      super.onCreateOptionsMenu(menu, inflater);
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
         case R.id.accion_compartir:
            usoLugar.compartir(lugar);
            return true;
         case R.id.accion_llegar:
            usoLugar.verMapa(lugar);
            return true;
         case R.id.accion_editar:
            usoLugar.editar(pos, RESULTADO_EDITAR);
            return true;
         case R.id.accion_borrar:
            int id = adaptador.idPosicion(pos);
            usoLugar.borrar(id);
            return true;
         default:
            return super.onOptionsItemSelected(item);
      }
   }

}
