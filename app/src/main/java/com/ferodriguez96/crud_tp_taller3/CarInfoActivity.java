package com.ferodriguez96.crud_tp_taller3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CarInfoActivity extends AppCompatActivity {

    Auto auto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);
        String id = getIntent().getStringExtra("id");
        if(null!=id){
            getAuto(id);
        }
    }

    public void getAuto(String id){
        // Establezco una relacion de mi app con este endpoint:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-be-tp3-a.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Defnimos la interfaz para que utilice la base retrofit de mi aplicacion ()
        AutoService autoService = retrofit.create(AutoService.class);
        Call<Auto> http_call = autoService.getAutoById(id);
        http_call.enqueue(new Callback<Auto>() {
            @Override
            public void onResponse(Call<Auto> call, Response<Auto> response) {
                auto = response.body();
                fillForm();
            }

            @Override
            public void onFailure(Call<Auto> call, Throwable t) {
                // SI el servidor o la llamada no puede ejecutarse, muestro un mensaje de eror:
                Toast.makeText(CarInfoActivity.this,"Hubo un error con la llamada a la API", Toast.LENGTH_LONG);
            }
        });
    }

    private void  fillForm(){
        EditText marca = (EditText)findViewById(R.id.etxtMarca);
        EditText modelo = (EditText)findViewById(R.id.etxtModelo);
        marca.setText(auto.getMarca());
        modelo.setText(auto.getModelo());
        Button delete = (Button)findViewById(R.id.btnDelete);
        delete.setEnabled(true);
    }


    public void onClickSaveAuto(View view){
        if(null==auto){
            auto = new Auto();
        }
        EditText marca = (EditText) findViewById(R.id.etxtMarca);
        EditText modelo = (EditText) findViewById(R.id.etxtModelo);
        auto.setMarca(marca.getText().toString());
        auto.setModelo(modelo.getText().toString());
        saveAuto();
    }

    private void saveAuto(){
        if(null==auto.getId()){
            createAuto();
        }
        else{
            updateAuto();
        }
        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(intent);
        finish();
    }

    private void createAuto(){
        // Establezco una relacion de mi app con este endpoint:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-be-tp3-a.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Defnimos la interfaz para que utilice la base retrofit de mi aplicacion ()
        AutoService autoService = retrofit.create(AutoService.class);
        Call<Auto> http_call = autoService.createAuto(auto);
        http_call.enqueue(new Callback<Auto>() {
            @Override
            public void onResponse(Call<Auto> call, Response<Auto> response) {
                Toast.makeText(CarInfoActivity.this,"Se creo un nuevo auto",Toast.LENGTH_LONG).show();//.show();
            }

            @Override
            public void onFailure(Call<Auto> call, Throwable t) {
                Toast.makeText(CarInfoActivity.this,"Hubo un error al crear un nuevo auto", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateAuto(){
        // Establezco una relacion de mi app con este endpoint:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-be-tp3-a.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Defnimos la interfaz para que utilice la base retrofit de mi aplicacion ()
        AutoService autoService = retrofit.create(AutoService.class);
        Call<Void> http_call = autoService.updateAuto(auto.getId(),auto);
        http_call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(CarInfoActivity.this,"Se actualizo satisfactoriamente", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CarInfoActivity.this,"Hubo un error al modificar el auto", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void onClickDeleteAuto(View view){
        // Establezco una relacion de mi app con este endpoint:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-be-tp3-a.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Defnimos la interfaz para que utilice la base retrofit de mi aplicacion ()
        AutoService autoService = retrofit.create(AutoService.class);
        Call<Void> http_call = autoService.deleteAutoById(auto.getId());
        http_call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(CarInfoActivity.this,"Se eliminó satisfactoriamente", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CarInfoActivity.this,"Hubo un error con la llamada a la API", Toast.LENGTH_LONG).show();
            }
        });
        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(intent);
        finish();
    }

}
