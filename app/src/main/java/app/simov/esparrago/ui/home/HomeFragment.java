package app.simov.esparrago.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.simov.esparrago.Infracciones;
import app.simov.esparrago.R;
import app.simov.esparrago.WsgobConsulta;

public class HomeFragment extends Fragment {
    CheckBox checkBoxLicencia;
    boolean banderaLicencia = false;
    boolean banderaPlaca = false;
    String enviaBanderaLic;
    String licencia;
    String placa;
    EditText editTextPlaca;
    EditText editTextLicencia;
    ProgressDialog progressDialog;
    Spinner spinnerModalidad;
    Spinner spinerSector;
    Spinner spinnerInfra1;
    Spinner spinnerInfra2;
    Spinner spinnerInfra3;
    Spinner spinnerInfra4;
    Spinner spinnerInfra5;
    String modalidad;
    String sector;
    String infraccion1;
    String infraccion2;
    String infraccion3;
    String infraccion4;
    String infraccion5;

    int cuenta ;
    String usersId;
    String username;
    String profile;
    String nombre;
    String delegacionId;
    String activo;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        //Parametros XML
        final TextView nombreApp = root.findViewById(R.id.tvApp);
        final TextView tvUsuario = root.findViewById(R.id.tvUsuario);
        final TextView tvUsuario2 = root.findViewById(R.id.tvUsuario2);
        final TextView tvMunicipio = root.findViewById(R.id.tvMunicipio);
        checkBoxLicencia = root.findViewById(R.id.cBoxLicencia);
        final CheckBox checkBoxPlaca = root.findViewById(R.id.cBoxPlaca);

        editTextPlaca = root.findViewById(R.id.edtPlaca);
        editTextLicencia = root.findViewById(R.id.edtLicencia);
        final Spinner spinnerZona = root.findViewById(R.id.spZona);
        final Spinner spinnerInfraccion = root.findViewById(R.id.spInfraccion3);
        final Button buttonInfraccion = root.findViewById(R.id.btnInfraccion);
        final Button buttonConsulta = root.findViewById(R.id.btnConsulta);
        final Button bntCuenta = root.findViewById(R.id.btnCuenta);
        final Button bntQuitar = root.findViewById(R.id.btnQuitar);


        spinnerModalidad = root.findViewById(R.id.spModalidad);
        spinerSector = root.findViewById(R.id.spZona);
        spinnerInfra1 = root.findViewById(R.id.spInfraccion1);
        spinnerInfra2 = root.findViewById(R.id.spInfraccion2);
        spinnerInfra3 = root.findViewById(R.id.spInfraccion3);
        spinnerInfra4 = root.findViewById(R.id.spInfraccion4);
        spinnerInfra5 = root.findViewById(R.id.spInfraccion5);

        spinnerInfra1.setVisibility(View.GONE);
        spinnerInfra2.setVisibility(View.GONE);
        spinnerInfra3.setVisibility(View.GONE);
        spinnerInfra4.setVisibility(View.GONE);
        spinnerInfra5.setVisibility(View.GONE);

        bntCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuenta++;

                Log.d("CUENTS+++","CONTADOR"+cuenta);
                if (cuenta == 1){
                    spinnerInfra1.setVisibility(View.VISIBLE);
                    bntQuitar.setVisibility(View.VISIBLE);
                }
                if (cuenta == 2){
                    spinnerInfra2.setVisibility(View.VISIBLE);

                }
                if(cuenta == 3){
                    spinnerInfra3.setVisibility(View.VISIBLE);
                }
                if(cuenta == 4){
                    spinnerInfra4.setVisibility(View.VISIBLE);
                }
                if(cuenta == 5){
                    spinnerInfra5.setVisibility(View.VISIBLE);

                }
            }
        });


        bntQuitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cuenta--;
                Log.d("CUENTS---","CONTADOR"+cuenta);
                if (cuenta == 4){
                    spinnerInfra5.setVisibility(View.GONE);
                }
                if (cuenta == 3){
                    spinnerInfra4.setVisibility(View.GONE);
                }
                if(cuenta == 2){
                    spinnerInfra3.setVisibility(View.GONE);
                }
                if(cuenta == 1){
                    spinnerInfra2.setVisibility(View.GONE);
                }
                if(cuenta == 0){
                    spinnerInfra1.setVisibility(View.GONE);
                    bntQuitar.setVisibility(View.GONE);


                }
            }
        });



        editTextLicencia.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        editTextPlaca.setFilters(new InputFilter[]{new InputFilter.AllCaps()});


        //Seteamos Valores.
        nombreApp.setText("THOR");
        tvUsuario.setText("Abdiel Carrasco");
       // tvMunicipio.setText("Tijuana(USR213)");

        //Datos de Bundle de inicio de session.
        Bundle args = getActivity().getIntent().getExtras();

        if (args != null) {
            //Recojemos parametros.
            usersId  = args.getString("usersId");
            username  = args.getString("username");
            profile  = args.getString("profile");
            nombre  = args.getString("nombre");
            delegacionId  = args.getString("delegacionId");
            activo  = args.getString("activo");
            tvUsuario.setText(nombre);
            tvUsuario2.setText(username);
            tvMunicipio.setText("Tijuana");
          Log.d("USERSID","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ ESTO ES LO QUE RECOJI DEL USERS ID"+usersId);
        }


        //#####################################################



//Validacion de Checkbox
        checkBoxLicencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxLicencia.isChecked() == true) {
                    banderaLicencia = true;
                    Log.d("CHBOX", "ESTAS CLIKEANDO DE CHECK DE LICENCIA## el valor de la bandera es: " + banderaLicencia);

                } else {
                    banderaLicencia = false;
                    Log.d("CHBOX", "ESTAS CLIKEANDO DE CHECK DE LICENCIA## el valor de la bandera es: " + banderaLicencia);
                }

            }
        });


        checkBoxPlaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxPlaca.isChecked() == true) {
                    banderaPlaca = true;
                    Log.d("CHBOX", "ESTAS CLIKEANDO CHECK DE PLACA## el valor de la bandera es:  " + banderaPlaca);
                } else {
                    banderaPlaca = false;
                    Log.d("CHBOX", "ESTAS CLIKEANDO CHECK DE PLACA## el valor de la bandera es:  " + banderaPlaca);
                }

            }
        });


//Boton que termina el proceso para enviar la informacion de Infraccion.
        buttonInfraccion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Inicializamos el progress BAR
                progressDialog = new ProgressDialog(getContext());
                //Mostramos el progressBAR
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                //Fondo transparente
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );


                if (banderaPlaca == true) {
                    banderaLicencia = false;
                    //Aqui declaramos solo lo que queremos que se cargue despues del click del boton para iniciar la nueva actividad
                    editTextPlaca = root.findViewById(R.id.edtPlaca);
                    placa = editTextPlaca.getText().toString(); //gets you the contents of edit text

                    Log.d("Variable", "LICENCIA## " + placa);
                    String URL = "https://simov.app/servicios/controlVehicular.php";

                    //Envia Ws
                    enviarWSConsultaInfraccion(URL);

                }if (banderaLicencia == true){

                    String URL = "https://simov.app/servicios/controlVehicular.php";

                    //Envia Ws
                    enviarWSConsultaInfraccion(URL);

                }
                else {
                    progressDialog.hide();
                    Toast.makeText(getContext(), "Tienes que seleccionar PLACA o LICENCIA", Toast.LENGTH_LONG).show();
                }



            }
        });

        //Boton para consulta WS de placa y licencia
        buttonConsulta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Inicializamos el progress BAR
                progressDialog = new ProgressDialog(getContext());
                //Mostramos el progressBAR
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                //Fondo transparente
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );


                if (banderaPlaca == true) {
                    banderaLicencia = false;
                    //Aqui declaramos solo lo que queremos que se cargue despues del click del boton para iniciar la nueva actividad
                    editTextPlaca = root.findViewById(R.id.edtPlaca);
                    placa = editTextPlaca.getText().toString(); //gets you the contents of edit text

                    Log.d("Variable", "LICENCIA## " + placa);
                    String URL = "https://simov.app/servicios/controlVehicular.php";

                    //Envia Ws
                    enviarWSConsulta(URL);

                }if (banderaLicencia == true){

                    String URL = "https://simov.app/servicios/controlVehicular.php";

                    //Envia Ws
                    enviarWSConsulta(URL);

                }
                else {
                    progressDialog.hide();
                    Toast.makeText(getContext(), "Tienes que seleccionar PLACA o LICENCIA", Toast.LENGTH_LONG).show();
                }

            }
        });


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

/*
    private void enviarWSInfraccion() {


        //StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://simov.app/servicios/controlVehicular.php", new Response.Listener<JSONObject>() {
        String url = "https://simov.app/servicios/controlVehicular.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject wsGob = jsonArray.getJSONObject(i);

                        //Obteniendo datos del WS de gobbierno

                        String placa = wsGob.getString("placa");
                        Log.d("PLACAWS", "placa que vienedel WSGOB" + placa.toString());

                        String estatus = wsGob.getString("estatus");
                        String propietario = wsGob.getString("propietario");
                        Log.d("NOMBREWS", "placa que vienedel WSGOB" + propietario.toString());
                        String vigencia = wsGob.getString("fechaVencimiento");
                        String vim = wsGob.getString("serie");
                        String marca = wsGob.getString("marca");

                        Log.d("WS", "ESTAS EN WS " + marca);

                        //Obtenemos el valor del edit de la licencia.
                        licencia = editTextLicencia.getText().toString();

                        Intent intentWs = new Intent(getActivity(), Wsgob.class);
                        intentWs.putExtra("licencia", licencia);
                        intentWs.putExtra("bandera", enviaBanderaLic);
                        intentWs.putExtra("placa", placa);
                        intentWs.putExtra("estatus", estatus);
                        intentWs.putExtra("propietario", propietario);
                        intentWs.putExtra("vigencia", vigencia);
                        intentWs.putExtra("vim", vim);
                        intentWs.putExtra("marca", marca);

                        startActivity(intentWs);
                        //int age = employee.getInt("age");
                        //String mail = employee.getString("mail");
                        //textView.append(firstName + ", " + String.valueOf(age) + ", " + mail +"\n\n");
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
        });
        RequestQueue requesrQueue = Volley.newRequestQueue(getContext());
        requesrQueue.add(request);

    }
*/

    /*
    *  private void validarUsuario(String URL){
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
    *
    * */

   /* private void enviarWSConsulta() {
        String url = "https://simov.app/servicios/controlVehicular.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject wsGob = jsonArray.getJSONObject(i);
                        //Obteniendo datos del WS de gobbierno

                        placa = wsGob.getString("placa");
                        Log.d("PLACAWS","placa que vienedel WSGOB"+placa.toString());

                        String estatus = wsGob.getString("estatus");
                        String propietario = wsGob.getString("propietario");
                        Log.d("NOMBREWS","placa que vienedel WSGOB"+propietario.toString());
                        String vigencia = wsGob.getString("fechaVencimiento");
                        String vim  = wsGob.getString("serie");
                        String marca = wsGob.getString("marca");

                        Log.d("WS", "ESTAS EN WS "+ marca);

                        Intent intentWs = new Intent(getActivity(), WsgobConsulta.class);
                        intentWs.putExtra("param",licencia);
                        intentWs.putExtra("bandera",enviaBanderaLic);
                        intentWs.putExtra("placa",placa);
                        intentWs.putExtra("estatus",estatus);
                        intentWs.putExtra("propietario",propietario);
                        intentWs.putExtra("vigencia",vigencia);
                        intentWs.putExtra("vim",vim);
                        intentWs.putExtra("marca",marca);

                        startActivity(intentWs);
                        //int age = employee.getInt("age");
                        //String mail = employee.getString("mail");
                        //textView.append(firstName + ", " + String.valueOf(age) + ", " + mail +"\n\n");
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
                parametros.put("placa",editTextLicencia.getText().toString());
                Log.d("parametro","Valor parametro edt placa"+parametros.toString());
                return parametros;
            }
        };
        RequestQueue requesrQueue   = Volley.newRequestQueue(getContext());
        requesrQueue.add(request);

    }
*/


    private void enviarWSConsulta(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            //Para mandar un post aun WS el response Listener tiene que ser de tipo  String , y despues convertir la respuesta a JsonObject.
            public void onResponse(String response) {
                //Validamos que el response no este vacio
                if (!response.isEmpty()) {
                    //Esto contiene toda la cadena de respuesta del Ws.
                    Toast.makeText(getContext(), "CONSULTA" + response, Toast.LENGTH_LONG).show();

                    try {
                        //Convertimos el String en JsonObject
                        JSONObject obj = new JSONObject(response);
                        Log.d("objVehicular", "###Respuesta WS padron vehicular" + obj.toString());
                        //Accedemos al valor del Objeto deseado completo.
                        JSONArray jsonarray = obj.getJSONArray("data");

                        Log.w("jARRAY","### QUE TIENE EL ARRAY?"+jsonarray.toString());


                       if (jsonarray.length()==0){
                           Log.d("#####","#### ENTRE");
                           Intent intentWs = new Intent(getActivity(), WsgobConsulta.class);
                           licencia = editTextLicencia.getText().toString();
                           intentWs.putExtra("licencia", licencia);
                           placa = editTextPlaca.getText().toString();
                           //Spinner Modalidad
                           modalidad =spinnerModalidad.getSelectedItem().toString();
                           intentWs.putExtra("modalidad",modalidad);
                           intentWs.putExtra("placa", placa);
                           startActivity(intentWs);
                       }

                        //Obtenemos el total de elementos del objeto.
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            //Accedemos a los elementos por medio de getString.


                            String PLACA = jsonobject.getString("placa");

                               Boolean validaEstatus = jsonobject.has("estatus");
                               Boolean validaFechaVencimiento = jsonobject.has("fechaVencimiento");

                                modalidad =spinnerModalidad.getSelectedItem().toString();
                                String PROPIETARIO = jsonobject.getString("propietario");

                                String VIM = jsonobject.getString("serie");
                                String MARCA = jsonobject.getString("marca");
                                //Iniciamos actividad y mandamos parametros.
                                Intent intentWs = new Intent(getActivity(), WsgobConsulta.class);

                            if (validaEstatus==false){
                                String  ESTATUS = jsonobject.getString("estatusActual");
                                intentWs.putExtra("estatus", ESTATUS);
                                Log.d("ESATUS","### VALOR ESTATUS"+ESTATUS);
                            }else {
                                String  ESTATUS = jsonobject.getString("estatus");
                                intentWs.putExtra("estatus", ESTATUS);
                            }

                            if (validaFechaVencimiento==false){
                                String VIGENCIA = jsonobject.getString("fechaVigencia");
                                intentWs.putExtra("vigencia", VIGENCIA);
                                Log.d("ESATUS","### VALOR ESTATUS"+VIGENCIA);
                            }else {
                                String VIGENCIA = jsonobject.getString("fechaVencimiento");
                                intentWs.putExtra("vigencia", VIGENCIA);
                            }


                                licencia = editTextLicencia.getText().toString();
                                intentWs.putExtra("licencia", licencia);
                                Log.d("licencia1", "###Valor de la licencia" + licencia);
                                intentWs.putExtra("bandera", enviaBanderaLic);
                                intentWs.putExtra("placa", PLACA);
                                intentWs.putExtra("modalidad",modalidad);
                                intentWs.putExtra("propietario", PROPIETARIO);

                                intentWs.putExtra("vim", VIM);
                                intentWs.putExtra("marca", MARCA);

                                startActivity(intentWs);



                        }


                    } catch (JSONException e) {
                        Intent intentWs = new Intent(getActivity(), WsgobConsulta.class);
                        licencia = editTextLicencia.getText().toString();
                        intentWs.putExtra("licencia", licencia);
                        intentWs.putExtra("placa", "NO-PLACA");
                        Log.d("licencia2", "###Valor de la licencia" + licencia);
                        intentWs.putExtra("bandera", enviaBanderaLic);
                        startActivity(intentWs);
                        e.printStackTrace();

                    }

                    //Lanzamos Intent Navigation Drawer.
                    /*Intent intent = new Intent(getApplicationContext(), Drawer.class);
                    startActivity(intent);*/
                } else {
                    Toast.makeText(getContext(), "No se encontraron parametros en la consulta", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("placa", editTextPlaca.getText().toString());



                return parametros;
            }
        };
        RequestQueue requesrQueue = Volley.newRequestQueue(getContext());
        requesrQueue.add(stringRequest);
    }

    private void enviarWSConsultaInfraccion(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            //Para mandar un post aun WS el response Listener tiene que ser de tipo  String , y despues convertir la respuesta a JsonObject.
            public void onResponse(String response) {
                //Validamos que el response no este vacio
                if (!response.isEmpty()) {
                    //Esto contiene toda la cadena de respuesta del Ws.
                    Toast.makeText(getContext(), "CONSULTA" + response, Toast.LENGTH_LONG).show();

                    try {
                        //Convertimos el String en JsonObject
                        JSONObject obj = new JSONObject(response);
                        Log.d("objVehicular", "###Respuesta WS padron vehicular" + obj.toString());
                        //Accedemos al valor del Objeto deseado completo.
                        JSONArray jsonarray = obj.getJSONArray("data");

                        Log.w("jARRAY","### QUE TIENE EL ARRAY?"+jsonarray.toString());


                        if (jsonarray.length()==0){
                            Log.d("#####","#### ENTRE");
                            Intent intentWs = new Intent(getActivity(), Infracciones.class);
                            licencia = editTextLicencia.getText().toString();
                            modalidad = spinnerModalidad.getSelectedItem().toString();
                            sector = spinerSector.getSelectedItem().toString();
                            infraccion1 = spinnerInfra1.getSelectedItem().toString();
                            infraccion2 = spinnerInfra2.getSelectedItem().toString();
                            infraccion3 = spinnerInfra3.getSelectedItem().toString();
                            infraccion4 = spinnerInfra4.getSelectedItem().toString();
                            infraccion5 = spinnerInfra5.getSelectedItem().toString();

                            //############################

                            intentWs.putExtra("usersId",usersId);
                            Log.d("HomeFragment","USERSID########################--->"+usersId);
                            intentWs.putExtra("username",username);
                            intentWs.putExtra("profile",profile);
                            intentWs.putExtra("nombre",nombre);
                            intentWs.putExtra("delegacionId",delegacionId);
                            intentWs.putExtra("activo",activo);

                            //############################

                            String cuentaString = Integer.toString(cuenta);

                            intentWs.putExtra("sector",sector);
                            intentWs.putExtra("modalidad",modalidad);
                            intentWs.putExtra("infra1",infraccion1);
                            intentWs.putExtra("infra2",infraccion2);
                            intentWs.putExtra("infra3",infraccion3);
                            intentWs.putExtra("infra4",infraccion4);
                            intentWs.putExtra("infra5",infraccion5);
                            intentWs.putExtra("cuenta",cuentaString);

                            intentWs.putExtra("licencia", licencia);
                            placa = editTextPlaca.getText().toString();
                            intentWs.putExtra("placa", placa);
                            startActivity(intentWs);
                        }

                        //Obtenemos el total de elementos del objeto.
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            //Accedemos a los elementos por medio de getString.


                            String PLACA = jsonobject.getString("placa");

                            Boolean validaEstatus = jsonobject.has("estatus");
                            Boolean validaFechaVencimiento = jsonobject.has("fechaVencimiento");


                            String PROPIETARIO = jsonobject.getString("propietario");

                            String VIM = jsonobject.getString("serie");
                            String MARCA = jsonobject.getString("marca");
                            //Iniciamos actividad y mandamos parametros.
                            Intent intentWs = new Intent(getActivity(), Infracciones.class);

                            if (validaEstatus==false){
                                String  ESTATUS = jsonobject.getString("estatusActual");
                                intentWs.putExtra("estatus", ESTATUS);
                                Log.d("ESATUS","### VALOR ESTATUS"+ESTATUS);
                            }else {
                                String  ESTATUS = jsonobject.getString("estatus");
                                intentWs.putExtra("estatus", ESTATUS);
                            }

                            if (validaFechaVencimiento==false){
                                //Publico
                                String VIGENCIA = jsonobject.getString("fechaVigencia");
                                intentWs.putExtra("vigencia", VIGENCIA);
                                Log.d("ESATUS","### VALOR ESTATUS"+VIGENCIA);
                            }else {
                                //Particular
                                String VIGENCIA = jsonobject.getString("fechaVencimiento");
                                intentWs.putExtra("vigencia", VIGENCIA);
                            }




                            //############################

                            intentWs.putExtra("usersId",usersId);
                            Log.d("HomeFragment","USERSID########################--->"+usersId);
                            intentWs.putExtra("username",username);
                            intentWs.putExtra("profile",profile);
                            intentWs.putExtra("nombre",nombre);
                            intentWs.putExtra("delegacionId",delegacionId);
                            intentWs.putExtra("activo",activo);

                            //############################

                            licencia = editTextLicencia.getText().toString();
                            intentWs.putExtra("licencia", licencia);
                            Log.d("licencia1", "###Valor de la licencia" + licencia);
                            intentWs.putExtra("bandera", enviaBanderaLic);
                            intentWs.putExtra("placa", PLACA);
                            modalidad = spinnerModalidad.getSelectedItem().toString();
                            sector = spinerSector.getSelectedItem().toString();
                            infraccion1 = spinnerInfra1.getSelectedItem().toString();
                            infraccion2 = spinnerInfra2.getSelectedItem().toString();
                            infraccion3 = spinnerInfra3.getSelectedItem().toString();
                            infraccion4 = spinnerInfra4.getSelectedItem().toString();
                            infraccion5 = spinnerInfra5.getSelectedItem().toString();
                            String cuentaString = Integer.toString(cuenta);

                            intentWs.putExtra("sector",sector);
                            intentWs.putExtra("modalidad",modalidad);
                            intentWs.putExtra("infra1",infraccion1);
                            intentWs.putExtra("infra2",infraccion2);
                            intentWs.putExtra("infra3",infraccion3);
                            intentWs.putExtra("infra4",infraccion4);
                            intentWs.putExtra("infra5",infraccion5);
                            intentWs.putExtra("cuenta",cuentaString);
                            intentWs.putExtra("propietario", PROPIETARIO);

                            intentWs.putExtra("vim", VIM);
                            intentWs.putExtra("marca", MARCA);

                            startActivity(intentWs);



                        }


                    } catch (JSONException e) {
                        Intent intentWs = new Intent(getActivity(), Infracciones.class);
                        licencia = editTextLicencia.getText().toString();
                        intentWs.putExtra("licencia", licencia);
                        intentWs.putExtra("placa", "NO-PLACA");
                        Log.d("licencia2", "###Valor de la licencia" + licencia);
                        intentWs.putExtra("bandera", enviaBanderaLic);
                        startActivity(intentWs);
                        e.printStackTrace();

                    }

                    //Lanzamos Intent Navigation Drawer.
                    /*Intent intent = new Intent(getApplicationContext(), Drawer.class);
                    startActivity(intent);*/
                } else {
                    Toast.makeText(getContext(), "No se encontraron parametros en la consulta", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("placa", editTextPlaca.getText().toString());



                return parametros;
            }
        };
        RequestQueue requesrQueue = Volley.newRequestQueue(getContext());
        requesrQueue.add(stringRequest);
    }


}

