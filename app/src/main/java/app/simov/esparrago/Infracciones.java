package app.simov.esparrago;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.location.LocationListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Logger;

import app.simov.esparrago.utils.GPSTracker;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Infracciones extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    LocationManager locationManager;
    //ProgressDialog loading;
    String locationText = "";
    String locationLatitude = "";
    String locationLongitude = "";

    private int mInterval = 3000; // 3 seconds by default, can be changed later
    private Handler mHandler;
    ProgressDialog progressDialog;
    String placa;
    String estatus;
    String propietario;
    String vigencia;
    String vim;
    String marca;
    String infracciones;
   // String licencia;
   //String LICENCIA;
    String nombre;
    String fechaVecimiento;
    String boleta;
    TextView textViewLicencia;
    TextView textViewNombre;
    TextView textViewFechaVencimiento;
    TextView textViewInfracciones;
    TextView textViewFechaInfracciones;
    TextView mensaje1;
    TextView mensaje2;
    TextView placaInfracciones;
    TextView estatusPlacaInfracciones;
    TextView tvVigenciaTcInfraccion;
    TextView tvVimInfraccion;
    TextView tvInfraInfraccion;
    String   modalidadH;
    String   modalidad;
    String   infra1;
    String   infra2;
    String   infra3;
    String   infra4;
    String   infra5;
    String   infraConcat;
    String   cuenta;
    String   sector;

    String folio;
    TextView tvModalidadInfraccion;
    TextView tvZonaSector;
    EditText edtComentarios;
    EditText edtFolio;
    String UPLOAD_URL = "https://simov.app/servicios/insertaInfraccion.php";
   // String URLICENCIA = "https://simov.app/servicios/consultaLicencia.php";
   // String URLVEHICULAR = "https://simov.app/servicios/controlVehicular.php";
    String URLINFRACCION = "https://simov.app/servicios/consultaInfraccion.php";
    private final String CARPETA_RAIZ="misImagenesPrueba/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"misFotos";
    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    Button botonCargar;
    Button botonInfraccion;
    Button warning;
    ImageView imagen;
    //Image2
    ImageView imagen2;
    //Image3
    ImageView imagen3;
    String path;
    String path2;
    String path3;
    int count = 0;

    //Imagenes
    Bitmap bitmap;
    Bitmap bitmap2;
    Bitmap bitmap3;

    //Variables de session.
    String usersId;
    String username;
    String profile;
    String nombreLogin;
    String delegacionId;
    String activo;
    String color;
    String agrupacion;
    String rutaSitio;
    String VENCIMIENTO;
    String NOMBRECOMPLETO;
    String ECONOMICO;
    String sectorId;
    String imagenB;
    String imagenB2;
    String imagenB3;
    Uri miPath;
    Uri miPath1;
    Uri miPath2;
    String modi;
    TextView tvColor;
    TextView tvAgrupacion;
    TextView tvRutaSitio;
    TextView tvEstatusInfracciones;
    // GPSTracker class
    GPSTracker gps;
    double latitude;
    double longitud;
    private Marker markerInfraacion;
    //Licencia
    String licenciaWs;
    String vencimientoLicenciaWs;
    String nombreCompletoLicenciaWs;
    //VALIDACION MSJ
    String mensaje;
    private final String _TAG = "INFRALOG";
    private String zonaSectorFinal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infracciones);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //MAPA
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapInfracciones);
        mapFragment.getMapAsync(this);
        //DIALOGO
        //PROGRESS DIALOG
        progressDialog = new ProgressDialog(Infracciones.this);
        //Mostramos el progressBAR
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.rgb(109, 30, 30)));
        progressDialog.setMessage("Subiendo Imagenes al servidor...");
        //Layouts de las imagenes
        imagen= (ImageView) findViewById(R.id.imagemId);
        imagen2= (ImageView) findViewById(R.id.imagemId2);
        imagen3= (ImageView) findViewById(R.id.imagemId3);
        //Boton que lanza el AlertDialog.
        botonCargar= (Button) findViewById(R.id.btnCargarImg);
        botonInfraccion= (Button) findViewById(R.id.btnCargarImg2);
        warning = (Button) findViewById(R.id.btnWarning);
        //Seteamos los valores de los Textviews.
        textViewLicencia = findViewById(R.id.tvLicenciaInfraccion);
        textViewFechaVencimiento = findViewById(R.id.tvFechaVencimientoInfraccion);
        placaInfracciones = findViewById(R.id.tvPlacaInfraccion);
        estatusPlacaInfracciones = findViewById(R.id.tvEstatusInfracciones);
        tvVigenciaTcInfraccion = findViewById(R.id.tvVigenciaTcInfraccion);
        tvVimInfraccion = findViewById(R.id.tvVimInfraccion);
        //tvInfraInfraccion = findViewById(R.id.tvInfraInfraccion);
        tvModalidadInfraccion = findViewById(R.id.tvModalidadInfraccion);
        tvZonaSector = findViewById(R.id.tvZonaSector);
        tvInfraInfraccion = findViewById(R.id.tvInfra);
        edtComentarios = findViewById(R.id.edtComentarios);
        edtFolio = findViewById(R.id.edtFolio);
        tvColor = findViewById(R.id.tvColorInfra);
        tvAgrupacion = findViewById(R.id.tvAgrupacion);
        tvRutaSitio = findViewById(R.id.tvRutaSitio);

        //Bundle de actividad anterior
        Bundle bundle  = getIntent().getExtras();
        Log.d("BUNDLE","VALOR DEL BUNDLE ##############"+bundle.toString());
        //Validamos que no venga vacio
        if (bundle != null){
            //Recojemos parametros que vienen desde el LOGIN.
            //VALORES POR DEFECTO
            usersId  = bundle.getString("usersId");
            Log.d("B-l-Infracciones-0","Valor Users ID"+usersId);
            username  = bundle.getString("username");
            profile  = bundle.getString("profile");
            nombreLogin  = bundle.getString("nombre");
            delegacionId  = bundle.getString("delegacionId");
            activo  = bundle.getString("activo");


            //##################### BLOQUE LICENCIAS #######################################################
             try {
                licenciaWs = bundle.getString("licenciaWs");
                vencimientoLicenciaWs = bundle.getString("vecimientoLicenciaWs");
                nombreCompletoLicenciaWs = bundle.getString("nombreCompletoLicenciaWs");

                if (licenciaWs.equals("NO-LICENCIA") && placa.equals("null")){
                    textViewLicencia.setText("NO-LICENCIA");
                    textViewFechaVencimiento.setText("NO-LICENCIA");
                    licenciaWs = "NO-LICENCIA";
                    Log.d("B-l-Infracciones-1","Valor licencia del Bundle recojido :"+ licenciaWs);
                    licenciaWs = "NO-LICENCIA";
                    vencimientoLicenciaWs = "NO-LICENCUA";
                    nombreCompletoLicenciaWs = "NO-LICENCIA";

                    AlertDialog.Builder dialogo=new AlertDialog.Builder(Infracciones.this);
                    dialogo.setTitle("CONSULTA LICENCIA");
                    dialogo.setMessage("LICENCIA MAL CAPTURADA O INEXISTENTE");


                    dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });

                    dialogo.show();


                }else{
                    textViewLicencia.setText(licenciaWs);
                    textViewFechaVencimiento.setText(vencimientoLicenciaWs);
                    Log.d("B-l-Infracciones-2","Valor licencia del Bundle recojido :"+ licenciaWs);
                }
            }catch(Exception error){
                textViewLicencia.setText("NO-LICENCIA");
                textViewFechaVencimiento.setText("NO-LICENCIA");
                Log.d("B-l-Infracciones-3","Valor licencia del Bundle recojido :"+ licenciaWs);

                    licenciaWs = "NO-LICENCIA";

            }
            //##################### TERMINA BLOQUE LICENCIAS #######################################################



            //##################### BLOQUE GPS #######################################################
            // Create class object
            gps = new GPSTracker(Infracciones.this);
            // Check if GPS enabled
            if(gps.canGetLocation()) {
                latitude = gps.getLatitude();
                longitud = gps.getLongitude();
            } else {
                gps.showSettingsAlert();
            }

            //##################### TERMINA BLOQUE GPS #######################################################


            //#####################  DATOS VEHICULO #######################################################

            //VIGENCIA PLACAS
            vigencia = bundle.getString("vigencia");
            if(vigencia== null || vigencia == "" || vigencia.equals(null) || vigencia.equals("")){
                Log.d("VALIDA-FLUJO-1","Entro a la validacion de la LICENCIA : "+ licenciaWs);
                vigencia = "SIN-PLACA";
                vim = "SIN-PLACA";
                placa = "null";

            }else{
                placa = bundle.getString("placa");
                Log.d("VALIDA-FLUJO-2","Entro a la validacion de la PLACA : "+ placa);
                Log.d("B-l-Infracciones-5","Valor de la placa que viene del HomeFragmet : "+placa);
                estatus = bundle.getString("estatus");
                Log.d("B-l-Infracciones-6","Estatus de placa de HomeFragment : "+estatus);
                propietario = bundle.getString("propietario");
                Log.d("B-l-Infracciones-7","Propietario Placas : "+propietario);
                vim = bundle.getString("vim");
                Log.d("B-l-Infracciones-8","Numero de serie de Vehiculo : "+vim);
                color  = bundle.getString("colorW");
                agrupacion  = bundle.getString("agrupacionW");
                rutaSitio  = bundle.getString("rutaSitioW");
                Log.d("B-l-Infracciones-9","Color del Taxi : "+color);
                placaInfracciones.setText(placa);
                tvColor.setText(color);
                tvAgrupacion.setText(agrupacion);
                tvRutaSitio.setText(rutaSitio);
                marca = bundle.getString("marca");
                infracciones = bundle.getString("infracciones");
                estatusPlacaInfracciones.setText(estatus);
                tvVimInfraccion.setText(vim);
                tvVigenciaTcInfraccion.setText(vigencia);

                if(tvColor.getText() == null ||  tvColor.getText() == ""){
                    tvColor.setText("SIN DATOS");
                }
                if(tvAgrupacion.getText() == null ||  tvAgrupacion.getText() == ""){
                    tvAgrupacion.setText("SIN DATOS");
                }
                if(tvRutaSitio.getText() == null ||  tvRutaSitio.getText() == ""){
                    tvRutaSitio.setText("SIN DATOS");
                }
                if(estatusPlacaInfracciones.getText() == null ||  estatusPlacaInfracciones.getText() == ""){
                    estatusPlacaInfracciones.setText("SIN DATOS");
                }
                if(tvVimInfraccion.getText() == null ||  tvVimInfraccion.getText() == ""){
                    tvVimInfraccion.setText("SIN DATOS");
                }
                if(tvVigenciaTcInfraccion.getText() == null ||  tvVigenciaTcInfraccion.getText() == ""){
                    tvVigenciaTcInfraccion.setText("SIN DATOS");
                }

            }
            modalidad = bundle.getString("modalidad");
            sector = bundle.getString("sector");

            tvModalidadInfraccion.setText(modalidad);
            tvZonaSector.setText(sector);
            Log.d(_TAG, "VALOR SECTOR : $ " + sector);

            sectorId = "2";//eSTA

            Log.d("modalidad","$"+modalidad);
            if (modalidad !=null){
                modalidad = modalidad.trim();
            }else{
                modalidad = "SIN MODALIDAD";
            }

            infra1 = bundle.getString("infra1");
            infra2 = bundle.getString("infra2");
            infra3 = bundle.getString("infra3");
            infra4 = bundle.getString("infra4");
            infra5 = bundle.getString("infra5");
            cuenta = bundle.getString("cuenta");


            String[] split = infra1.split("--");
            String articulo,inciso;
            articulo = split[0];
            inciso = split[1];
            String infraS1 = "Art - "+articulo+" Inc - "+inciso;
            String delegacionIdString = String.valueOf(delegacionId);
            infra1 = "**"+articulo+"**"+inciso+"**"+sector+"**"+delegacionIdString;
            Log.d(_TAG,"DELEGACION ID"+ delegacionId);
            Log.d("INFRA1","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+infra1);
            /*Log.d("INFRA2","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+infra2);
            Log.d("INFRA3","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+infra3);
            Log.d("INFRA4","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+infra4);
            Log.d("INFRA5","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+infra5);*/
            Log.d("CUENTA$$$","$$$$$$$$$$$$$$$$$%$%#######################"+cuenta);

          //  Log.d("LICENCIA-VERGAS1","$$$$$$$$$$$$$$$"+licencia);
            nombre = bundle.getString("nnombre");
            fechaVecimiento = bundle.getString("fechaVencimiento");


            tvInfraInfraccion.setText(infraS1);

            Log.d("MODALIDAD","&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+modalidad);
        }
        //ENVIAR INFO
        botonInfraccion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cargarInfraccion();
            }

        });


        warning.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                uploadImageWarning();
            }


        });



        if(validaPermisos()){
            botonCargar.setEnabled(true);
        }else{
            botonCargar.setEnabled(false);
        }

    }

    private boolean validaPermisos() {

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Super para llamarse al iniciar la actividad
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                    && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                botonCargar.setEnabled(true);
            }else{
                solicitarPermisosManual();
            }
        }

    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(Infracciones.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(Infracciones.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }


    private void cargarAceptacion() {
        Log.d("ENTRE","$$$");
        if(edtFolio.getText().toString().trim().equals("")){
            AlertDialog.Builder dialogo=new AlertDialog.Builder(Infracciones.this);
            dialogo.setTitle("FOLIO VACIO");
            dialogo.setMessage("EL CAMPO FOLIO ES OBLIGATORIO");


            dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                }
            });
            dialogo.show();
        }else {

            AlertDialog.Builder dialogo = new AlertDialog.Builder(Infracciones.this);
            dialogo.setTitle("GENERA INFRACCION");
            Log.d("Cargar-Aceptacion-1", "Valor placa : " + placa);


            if (placa == "null") {
                mensaje = licenciaWs;
                dialogo.setMessage("LA INFRACCION SE GENERO CORRECTAMENT CON LICENCIA : " + mensaje);
            } else {
                mensaje = placa;
                dialogo.setMessage("LA INFRACCION SE GENERO CORRECTAMENT CON PLACA : " + mensaje);
            }


            dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
                    // Toast.makeText(Infracciones.this, "##1"+response, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Drawer.class);
                    intent.putExtra("usersId", usersId);
                    intent.putExtra("username", username);
                    intent.putExtra("profile", profile);
                    intent.putExtra("nombre", nombreLogin);
                    intent.putExtra("delegacionId", delegacionId);
                    intent.putExtra("activo", activo);
                    Toast.makeText(Infracciones.this, "Infracción creada con identificador : " + mensaje, Toast.LENGTH_LONG).show();


                    finish();
                    startActivity(intent);
                }
            });
            dialogo.show();
        }
    }

    private void cargarCancelacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(Infracciones.this);
        dialogo.setTitle("GENERA INFRACCION");
        dialogo.setMessage("La infracción no se  genero correctamente");


        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);

                // Toast.makeText(Infracciones.this, "##1"+response, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),Drawer.class);
                intent.putExtra("usersId",usersId);
                intent.putExtra("username",username);
                intent.putExtra("profile",profile);
                intent.putExtra("nombre",nombreLogin);
                intent.putExtra("delegacionId",delegacionId);
                intent.putExtra("activo",activo);
                finish();
                startActivity(intent);

                finish();
                startActivity(intent);
            }
        });
        dialogo.show();
    }


    private void cargarAceptacionWarning() {



            AlertDialog.Builder dialogo = new AlertDialog.Builder(Infracciones.this);
            dialogo.setTitle("WARNING INFRACCIONES");

            if (placa == "null") {
                mensaje = licenciaWs;
                dialogo.setMessage("LA INFRACCION SE GENERO CORRECTAMENTE CON LICENCIA : " + mensaje);
            } else {
                mensaje = placa;
                dialogo.setMessage("LA INFRACCION SE GENERO CORRECTAMENTE CON PLACA : " + mensaje);
            }


            dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);

                    // Toast.makeText(Infracciones.this, "##1"+response, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Drawer.class);
                    intent.putExtra("usersId", usersId);
                    intent.putExtra("username", username);
                    intent.putExtra("profile", profile);
                    intent.putExtra("nombre", nombreLogin);
                    intent.putExtra("delegacionId", delegacionId);
                    intent.putExtra("activo", activo);
                    Toast.makeText(Infracciones.this, "Warning creado  con PLACA : " + mensaje, Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(intent);
                }
            });
            dialogo.show();

    }


    private void cargarCancelacionWarning() {


            AlertDialog.Builder dialogo = new AlertDialog.Builder(Infracciones.this);
            dialogo.setTitle("WARNING INFRACCIONES");
            dialogo.setMessage("EL WARNING NO SE GENERO");


            dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);


                    // Toast.makeText(Infracciones.this, "##1"+response, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), Drawer.class);
                    intent.putExtra("usersId", usersId);
                    intent.putExtra("username", username);
                    intent.putExtra("profile", profile);
                    intent.putExtra("nombre", nombreLogin);
                    intent.putExtra("delegacionId", delegacionId);
                    intent.putExtra("activo", activo);
                    finish();
                    startActivity(intent);

                }
            });
            dialogo.show();
        }


    public void onclick(View view) {
        count++;
        cargarImagen();



    }

    private void cargarImagen() {

        final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(Infracciones.this);
        alertOpciones.setTitle("Seleccione una Opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    if (count == 1){
                        //Aqui puedo llammar un nuevo metodo para otra imagen.
                        Log.d("CONTADOR","###### ESTE ES EL VALOR DEL CONTADOR"+count);
                        tomarFotografia();
                    }
                    if (count == 2){
                        //Aqui puedo llammar un nuevo metodo para otra imagen.
                        Log.d("CONTADOR","###### ESTE ES EL VALOR DEL CONTADOR"+count);
                        tomarFotografia2();
                    }
                    if (count == 3){
                        //Aqui puedo llammar un nuevo metodo para otra imagen.
                        Log.d("CONTADOR","###### ESTE ES EL VALOR DEL CONTADOR"+count);
                        tomarFotografia3();
                    }
                }else{
                    if (opciones[i].equals("Cargar Imagen")){

                        if (count == 1){
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), COD_SELECCIONA);

                        }
                        if (count == 2){
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), COD_SELECCIONA);

                        }
                        if (count == 3){
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), COD_SELECCIONA);

                        }


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
            String authorities=getApplicationContext().getPackageName()+".provider";
            Uri imageUri= FileProvider.getUriForFile(this,authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else
        {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent,COD_FOTO);

        ////
    }

    private void tomarFotografia2() {
        File fileImagen=new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
        boolean isCreada=fileImagen.exists();
        String nombreImagen="";
        if(isCreada==false){
            isCreada=fileImagen.mkdirs();
        }

        if(isCreada==true){
            nombreImagen=(System.currentTimeMillis()/1000)+".jpg";
        }


        path2=Environment.getExternalStorageDirectory()+
                File.separator+RUTA_IMAGEN+File.separator+nombreImagen;

        File imagen=new File(path2);


        Intent intent=null;
        intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ////
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
        {
            String authorities=getApplicationContext().getPackageName()+".provider";
            Uri imageUri= FileProvider.getUriForFile(this,authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else
        {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent,COD_FOTO);

        ////
    }

    private void tomarFotografia3() {
        File fileImagen=new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
        boolean isCreada=fileImagen.exists();
        String nombreImagen="";
        if(isCreada==false){
            isCreada=fileImagen.mkdirs();
        }

        if(isCreada==true){
            nombreImagen=(System.currentTimeMillis()/1000)+".jpg";
        }


        path3=Environment.getExternalStorageDirectory()+
                File.separator+RUTA_IMAGEN+File.separator+nombreImagen;

        File imagen=new File(path3);


        Intent intent=null;
        intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ////
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
        {
            String authorities=getApplicationContext().getPackageName()+".provider";
            Uri imageUri= FileProvider.getUriForFile(this,authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else
        {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent,COD_FOTO);

        ////
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){

            switch (requestCode){
                case COD_SELECCIONA:

                    if (count == 1){
                        miPath=data.getData();
                        imagen.setImageURI(miPath);

                    }
                    if (count == 2){
                        miPath1=data.getData();
                        imagen2.setImageURI(miPath1);

                    }
                    if (count == 3){
                        miPath2=data.getData();
                        imagen3.setImageURI(miPath2);
                    }

                    break;

                case COD_FOTO:
                    if (count == 1){

                        MediaScannerConnection.scanFile(this, new String[]{path}, null,
                                new MediaScannerConnection.OnScanCompletedListener() {
                                    @Override
                                    public void onScanCompleted(String path, Uri uri) {
                                        Log.i("Ruta de almacenamiento","Path: "+path);
                                    }
                                });


                        bitmap = BitmapFactory.decodeFile(path);
                        imagen.setImageBitmap(bitmap);

                    }
                    if (count == 2){


                        MediaScannerConnection.scanFile(this, new String[]{path2}, null,
                                new MediaScannerConnection.OnScanCompletedListener() {
                                    @Override
                                    public void onScanCompleted(String path, Uri uri) {
                                        Log.i("Ruta de almacenamiento","Path: "+path);
                                    }
                                });
                        bitmap2= BitmapFactory.decodeFile(path2);
                        imagen2.setImageBitmap(bitmap2);
                    }
                    if (count == 3){


                        MediaScannerConnection.scanFile(this, new String[]{path3}, null,
                                new MediaScannerConnection.OnScanCompletedListener() {
                                    @Override
                                    public void onScanCompleted(String path, Uri uri) {
                                        Log.i("Ruta de almacenamiento","Path: "+path);
                                    }
                                });
                        bitmap3= BitmapFactory.decodeFile(path3);
                        imagen3.setImageBitmap(bitmap3);

                    }

                    break;
            }


        }
    }


    //Subir imagen

    public void uploadImageWarning() {
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.hide();
                        cargarAceptacionWarning();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.hide();
                cargarCancelacionWarning();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String comentarios = edtComentarios.getText().toString().trim();
                String folio = edtFolio.getText().toString().trim();
                // Log.d("imagen","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+imagen);
                Log.d("imagen","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+imagen);
                Map<String, String> params = new Hashtable<String, String>();

                if (bitmap!=null){
                    imagenB = convertirImgString(bitmap);
                    params.put("foto", imagenB);
                }else{

                    imagen.buildDrawingCache();
                    Bitmap bitmap = imagen.getDrawingCache();
                    ByteArrayOutputStream stream=new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 20, stream);
                    byte[] image=stream.toByteArray();
                    String img_str = Base64.encodeToString(image, 0);
                    params.put("foto", img_str);
                }

                if (bitmap2!=null){
                    imagenB2 = convertirImgString2(bitmap2);
                    params.put("foto2", imagenB2);
                }else{
                    imagen2.buildDrawingCache();
                    Bitmap bitmap2 = imagen2.getDrawingCache();
                    ByteArrayOutputStream stream=new ByteArrayOutputStream();
                    bitmap2.compress(Bitmap.CompressFormat.PNG, 20, stream);
                    byte[] image=stream.toByteArray();
                    String img_str2 = Base64.encodeToString(image, 0);
                    params.put("foto2", img_str2);
                }
                if (bitmap3!=null){
                    imagenB3 = convertirImgString3(bitmap3);
                    params.put("foto3", imagenB3);
                }else{
                    imagen3.buildDrawingCache();
                    Bitmap bitmap3 = imagen3.getDrawingCache();
                    ByteArrayOutputStream stream=new ByteArrayOutputStream();
                    bitmap3.compress(Bitmap.CompressFormat.PNG, 20, stream);
                    byte[] image=stream.toByteArray();
                    String img_str3 = Base64.encodeToString(image, 0);
                    params.put("foto3", img_str3);
                }



                params.put("usersId", usersId);
                Log.d("CARGA-IFRN-1","Valor licencia antes de cargar Infraccion : "+ usersId);
                params.put("warning","true");
                params.put("username", username);
                Log.d("CARGA-IFRN-3","Valor licencia antes de cargar Infraccion : "+ username);
                params.put("profile", profile);
                Log.d("CARGA-IFRN-4","Valor licencia antes de cargar Infraccion : "+ profile);
                params.put("nombreLogin", nombreLogin);
                Log.d("CARGA-IFRN-5","Valor licencia antes de cargar Infraccion : "+ nombreLogin);
                params.put("delegacionId", delegacionId);
                Log.d("CARGA-IFRN-6","Valor licencia antes de cargar Infraccion : "+ delegacionId);
                params.put("activo", activo);
                Log.d("CARGA-IFRN-7","Valor licencia antes de cargar Infraccion : "+ activo);


                try {
                    if (propietario==null){
                        params.put("propietario","Sin propietario");
                        params.put("placa","SIN-PLACA");
                    }else{
                        params.put("propietario",propietario);
                        params.put("placa",placa);

                        params.put("vigencia",vigencia);
                        Log.d("CARGA-IFRN-10","Valor licencia antes de cargar Infraccion : "+ vigencia);

                        Log.d("CARGA-IFRN-8","Valor licencia antes de cargar Infraccion : "+ placa);
                        Log.d("CARGA-IFRN-9","Valor licencia antes de cargar Infraccion : "+ propietario);
                    }
                }catch (Exception e){
                    progressDialog.hide();
                }


                try{
                    params.put("num",licenciaWs);
                    Log.d("CARGA-IFRN-1","Valor licencia antes de cargar Infraccion : "+ licenciaWs);
                    params.put("nombreL",nombreCompletoLicenciaWs);
                    Log.d("CARGA-IFRN-12","Valor licencia antes de cargar Infraccion : "+ nombreCompletoLicenciaWs);
                    params.put("fvl",vencimientoLicenciaWs);
                    Log.d("CARGA-IFRN-13","Valor licencia antes de cargar Infraccion : "+ vencimientoLicenciaWs);
                }catch(Exception e){
                    params.put("num","NO-LICENCIA");
                    Log.d("CARGA-IFRN-1","Valor licencia antes de cargar Infraccion : "+ licenciaWs);
                    params.put("nombreL","NO-LICENCIA");
                    Log.d("CARGA-IFRN-12","Valor licencia antes de cargar Infraccion : "+ nombreCompletoLicenciaWs);
                    params.put("fvl","NO-LICENCIA");
                    params.put("fvl","NO-LICENCIA");
                    Log.d("CARGA-IFRN-13","Valor licencia antes de cargar Infraccion : "+ vencimientoLicenciaWs);
                }


                //Infra
                if (cuenta.equals("1")){
                    infra2 = "-";
                    infra3 = "-";
                    infra4 = "-";
                    infra5 = "-";
                }
                if (cuenta.equals("2")){
                    infra3 = "-";
                    infra4 = "-";
                    infra5 = "-";
                }
                if (cuenta.equals("3")){
                    infra4 = "-";
                    infra5 = "-";
                }
                if (cuenta.equals("4")){
                    infra5 = "-";
                }
                if (cuenta.equals("5")){

                }
                infraConcat = infra1+" | "+infra2+" | "+infra3+" | "+infra4+" | "+infra5;
                params.put("infra",infraConcat);
                Log.d("CARGA-IFRN-14","Valor licencia antes de cargar Infraccion : "+ infraConcat);
                params.put("cuenta",cuenta);
                Log.d("CARGA-IFRN-15","Valor licencia antes de cargar Infraccion : "+ cuenta);

                params.put("comentarios",comentarios);
                Log.d("CARGA-IFRN-16","Valor licencia antes de cargar Infraccion : "+ comentarios);
                params.put("folio",folio);
                Log.d("CARGA-IFRN-17","Valor licencia antes de cargar Infraccion : "+ folio);

                if(sectorId != null){
                    params.put("sectorId",sectorId);
                    Log.d("CARGA-IFRN-18","Valor licencia antes de cargar Infraccion : "+ sectorId);
                }else{
                    params.put("sectorId","99");
                }
                String latitudS = Double.toString(latitude);
                Log.d("CARGA-IFRN-19","Valor licencia antes de cargar Infraccion : "+ latitudS);
                String longitudS = Double.toString(longitud);
                Log.d("CARGA-IFRN-20","Valor licencia antes de cargar Infraccion : "+ longitudS);

                params.put("latitud",latitudS);
                params.put("longitud",longitudS);




                if (ECONOMICO!=null){
                    params.put("numeroEconomico",ECONOMICO);
                }else{
                    params.put("numeroEconomico","SIN/NUMERO");
                }
                Calendar calendar = Calendar.getInstance();
                int numberWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
                numberWeekOfYear = numberWeekOfYear-1;
                int year = Calendar.getInstance().get(Calendar.YEAR);

                String semana=String.valueOf(numberWeekOfYear);
                String anio=String.valueOf(year);

                params.put("semana",semana);
                params.put("anio",anio);
                params.put("modalidad",modalidad);



                params.put("vim",vim);


                if (color!=null){
                    params.put("color",color);
                }else{
                    params.put("color","NO APLICA");
                }

                if (agrupacion!=null){
                    params.put("agrupacion",agrupacion);
                }else{
                    params.put("agrupacion","NO APLICA");
                }

                if (rutaSitio!=null){
                    params.put("rutaSitio",rutaSitio);
                }else{
                    params.put("rutaSitio","NO APLICA");
                }



                if (modi != null){
                    params.put("modi",modi);
                }else{
                    params.put("modi","OTRO-SIN-REGISTRO");
                }




                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    //SUBIR INFRACCION
    public void cargarInfraccion() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.show();
                        if (edtFolio.getText().toString().trim().equals(null)){
                            AlertDialog.Builder dialogo=new AlertDialog.Builder(Infracciones.this);
                            dialogo.setTitle("FOLIO VACIO");
                            dialogo.setMessage("EL CAMPO FOLIO ES OBLIGATORIO");


                            dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            progressDialog.hide();
                            dialogo.show();
                        }else{
                            progressDialog.hide();
                            cargarAceptacion();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.hide();
                //CUANDO FALLA LA CAERGA DE LA INFRACCION
                cargarCancelacion();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String comentarios = edtComentarios.getText().toString().trim();
                String folio = edtFolio.getText().toString().trim();
                // Log.d("imagen","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+imagen);
                Log.d("CARGA-IFRN-1","Valor de la Imagen: "+imagen);
                Map<String, String> params = new Hashtable<String, String>();

                if (bitmap!=null){
                    imagenB = convertirImgString(bitmap);
                    params.put("foto", imagenB);
                }else{

                    imagen.buildDrawingCache();
                    Bitmap bitmap = imagen.getDrawingCache();
                    ByteArrayOutputStream stream=new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 20, stream);
                    byte[] image=stream.toByteArray();
                    String img_str = Base64.encodeToString(image, 0);
                    params.put("foto", img_str);
                }

                if (bitmap2!=null){
                    imagenB2 = convertirImgString2(bitmap2);
                    params.put("foto2", imagenB2);
                }else{
                    imagen2.buildDrawingCache();
                    Bitmap bitmap2 = imagen2.getDrawingCache();
                    ByteArrayOutputStream stream=new ByteArrayOutputStream();
                    bitmap2.compress(Bitmap.CompressFormat.PNG, 20, stream);
                    byte[] image=stream.toByteArray();
                    String img_str2 = Base64.encodeToString(image, 0);
                    params.put("foto2", img_str2);
                }
                if (bitmap3!=null){
                    imagenB3 = convertirImgString3(bitmap3);
                    params.put("foto3", imagenB3);
                }else{
                    imagen3.buildDrawingCache();
                    Bitmap bitmap3 = imagen3.getDrawingCache();
                    ByteArrayOutputStream stream=new ByteArrayOutputStream();
                    bitmap3.compress(Bitmap.CompressFormat.PNG, 20, stream);
                    byte[] image=stream.toByteArray();
                    String img_str3 = Base64.encodeToString(image, 0);
                    params.put("foto3", img_str3);
                }
                params.put("usersId", usersId);
                Log.d("CARGA-IFRN-1","Valor licencia antes de cargar Infraccion : "+ usersId);
                params.put("username", username);
                Log.d("CARGA-IFRN-3","Valor licencia antes de cargar Infraccion : "+ username);
                params.put("profile", profile);
                Log.d("CARGA-IFRN-4","Valor licencia antes de cargar Infraccion : "+ profile);
                params.put("nombreLogin", nombreLogin);
                Log.d("CARGA-IFRN-5","Valor licencia antes de cargar Infraccion : "+ nombreLogin);
                params.put("delegacionId", delegacionId);
                Log.d("CARGA-IFRN-6","Valor licencia antes de cargar Infraccion : "+ delegacionId);
                params.put("activo", activo);
                Log.d("CARGA-IFRN-7","Valor licencia antes de cargar Infraccion : "+ activo);


                try {
                    if (propietario==null){
                        params.put("propietario","Sin propietario");
                        params.put("placa","SIN-PLACA");
                    }else{
                        params.put("propietario",propietario);
                        params.put("placa",placa);

                        params.put("vigencia",vigencia);
                        Log.d("CARGA-IFRN-10","Valor licencia antes de cargar Infraccion : "+ vigencia);

                        Log.d("CARGA-IFRN-8","Valor licencia antes de cargar Infraccion : "+ placa);
                        Log.d("CARGA-IFRN-9","Valor licencia antes de cargar Infraccion : "+ propietario);
                    }
                }catch (Exception e){
                    progressDialog.hide();
                }
                params.put("num",licenciaWs);
                Log.d("CARGA-IFRN-1","Valor licencia antes de cargar Infraccion : "+ licenciaWs);
                params.put("nombreL",nombreCompletoLicenciaWs);
                Log.d("CARGA-IFRN-12","Valor licencia antes de cargar Infraccion : "+ nombreCompletoLicenciaWs);
                params.put("fvl",vencimientoLicenciaWs);
                Log.d("CARGA-IFRN-13","Valor licencia antes de cargar Infraccion : "+ vencimientoLicenciaWs);
                //Infra
                if (cuenta.equals("1")){
                    infra2 = "-";
                    infra3 = "-";
                    infra4 = "-";
                    infra5 = "-";
                }
                if (cuenta.equals("2")){
                    infra3 = "-";
                    infra4 = "-";
                    infra5 = "-";
                }
                if (cuenta.equals("3")){
                    infra4 = "-";
                    infra5 = "-";
                }
                if (cuenta.equals("4")){
                    infra5 = "-";
                }
                if (cuenta.equals("5")){

                }
                infraConcat = infra1+" | "+infra2+" | "+infra3+" | "+infra4+" | "+infra5;
                params.put("infra",infraConcat);
                Log.d("CARGA-IFRN-14","Valor licencia antes de cargar Infraccion : "+ infraConcat);
                params.put("cuenta",cuenta);
                Log.d("CARGA-IFRN-15","Valor licencia antes de cargar Infraccion : "+ cuenta);

                params.put("comentarios",comentarios);
                Log.d("CARGA-IFRN-16","Valor licencia antes de cargar Infraccion : "+ comentarios);
                params.put("folio",folio);
                Log.d("CARGA-IFRN-17","Valor licencia antes de cargar Infraccion : "+ folio);

                if(sectorId != null){
                    params.put("sectorId",sectorId);
                    Log.d("CARGA-IFRN-18","Valor licencia antes de cargar Infraccion : "+ sectorId);
                }else{
                    params.put("sectorId","99");
                }
                String latitudS = Double.toString(latitude);
                Log.d("CARGA-IFRN-19","Valor licencia antes de cargar Infraccion : "+ latitudS);
                String longitudS = Double.toString(longitud);
                Log.d("CARGA-IFRN-20","Valor licencia antes de cargar Infraccion : "+ longitudS);

                params.put("latitud",latitudS);
                params.put("longitud",longitudS);

                if (ECONOMICO!=null){
                    params.put("numeroEconomico",ECONOMICO);
                }else{
                    params.put("numeroEconomico","SIN/NUMERO");
                }
                Calendar calendar = Calendar.getInstance();
                int numberWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
                numberWeekOfYear = numberWeekOfYear-1;
                int year = Calendar.getInstance().get(Calendar.YEAR);

                String semana=String.valueOf(numberWeekOfYear);
                String anio=String.valueOf(year);

                params.put("semana",semana);
                params.put("anio",anio);
                params.put("modalidad",modalidad);
                params.put("vim",vim);
                if (color!=null){
                    params.put("color",color);
                }else{
                    params.put("color","NO APLICA");
                }

                if (agrupacion!=null){
                    params.put("agrupacion",agrupacion);
                }else{
                    params.put("agrupacion","NO APLICA");
                }

                if (rutaSitio!=null){
                    params.put("rutaSitio",rutaSitio);
                }else{
                    params.put("rutaSitio","NO APLICA");
                }



                if (modi != null){
                    params.put("modi",modi);
                }else{
                    params.put("modi","OTRO-SIN-REGISTRO");
                }



                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private String convertirImgString(Bitmap bitmap) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,array);
        byte[] imagenByte=array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte,Base64.DEFAULT);
        return imagenString;
    }

    private String convertirImgString2(Bitmap bitmap2) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.JPEG,50,array);
        byte[] imagenByte=array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte,Base64.DEFAULT);
        return imagenString;
    }
    private String convertirImgString3(Bitmap bitmap3) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap3.compress(Bitmap.CompressFormat.JPEG,50,array);
        byte[] imagenByte=array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte,Base64.DEFAULT);
        return imagenString;
    }


    //Elimnar contenido cuando se regrese a la pantalla anterior.
    @Override
    public void onBackPressed()
    {
        //Para llamarse asi mismo y ejecutarse
        super.onBackPressed();

        //Terminando la actividad en curso
        this.finish();
        //Regresando a la actividad principal
        Log.d("Back1","Entre en el BACKPRESSED");
        Intent gotoBack = new Intent(Infracciones.this, Drawer.class);

        gotoBack.putExtra("usersId",usersId);
        gotoBack.putExtra("username",username);
        gotoBack.putExtra("profile",profile);
        gotoBack.putExtra("nombre",nombreLogin);
        gotoBack.putExtra("delegacionId",delegacionId);
        gotoBack.putExtra("activo",activo);
        gotoBack.putExtra("licencia","");
        if (propietario==null){
            gotoBack.putExtra("placa","");
        }else{
            gotoBack.putExtra("placa",placa);
        }

        //gotoBack.putExtra(USER_GLOBAL_SENDER, username_global); <-- Use this if you want to carry some data to the other activity.
        finish();
        startActivity(gotoBack);

    }


    public void onMapReady(GoogleMap map) {
        // Add some markers to the map, and add a data object to each marker.
        try {

            LatLng INFRACCIONESWS = new LatLng(latitude, longitud);
            markerInfraacion = map.addMarker(new MarkerOptions().position(INFRACCIONESWS).title("Perth"));
            map.moveCamera(CameraUpdateFactory.newLatLng(INFRACCIONESWS));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(INFRACCIONESWS,17));
            markerInfraacion.setTag(0);


            // Set a listener for marker click.
            map.setOnMarkerClickListener(this);
        }catch (Exception e){

        }


    }

    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        return false;
    }




}