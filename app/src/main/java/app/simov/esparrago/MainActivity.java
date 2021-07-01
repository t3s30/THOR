 package app.simov.esparrago;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
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

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

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
    AlertDialog.Builder dialogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Dialogo.

        dialogo=new AlertDialog.Builder(MainActivity.this);
        dialogo.setTitle("MENSAJE DEL THOR");
        dialogo.setMessage("Debes ser Inspector para poder ingresar...");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

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
        sp = getSharedPreferences("credenciales",Context.MODE_PRIVATE);
        Log.d("tag","$$$$$$$$$$$$$$$$$$$$$"+sp.toString());

        //Del Logout
        Bundle bundle  = getIntent().getExtras();

        //Validamos que no venga vacio
        if (bundle != null) {

           String userOut  = bundle.getString("userOut");
           String passOut  = bundle.getString("passOut");


            edtUser.setText(userOut);
            edtPassword.setText(passOut);
        }else{
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
                        if(profile.equals("inspector")){
                            startActivity(intent);
                            Toast.makeText(MainActivity.this,"BIENVENIDO A THOR",Toast.LENGTH_LONG).show();
                        }else{
                            dialogo.show();

                        }




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{

                    Toast.makeText(MainActivity.this,"INGRESA DATOS",Toast.LENGTH_LONG).show();
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