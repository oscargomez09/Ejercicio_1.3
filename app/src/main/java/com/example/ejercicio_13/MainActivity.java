package com.example.ejercicio_13;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Transacciones transacciones;
    Adaptador adaptador;
    ArrayList<Personas> lista;
    Personas personas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transacciones = new Transacciones(MainActivity.this);
        lista = transacciones.verTodos();
        adaptador = new Adaptador(this,lista,transacciones);

        ListView list =(ListView) findViewById(R.id.lista);
        FloatingActionButton nuevo=(FloatingActionButton)findViewById(R.id.btnnuevo);
        list.setAdapter(adaptador);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //LLAMA VISTA.XML
            }
        });

        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //LLAMA AGREGAR.XML
                final Dialog dialogo=new Dialog(MainActivity.this);
                dialogo.setTitle("Nuevo registro");
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

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            personas =new Personas(nombre.getText().toString(),
                                                    apellido.getText().toString(),
                                                    Integer.parseInt(edad.getText().toString()),
                                                    Integer.parseInt(telefono.getText().toString()),
                                                    correo.getText().toString(),
                                                    direccion.getText().toString());
                            transacciones.insertar(personas);
                            lista = transacciones.verTodos();
                            adaptador.notifyDataSetChanged();
                            dialogo.dismiss();

                        }catch (Exception e){
                            Toast.makeText(getApplication(), "ERROR", Toast.LENGTH_SHORT).show();
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

    }
}