package app.simov.esparrago;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText edtUser;
    private EditText edtPassword;
    private Button   btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Ocultanos e action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //Llamamos a los objetos que estan en la vista XML por su ID
        edtUser = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        //Metodo para disparar la validacion de usuario.
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL = getString(R.string.app_url_login);
                validarUsuario(URL);
            }
        });

    }

    private void validarUsuario(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Validamos que el response no este vacio
                if(!response.isEmpty()){
                    Toast.makeText(MainActivity.this,"ENTRASTE AL LOGIN"+response,Toast.LENGTH_LONG).show();
                    //Lanzamos Intent Navigation Drawer.
                Intent intent = new Intent(getApplicationContext(),Drawer.class);
                startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this,"CONTRASEÑA INCORRECTA",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("usuario",edtUser.getText().toString());
                parametros.put("password",edtPassword.getText().toString());
                return parametros;
            }
        };
        RequestQueue requesrQueue   = Volley.newRequestQueue(this);
        requesrQueue.add(stringRequest);
    }
}