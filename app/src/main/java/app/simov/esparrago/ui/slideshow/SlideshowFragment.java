package app.simov.esparrago.ui.slideshow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.simov.esparrago.R;
import app.simov.esparrago.WsgobConsulta;
import app.simov.esparrago.ui.home.HomeFragment;

public class SlideshowFragment extends Fragment {

    public SlideshowFragment(){

    }

    CheckBox checkBoxLicencia;
    boolean banderaLicencia = false;
    boolean banderaPlaca = false;
    String enviaBanderaLic;
    String licencia;
    String placa;
    EditText editTextPlaca;
    EditText editTextLicencia;
    ProgressDialog progressDialog;



    int cuenta ;
    String usersId;
    String username;
    String profile;
    String nombre;
    String delegacionId;
    String activo;
    String mensaje;

//TextViewa
    TextView tvEmpresa;
    TextView tvModalidad;
    TextView tvQR;
    TextView tvNumeroEconomico;
    TextView tvPlaca;
    TextView tvTipo;
    TextView tvSerie;
    TextView tvMarca;
    TextView tvModelo;
    TextView tvLinea;
    TextView tvColor;
    TextView tvCirculacion;
    TextView tvCobertura;
    TextView tvVigencia;
    TextView tvObservaciones;


//Variables LayOut.

    String empresa;
    String modalidad;
    String qr;
    String numeroEconomico;
    String placass;
    String TipoV;
    String Serie;
    String Marca;
    String Modelo;
    String Linea;
    String Color;
    String Circulacion;
    String Cobertura;
    String VigenciaSeguro;
    String observaciones;



    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        //final TextView textView = root.findViewById(R.id.text_slideshow);

        //Orientacion de pantalla en fragment
        Activity a = getActivity();
        if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // final TextView textView = root.findViewById(R.id.text_home);
        //Parametros XML
        final TextView nombreApp = root.findViewById(R.id.tvApp);
        final TextView tvUsuario = root.findViewById(R.id.tvUsuario);
        // final TextView tvUsuario2 = root.findViewById(R.id.tvUsuario2);
        final TextView tvMunicipio = root.findViewById(R.id.tvMunicipio);
        checkBoxLicencia = root.findViewById(R.id.cBoxLicencia);
        final CheckBox checkBoxPlaca = root.findViewById(R.id.cBoxPlaca);

        editTextPlaca = root.findViewById(R.id.edtPlaca);
        editTextLicencia = root.findViewById(R.id.edtLicencia);


        final Button buttonConsulta = root.findViewById(R.id.btnConsulta);


        final Button bntQr = root.findViewById(R.id.btnQr);


        NavigationView navigationView = (NavigationView) root.findViewById(R.id.nav_view);

//TextViewa.





        //Boton Iniciar QR
        bntQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escanear();
            }
        });

        //Mayusculas a LICENCIA Y PLACA.
        editTextLicencia.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        editTextPlaca.setFilters(new InputFilter[]{new InputFilter.AllCaps()});



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
            tvUsuario.setText(nombre+" "+username);




            tvMunicipio.setText("Tijuana");
            Log.d("USERSID","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ ESTO ES LO QUE RECOJI DEL USERS ID"+usersId);
        }


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




        //Boton para consulta WS de placa y licencia
        buttonConsulta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               /* //Inicializamos el progress BAR
                progressDialog = new ProgressDialog(getContext());
                //Mostramos el progressBAR
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                //Fondo transparente
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );*/


                if (banderaPlaca == true) {
                    banderaLicencia = false;
                    //Aqui declaramos solo lo que queremos que se cargue despues del click del boton para iniciar la nueva actividad
                    editTextPlaca = root.findViewById(R.id.edtPlaca);
                    placa = editTextPlaca.getText().toString(); //gets you the contents of edit text

                    Log.d("Variable", "PLACA## " + placa);
                    String URL = getResources().getString(R.string.URL_POLIZA);

                    //Envia Ws
                    enviarWSConsulta(URL);

                }if (banderaLicencia == true){

                    String URL = getResources().getString(R.string.URL_POLIZA);

                    //Envia Ws
                    enviarWSConsulta(URL);

                }
                else {
                   /* progressDialog.hide();*/
                    Toast.makeText(getContext(), "Tienes que seleccionar PLACA o LICENCIA", Toast.LENGTH_LONG).show();
                }

            }
        });


        return root;
    }


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
                        Log.d("objPoliza", "###Respuesta WS poliza" + obj.toString());

                        if (obj.has("thor")) {

                            //Aqui empieza el alert##########################################################################################
                            // create an alert builder
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("DATOS POLIZA");

                            // set the custom layout
                            final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout_poliza, null);
                            builder.setView(customLayout);

                            // add a button
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            // create and show the alert dialog
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            //Aqui termina el alert##########################################################################################




                        } if (obj.has("empresa")) {

                            Log.d("POLIZA","ENTRE AQUI PRRROOOOOO");


                            empresa = obj.getString("empresa");
                            modalidad = obj.getString("modalidad");
                            qr = obj.getString("qr");
                            numeroEconomico = obj.getString("numeroEconomico");
                            placass = obj.getString("placas");
                            TipoV = obj.getString("tipo");
                            Serie = obj.getString("serie");
                            Marca = obj.getString("marcas");
                            Modelo = obj.getString("modelo");
                            Linea = obj.getString("linea");
                            Color = obj.getString("color");
                            Circulacion = obj.getString("tarjetaCirculacion");
                            Cobertura = obj.getString("cobertura");
                            VigenciaSeguro = obj.getString("vigenciaSeguro");
                            observaciones = obj.getString("observaciones");


                            //Aqui empieza el alert##########################################################################################
                            // create an alert builder
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("DATOS POLIZA");

                            // set the custom layout
                            final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout_poliza, null);
                            builder.setView(customLayout);

                            //Aqui llamamos a la subvista no  a la vista principal para poder setear el contenido en el layout que se carga.
                            ((TextView) customLayout.findViewById(R.id.tvEmpresa))//get element TextView
                                    .setText(empresa);//set text

                            ((TextView) customLayout.findViewById(R.id.tvModalidad))//get element TextView
                                    .setText(modalidad);//set text

                            ((TextView) customLayout.findViewById(R.id.tvQR))//get element TextView
                                    .setText(qr);//set text

                            ((TextView) customLayout.findViewById(R.id.tvNumeroEconomico))//get element TextView
                                    .setText(numeroEconomico);//set text

                            ((TextView) customLayout.findViewById(R.id.tvPlaca))//get element TextView
                                    .setText(placass);//set text

                            ((TextView) customLayout.findViewById(R.id.tvTipo))//get element TextView
                                    .setText(TipoV);//set text

                            ((TextView) customLayout.findViewById(R.id.tvSerie))//get element TextView
                                    .setText(Serie);//set text

                            ((TextView) customLayout.findViewById(R.id.tvMarcaV))//get element TextView
                                    .setText(Marca);//set text

                            ((TextView) customLayout.findViewById(R.id.tvModelo))//get element TextView
                                    .setText(Modelo);//set text

                            ((TextView) customLayout.findViewById(R.id.tvLinea))//get element TextView
                                    .setText(Linea);//set text

                            ((TextView) customLayout.findViewById(R.id.tvColor))//get element TextView
                                    .setText(Color);//set text

                            ((TextView) customLayout.findViewById(R.id.tvTarjetaCirculacion))//get element TextView
                                    .setText(Circulacion);//set text

                            ((TextView) customLayout.findViewById(R.id.tvCobertura))//get element TextView
                                    .setText(Cobertura);//set text

                            ((TextView) customLayout.findViewById(R.id.tvVigenciaSeguro))//get element TextView
                                    .setText(VigenciaSeguro);//set text

                            ((TextView) customLayout.findViewById(R.id.tvObservaciones))//get element TextView
                                    .setText(observaciones);//set text

                            // add a button
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            // create and show the alert dialog
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            //Aqui termina el alert##########################################################################################





                        }

                    } catch (JSONException e) {

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
                //Enviamos parametros de placa o licencia
                parametros.put("placa", editTextPlaca.getText().toString());
                parametros.put("licencia", editTextLicencia.getText().toString());
                return parametros;
            }
        };
        RequestQueue requesrQueue = Volley.newRequestQueue(getContext());
        requesrQueue.add(stringRequest);
    }



    //Clase para scanear el codigo QR
    public void escanear(){

        IntentIntegrator intent = IntentIntegrator.forSupportFragment(SlideshowFragment.this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intent.setPrompt("ESCANEAR QR - IMOS -");
        intent.setCameraId(0);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(getContext(),"CANCELASTE EL ESCANEO", Toast.LENGTH_LONG);
            }else {
                //Aqui Agregamos las validaciones para los diferentes Formatos de QR´S
                String infoQr = result.getContents();
                List<String> datosLicencia = Arrays.asList(infoQr.split(","));
                boolean isFound = datosLicencia.get(4).contains("BC"); // true

                int sizeDatosLicencia = datosLicencia.size();
                try {
                    if (isFound == true) {
                        editTextLicencia.setText(datosLicencia.get(4).trim());
                        Log.d("QRSTRING", "ESTE ES EL VALOR DEL QR STRING" + result.getContents().toString());
                    }

                    if (sizeDatosLicencia >= 11) {
                        editTextPlaca.setText(datosLicencia.get(8).trim());
                    }
                }catch (Exception e){
                    Toast.makeText(getContext(),"Hubo un error en QR",Toast.LENGTH_LONG);
                }


            }
        }else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

}