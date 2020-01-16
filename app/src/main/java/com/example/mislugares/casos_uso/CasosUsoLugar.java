package com.example.mislugares.casos_uso;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mislugares.Aplicacion;
import com.example.mislugares.R;
import com.example.mislugares.datos.LugaresBD;
import com.example.mislugares.modelo.GeoPunto;
import com.example.mislugares.modelo.Lugar;
import com.example.mislugares.presentacion.AdaptadorLugaresBD;
import com.example.mislugares.presentacion.EdicionLugarActivity;
import com.example.mislugares.presentacion.VistaLugarActivity;
import com.example.mislugares.presentacion.VistaLugarFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class CasosUsoLugar {

   protected FragmentActivity actividad;
   protected Fragment fragment;
   protected LugaresBD lugares;
   protected AdaptadorLugaresBD adaptador;

   public CasosUsoLugar(FragmentActivity actividad, Fragment fragment,
                        LugaresBD lugares,
                        AdaptadorLugaresBD adaptador) {
      this.actividad = actividad;
      this.fragment = fragment;
      this.lugares = lugares;
      this.adaptador = adaptador;
   }

   // OPERACIONES BÁSICAS

   public void actualizaPosLugar(int pos, Lugar lugar) {
      int id = adaptador.idPosicion(pos);
      guardar(id, lugar);
   }

   public void guardar(int id, Lugar nuevoLugar) {
      lugares.actualiza(id, nuevoLugar);
      adaptador.setCursor(lugares.extraeCursor());
      adaptador.notifyDataSetChanged();
   }

   public void mostrar(int pos) {
      VistaLugarFragment fragmentVista = obtenerFragmentVista();
      if (fragmentVista != null) {
         fragmentVista.pos = pos;
         fragmentVista._id = adaptador.idPosicion(pos);
         fragmentVista.actualizaVistas();
      } else {
         Intent i = new Intent(actividad, VistaLugarActivity.class);
         i.putExtra("pos", pos);
         actividad.startActivity(i);
      }
   }

   public VistaLugarFragment obtenerFragmentVista() {
      FragmentManager manejador = actividad.getSupportFragmentManager();
      return (VistaLugarFragment)
              manejador.findFragmentById(R.id.vista_lugar_fragment);
   }

   public void editar(int pos, int codidoSolicitud) {
      Intent i = new Intent(actividad, EdicionLugarActivity.class);
      i.putExtra("pos", pos);
      if (fragment != null)
           fragment.startActivityForResult(i, codidoSolicitud);
      else actividad.startActivityForResult(i, codidoSolicitud);

   }

   public void borrar(int id) {
      lugares.borrar(id);
      adaptador.setCursor(lugares.extraeCursor());
      adaptador.notifyDataSetChanged();

      FragmentManager manejador = actividad.getSupportFragmentManager();
      if (manejador.findFragmentById(R.id.selector_fragment) == null) {
         actividad.finish();
      } else {
         mostrar(0);
      }
   }

   public void nuevo() {
      int id = lugares.nuevo();
      GeoPunto posicion = ((Aplicacion) actividad.getApplication())
              .posicionActual;
      if (!posicion.equals(GeoPunto.SIN_POSICION)) {
         Lugar lugar = lugares.elemento(id);
         lugar.setPosicion(posicion);
         lugares.actualiza(id, lugar);
      }
      Intent i = new Intent(actividad, EdicionLugarActivity.class);
      i.putExtra("_id", id);
      actividad.startActivity(i);
   }


   // INTENCIONES
   public void compartir(Lugar lugar) {
      Intent i = new Intent(Intent.ACTION_SEND);
      i.setType("text/plain");
      i.putExtra(Intent.EXTRA_TEXT,
              lugar.getNombre() + " - " + lugar.getUrl());
      actividad.startActivity(i);
   }

   public void llamarTelefono(Lugar lugar) {
      actividad.startActivity(new Intent(Intent.ACTION_DIAL,
              Uri.parse("tel:" + lugar.getTelefono())));
   }

   public void verPgWeb(Lugar lugar) {
      actividad.startActivity(new Intent(Intent.ACTION_VIEW,
              Uri.parse(lugar.getUrl())));
   }

   public final void verMapa(Lugar lugar) {
      double lat = lugar.getPosicion().getLatitud();
      double lon = lugar.getPosicion().getLongitud();
      Uri uri = lugar.getPosicion() != GeoPunto.SIN_POSICION
              ? Uri.parse("geo:" + lat + ',' + lon)
              : Uri.parse("geo:0,0?q=" + lugar.getDireccion());
      actividad.startActivity(new Intent("android.intent.action.VIEW", uri));
   }


   // FOTOGRAFÍAS
   public void ponerDeGaleria(int codidoSolicitud) {
      String action;
      if (android.os.Build.VERSION.SDK_INT >= 19) { // API 19 - Kitkat
         action = Intent.ACTION_OPEN_DOCUMENT;
      } else {
         action = Intent.ACTION_PICK;
      }
      Intent i = new Intent(action,
              MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
      i.addCategory(Intent.CATEGORY_OPENABLE);
      i.setType("image/*");
      if (fragment != null)
         fragment.startActivityForResult(i, codidoSolicitud);
      else actividad.startActivityForResult(i, codidoSolicitud);

   }

   public void ponerFoto(int pos, String uri, ImageView imageView) {
      Lugar lugar = adaptador.lugarPosicion(pos);//lugares.elemento(pos);
      lugar.setFoto(uri);
      visualizarFoto(lugar, imageView);
      actualizaPosLugar(pos, lugar);
   }

   public void visualizarFoto(Lugar lugar, ImageView imageView) {
      if (lugar.getFoto() != null && !lugar.getFoto().isEmpty()) {
         imageView.setImageBitmap(reduceBitmap(actividad, lugar.getFoto(), 1024, 1024));
      } else {
         imageView.setImageBitmap(null);
      }
   }

   public Uri tomarFoto(int codidoSolicitud) {
      try {
         Uri uriUltimaFoto;
         File file = File.createTempFile(
                 "img_" + (System.currentTimeMillis() / 1000), ".jpg",
                 actividad.getExternalFilesDir(Environment.DIRECTORY_PICTURES));
         if (Build.VERSION.SDK_INT >= 24) {
            uriUltimaFoto = FileProvider.getUriForFile(
                    actividad, "es.upv.jtomas.mislugares.fileProvider2", file);
         } else {
            uriUltimaFoto = Uri.fromFile(file);
         }
         Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
         //intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
         i.putExtra(MediaStore.EXTRA_OUTPUT, uriUltimaFoto);
         //actividad.startActivityForResult(i, codidoSolicitud);
         if (fragment != null)
            fragment.startActivityForResult(i, codidoSolicitud);
         else actividad.startActivityForResult(i, codidoSolicitud);
         return uriUltimaFoto;
      } catch (IOException ex) {
         Toast.makeText(actividad, "Error al crear fichero de imagen",
                 Toast.LENGTH_LONG).show();
         return null;
      }
   }

   private Bitmap reduceBitmap(Context contexto, String uri,
                               int maxAncho, int maxAlto) {
      try {
         InputStream input = null;
         Uri u = Uri.parse(uri);
         if (u.getScheme().equals("http") || u.getScheme().equals("https")) {
            input = new URL(uri).openStream();
         } else {
            input = contexto.getContentResolver().openInputStream(u);
         }
         final BitmapFactory.Options options = new BitmapFactory.Options();
         options.inJustDecodeBounds = true;
         options.inSampleSize = (int) Math.max(
                 Math.ceil(options.outWidth / maxAncho),
                 Math.ceil(options.outHeight / maxAlto));
         options.inJustDecodeBounds = false;
         return BitmapFactory.decodeStream(input, null, options);
      } catch (FileNotFoundException e) {
         Toast.makeText(contexto, "Fichero/recurso de imagen no encontrado",
                 Toast.LENGTH_LONG).show();
         e.printStackTrace();
         return null;
      } catch (IOException e) {
         Toast.makeText(contexto, "Error accediendo a imagen",
                 Toast.LENGTH_LONG).show();
         e.printStackTrace();
         return null;
      }
   }

}