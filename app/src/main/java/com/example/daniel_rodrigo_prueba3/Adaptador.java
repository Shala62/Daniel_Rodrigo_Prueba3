package com.example.daniel_rodrigo_prueba3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {

    ArrayList<Contacto> lista;
    daoContacto dao;
    Contacto c;
    Activity a;
    int id = 0;

    //Constructor de la clase que recibe los parámetros
    public Adaptador(Activity a, ArrayList<Contacto> lista, daoContacto dao){
        this.lista = lista;
        this.a = a;
        this.dao = dao;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        c = lista.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        c = lista.get(i);
        return c.getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View v = view;
        if (v == null) {
            LayoutInflater li = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.item, null);
        }
        //Obtenemos el contacto en la posicion
        c = lista.get(position);

        //Se obtiene referencias a la vista con su Id
        TextView nombre = v.findViewById(R.id.editmascota);
        TextView edad = v.findViewById(R.id.editedad);
        TextView raza = v.findViewById(R.id.editraza);


        Button editar = v.findViewById(R.id.btneditar);
        Button eliminar = v.findViewById(R.id.btneliminar);

        nombre.setText(c.getNombre());
        edad.setText(c.getEdad());
        raza.setText(c.getRaza());

        editar.setTag(position);
        eliminar.setTag(position);

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dialog editar
                int pos = Integer.parseInt(view.getTag().toString());
                final Dialog dialog = new Dialog(a);
                dialog.setTitle("Editar Registro");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.activity_agregardatos);
                dialog.show();
                final EditText nombre = dialog.findViewById(R.id.editmascota);
                final EditText edad = dialog.findViewById(R.id.editedad);
                final EditText raza = dialog.findViewById(R.id.editraza);

                Button guardar = dialog.findViewById(R.id.btnagregar);
                Button cancelar = dialog.findViewById(R.id.btncancelar);

                c = lista.get(pos);
                setId(c.getId());
                nombre.setText(c.getNombre());
                edad.setText(c.getEdad());
                raza.setText(c.getRaza());
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            c = new Contacto(getId(), nombre.getText().toString(),
                                    edad.getInputType(),
                                    raza.getText().toString());
                            dao.editar(c);
                            lista = dao.verTodo();
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }catch (Exception e){
                            Toast.makeText(a, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dialog para confirmar si se elimina o no
                int pos = Integer.parseInt(view.getTag().toString());
                c = lista.get(position);
                setId(getId());
                AlertDialog.Builder del = new AlertDialog.Builder(a);
                del.setMessage("¿Estás seguro de Eliminar?");
                del.setCancelable(false);
                del.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dao.eliminar(getId());
                        lista = dao.verTodo();
                        notifyDataSetChanged();
                    }
                });
                del.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                del.show();
            }
        });
        return v;
    }
}
