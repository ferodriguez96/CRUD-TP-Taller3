package com.ferodriguez96.crud_tp_taller3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListActivity extends android.app.ListActivity {

    ListView list;
    ListAdapter adaptador;
    ArrayList<Auto> cars = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        loadList();
    }

    public  void onResume(){
        super.onResume();
        loadList();
    }

    private void loadList(){
        cars.clear();
        //adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autos);
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cars);

        list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(adaptador);
        this.getListadoVehiculos();
    }
    public void getListadoVehiculos(){

        // Establezco una relacion de mi app con este endpoint:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-be-tp3-a.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Defnimos la interfaz para que utilice la base retrofit de mi aplicacion ()
        AutoService autoService = retrofit.create(AutoService.class);
        Call<List<Auto>> http_call = autoService.getAutos();
        http_call.enqueue(new Callback<List<Auto>>() {
            @Override
            public void onResponse(Call<List<Auto>> call, Response<List<Auto>> response) {
                // Si el servidor responde correctamente puedo hacer uso de la respuesta esperada:
                cars.clear();
                for (Auto auto: response.body()){
                    cars.add(auto);
                }
                // Aviso al base adapter que cambio mi set de datos.
                // Renderizacion general de mi ListView
                ((BaseAdapter) adaptador).notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Auto>> call, Throwable t) {
                // SI el servidor o la llamada no puede ejecutarse, muestro un mensaje de eror:
                Toast.makeText(ListActivity.this,"Hubo un error con la llamada a la API", Toast.LENGTH_LONG).show();

            }
        });

    }

    // Evento que se disparará al pulsar en un elemento de la lista

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(ListActivity.this,cars.get(position).toString(),Toast.LENGTH_LONG).show();
        goToInfoActivity(cars.get(position).getId());
        // Muestra un mensaje al presionar sobre un item de la lista
    }

    public void goToInfoActivity(String id){
        Intent intent = new Intent(getApplicationContext(), CarInfoActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
        finish();
    }

    public void createNewCar(View view){
        goToInfoActivity(null);
    }

}
