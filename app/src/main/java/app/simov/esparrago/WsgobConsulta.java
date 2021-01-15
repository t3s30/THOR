package app.simov.esparrago;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WsgobConsulta extends AppCompatActivity {

    String placa;
    String estatus;
    String propietario;
    String vigencia;
    String vim;
    String marca;
    String infracciones;
    String licencia;
    String LICENCIA;
    String nombre;
    String fechaVecimiento;
    String boleta;
    TextView textViewLicencia;
    TextView textViewNombre;
    TextView textViewFechaVencimiento;
    TextView textViewInfracciones;
    TextView textViewFechaInfracciones;


    String usersId;
    String username;
    String profile;
    String nombreLogin;
    String delegacionId;
    String activo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wsgob_consulta);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //Orientacion de pantalla.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setSupportActionBar(toolbar);
        String URLICENCIA = getResources().getString(R.string.URL_LICENCIA);;
        String URLINFRACCION = getResources().getString(R.string.URL_INFRACCION);
        enviarWSConsultaLicencia(URLICENCIA);
        enviarWSConsultaInfraccion(URLINFRACCION);

        TextView textViewPlaca = (TextView)findViewById(R.id.tvPlaca);
        TextView textViewEstatus = (TextView)findViewById(R.id.tvEstatus);
        TextView textViewPropietario = (TextView)findViewById(R.id.tvPropietario);
        TextView textViewVigencia = (TextView)findViewById(R.id.tvVigencia);
        TextView textViewVim = (TextView)findViewById(R.id.tvVim);
        TextView textViewMarca = (TextView)findViewById(R.id.tvMarca);
        textViewInfracciones = (TextView)findViewById(R.id.tvInfracciones);
        textViewLicencia = (TextView)findViewById(R.id.tvLicencia);
        textViewNombre = (TextView)findViewById(R.id.tvNombre);
        textViewFechaVencimiento = (TextView)findViewById(R.id.tvFechaVencimiento);
        textViewFechaInfracciones = (TextView)findViewById(R.id.tvFechaInfraccion);


        Bundle bundle  = getIntent().getExtras();
        //Validamos que no venga vacio
        if (bundle != null){

            //Recojemos parametros.
            placa = bundle.getString("placa");
            Log.d("###PLACASSS","#######"+placa);
            estatus = bundle.getString("estatus");
            propietario = bundle.getString("propietario");
            vigencia = bundle.getString("vigencia");
            vim = bundle.getString("vim");
            marca = bundle.getString("marca");
            infracciones = bundle.getString("infracciones");
            licencia = bundle.getString("licencia");

            Log.d("LICENCIA-VERGAS1","$$$$$$$$$$$$$$$"+licencia);
            nombre = bundle.getString("nnombre");
            fechaVecimiento = bundle.getString("fechaVencimiento");
            textViewPlaca.setText(placa);


            usersId  = bundle.getString("usersId");
            Log.d("USERSSSSSSSS","#####################&%&%&%&%&%&%&%&%&USERSSSSSSSS"+usersId);
            username  = bundle.getString("username");
            profile  = bundle.getString("profile");
            nombreLogin  = bundle.getString("nombre");
            delegacionId  = bundle.getString("delegacionId");
            activo  = bundle.getString("activo");



            if (propietario==null){

                textViewEstatus.setText("NO EXISTEN DATOS");
                textViewPropietario.setText("NO EXISTEN DATOS");
                textViewVigencia.setText("NO EXISTEN DATOS");
                textViewVim.setText("NO EXISTEN DATOS");
                textViewMarca.setText("NO EXISTEN DATOS");
            }else {
                String banderaLic=  bundle.getString("bandera");

                textViewEstatus.setText(estatus);
                textViewPropietario.setText(propietario);
                textViewVigencia.setText(vigencia);
                textViewVim.setText(vim);
                textViewMarca.setText(marca);
            }

        }



    }



    private void enviarWSConsultaLicencia(String URLICENCIA) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLICENCIA, new Response.Listener<String>() {
            @Override
            //Para mandar un post aun WS el response Listener tiene que ser de tipo  String , y despues convertir la respuesta a JsonObject.
            public void onResponse(String response) {
                //Validamos que el response no este vacio
                if (!response.isEmpty()) {
                    //Esto contiene toda la cadena de respuesta del Ws.
                    //Toast.makeText(WsgobConsulta.this, "SE MANDO PETICION CORRECTA A WS LICENCIA" + response, Toast.LENGTH_LONG).show();

                    try {
                        //Convertimos el String en JsonObject
                        JSONObject obj = new JSONObject(response);
                        Log.d("objLicencia", "###Respuesta WS licencia" + obj.toString());
                        //Accedemos al valor del Objeto deseado completo.tos
                        JSONArray jsonarray = obj.getJSONArray("data");


                        if (jsonarray.length()==0){
                            textViewNombre.setText("NO EXISTE EN BD");
                            textViewLicencia.setText("NO EXISTE EN BD");
                            textViewFechaVencimiento.setText("NO EXISTE EN BD");
                        }


                        //Obtenemos el total de elementos del objeto
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            //Accedemos a los elementos por medio de getString.
                            LICENCIA = jsonobject.getString("licencia");
                            String VENCIMIENTO = jsonobject.getString("fechaVenc");
                            String paterno = jsonobject.getString("paterno");
                            String materno = jsonobject.getString("materno");
                            String nombre  = jsonobject.getString("nombre");

                            String nombreCompleto = nombre+" "+paterno+" "+materno;

                            textViewNombre.setText(nombreCompleto);
                            textViewLicencia.setText(LICENCIA);
                            textViewFechaVencimiento.setText(VENCIMIENTO);


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                            //Seteamos valor cuando no se ingrese licencia
                            textViewNombre.setText("NO-LICENCIA");
                            textViewLicencia.setText("NO-LICENCIA");
                            textViewFechaVencimiento.setText("NO-LICENCIA");

                    }

                } else {

                    Toast.makeText(WsgobConsulta.this, "No se encontraron parametros en la consulta de licencia", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Seteamos valor cuando sobre pasa el tiempo de esepera
                textViewNombre.setText("LIMITE DE ESPERA");
                textViewLicencia.setText("LIMITE DE ESPERA");
                textViewFechaVencimiento.setText("LIMITE DE ESPERA");
                Toast.makeText(WsgobConsulta.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                Log.d("LICENCIA-VERGAS2","$$$$$$$$$$$$$$$$"+licencia);
                parametros.put("licencia", licencia);

                return parametros;
            }
        };
        RequestQueue requesrQueue = Volley.newRequestQueue(WsgobConsulta.this);
        requesrQueue.add(stringRequest);
    }


   /* private void enviarWSConsultaInfraccion() {

        String url = "https://simov.app/servicios/consultaInfraccion.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            public void onResponse(JSONObject response) {
                try {

                  //  JSONArray jsonArray = response.getJSONArray("data");
                    JSONObject jObject2 = response.getJSONObject("data");

                    Log.d("boleta","Estamos en la boleta"+jObject2.toString());
                    for (int i = 0; i < jObject2.length(); i++) {
                        boleta = jObject2.getString("boleta");


                      textViewInfracciones.setText(boleta);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("placa",placa);

                return parametros;
            }
        };
        RequestQueue requesrQueue   = Volley.newRequestQueue(WsgobConsulta.this);
        requesrQueue.add(request);

    }*/

    private void enviarWSConsultaInfraccion(String URLINFRACCION) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLINFRACCION, new Response.Listener<String>() {
            @Override
            //Para mandar un post aun WS el response Listener tiene que ser de tipo  String , y despues convertir la respuesta a JsonObject.
            public void onResponse(String response) {
                //Validamos que el response no este vacio
                if (!response.isEmpty()) {
                    //Esto contiene toda la cadena de respuesta del Ws.
                   // Toast.makeText(WsgobConsulta.this, "SE MANDO PETICION CORRECTA A WS INFRACCIONES" + response, Toast.LENGTH_LONG).show();

                    try {
                        //Convertimos el String en JsonObject
                        JSONObject obj = new JSONObject(response);
                        Log.d("objInfraccion", "###Respuesta WS infraccion" + obj.toString());
                        //Accedemos al valor del Objeto deseado completo.
                        String infracciones = obj.getString("infracciones");

                        Log.d("infraccion1",infracciones);
                       // Log.d("infraccion2",fechaInfracion);

                        if (infracciones.equals("No hay datos")){
                            textViewInfracciones.setText("SIN INFRACCIONES");
                            textViewFechaInfracciones.setText("SIN INFRACCIONES");
                        }else{
                            String fechaInfracion = obj.getString("fechaInfracion");
                            textViewInfracciones.setText(infracciones);
                            textViewFechaInfracciones.setText(fechaInfracion);
                        }


                        //Obtenemos el total de elementos del objeto
                   /*     for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            //Accedemos a los elementos por medio de getString.
                            String BOLETA = jsonobject.getString("boleta");
                            Log.d("wsInfraccion#1","Los datos de infraciones son los siguientes"+BOLETA.toString());

                        }
*/


                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                    //Lanzamos Intent Navigation Drawer.
                    /*Intent intent = new Intent(getApplicationContext(), Drawer.class);
                    startActivity(intent);*/
                } else {
                    Toast.makeText(WsgobConsulta.this, "No se encontraron parametros en la consulta de infracciones", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WsgobConsulta.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("licencia",licencia);
                Log.d("licencia","######################### mi parametro LICENCIA"+licencia);

                parametros.put("placa", placa);

                return parametros;
            }
        };
        RequestQueue requesrQueue = Volley.newRequestQueue(WsgobConsulta.this);
        requesrQueue.add(stringRequest);
    }

//Cuando se va atras refrescar fragmento.


    @Override
    public void onBackPressed()
    {
        //Para llamarse asi mismo y ejecutarse
        super.onBackPressed();
        //Terminando la actividad en curso
        this.finish();
        //Regresando a la actividad principal
        Log.d("Back1","Entre en el BACKPRESSED");
        Intent gotoBack = new Intent(WsgobConsulta.this, Drawer.class);
        gotoBack.putExtra("usersId",usersId);
        gotoBack.putExtra("username",username);
        gotoBack.putExtra("profile",profile);
        gotoBack.putExtra("nombre",nombreLogin);
        gotoBack.putExtra("delegacionId",delegacionId);
        gotoBack.putExtra("activo",activo);
        gotoBack.putExtra("placa",placa);
        //gotoBack.putExtra(USER_GLOBAL_SENDER, username_global); <-- Use this if you want to carry some data to the other activity.
        finish();
        startActivity(gotoBack);
    }

}