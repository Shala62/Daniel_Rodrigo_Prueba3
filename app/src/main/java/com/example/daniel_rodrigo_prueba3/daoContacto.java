package com.example.daniel_rodrigo_prueba3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class daoContacto {

    //Instanciamos la BD
    SQLiteDatabase bd;
    //Almacenar datos en la lista
    ArrayList<Contacto> lista = new ArrayList<Contacto>();
    //Instanciar la clase contacto
    Contacto c;
    //Contexto de la app
    Context ct;
    //Nombre de la BD
    String nombreBD= "BDContactos";
    //Crear la cadena SQL que crea la tabla, en caso de que no exista
    String tabla = "create table if not exists datos(id integer primary key autoincrement, nombre text, edad int, raza text)";

    //Constructor de la clase acepte los parámetros de tipo context y los inicializa
    public daoContacto(Context c){
        this.ct = c;
        bd = c.openOrCreateDatabase(nombreBD, Context.MODE_PRIVATE, null);
        bd.execSQL(tabla);
    }

    //Metodo Insertar
    public boolean insertar(Contacto c){
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", c.getNombre());
        contenedor.put("edad", c.getEdad());
        contenedor.put("raza", c.getRaza());

        return (bd.insert("datos", null, contenedor))>0;
    }

    //Metodo Eliminar
    public boolean eliminar(int id){
        return (bd.delete("datos", "id="+id, null))>0;
    }

    //Metodo Editar
    public boolean editar(Contacto c){
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre", c.getNombre());
        contenedor.put("edad", c.getEdad());
        contenedor.put("raza", c.getRaza());

        return (bd.update("datos", contenedor,"id="+c.getId(), null))>0;
    }

    //Metodo para mostrar Todos
    public ArrayList<Contacto>verTodo(){
        lista.clear();
        Cursor cursor = bd.rawQuery("select * from datos", null);

        if (cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                lista.add(new Contacto(cursor.getInt(0),
                        cursor.getString(1), cursor.getInt(2),
                        cursor.getString(3)));
            }while (cursor.moveToNext());
        }
        return lista;
    }

    public Contacto verUno(int posicion){
        Cursor cursor = bd.rawQuery("select * from datos", null);
        cursor.moveToPosition(posicion);
        c=new Contacto(cursor.getInt(0),
                cursor.getString(1), cursor.getInt(2),
                cursor.getString(3));
        return c;
    }
}
