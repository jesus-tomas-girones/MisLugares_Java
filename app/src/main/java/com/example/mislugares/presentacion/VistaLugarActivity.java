package com.example.mislugares.presentacion;

import android.os.Bundle;
import com.example.mislugares.R;

import androidx.appcompat.app.AppCompatActivity;

public class VistaLugarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_lugar);
    }
}

/*    public class VistaLugarActivity extends AppCompatActivity {

    final static int RESULTADO_EDITAR = 1;
    final static int RESULTADO_GALERIA = 2;
    final static int RESULTADO_FOTO = 3;
    private ImageView foto;
    private Uri uriUltimaFoto;

    private LugaresBD lugares;
    private AdaptadorLugaresBD adaptador;

    private CasosUsoLugar usoLugar;
    private int pos;
    private Lugar lugar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_lugar);
        lugares = ((Aplicacion) getApplication()).lugares;
        adaptador = ((Aplicacion) getApplication()).adaptador;
        Bundle extras = getIntent().getExtras();
        pos = extras.getInt("pos", 0);
        lugar = adaptador.lugarPosicion (pos);
        usoLugar = new CasosUsoLugar(this, lugares, adaptador);
        foto = findViewById(R.id.foto);
        actualizaVistas();
    }

    public void actualizaVistas() {
//        lugar = adaptador.lugarPosicion (pos);
        TextView nombre = findViewById(R.id.nombre);
        nombre.setText(lugar.getNombre());
        ImageView logo_tipo = findViewById(R.id.logo_tipo);
        logo_tipo.setImageResource(lugar.getTipo().getRecurso());
        TextView tipo = findViewById(R.id.tipo);
        tipo.setText(lugar.getTipo().getTexto());
        TextView direccion = findViewById(R.id.direccion);
        direccion.setText(lugar.getDireccion());

        if (lugar.getTelefono() == 0) {
            findViewById(R.id.telefono).setVisibility(View.GONE);
        } else {
            findViewById(R.id.telefono).setVisibility(View.VISIBLE);
            TextView telefono = findViewById(R.id.telefono);
            telefono.setText(Integer.toString(lugar.getTelefono()));
        }

        TextView url = findViewById(R.id.url);
        url.setText(lugar.getUrl());
        TextView comentario = findViewById(R.id.comentario);
        comentario.setText(lugar.getComentario());
        TextView fecha = findViewById(R.id.fecha);
        fecha.setText(DateFormat.getDateInstance().format(
                new Date(lugar.getFecha())));
        TextView hora = findViewById(R.id.hora);
        hora.setText(DateFormat.getTimeInstance().format(
                new Date(lugar.getFecha())));
        RatingBar valoracion = findViewById(R.id.valoracion);
        valoracion.setRating(lugar.getValoracion());
        valoracion.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override public void onRatingChanged(RatingBar ratingBar,
                                                          float valor, boolean fromUser) {
                        //lugar.setValoracion(valor);
                        usoLugar.ponerValoracion(pos, valor);
                    }
                });
        usoLugar.visualizarFoto(lugar, foto);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode,
                                              Intent data) {
        if (requestCode == RESULTADO_EDITAR) {

//           adaptador.setCursor(lugares.extraeCursor());
           finish();
//            lugar = adaptador.lugarPosicion (pos);  //ESto es peligroso, el lugar puede haber

//            int _id = adaptador.idPosicion(pos);
//            lugar = lugares.elemento(_id);
//            adaptador.setCursor(lugares.extraeCursor()); // Lo hemos quitado de CasosUsoLugar.guardar
//            pos = adaptador.idPosicion(_id);  //MAL!!!!!!!!!!!!!

            //Antes:    actualizaVistas();
            //findViewById(R.id.scrollView1).invalidate();
        } else if (requestCode == RESULTADO_GALERIA) {
            if (resultCode == Activity.RESULT_OK) {
                usoLugar.ponerFoto(pos, data.getDataString(), foto);
            } else {
                Toast.makeText(this, "Foto no cargada", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == RESULTADO_FOTO) {
            if (resultCode == Activity.RESULT_OK && uriUltimaFoto!=null) {
                lugar.setFoto(uriUltimaFoto.toString());
                usoLugar.ponerFoto(pos, lugar.getFoto(), foto);
            } else {
                Toast.makeText(this, "Error en captura", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vista_lugar, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
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

    void verMapa(View view) {
        usoLugar.verMapa(lugar);
    }

    void llamarTelefono(View view) {
        usoLugar.llamarTelefono(lugar);
    }

    void verPgWeb(View view) {
        usoLugar.verPgWeb(lugar);
    }

    public void ponerDeGaleria(View view) {
        usoLugar.ponerDeGaleria(RESULTADO_GALERIA);
    }

    void tomarFoto(View view) {
        uriUltimaFoto = usoLugar.tomarFoto(RESULTADO_FOTO);
    }

    public void eliminarFoto(View view) {
        usoLugar.ponerFoto(pos, "", foto);
    }

}*/
