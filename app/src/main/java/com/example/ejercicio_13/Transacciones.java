package com.example.ejercicio_13;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Transacciones {

    SQLiteDatabase conexion;
    ArrayList<Personas> lista = new ArrayList<Personas>();
    Personas personas;
    Context context;
    String DBname = "BDPersonas";
    String tabla = "CREATE TABLE IF NOT EXISTS personas (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT, apellido TEXT, edad INTEGER, telefono INTEGER, correo TEXT, direccion TEXT)";

    public Transacciones(Context personas){
        this.context=personas;
        conexion=personas.openOrCreateDatabase(DBname, personas.MODE_PRIVATE, null);
        conexion.execSQL(tabla);
    }

    public boolean insertar(Personas personas){
        ContentValues contenedor = new ContentValues();

        contenedor.put("nombre",personas.getNombre());
        contenedor.put("apellido", personas.getApellido());
        contenedor.put("edad", personas.getEdad());
        contenedor.put("telefono", personas.getTelefono());
        contenedor.put("correo", personas.getCorreo());
        contenedor.put("direccion", personas.getDireccion());

        return (conexion.insert("personas",null, contenedor)) >0;
    }

    public boolean eliminar(int id){

        return (conexion.delete("personas", "id="+id,null))>0;
    }

    public boolean editar(Personas personas){
        ContentValues contenedor = new ContentValues();

        contenedor.put("nombre",personas.getNombre());
        contenedor.put("apellido", personas.getApellido());
        contenedor.put("edad", personas.getEdad());
        contenedor.put("telefono", personas.getTelefono());
        contenedor.put("correo", personas.getCorreo());
        contenedor.put("direccion", personas.getDireccion());

        return (conexion.update("personas", contenedor, "id="+personas.getId(),null)) >0;
    }

    public ArrayList<Personas> verTodos(){
        lista.clear();
        Cursor cursor = conexion.rawQuery("SELECT * FROM personas",null);
        if (cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();

            do{
                lista.add(new Personas(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getString(6)));
            }while (cursor.moveToNext());
        }
        return lista;
    }

    public Personas verUno(int posicion){
        Cursor cursor = conexion.rawQuery("SELECT * FROM personas",null);
        cursor.moveToPosition(posicion);
        personas = new Personas(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getInt(4),
                cursor.getString(5),
                cursor.getString(6));
        return personas;
    }
}
