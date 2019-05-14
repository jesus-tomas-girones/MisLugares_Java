package com.example.mislugares.presentacion;

import android.database.Cursor;

import com.example.mislugares.datos.LugaresBD;
import com.example.mislugares.datos.RepositorioLugares;
import com.example.mislugares.modelo.Lugar;

public class AdaptadorLugaresBD extends AdaptadorLugares  {

    protected Cursor cursor;

    public AdaptadorLugaresBD(RepositorioLugares lugares, Cursor cursor) {
        super(lugares);
        this.cursor = cursor;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public Lugar lugarPosicion(int posicion) {
        cursor.moveToPosition(posicion);
        return LugaresBD.extraeLugar(cursor);
    }

    public int idPosicion(int posicion) {
        cursor.moveToPosition(posicion);
        return cursor.getInt(0);
    }

    public int posicionId(int id) {
        int pos = 0;
        while (pos<getItemCount() && idPosicion(pos)!=id) pos++;
        if (pos >= getItemCount()) return -1;
        else                       return pos;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion) {
        Lugar lugar = lugarPosicion(posicion);
        holder.personaliza(lugar);
        holder.itemView.setTag(new Integer(posicion));
    }

    @Override public int getItemCount() {
        return cursor.getCount();
    }

}
