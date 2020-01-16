package com.example.mislugares.datos;

import com.example.mislugares.modelo.Lugar;

public interface RepositorioLugares {
    Lugar elemento(int id); //Devuelve el elemento dado su id
    void añade(Lugar lugar); //Añade el elemento indicado
    int nuevo(); //Añade un elemento en blanco y devuelve su id
    void borrar(int id); //Elimina el elemento con el id indicado
    int tamaño(); //Devuelve el número de elementos
    void actualiza(int id, Lugar lugar); //Reemplaza un elemento
}
