package app.simov.esparrago.ui.home;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
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

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.simov.esparrago.Infracciones;
import app.simov.esparrago.R;
import app.simov.esparrago.WsgobConsulta;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class HomeFragment extends Fragment  {

    public HomeFragment(){

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
    Spinner spinnerModalidad;
    Spinner spinerSector;
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


    //EDTS INFRACCIÓN

    AutoCompleteTextView edtInfraccion1;
    AutoCompleteTextView edtInfraccion2;
    AutoCompleteTextView edtInfraccion3;
    AutoCompleteTextView edtInfraccion4;
    AutoCompleteTextView edtInfraccion5;

    ImageView imageViewP;
    TextView textViewP;




    String placaPlataforma;

    String folioPlataforma;

    String delegacionIDPlataforma;
    String nombre_plataformaPlataforma;
    String numero_polizaPlataforma     ;
    String nombre_propietarioPlataforma ;
    String seriePlataforma     ;

    String marcaPlataforma  ;
    String tipoPlataforma    ;
    String modeloPlataforma   ;
    String fecha_vigenciaPlataforma ;
    String fecha_altaPlataforma     ;
    String nombre_socioPlataforma    ;
    String estatusPlataforma     ;
    String colorPlataforma;



    AlertDialog.Builder builder;
    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;
    private final String CARPETA_RAIZ="misImagenesPrueba/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"misFotos";
    String path;
    String miPlacosa;
    private HomeViewModel homeViewModel;
    private static final String EXTRA_CODE = "app.simov.esparrago";


    TextView tvplacasRM;
    TextView tvdelegacionRM;
    TextView tvplataformaRM;
    TextView tvpolizaRM;
    TextView tvpropietarioRM;
    TextView tvserieRM;
    TextView tvmarcaRM;
    TextView tvtipoRM;
    TextView tvcolorRM;
    TextView tvmodeloRM;
    TextView tvvigenciaRM;
    TextView tvsocioRM;
    TextView tvstatusRM;




    String placaQR;
    String serialQR;
    String delegacionIdQR;
    String economicoQR;
    String serieQR;
    String marcaQR;
    String modeloQR;
    String tipoQR;
    String colorQR;
    String padronQR;
    String modalidadQR;
    String fechaAltaQR;
    String prorrogaQR;
    String fechaProrrojgaQR;
    String estatusQR;
    String coberturaSeguroQR;
    String vigenciaPolizaQR;
    String periodoQR;
    String observacionesQR;
    String revisionQR;

    String placarm2;
    String serialrm2;
    String delegacionrm2;
    String economicorm2;
    String serierm2;
    String marcarm2;
    String modelorm2;
    String tiporm2;
    String colorrm2;
    String padronrm2;
    String modalidadrm2;
    String fechaAltarm2;
    String prorrogarm2;
    String fechaProrrojgarm2;
    String estatusrm2;
    String coberturaSegurorm2;
    String vigenciaPolizarm2;
    String periodorm2;
    String observacionesrm2;
    String revisionrm22;


    String placaFolio;
    String foliofolio;
    String delegacionFolio;
    String nombrePlataformaFolio;
    String numeroPolizaFolio;
    String nombrePropietarioFolio;
    String serieFolio;
    String marcaFolio;
    String tipoFolio;
    String colorFolio;
    String modeloFolio;
    String fechaVigenciaFolio;
    String fechaAltaFolio;
    String fechaVigenciaFoliio;
    String nombreSocioFolio;
    String estatusFolio;


    String lnumeroTarjeton;
    String licenciaTarjeton;
    String tipoChoferTarjeton;
    String folioTarjeton;
    String maternoTarjeton;
    String paternoTarjerton;
    String nombreTarjeton;
    String fechaAltaTarjeton;
    String fechaVigenciaTarjeton;
    String fechaLabTarjerton;
    String estatusTarjerton;

    String folioGafeteQR;
    String delegacionGafeteQR;
    String modalidadGafeteQR;
    String serieRegistroGafeteQR;
    String vigenciaGafeteQR;

    String infoQr;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      //  FirebaseApp.initializeApp(getActivity());
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


         tvplacasRM = root.findViewById(R.id.placasRMTablaLay);
         tvdelegacionRM = root.findViewById(R.id.delegacionRM);
         tvplataformaRM = root.findViewById(R.id.plataformaRM);
         tvpolizaRM = root.findViewById(R.id.propietarioRM);
         tvpropietarioRM = root.findViewById(R.id.propietarioRM);
         tvserieRM = root.findViewById(R.id.serieRM);
         tvmarcaRM = root.findViewById(R.id.marcaRM);
         tvtipoRM = root.findViewById(R.id.tipoRM);
         tvcolorRM = root.findViewById(R.id.colorRM);
         tvmodeloRM = root.findViewById(R.id.modeloRM);
         tvvigenciaRM = root.findViewById(R.id.vigenciaRM);
         tvsocioRM = root.findViewById(R.id.socioRM);
         tvstatusRM = root.findViewById(R.id.estatusRM);





        builder = new AlertDialog.Builder(getActivity());

        //camara placa
        //find imageview
        imageViewP = root.findViewById(R.id.imageId);
        //find textview
        textViewP = root.findViewById(R.id.textId);
        textViewP.setVisibility(View.GONE);
        //check app level permission is granted for Camera

        requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);


        //camara placa


        //Orientacion de pantalla en fragment
        Activity a = getActivity();
        if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

       // final TextView textView = root.findViewById(R.id.text_home);
        //Parametros XML
       // final TextView nombreApp = root.findViewById(R.id.tvApp);
        final TextView tvUsuario = root.findViewById(R.id.tvUsuario);
       // final TextView tvUsuario2 = root.findViewById(R.id.tvUsuario2);
        final TextView tvMunicipio = root.findViewById(R.id.tvMunicipio);
        checkBoxLicencia = root.findViewById(R.id.cBoxLicencia);
        final CheckBox checkBoxPlaca = root.findViewById(R.id.cBoxPlaca);

        editTextPlaca = root.findViewById(R.id.edtPlaca);
        editTextLicencia = root.findViewById(R.id.edtLicencia);
        //final Spinner spinnerZona = root.findViewById(R.id.spZona);

        //final Spinner spinnerInfraccion = root.findViewById(R.id.spInfraccion3);

        final Button buttonInfraccion = root.findViewById(R.id.btnInfraccion);
        final Button buttonConsulta = root.findViewById(R.id.btnConsulta);
        final Button bntCuenta = root.findViewById(R.id.btnCuenta);

        final Button bntQuitar = root.findViewById(R.id.btnQuitar);

        final Button bntQr = root.findViewById(R.id.btnQr);

        final Button bntFoto = root.findViewById(R.id.btnFotoPlaca);

        String[] InfracionesList = getResources().getStringArray(R.array.infracciones_arrays);

        edtInfraccion1 = root.findViewById(R.id.edtInfraccion1);
        ArrayAdapter<String> adapterInfracciones = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,InfracionesList);
        edtInfraccion1.setAdapter(adapterInfracciones);

        edtInfraccion2 = root.findViewById(R.id.edtInfraccion2);
        ArrayAdapter<String> adapterInfracciones2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,InfracionesList);
        edtInfraccion2.setAdapter(adapterInfracciones2);

        edtInfraccion3 = root.findViewById(R.id.edtInfraccion3);
        ArrayAdapter<String> adapterInfracciones3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,InfracionesList);
        edtInfraccion3.setAdapter(adapterInfracciones3);

        edtInfraccion4 = root.findViewById(R.id.edtInfraccion4);
        ArrayAdapter<String> adapterInfraccione4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,InfracionesList);
        edtInfraccion4.setAdapter(adapterInfraccione4);

        edtInfraccion5 = root.findViewById(R.id.edtInfraccion5);
        ArrayAdapter<String> adapterInfraccione5 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,InfracionesList);
        edtInfraccion5.setAdapter(adapterInfraccione5);


        //Defenimos vista del spinner "CAJA"
        spinnerModalidad = root.findViewById(R.id.spModalidad);
        //Lista principal despliega primero
        ArrayAdapter adapterModalidad = ArrayAdapter.createFromResource(getActivity(),R.array.modalidad_arrays,R.layout.spinner_item);
        //Mostramos el contenido del source en un dropDown y lo seteamos.
        adapterModalidad.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnerModalidad.setAdapter(adapterModalidad);


        spinerSector = root.findViewById(R.id.spZona);
        //Lista principal despliega primero
        ArrayAdapter adapterZona = ArrayAdapter.createFromResource(getActivity(),R.array.zonas_arrays,R.layout.spinner_item);
        //Mostramos el contenido del source en un dropDown y lo seteamos.
        adapterZona.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinerSector.setAdapter(adapterZona);



        edtInfraccion1.setVisibility(View.GONE);
        edtInfraccion2.setVisibility(View.GONE);
        edtInfraccion3.setVisibility(View.GONE);
        edtInfraccion4.setVisibility(View.GONE);
        edtInfraccion5.setVisibility(View.GONE);
        bntQuitar.setVisibility(View.GONE);

        NavigationView navigationView = (NavigationView) root.findViewById(R.id.nav_view);




 //Boton Iniciar QR
 bntQr.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
        escanear();
     }
 });

        //Boton Foto placa
        bntFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doProcess();
            }
        });



        //Boton agregar Infracciones
 bntCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuenta++;

                Log.d("CUENTS+++","CONTADOR"+cuenta);
                if (cuenta == 1){
                    edtInfraccion1.setVisibility(View.VISIBLE);
                    bntQuitar.setVisibility(View.VISIBLE);
                }
                if (cuenta == 2){
                    edtInfraccion2.setVisibility(View.VISIBLE);

                }
                if(cuenta == 3){
                    edtInfraccion3.setVisibility(View.VISIBLE);
                }
                if(cuenta == 4){
                    edtInfraccion4.setVisibility(View.VISIBLE);
                }
                if(cuenta == 5){
                    edtInfraccion5.setVisibility(View.VISIBLE);

                }
            }
        });

//Boton Quitar Infracciones
         bntQuitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cuenta--;
                Log.d("CUENTS---","CONTADOR"+cuenta);
                if (cuenta == 4){
                    edtInfraccion5.setVisibility(View.GONE);
                }
                if (cuenta == 3){
                    edtInfraccion4.setVisibility(View.GONE);
                }
                if(cuenta == 2){
                    edtInfraccion3.setVisibility(View.GONE);
                }
                if(cuenta == 1){
                    edtInfraccion2.setVisibility(View.GONE);
                }
                if(cuenta == 0){
                    edtInfraccion1.setVisibility(View.GONE);
                    bntQuitar.setVisibility(View.GONE);


                }
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
            placa  = args.getString("placa");
            licencia  = args.getString("licencia");
            Log.d("MIPLACA","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ ESTO ES LO QUE RECOJI DEL USERS ID"+placa);
            tvUsuario.setText(nombre+" "+username);
            editTextPlaca.setText(placa);
            editTextLicencia.setText(licencia);





            tvMunicipio.setText("Tijuana");
          Log.d("USERSID","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ ESTO ES LO QUE RECOJI DEL USERS ID"+usersId);
        }


      /*//Validacion de Checkbox
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
*/

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



                    banderaLicencia = false;
                    //Aqui declaramos solo lo que queremos que se cargue despues del click del boton para iniciar la nueva actividad
                    editTextPlaca = root.findViewById(R.id.edtPlaca);
                    placa = editTextPlaca.getText().toString(); //gets you the contents of edit text

                    Log.d("Variable", "LICENCIA## " + placa);
                    String URL = "https://simov.app/servicios/controlVehicularNew.php";

                    //Envia Ws
                    enviarWSConsultaInfraccion(URL);




               /*
                    progressDialog.hide();
                    Toast.makeText(getContext(), "Tienes que seleccionar PLACA o LICENCIA", Toast.LENGTH_LONG).show();
                }*/



            }
        });

        //Boton para consulta WS de placa y licencia
        buttonConsulta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*//Inicializamos el progress BAR
                progressDialog = new ProgressDialog(getContext());
                //Mostramos el progressBAR
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                //Fondo transparente
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );*/



                    banderaLicencia = false;
                    //Aqui declaramos solo lo que queremos que se cargue despues del click del boton para iniciar la nueva actividad
                    editTextPlaca = root.findViewById(R.id.edtPlaca);
                    placa = editTextPlaca.getText().toString(); //gets you the contents of edit text

                    Log.d("Variable", "LICENCIA## " + placa);
                    String URL = "https://simov.app/servicios/controlVehicularNew.php";
                    String URL2  = "https://simov.app/servicios/abdiel.php";
                    //Envia Ws
                    enviarWSConsulta(URL);
                    enviarWSConsultaRM(URL2);

                /*

                   *//* progressDialog.hide();*//*
                    Toast.makeText(getContext(), "Tienes que seleccionar PLACA o LICENCIA", Toast.LENGTH_LONG).show();
               */

            }
        });


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;


    }

    public void doProcess() {
        //open the camera => create an Intent object
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 101);
    }


    private void enviarWSConsulta(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            //Para mandar un post aun WS el response Listener tiene que ser de tipo  String , y despues convertir la respuesta a JsonObject.
            public void onResponse(String response) {
                //Validamos que el response no este vacio
                if (!response.isEmpty()) {
                    //Esto contiene toda la cadena de respuesta del Ws.
                    //Toast.makeText(getContext(), "CONSULTA" + response, Toast.LENGTH_LONG).show();

                    try {
                        //Convertimos el String en JsonObject
                        JSONArray jsonarray = new JSONArray(response);
                        //Aqui hay que continuarle compa..


                        Log.d("objVehicular", "###Respuesta WS padron vehicular" + jsonarray.toString());
                        //Accedemos al valor del Objeto deseado completo.
                       // JSONArray jsonarray = obj.getJSONArray("data");

                        //Log.w("jARRAY","### QUE TIENE EL ARRAY?"+jsonarray.toString());


                        if (jsonarray.length()==0){
                            Log.d("#####","#### ENTRE");
                            Intent intentWs = new Intent(getActivity(), WsgobConsulta.class);
                            licencia = editTextLicencia.getText().toString();
                            intentWs.putExtra("licencia", licencia);
                            placa = editTextPlaca.getText().toString();
                            //Spinner Modalidad
                            // modalidad =spinnerModalidad.getSelectedItem().toString();
                            intentWs.putExtra("modalidad",modalidad);
                            intentWs.putExtra("placa", placa);


                            intentWs.putExtra("usersId",usersId);
                            Log.d("HomeFragment","USERSID########################--->"+usersId);
                            intentWs.putExtra("username",username);
                            intentWs.putExtra("profile",profile);
                            intentWs.putExtra("nombre",nombre);
                            intentWs.putExtra("delegacionId",delegacionId);
                            intentWs.putExtra("activo",activo);


                            intentWs.putExtra("placaPlataforma",placaPlataforma);
                            Log.d("RMWSGOB","VALOR"+placaPlataforma);

                            Log.d("RMWSGOB1","VALOR"+placaPlataforma);

                            intentWs.putExtra("delegacionPlataforma",delegacionIDPlataforma);
                            intentWs.putExtra("nombrePlataforma",nombre_plataformaPlataforma);
                            intentWs.putExtra("polizaPlataforma",numero_polizaPlataforma);
                            intentWs.putExtra("propietarioPlataforma",nombre_propietarioPlataforma);
                            intentWs.putExtra("seriePlataforma",seriePlataforma);
                            intentWs.putExtra("marcaPlataforma",marcaPlataforma);
                            intentWs.putExtra("tipoPlataforma",tipoPlataforma);
                            intentWs.putExtra("colorPlataforma",colorPlataforma);
                            intentWs.putExtra("modeloPlataforma",modeloPlataforma);
                            intentWs.putExtra("vigenciaPlataforma",fecha_vigenciaPlataforma);
                            intentWs.putExtra("socioPlataforma",nombre_socioPlataforma);
                            intentWs.putExtra("estatusPlataforma",estatusPlataforma);


                            intentWs.putExtra("placaQR",placaQR);
                            intentWs.putExtra("qr_serial",serialQR);
                            intentWs.putExtra("delegacionIdQR",delegacionIdQR);
                            intentWs.putExtra("economicoQR",economicoQR);
                            intentWs.putExtra("serieQR",serieQR);
                            intentWs.putExtra("marcaQR",marcaQR);
                            intentWs.putExtra("modeloQR",modeloQR);
                            intentWs.putExtra("tipoQR",tipoQR);
                            intentWs.putExtra("colorQR",colorQR);
                            intentWs.putExtra("padronQR",padronQR);
                            intentWs.putExtra("modalidadQR",modalidadQR);
                            intentWs.putExtra("fechaAltaQR",fechaAltaQR);
                            intentWs.putExtra("prorrogaQR",prorrogaQR);
                            intentWs.putExtra("fechaProrrojgaQR",fechaProrrojgaQR);
                            intentWs.putExtra("estatusQR",estatusQR);
                            intentWs.putExtra("coberturaSeguroQR",coberturaSeguroQR);
                            intentWs.putExtra("vigenciaPolizaQR",vigenciaPolizaQR);
                            intentWs.putExtra("periodoQR",periodoQR);
                            intentWs.putExtra("observacionesQR",observacionesQR);
                            intentWs.putExtra("revisionQR",revisionQR);

                            intentWs.putExtra("placarm2",placarm2);
                            intentWs.putExtra("serialrm2",serialrm2);
                            intentWs.putExtra("delegacionrm2",delegacionrm2);
                            intentWs.putExtra("economicorm2",economicorm2);
                            intentWs.putExtra("serierm2",serierm2);
                            intentWs.putExtra("marcarm2",marcarm2);
                            intentWs.putExtra("modelorm2",modelorm2);
                            intentWs.putExtra("tiporm2",tiporm2);
                            intentWs.putExtra("colorrm2",colorrm2);
                            intentWs.putExtra("padronrm2",padronrm2);
                            intentWs.putExtra("modalidadrm2",modalidadrm2);
                            intentWs.putExtra("fechaAltarm2",fechaAltarm2);
                            intentWs.putExtra("prorrogarm2",prorrogarm2);
                            intentWs.putExtra("fechaProrrojgarm2",fechaProrrojgarm2);
                            intentWs.putExtra("estatusrm2",estatusrm2);
                            intentWs.putExtra("coberturaSegurorm2",coberturaSegurorm2);
                            intentWs.putExtra("vigenciaPolizarm2",vigenciaPolizarm2);
                            intentWs.putExtra("periodorm2",periodorm2);
                            intentWs.putExtra("observacionesrm2",observacionesrm2);
                            intentWs.putExtra("revisionrm22",revisionrm22);









                            intentWs.putExtra("placaFolio",placaFolio);
                            intentWs.putExtra("foliofolio",foliofolio);
                            intentWs.putExtra("delegacionFolio",delegacionFolio);
                            intentWs.putExtra("nombrePlataformaFolio",nombrePlataformaFolio);
                            intentWs.putExtra("numeroPolizaFolio",numeroPolizaFolio);
                            intentWs.putExtra("nombrePropietarioFolio",nombrePropietarioFolio);
                            intentWs.putExtra("serieFolio",serieFolio);
                            intentWs.putExtra("marcaFolio",marcaFolio);
                            intentWs.putExtra("tipoFolio",tipoFolio);
                            intentWs.putExtra("colorFolio",colorFolio);
                            intentWs.putExtra("modeloFolio",modeloFolio);
                            intentWs.putExtra("fechaVigenciaFolio",fechaVigenciaFolio);
                            intentWs.putExtra("fechaAltaFolio",fechaAltaFolio);
                            intentWs.putExtra("fechaVigenciaFoliio",fechaVigenciaFoliio);
                            intentWs.putExtra("nombreSocioFolio",nombreSocioFolio);
                            intentWs.putExtra("estatusFolio",estatusFolio);



                            intentWs.putExtra("lnumeroTarjeton",lnumeroTarjeton);
                            intentWs.putExtra("licenciaTarjeton",licenciaTarjeton);
                            intentWs.putExtra("tipoChoferTarjeton",tipoChoferTarjeton);
                            intentWs.putExtra("folioTarjeton",folioTarjeton);
                            intentWs.putExtra("maternoTarjeton",maternoTarjeton);
                            intentWs.putExtra("paternoTarjerton",paternoTarjerton);
                            intentWs.putExtra("nombreTarjeton",nombreTarjeton);
                            intentWs.putExtra("fechaAltaTarjeton",fechaAltaTarjeton);
                            intentWs.putExtra("fechaVigenciaTarjeton",fechaVigenciaTarjeton);
                            intentWs.putExtra("fechaLabTarjerton",fechaLabTarjerton);
                            intentWs.putExtra("estatusTarjerton",estatusTarjerton);

                            intentWs.putExtra("folioGafeteQR",folioGafeteQR);
                            intentWs.putExtra("delegacionGafeteQR",delegacionGafeteQR);
                            intentWs.putExtra("modalidadGafeteQR",modalidadGafeteQR);
                            intentWs.putExtra("serieRegistroGafeteQR",serieRegistroGafeteQR);
                            intentWs.putExtra("vigenciaGafeteQR",vigenciaGafeteQR);



                            startActivity(intentWs);
                            getActivity().finish();
                        }

                        //Obtenemos el total de elementos del objeto.
                        for (int i = 0; i < jsonarray.length(); i++) {
                           // JSONObject jsonobject = jsonarray.getJSONObject(i);
                            //Accedemos a los elementos por medio de getString.


                            //Iniciamos actividad y mandamos parametros.
                            Intent intentWs = new Intent(getActivity(), WsgobConsulta.class);
                            String PLACA = jsonarray.getString(0);
                            Log.d("vergasVato","### $#$#$$$US"+PLACA);
                           // Boolean validaEstatus = false;



                            try {
                                String  ESTATUS = jsonarray.getString(24);


                                if (ESTATUS.equals("ACTIVO") || ESTATUS.equals("BAJA TEMPORAL") ){
                                    intentWs.putExtra("economico", "NO APLICA");
                                    intentWs.putExtra("estatus", ESTATUS);
                                    String VIGENCIA = jsonarray.getString(23);
                                    intentWs.putExtra("vigencia", VIGENCIA);
                                    Log.d("ESATUS","### VALOR ESTATUS"+ESTATUS);

                                    Boolean validaFechaVencimiento = false;

                                    // modalidad =spinnerModalidad.getSelectedItem().toString();
                                    String PROPIETARIO = jsonarray.getString(18);

                                    String VIM = jsonarray.getString(1);

                                    String MARCA = jsonarray.getString(3);

                                    intentWs.putExtra("propietario", PROPIETARIO);

                                    intentWs.putExtra("vim", VIM);
                                    intentWs.putExtra("marca", MARCA);

                                }

                            }catch (Exception e){

                            }
                            try {
                                String  ESTATUST = jsonarray.getString(2);
                                Log.d("WS111","entreeeeeee   "+ESTATUST);
                                if (ESTATUST.equals("ACTIVO") || ESTATUST.equals("BAJA TEMPORAL") ){
                                    Log.d("WS44","entreeeeeee   "+ESTATUST);
                                    intentWs.putExtra("estatus", ESTATUST);
                                    String economico = jsonarray.getString(1);
                                    intentWs.putExtra("economico", economico);
                                    String MARCA = jsonarray.getString(4);
                                    intentWs.putExtra("marca", MARCA);
                                    String VIM = jsonarray.getString(5);
                                    intentWs.putExtra("vim", VIM);
                                    String PROPIETARIO = jsonarray.getString(6);
                                    intentWs.putExtra("propietario", PROPIETARIO);
                                    String VIGENCIA = jsonarray.getString(3);
                                    intentWs.putExtra("vigencia", VIGENCIA);

                                    String COLOR = jsonarray.getString(7);
                                    String AGRUPACION = jsonarray.getString(8);
                                    String RUTASITIO = jsonarray.getString(9);

                                    Log.d("datoswsInserta1","###################"+COLOR);


                                    intentWs.putExtra("colorW", COLOR);
                                    intentWs.putExtra("agrupacionW", AGRUPACION);
                                    intentWs.putExtra("rutaSitioW", RUTASITIO);


                                }
                            }catch (Exception e){

                            }


                           /* if (validaFechaVencimiento==false){
                                String economico = jsonarray.getString(0);
                                intentWs.putExtra("economico", economico);
                                String VIGENCIA = jsonarray.getString(0);
                                intentWs.putExtra("vigencia", VIGENCIA);
                                Log.d("ESATUS","### VALOR ESTATUS"+VIGENCIA);
                            }else {
                                String VIGENCIA = jsonarray.getString(26);
                                intentWs.putExtra("vigencia", VIGENCIA);
                            }*/

                            intentWs.putExtra("usersId",usersId);
                            Log.d("HomeFragment","USERSID########################--->"+usersId);
                            intentWs.putExtra("username",username);
                            intentWs.putExtra("profile",profile);
                            intentWs.putExtra("nombre",nombre);
                            intentWs.putExtra("delegacionId",delegacionId);
                            intentWs.putExtra("activo",activo);

                            licencia = editTextLicencia.getText().toString();
                            intentWs.putExtra("licencia", licencia);
                            Log.d("licencia1", "###Valor de la licencia" + licencia);
                            intentWs.putExtra("bandera", enviaBanderaLic);
                            intentWs.putExtra("placa", PLACA);
                            // intentWs.putExtra("modalidad",modalidad);





                            intentWs.putExtra("placaQR",placaQR);
                            intentWs.putExtra("qr_serial",serialQR);
                            intentWs.putExtra("delegacionIdQR",delegacionIdQR);
                            intentWs.putExtra("economicoQR",economicoQR);
                            intentWs.putExtra("serieQR",serieQR);
                            intentWs.putExtra("marcaQR",marcaQR);
                            intentWs.putExtra("modeloQR",modeloQR);
                            intentWs.putExtra("tipoQR",tipoQR);
                            intentWs.putExtra("colorQR",colorQR);
                            intentWs.putExtra("padronQR",padronQR);
                            intentWs.putExtra("modalidadQR",modalidadQR);
                            intentWs.putExtra("fechaAltaQR",fechaAltaQR);
                            intentWs.putExtra("prorrogaQR",prorrogaQR);
                            intentWs.putExtra("fechaProrrojgaQR",fechaProrrojgaQR);
                            intentWs.putExtra("estatusQR",estatusQR);
                            intentWs.putExtra("coberturaSeguroQR",coberturaSeguroQR);
                            intentWs.putExtra("vigenciaPolizaQR",vigenciaPolizaQR);
                            intentWs.putExtra("periodoQR",periodoQR);
                            intentWs.putExtra("observacionesQR",observacionesQR);
                            intentWs.putExtra("revisionQR",revisionQR);


                            intentWs.putExtra("placarm2",placarm2);
                            intentWs.putExtra("serialrm2",serialrm2);
                            intentWs.putExtra("economicorm2",economicorm2);
                            intentWs.putExtra("serierm2",serierm2);
                            intentWs.putExtra("marcarm2",marcarm2);
                            intentWs.putExtra("modelorm2",modelorm2);
                            intentWs.putExtra("tiporm2",tiporm2);
                            intentWs.putExtra("colorrm2",colorrm2);
                            intentWs.putExtra("padronrm2",padronrm2);
                            intentWs.putExtra("modalidadrm2",modalidadrm2);
                            intentWs.putExtra("fechaAltarm2",fechaAltarm2);
                            intentWs.putExtra("prorrogarm2",prorrogarm2);
                            intentWs.putExtra("fechaProrrojgarm2",fechaProrrojgarm2);
                            intentWs.putExtra("estatusrm2",estatusrm2);
                            intentWs.putExtra("coberturaSegurorm2",coberturaSegurorm2);
                            intentWs.putExtra("vigenciaPolizarm2",vigenciaPolizarm2);
                            intentWs.putExtra("periodorm2",periodorm2);
                            intentWs.putExtra("observacionesrm2",observacionesrm2);
                            intentWs.putExtra("revisionrm22",revisionrm22);


                            intentWs.putExtra("placaPlataforma",placaPlataforma);
                            Log.d("RMWSGOB2","VALOR"+placaPlataforma);

                            intentWs.putExtra("delegacionPlataforma",delegacionIDPlataforma);
                            intentWs.putExtra("nombrePlataforma",nombre_plataformaPlataforma);
                            intentWs.putExtra("polizaPlataforma",numero_polizaPlataforma);
                            intentWs.putExtra("propietarioPlataforma",nombre_propietarioPlataforma);
                            intentWs.putExtra("seriePlataforma",seriePlataforma);
                            intentWs.putExtra("marcaPlataforma",marcaPlataforma);
                            intentWs.putExtra("tipoPlataforma",tipoPlataforma);
                            intentWs.putExtra("colorPlataforma",colorPlataforma);
                            intentWs.putExtra("modeloPlataforma",modeloPlataforma);
                            intentWs.putExtra("vigenciaPlataforma",fecha_vigenciaPlataforma);
                            intentWs.putExtra("socioPlataforma",nombre_socioPlataforma);
                            intentWs.putExtra("estatusPlataforma",estatusPlataforma);



                            intentWs.putExtra("placaFolio",placaFolio);
                            intentWs.putExtra("foliofolio",foliofolio);
                            intentWs.putExtra("delegacionFolio",delegacionFolio);
                            intentWs.putExtra("nombrePlataformaFolio",nombrePlataformaFolio);
                            intentWs.putExtra("numeroPolizaFolio",numeroPolizaFolio);
                            intentWs.putExtra("nombrePropietarioFolio",nombrePropietarioFolio);
                            intentWs.putExtra("serieFolio",serieFolio);
                            intentWs.putExtra("marcaFolio",marcaFolio);
                            intentWs.putExtra("tipoFolio",tipoFolio);
                            intentWs.putExtra("colorFolio",colorFolio);
                            intentWs.putExtra("modeloFolio",modeloFolio);
                            intentWs.putExtra("fechaVigenciaFolio",fechaVigenciaFolio);
                            intentWs.putExtra("fechaAltaFolio",fechaAltaFolio);
                            intentWs.putExtra("fechaVigenciaFoliio",fechaVigenciaFoliio);
                            intentWs.putExtra("nombreSocioFolio",nombreSocioFolio);
                            intentWs.putExtra("estatusFolio",estatusFolio);


                            intentWs.putExtra("lnumeroTarjeton",lnumeroTarjeton);
                            intentWs.putExtra("licenciaTarjeton",licenciaTarjeton);
                            intentWs.putExtra("tipoChoferTarjeton",tipoChoferTarjeton);
                            intentWs.putExtra("folioTarjeton",folioTarjeton);
                            intentWs.putExtra("maternoTarjeton",maternoTarjeton);
                            intentWs.putExtra("paternoTarjerton",paternoTarjerton);
                            intentWs.putExtra("nombreTarjeton",nombreTarjeton);
                            intentWs.putExtra("fechaAltaTarjeton",fechaAltaTarjeton);
                            intentWs.putExtra("fechaVigenciaTarjeton",fechaVigenciaTarjeton);
                            intentWs.putExtra("fechaLabTarjerton",fechaLabTarjerton);
                            intentWs.putExtra("estatusTarjerton",estatusTarjerton);


                            intentWs.putExtra("folioGafeteQR",folioGafeteQR);
                            intentWs.putExtra("delegacionGafeteQR",delegacionGafeteQR);
                            intentWs.putExtra("modalidadGafeteQR",modalidadGafeteQR);
                            intentWs.putExtra("serieRegistroGafeteQR",serieRegistroGafeteQR);
                            intentWs.putExtra("vigenciaGafeteQR",vigenciaGafeteQR);

                            startActivity(intentWs);
                            getActivity().finish();

                        }


                    } catch (JSONException e) {
                        Intent intentWs = new Intent(getActivity(), WsgobConsulta.class);

                        intentWs.putExtra("usersId",usersId);
                        Log.d("HomeFragment","USERSID########################--->"+usersId);
                        intentWs.putExtra("username",username);
                        intentWs.putExtra("profile",profile);
                        intentWs.putExtra("nombre",nombre);
                        intentWs.putExtra("delegacionId",delegacionId);
                        intentWs.putExtra("activo",activo);

                        licencia = editTextLicencia.getText().toString();
                        intentWs.putExtra("licencia", licencia);
                        intentWs.putExtra("placa", "NO-PLACA");
                        Log.d("licencia2", "###Valor de la licencia" + licencia);
                        intentWs.putExtra("bandera", enviaBanderaLic);


                        intentWs.putExtra("placaQR",placaQR);
                        intentWs.putExtra("qr_serial",serialQR);
                        intentWs.putExtra("delegacionIdQR",delegacionIdQR);
                        intentWs.putExtra("economicoQR",economicoQR);
                        intentWs.putExtra("serieQR",serieQR);
                        intentWs.putExtra("marcaQR",marcaQR);
                        intentWs.putExtra("modeloQR",modeloQR);
                        intentWs.putExtra("tipoQR",tipoQR);
                        intentWs.putExtra("colorQR",colorQR);
                        intentWs.putExtra("padronQR",padronQR);
                        intentWs.putExtra("modalidadQR",modalidadQR);
                        intentWs.putExtra("fechaAltaQR",fechaAltaQR);
                        intentWs.putExtra("prorrogaQR",prorrogaQR);
                        intentWs.putExtra("fechaProrrojgaQR",fechaProrrojgaQR);
                        intentWs.putExtra("estatusQR",estatusQR);
                        intentWs.putExtra("coberturaSeguroQR",coberturaSeguroQR);
                        intentWs.putExtra("vigenciaPolizaQR",vigenciaPolizaQR);
                        intentWs.putExtra("periodoQR",periodoQR);
                        intentWs.putExtra("observacionesQR",observacionesQR);
                        intentWs.putExtra("revisionQR",revisionQR);


                        intentWs.putExtra("placarm2",placarm2);
                        intentWs.putExtra("serialrm2",serialrm2);
                        intentWs.putExtra("economicorm2",economicorm2);
                        intentWs.putExtra("serierm2",serierm2);
                        intentWs.putExtra("marcarm2",marcarm2);
                        intentWs.putExtra("modelorm2",modelorm2);
                        intentWs.putExtra("tiporm2",tiporm2);
                        intentWs.putExtra("colorrm2",colorrm2);
                        intentWs.putExtra("padronrm2",padronrm2);
                        intentWs.putExtra("modalidadrm2",modalidadrm2);
                        intentWs.putExtra("fechaAltarm2",fechaAltarm2);
                        intentWs.putExtra("prorrogarm2",prorrogarm2);
                        intentWs.putExtra("fechaProrrojgarm2",fechaProrrojgarm2);
                        intentWs.putExtra("estatusrm2",estatusrm2);
                        intentWs.putExtra("coberturaSegurorm2",coberturaSegurorm2);
                        intentWs.putExtra("vigenciaPolizarm2",vigenciaPolizarm2);
                        intentWs.putExtra("periodorm2",periodorm2);
                        intentWs.putExtra("observacionesrm2",observacionesrm2);
                        intentWs.putExtra("revisionrm22",revisionrm22);

                        intentWs.putExtra("placaPlataforma",placaPlataforma);
                        Log.d("RMWSGOB3","VALOR"+placaPlataforma);

                        intentWs.putExtra("delegacionPlataforma",delegacionIDPlataforma);
                        intentWs.putExtra("nombrePlataforma",nombre_plataformaPlataforma);
                        intentWs.putExtra("polizaPlataforma",numero_polizaPlataforma);
                        intentWs.putExtra("propietarioPlataforma",nombre_propietarioPlataforma);
                        intentWs.putExtra("seriePlataforma",seriePlataforma);
                        intentWs.putExtra("marcaPlataforma",marcaPlataforma);
                        intentWs.putExtra("tipoPlataforma",tipoPlataforma);
                        intentWs.putExtra("colorPlataforma",colorPlataforma);
                        intentWs.putExtra("modeloPlataforma",modeloPlataforma);
                        intentWs.putExtra("vigenciaPlataforma",fecha_vigenciaPlataforma);
                        intentWs.putExtra("socioPlataforma",nombre_socioPlataforma);
                        intentWs.putExtra("estatusPlataforma",estatusPlataforma);



                        intentWs.putExtra("placaFolio",placaFolio);
                        intentWs.putExtra("foliofolio",foliofolio);
                        intentWs.putExtra("delegacionFolio",delegacionFolio);
                        intentWs.putExtra("nombrePlataformaFolio",nombrePlataformaFolio);
                        intentWs.putExtra("numeroPolizaFolio",numeroPolizaFolio);
                        intentWs.putExtra("nombrePropietarioFolio",nombrePropietarioFolio);
                        intentWs.putExtra("serieFolio",serieFolio);
                        intentWs.putExtra("marcaFolio",marcaFolio);
                        intentWs.putExtra("tipoFolio",tipoFolio);
                        intentWs.putExtra("colorFolio",colorFolio);
                        intentWs.putExtra("modeloFolio",modeloFolio);
                        intentWs.putExtra("fechaVigenciaFolio",fechaVigenciaFolio);
                        intentWs.putExtra("fechaAltaFolio",fechaAltaFolio);
                        intentWs.putExtra("fechaVigenciaFoliio",fechaVigenciaFoliio);
                        intentWs.putExtra("nombreSocioFolio",nombreSocioFolio);
                        intentWs.putExtra("estatusFolio",estatusFolio);

                        intentWs.putExtra("lnumeroTarjeton",lnumeroTarjeton);
                        intentWs.putExtra("licenciaTarjeton",licenciaTarjeton);
                        intentWs.putExtra("tipoChoferTarjeton",tipoChoferTarjeton);
                        intentWs.putExtra("folioTarjeton",folioTarjeton);
                        intentWs.putExtra("maternoTarjeton",maternoTarjeton);
                        intentWs.putExtra("paternoTarjerton",paternoTarjerton);
                        intentWs.putExtra("nombreTarjeton",nombreTarjeton);
                        intentWs.putExtra("fechaAltaTarjeton",fechaAltaTarjeton);
                        intentWs.putExtra("fechaVigenciaTarjeton",fechaVigenciaTarjeton);
                        intentWs.putExtra("fechaLabTarjerton",fechaLabTarjerton);
                        intentWs.putExtra("estatusTarjerton",estatusTarjerton);


                        intentWs.putExtra("folioGafeteQR",folioGafeteQR);
                        intentWs.putExtra("delegacionGafeteQR",delegacionGafeteQR);
                        intentWs.putExtra("modalidadGafeteQR",modalidadGafeteQR);
                        intentWs.putExtra("serieRegistroGafeteQR",serieRegistroGafeteQR);
                        intentWs.putExtra("vigenciaGafeteQR",vigenciaGafeteQR);


                        startActivity(intentWs);
                        e.printStackTrace();
                        getActivity().finish();

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





    private void enviarWSConsultaRM(String URL) {
        Log.d("RM", "###Respuesta WS RM---------------------------------" );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            //Para mandar un post aun WS el response Listener tiene que ser de tipo  String , y despues convertir la respuesta a JsonObject.
            public void onResponse(String response) {
                Log.d("RM2", "###Respuesta WS RM2---------------------------------"+response.toString() );
                //Validamos que el response no este vacio
                if (!response.isEmpty()) {
                    //Esto contiene toda la cadena de respuesta del Ws.
                    //Toast.makeText(getContext(), "CONSULTA" + response, Toast.LENGTH_LONG).show();
                    Log.d("RM3", "###ENTRE AQUI A RESPONSE RM ----------");

                        //Convertimos el String en JsonObject
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        JSONArray jsonarrayQR = new JSONArray(obj.getString("QRSERIAL"));
                        for(int a=0; a < jsonarrayQR.length(); a++) {
                            JSONObject jsonobject = jsonarrayQR.getJSONObject(a);
                            placaQR = jsonobject.getString("placas");
                            serialQR = jsonobject.getString("qr_serial");
                            delegacionIdQR = jsonobject.getString("delegacionID");
                            economicoQR = jsonobject.getString("numero_economico");
                            serieQR = jsonobject.getString("serie");
                            marcaQR = jsonobject.getString("marca");
                            modeloQR = jsonobject.getString("modelo");
                            tipoQR = jsonobject.getString("tipo");
                            colorQR = jsonobject.getString("color");
                            padronQR = jsonobject.getString("padron_general");
                            modalidadQR = jsonobject.getString("modalidad");
                            fechaAltaQR = jsonobject.getString("fecha_alta");
                            prorrogaQR = jsonobject.getString("prorroga");
                            fechaProrrojgaQR = jsonobject.getString("fecha_prorroga");
                            estatusQR = jsonobject.getString("estatus_revision_mecanica");
                            coberturaSeguroQR = jsonobject.getString("cobertura_seguro");
                            vigenciaPolizaQR = jsonobject.getString("vigencia_poliza");
                            periodoQR = jsonobject.getString("periodo");
                            observacionesQR = jsonobject.getString("observaciones");
                            revisionQR = jsonobject.getString("revision_por");

                            Log.d("RM15", "###Respuesta WS -- " + placaQR);

                        }
                    }catch (Exception e){

                    }


                    try {
                        JSONArray jsonarray = new JSONArray(obj.getString("PLACARM"));
                        for (int h = 0; h < jsonarray.length(); h++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(h);
                            placarm2 = jsonobject.getString("placas");
                            serialrm2 = jsonobject.getString("qr_serial");
                            delegacionrm2 = jsonobject.getString("delegacionID");
                            economicorm2 = jsonobject.getString("numero_economico");
                            serierm2 = jsonobject.getString("serie");
                            marcarm2 = jsonobject.getString("marca");
                            modelorm2 = jsonobject.getString("modelo");
                            tiporm2 = jsonobject.getString("tipo");
                            colorrm2 = jsonobject.getString("color");
                            padronrm2 = jsonobject.getString("padron_general");
                            modalidadrm2 = jsonobject.getString("modalidad");
                            fechaAltarm2 = jsonobject.getString("fecha_alta");
                            prorrogarm2 = jsonobject.getString("prorroga");
                            fechaProrrojgarm2 = jsonobject.getString("fecha_prorroga");
                            estatusrm2 = jsonobject.getString("estatus_revision_mecanica");
                            coberturaSegurorm2 = jsonobject.getString("cobertura_seguro");
                            vigenciaPolizarm2 = jsonobject.getString("vigencia_poliza");
                            periodorm2 = jsonobject.getString("periodo");
                            observacionesrm2 = jsonobject.getString("observaciones");
                            revisionrm22 = jsonobject.getString("revision_por");

                            Log.d("RM33", "###Respuesta WS --- " + placarm2);
                        }
                    }catch (Exception e){

                    }

/*  placas,folio,delegacionID,nombre_plataforma, numero_poliza,nombre_propietario,serie,marca,tipo,color,modelo,fecha_vigencia,fecha_alta,fecha_vigencia, nombre_socio,estatus FROM plata
forma  */

                    try {
                        JSONArray jsonarray = new JSONArray(obj.getString("FOLIOPLATAFORMA"));
                        for (int h = 0; h < jsonarray.length(); h++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(h);
                            placaFolio = jsonobject.getString("placas");
                            foliofolio = jsonobject.getString("folio");
                            delegacionFolio = jsonobject.getString("delegacionID");
                            nombrePlataformaFolio = jsonobject.getString("nombre_plataforma");
                            numeroPolizaFolio = jsonobject.getString("numero_poliza");
                            nombrePropietarioFolio = jsonobject.getString("nombre_propietario");
                            serieFolio = jsonobject.getString("serie");
                            marcaFolio = jsonobject.getString("marca");
                            tipoFolio = jsonobject.getString("tipo");
                            colorFolio = jsonobject.getString("color");
                            modeloFolio = jsonobject.getString("modelo");
                            fechaVigenciaFolio = jsonobject.getString("fecha_vigencia");
                            fechaAltaFolio = jsonobject.getString("fecha_alta");
                            fechaVigenciaFoliio = jsonobject.getString("fecha_vigencia");
                            nombreSocioFolio = jsonobject.getString("nombre_socio");
                            estatusFolio = jsonobject.getString("estatus");


                            Log.d("RM33", "###Respuesta WS --- " + placarm2);
                        }
                    }catch (Exception e){

                    }




                    try {
                        Log.d("RM10", "###Respuesta WS RM10" +obj.getString("QRSERIAL"));
                        Log.d("RM11", "###Respuesta WS RM11" +obj.getString("PLACARM"));
                        Log.d("RM12", "###Respuesta WS RM12" +obj.getString("FOLIOPLATAFORMA"));
                        Log.d("RM13", "###Respuesta WS RM13" +obj.getString("PLACASPLATAFORMA"));
                        Log.d("RM14", "###Respuesta WS RM14" +obj.getString("TARJETON"));

                        // JSONObject placasPlataforma = obj.getJSONObject("PLACASPLATAFORMA");
                        //JSONArray  placasPlataforma = obj.getString("PLACASPLATAFORMA");
                        //Log.d("RM15", "###Respuesta WS RM14" +placasPlataforma);
                        //String placasPlataformaArray = (String) placasPlataforma.get(1);
                        //Log.d("RM16", "###Respuesta WS RM14" +placasPlataformaArray);

                        JSONArray jsonarray = new JSONArray(obj.getString("PLACASPLATAFORMA"));

                        try {
                            Log.d("RM17",";;;;;;"+jsonarray);
                            for(int i=0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                placaPlataforma      = jsonobject.getString("placas");
                                Log.d("RM16", "###Respuesta WS -- " +placaPlataforma);
                                folioPlataforma      = jsonobject.getString("folio");

                                delegacionIDPlataforma      = jsonobject.getString("delegacionID");
                                nombre_plataformaPlataforma      = jsonobject.getString("nombre_plataforma");
                                numero_polizaPlataforma      = jsonobject.getString("numero_poliza");
                                nombre_propietarioPlataforma      = jsonobject.getString("nombre_propietario");
                                seriePlataforma      = jsonobject.getString("serie");
                                colorPlataforma = jsonobject.getString("color");
                                marcaPlataforma      = jsonobject.getString("marca");
                                tipoPlataforma      = jsonobject.getString("tipo");
                                modeloPlataforma      = jsonobject.getString("modelo");
                                fecha_vigenciaPlataforma      = jsonobject.getString("fecha_vigencia");
                                fecha_altaPlataforma      = jsonobject.getString("fecha_alta");
                                nombre_socioPlataforma      = jsonobject.getString("nombre_socio");
                                estatusPlataforma      = jsonobject.getString("estatus");

                            }

                        }catch (Exception e){
                            //   Log.d("RM16", "NO ENTRE PUTO -- ");
                        }

/*
* imos_tarjeton_lnumero,imos_tarjeton_licencia, imos_tarjeton_tipo_chofer,imos_tarjeton_folio,imos_tarjeton_materno,imos_tarjeton_paterno,imos_tarjeton_nombre, fecha_alta,fecha_vigencia_tarjeton,inserta_fecha_lab,estatus FROM
tarjeton_TIJUANA
*
* */


                        JSONArray jsonarrayTarjeton = new JSONArray(obj.getString("TARJETON"));

                        try {
                            Log.d("RM17",";;;;;;"+jsonarrayTarjeton);
                            for(int i=0; i < jsonarrayTarjeton.length(); i++) {
                                JSONObject jsonobject = jsonarrayTarjeton.getJSONObject(i);
                                lnumeroTarjeton      = jsonobject.getString("imos_tarjeton_lnumero");
                                licenciaTarjeton      = jsonobject.getString("imos_tarjeton_licencia");
                                tipoChoferTarjeton      = jsonobject.getString("imos_tarjeton_tipo_chofer");
                                folioTarjeton      = jsonobject.getString("imos_tarjeton_folio");
                                maternoTarjeton      = jsonobject.getString("imos_tarjeton_materno");
                                paternoTarjerton      = jsonobject.getString("imos_tarjeton_paterno");
                                nombreTarjeton      = jsonobject.getString("imos_tarjeton_nombre");
                                fechaAltaTarjeton = jsonobject.getString("fecha_alta");
                                fechaVigenciaTarjeton      = jsonobject.getString("fecha_vigencia_tarjeton");
                                fechaLabTarjerton      = jsonobject.getString("inserta_fecha_lab");
                                estatusTarjerton      = jsonobject.getString("estatus");

                                Log.d("TARJETON","DATOS DE TARJETON "+lnumeroTarjeton);

                            }

                        }catch (Exception e){
                            Log.d("RM16", "NO ENTRE PUTO -- ");
                        }



                        Log.d("wsRMPlataforma","DATOS DE RM PLATAFORMA "+placaPlataforma);

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
                    //Toast.makeText(getContext(), "CONSULTA" + response, Toast.LENGTH_LONG).show();

                    try {
                        //Convertimos el String en JsonObject
                        /*JSONObject obj = new JSONObject(response);*/

                        JSONArray jsonarray = new JSONArray(response);


                        Log.d("objVehicular", "###Respuesta WS padron vehicular" + jsonarray.toString());
                        //Accedemos al valor del Objeto deseado completo.
                       // JSONArray jsonarray = obj.getJSONArray("data");

                        Log.w("jARRAY","### QUE TIENE EL ARRAY?"+jsonarray.toString());


                        if (jsonarray.length()==0){
                            Log.d("#####","#### ENTRE");
                            Intent intentWs = new Intent(getActivity(), Infracciones.class);
                            licencia = editTextLicencia.getText().toString();
                            modalidad = spinnerModalidad.getSelectedItem().toString();
                            sector = spinerSector.getSelectedItem().toString();
                            infraccion1 = edtInfraccion1.getText().toString();
                            Log.d("INFRACCION1-1","################========>>>>>"+infraccion1);
                            infraccion2 = edtInfraccion2.getText().toString();
                            infraccion3 = edtInfraccion3.getText().toString();
                            infraccion4 = edtInfraccion4.getText().toString();
                            infraccion5 = edtInfraccion5.getText().toString();

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

                       /* //Obtenemos el total de elementos del objeto.
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            //Accedemos a los elementos por medio de getString.


                            String PLACA = jsonobject.getString("placa");

                            Boolean validaEstatus = jsonobject.has("estatus");
                            Boolean validaFechaVencimiento = jsonobject.has("fechaVencimiento");


                            String PROPIETARIO = jsonobject.getString("propietario");
                            Log.d("PROPIETARIO","#$##%#$%#$%#$%#$%#$%#$%#$%#$"+PROPIETARIO);

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
                            }*/

                        //Obtenemos el total de elementos del objeto.
                        for (int i = 0; i < jsonarray.length(); i++) {
                            // JSONObject jsonobject = jsonarray.getJSONObject(i);
                            //Accedemos a los elementos por medio de getString.


                            //Iniciamos actividad y mandamos parametros.
                            Intent intentWs = new Intent(getActivity(), Infracciones.class);
                            String PLACA = jsonarray.getString(0);
                            Log.d("vergasVato","### $#$#$$$US"+PLACA);
                            // Boolean validaEstatus = false;



                            try {
                                String  ESTATUS = jsonarray.getString(24);


                                if (ESTATUS.equals("ACTIVO") || ESTATUS.equals("BAJA TEMPORAL") ){
                                    intentWs.putExtra("economico", "NO APLICA");
                                    intentWs.putExtra("estatus", ESTATUS);
                                    String VIGENCIA = jsonarray.getString(23);
                                    intentWs.putExtra("vigencia", VIGENCIA);
                                    Log.d("ESATUS","### VALOR ESTATUS"+ESTATUS);

                                    Boolean validaFechaVencimiento = false;

                                    // modalidad =spinnerModalidad.getSelectedItem().toString();
                                    String PROPIETARIO = jsonarray.getString(18);

                                    String VIM = jsonarray.getString(1);

                                    String MARCA = jsonarray.getString(3);



                                    intentWs.putExtra("propietario", PROPIETARIO);

                                    intentWs.putExtra("vim", VIM);
                                    intentWs.putExtra("marca", MARCA);

                                }

                            }catch (Exception e){

                            }
                            try {
                                String  ESTATUST = jsonarray.getString(2);
                                Log.d("WS111","entreeeeeee   "+ESTATUST);
                                if (ESTATUST.equals("ACTIVO") || ESTATUST.equals("BAJA TEMPORAL") ){
                                    Log.d("WS44","entreeeeeee   "+ESTATUST);
                                    intentWs.putExtra("estatus", ESTATUST);
                                    String economico = jsonarray.getString(1);
                                    intentWs.putExtra("economico", economico);
                                    String MARCA = jsonarray.getString(4);
                                    intentWs.putExtra("marca", MARCA);
                                    String VIM = jsonarray.getString(5);
                                    intentWs.putExtra("vim", VIM);
                                    String PROPIETARIO = jsonarray.getString(6);
                                    intentWs.putExtra("propietario", PROPIETARIO);
                                    String VIGENCIA = jsonarray.getString(3);
                                    intentWs.putExtra("vigencia", VIGENCIA);

                                    String COLOR = jsonarray.getString(7);
                                    String AGRUPACION = jsonarray.getString(8);
                                    String RUTASITIO = jsonarray.getString(9);

                                    Log.d("datoswsInserta1","###################"+COLOR);


                                    intentWs.putExtra("colorW", COLOR);
                                    intentWs.putExtra("agrupacionW", AGRUPACION);
                                    intentWs.putExtra("rutaSitioW", RUTASITIO);

                                }
                            }catch (Exception e){

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
                            Log.d("MODALIDAD1","#################"+modalidad);
                            sector = spinerSector.getSelectedItem().toString();
                            infraccion1 = edtInfraccion1.getText().toString();
                            Log.d("INFRACCION1-2","################========>>>>>"+infraccion1);
                            infraccion2 = edtInfraccion2.getText().toString();
                            infraccion3 = edtInfraccion3.getText().toString();
                            infraccion4 = edtInfraccion4.getText().toString();
                            infraccion5 = edtInfraccion5.getText().toString();
                            String cuentaString = Integer.toString(cuenta);

                            intentWs.putExtra("sector",sector);
                            intentWs.putExtra("modalidad",modalidad);
                            Log.d("MODALIDAD2","#################"+modalidad);
                            intentWs.putExtra("infra1",infraccion1);
                            intentWs.putExtra("infra2",infraccion2);
                            intentWs.putExtra("infra3",infraccion3);
                            intentWs.putExtra("infra4",infraccion4);
                            intentWs.putExtra("infra5",infraccion5);
                            intentWs.putExtra("cuenta",cuentaString);


                           /* intentWs.putExtra("propietario", PROPIETARIO);

                            intentWs.putExtra("vim", VIM);
                            intentWs.putExtra("marca", MARCA);*/

                            startActivity(intentWs);



                        }


                    } catch (JSONException e) {
                        Intent intentWs = new Intent(getActivity(), Infracciones.class);
                        licencia = editTextLicencia.getText().toString();
                        intentWs.putExtra("usersId",usersId);
                        Log.d("HomeFragment","USERSID########################--->"+usersId);
                        intentWs.putExtra("username",username);
                        intentWs.putExtra("profile",profile);
                        intentWs.putExtra("nombre",nombre);
                        intentWs.putExtra("delegacionId",delegacionId);
                        intentWs.putExtra("activo",activo);

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
//Clase para scanear el codigo QR
public void escanear(){
    try {
        IntentIntegrator intent = IntentIntegrator.forSupportFragment(HomeFragment.this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intent.setPrompt("ESCANEAR QR - IMOS -");
        intent.setCameraId(0);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }catch (Exception e){
        Toast.makeText(getContext(),"QR NO VALIDO",Toast.LENGTH_LONG).show();
    }


}


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle bundle = data.getExtras();
        //from bundle, extract the image
        if(bundle!=null){
        Bitmap bitmap = (Bitmap) bundle.get("data");


        if (bitmap!=null){
            //set image in imageview
            imageViewP.setImageBitmap(bitmap);
            //process the image
            //1. create a FirebaseVisionImage object from a Bitmap object
            FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
            //2. Get an instance of FirebaseVision
            FirebaseApp.initializeApp(getActivity());

            FirebaseVision firebaseVision = FirebaseVision.getInstance();
            //3. Create an instance of FirebaseVisionTextRecognizer
            FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = firebaseVision.getOnDeviceTextRecognizer();
            //4. Create a task to process the image
            Task<FirebaseVisionText> task = firebaseVisionTextRecognizer.processImage(firebaseVisionImage);
            //5. if task is success
            task.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                @Override
                public void onSuccess(FirebaseVisionText firebaseVisionText) {

                    try {
                        String s = firebaseVisionText.getText();

                        boolean linea = s.contains("\n");
                        String[] lines = s.split("\n");

                        for (int i = 0; i < lines.length; ++i) {
                            if (lines[i].contains("-")) {
                                miPlacosa = lines[i];
                            }
                        }


                        Log.d("CONTIENE", "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%" + miPlacosa);

                        String[] splitPlaca = miPlacosa.split("-");

                        String dato1 = splitPlaca[0];
                        String dato2 = splitPlaca[1];
                        String dato3 = splitPlaca[2];

                        String nuevaPlaca = dato1 + dato2 + dato3;

                        Log.d("IMAGENPLACA", "ALV ESTA ES TU PLACA " + s);

                        editTextPlaca.setText(nuevaPlaca);

                       /* if (editTextPlaca.equals(null) || editTextPlaca.equals("")) {
                            Log.d("ENTREEEEEEEEEE", "ALV ESTA ES TU PLACA " + s);
                            editTextPlaca.setText(infoQr);

                        }*/


                        // textViewP.setText(s);

                    } catch (Exception error) {
                        Toast.makeText(getActivity(), "Tomar foto de nuevo", Toast.LENGTH_LONG).show();
                    }

                }


            });

            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }}
//###############################################################################################

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(getContext(),"CANCELASTE EL ESCANEO", Toast.LENGTH_LONG);
            }else {
                //Aqui Agregamos las validaciones para los diferentes Formatos de QR´S
                infoQr = result.getContents();
                try {
                    infoQr = result.getContents();
                    editTextPlaca.setText(infoQr);
                    Log.d("QRSTRING", "ESTE ES EL VALOR DEL QR STRING" + result.getContents().toString());
                }catch (Exception e){

                }
                Log.d("QRSTRING", "ESTE ES EL VALOR DEL QR STRING" + infoQr);
                    try {
                        List<String> datosLicencia = Arrays.asList(infoQr.split(","));
                        boolean isFound = datosLicencia.get(4).contains("BC"); // true

                        int sizeDatosLicencia = datosLicencia.size();
                        if (isFound == true) {
                            editTextLicencia.setText(datosLicencia.get(4).trim());

                            folioGafeteQR = datosLicencia.get(0).trim();
                            delegacionGafeteQR =datosLicencia.get(1).trim();
                            modalidadGafeteQR = datosLicencia.get(2).trim();
                            serieRegistroGafeteQR = datosLicencia.get(6).trim();
                            vigenciaGafeteQR = datosLicencia.get(7).trim();

                            Log.d("QRSTRING", "ESTE ES EL VALOR DEL QR STRING" + result.getContents().toString());
                            editTextPlaca.setText("");
                        }
                        int count = 0;

                        /*//Counts each character except space
                        for(int i = 0; i < infoQr.length(); i++) {
                            if(infoQr.charAt(i) != ' ')
                                count++;
                        }
                        Log.d("QRSTRING", "CONTADOR" + count);*/
                      //  if (count==6) {

                        /*}*/

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


// else continue with any other code you need in the method

    /*private static final String[] InfracionesList = new String[]{
            "FALTA DE PLACAS","CIRCULA SIN PLACAS","FALTA DE TARJETA CIRCULACION","TARJETA CIRCULACION VENCIDA","TRANSITAR EN EL CARRIL NO CORRESPONDIENTE","SUBIR BAJAR PASAJE LUGAR INDEBIDO",
            "ESTACIONARSE EN LUGAR PROHIBIDO","FALTA DE ESPEJO(S) RETROVISOR","FALTA DE LUCES DELANTERAS","FATLA DE LUCES TRASERAS-FRENO","FALTA DE LUCES","EXCESO DE PASAJE","EXCESO DE CARGA",
            "TRASLADO DE MERCANCIA VIA PUBLICA","IMPIDEN VISIBILIDAD AL CONDUCTOR","MODIFICACION CARROCERIA","FALTA DE ASEO AL OPERADOR","MODIFICAR ASIENTOS PARA ACEPTAR MAS PASAJEROS",
            "SERVICIO DE PASAJEROS EN VEHICULO PARTICULAR","NO CONTAR CON EL TIPO DE LICENCIA CORRESPONDIENTE","NO REUNIR CONDICIONES DE IMAGEN Y COMODIDAD","NO CONTAR CON REVISION MECANICA",
            "REVISION DE DOCUMENTOS","PRESTAR SERVICIO INTERMUNICIPAL SIN PERMISO","CONTAMINAR CON HUMO","LLANTAS LISAS","ROTULACION INAPROPIADA","EXTINGUIDOR,BOTIQUIN","VENTANAS ROTAS O SIN ELLA",
            "LIMPIA PARABRISAS","TRABAJAR FUERA DE HORARIO DE 4 A 22HRS","TRAER PUERTAS CERRADAS MIENTRAS LA UNIDA SE MUEVE","OBSTRUCCION DE VISIBILIDAD DEL CONDUCTOR","TENER VIGENTE POLIZA DAÑOS A TERCEROS",
            "UNIFORME OBLIGATORIO PARA PRESTAR EL SERVICIO","POR CIRCULAR CON CAJUELA ABIERTA","POR CIRCULAR CON MATERIAL FLAMABLE","SIN NUMERO ECONOMICO","POR CIRCULAR SIN NOMBRE DE LA RUTA A LOS COSTADOS",
            "CIRCULAR SIN LA LEYENDA DE TAXI A LOS COSTADOS","FUMAR O TOMAR BEBIDAS ALCOHOLICAS EN LA UNIDAD","ARROJAR BASURA U OBJETOS EN LA VIA PUBLICA","OBSTRUIR CARRIL DE CIRCULACION",
            "REALIZAR TODO TIPO DE REPARACIONES MECANICAS","HACER USO DE PARKIN AUN PAGANDO TARIFA","VIDRIOS POLARIZADOS","REALIZAR COBROS ADICIONALES","POR NO CONTAR CON PERMISO EN SITIO O LANZADERAS",
            "ALTERAR MODIFICAR SISTEMA DE SONIDO NO ORIGINAL","NO CONTAR CON EXAMEN TOXICOLOGICO","NO TRAER LICENCIA","POR ABASTASER COMBUSTIBLE CON PASAJE ABORDO","POR UTILIZAR CELULAR O RADIO MIENTRAS CODUCE",
            "POR MODIFICAR ESCAPE O SILENCIADOR","POR NO RESPETAR EL REGLAMENTO DE TRANSITO","POR CIRCULAR EN RUTA DIFERENTE A LA REGISTRADA"};*/

    private void cargarImagen() {

        final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(getActivity());
        alertOpciones.setTitle("Seleccione una Opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    doProcess();
                }else{
                    if (opciones[i].equals("Cargar Imagen")){
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), COD_SELECCIONA);




                       /* Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),COD_SELECCIONA);*/
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();

    }

    private void tomarFotografia() {
        File fileImagen=new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
        boolean isCreada=fileImagen.exists();
        String nombreImagen="";
        if(isCreada==false){
            isCreada=fileImagen.mkdirs();
        }

        if(isCreada==true){
            nombreImagen=(System.currentTimeMillis()/1000)+".jpg";
        }

        path=Environment.getExternalStorageDirectory()+
                File.separator+RUTA_IMAGEN+File.separator+nombreImagen;

        File imagen=new File(path);


        Intent intent=null;
        intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ////
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
        {
            String authorities=getActivity().getPackageName()+".provider";
            Uri imageUri= FileProvider.getUriForFile(getActivity(),authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else
        {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent,COD_FOTO);

        ////
    }

}

