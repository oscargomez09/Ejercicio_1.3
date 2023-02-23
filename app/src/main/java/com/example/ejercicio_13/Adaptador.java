package com.example.ejercicio_13;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class Adaptador extends BaseAdapter {

    ArrayList<Personas> lista;
    Transacciones transacciones;
    Personas personas;
    Activity a;
    int id=0;

    public Adaptador(Activity a, ArrayList<Personas> lista, Transacciones transacciones) {
        this.lista = lista;
        this.a = a;
        this.transacciones = transacciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Personas getItem(int i) {
        personas=lista.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        personas=lista.get(i);
        return personas.getId();
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
        View v = view;
        if(v==null){
            LayoutInflater li =(LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=li.inflate(R.layout.items, null);
        }
        personas = lista.get(posicion);

        TextView nombre=(TextView) v.findViewById(R.id.t_nombre);
        TextView apellido=(TextView) v.findViewById(R.id.t_apellido);
        TextView edad=(TextView) v.findViewById(R.id.t_edad);
        TextView telefono=(TextView) v.findViewById(R.id.t_telefono);
        TextView correo=(TextView) v.findViewById(R.id.t_correo);
        TextView direccion=(TextView) v.findViewById(R.id.t_direccion);

        FloatingActionButton editar=(FloatingActionButton) v.findViewById(R.id.btneditar);
        FloatingActionButton eliminar=(FloatingActionButton) v.findViewById(R.id.btneliminar);

        nombre.setText(personas.getNombre());
        apellido.setText(personas.getApellido());
        edad.setText(""+personas.getEdad());
        telefono.setText(""+personas.getTelefono());
        correo.setText(personas.getCorreo());
        direccion.setText(personas.getDireccion());

        editar.setTag(posicion);
        eliminar.setTag(posicion);

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LLAMA EDITAR CON AGREGAR.XML

                int pos= Integer.parseInt(view.getTag().toString());

                final Dialog dialogo=new Dialog(a);
                dialogo.setTitle("Editar registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.agregar);
                dialogo.show();

                EditText nombre = (EditText) dialogo.findViewById(R.id.nombre);
                EditText apellido = (EditText) dialogo.findViewById(R.id.apellido);
                EditText edad = (EditText) dialogo.findViewById(R.id.edad);
                EditText telefono = (EditText) dialogo.findViewById(R.id.telefono);
                EditText correo = (EditText) dialogo.findViewById(R.id.correo);
                EditText direccion = (EditText) dialogo.findViewById(R.id.direccion);

                FloatingActionButton guardar =(FloatingActionButton) dialogo.findViewById(R.id.btnguardar);
                FloatingActionButton cancelar =(FloatingActionButton) dialogo.findViewById(R.id.btncancelar);

                personas = lista.get(pos);
                setId(personas.getId());

                nombre.setText(personas.getNombre());
                apellido.setText(personas.getApellido());
                edad.setText(""+personas.getEdad());
                telefono.setText(""+personas.getTelefono());
                correo.setText(personas.getCorreo());
                direccion.setText(personas.getDireccion());

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            personas =new Personas(getId(),nombre.getText().toString(),
                                    apellido.getText().toString(),
                                    Integer.parseInt(edad.getText().toString()),
                                    Integer.parseInt(telefono.getText().toString()),
                                    correo.getText().toString(),
                                    direccion.getText().toString());
                            transacciones.editar(personas);
                            lista = transacciones.verTodos();
                            notifyDataSetChanged();
                            dialogo.dismiss();

                        }catch (Exception e){
                            Toast.makeText(a, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogo.dismiss();
                    }
                });
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LLAMA PAR CONFIRMAR SI/NO
                int pos= Integer.parseInt(view.getTag().toString());
                personas = lista.get(pos);
                setId(personas.getId());

                AlertDialog.Builder del = new AlertDialog.Builder(a);
                del.setMessage("¿Estas seguro de eliminar el contacto?");
                del.setCancelable(false);

                del.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        transacciones.eliminar(getId());
                        lista = transacciones.verTodos();
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
