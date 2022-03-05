package com.example.api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;


import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.api.model.Publicacion;

import org.json.JSONException;
import org.json.JSONObject;



import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> arrayDatos=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listViewApi);
        arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayDatos);
        listView.setAdapter(arrayAdapter);
        obtenerDatos();
    }

    private void obtenerDatos(){
       // String direccion="https://jsonplaceholder.typicode.com/posts";
        String direccion="http://10.0.2.2:8080/api/clientes";
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(direccion, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pasarJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);


    }

    private void pasarJson( JSONArray array){

        for(int i=0;i<array.length();i++){
            Publicacion post= new Publicacion();
            JSONObject json=null;

            try {
                json=array.getJSONObject(i);
                post.setNombre(json.getString("nombre"));
                post.setApellido(json.getString("apellido"));
                post.setEmail(json.getString("email"));
                post.setCreate_at(json.getString("create_at"));
                arrayDatos.add("Nombre: "+post.getNombre()+"\nApellido: " + post.getApellido()+
                        "\nFecha Nacimiento "+post.getCreate_at()+"\nCorreo:"+post.getEmail()+"\n" );

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        arrayAdapter.notifyDataSetChanged();
    }


}