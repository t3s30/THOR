package app.simov.esparrago;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity<restoredText> extends AppCompatActivity {

    private EditText edtUser;
    private EditText edtPassword;
    private Button   btnLogin;
    SharedPreferences sp;

    String username;
    String password;
    String profile;
    String nombre;
    String delegacionId;
    String activo;
    String usersId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Ocultanos e action bar9
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        String URL = getString(R.string.app_url_login);
        //Orientacion de pantalla.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Llamamos a los objetos que estan en la vista XML por su ID
        edtUser = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        sp = getSharedPreferences("login",MODE_PRIVATE);

        if(sp.getBoolean("logged",false)){
            sp.edit().putBoolean("logged",true).apply();
            SharedPreferences preferences = getSharedPreferences("credenciales",Context.MODE_PRIVATE);

            String estUsuario = preferences.getString("miUsuario","");
            String estPass = preferences.getString("miPassword","");

            edtUser.setText(estUsuario);
            edtPassword.setText(estPass);
            validarUsuario(URL);
        }

        //Metodo para disparar la validacion de usuario.
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                    try {
                        JSONObject obj = new JSONObject(response);
                        String usersId = obj.getString("UsersID");
                        Log.d("USERSID","!!!!!"+usersId);
                        username = obj.getString("username");
                        password = obj.getString("password");
                        profile = obj.getString("profile");
                        nombre = obj.getString("comment");
                        delegacionId = obj.getString("delegacionID");
                        activo = obj.getString("Activo");
                        Intent intent = new Intent(getApplicationContext(),Drawer.class);

                        intent.putExtra("usersId",usersId);
                        Log.d("USERSID-MAIN","Esta la info que viene del MAIN"+usersId);
                        intent.putExtra("username",username);
                        intent.putExtra("password",password);
                        intent.putExtra("profile",profile);
                        intent.putExtra("nombre",nombre);
                        intent.putExtra("delegacionId",delegacionId);
                        intent.putExtra("activo",activo);

                        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

                        String usuario = username;
                        String pass = password;

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("miUsuario",usuario);
                        editor.putString("miPassword",pass);

                        edtUser.setText(usuario);
                        edtPassword.setText(pass);

                        editor.commit();

                        startActivity(intent);

                       /* if (usersId.equals(null)){
                            Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);

                            startActivity(intent2);
                        }else{
                            sp.edit().putBoolean("logged",true).apply();
                            startActivity(intent);
                        }*/


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    //Lanzamos Intent Navigation Drawer.

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

    /*  public void goToMainActivity(String URL){

        *//*intent.putExtra("usersId",usersId);
        Log.d("USERSID-MAIN","Esta la info que viene del MAIN"+usersId);
        intent.putExtra("username",username);
        intent.putExtra("password",password);
        intent.putExtra("profile",profile);
        intent.putExtra("nombre",nombre);
        intent.putExtra("delegacionId",delegacionId);
        intent.putExtra("activo",activo);*//*

       *//* if (usersId.equals(null)){


        }else{
            startActivity(intent);
        }
*//*
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Validamos que el response no este vacio
                if(!response.isEmpty()){
                    Toast.makeText(MainActivity.this,"ENTRASTE AL LOGIN"+response,Toast.LENGTH_LONG).show();
                    try {
                        JSONObject obj = new JSONObject(response);
                        String usersId = obj.getString("UsersID");
                        Log.d("USERSID","!!!!!"+usersId);
                        username = obj.getString("username");
                        password = obj.getString("password");
                        profile = obj.getString("profile");
                        nombre = obj.getString("comment");
                        delegacionId = obj.getString("delegacionID");
                        activo = obj.getString("Activo");
                        Intent intent = new Intent(getApplicationContext(),Drawer.class);

                        intent.putExtra("usersId",usersId);
                        Log.d("USERSID-MAIN","Esta la info que viene del MAIN"+usersId);
                        intent.putExtra("username",username);
                        intent.putExtra("password",password);
                        intent.putExtra("profile",profile);
                        intent.putExtra("nombre",nombre);
                        intent.putExtra("delegacionId",delegacionId);
                        intent.putExtra("activo",activo);

                        startActivity(intent);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    //Lanzamos Intent Navigation Drawer.

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
}*/

    public void goToMainActivity(){


    }


}