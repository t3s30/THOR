package app.simov.esparrago;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Wsgob extends AppCompatActivity {
    String para;
    String marca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wsgob3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);







        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // enviarWS();
            }
        });

        //Cargamos los parametros y obtenemos el intent
        Bundle bundle  = getIntent().getExtras();
        //Validamos que no venga vacio
        if (bundle != null){
            //Recojemos parametros.
            para=  bundle.getString("param");
            marca = bundle.getString("marca");
            String banderaLic=  bundle.getString("bandera");
            Toast.makeText(getApplicationContext(),marca,Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),para,Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),banderaLic,Toast.LENGTH_LONG).show();
        }
    }





}