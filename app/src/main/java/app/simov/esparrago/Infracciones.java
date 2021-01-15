package app.simov.esparrago;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Infracciones extends AppCompatActivity{

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
    TextView mensaje1;
    TextView mensaje2;
    TextView placaInfracciones;
    TextView estatusPlacaInfracciones;
    TextView tvVigenciaTcInfraccion;
    TextView tvVimInfraccion;
    TextView tvInfraInfraccion;
    String   modalidad;
    String   infra1;
    String   infra2;
    String   infra3;
    String   infra4;
    String   infra5;
    String   infraConcat;
    String   cuenta;
    String   sector;
    TextView tvModalidadInfraccion;
    TextView tvSectorInfraccion;

    EditText edtComentarios;


    String UPLOAD_URL = "https://simov.app/servicios/insertaInfraccion.php";
    String URLICENCIA = "https://simov.app/servicios/consultaLicencia.php";
    String URLVEHICULAR = "https://simov.app/servicios/controlVehicular.php";
    String URLINFRACCION = "https://simov.app/servicios/consultaInfraccion.php";

    private final String CARPETA_RAIZ="misImagenesPrueba/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"misFotos";

    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    Button botonCargar;
    Button botonCargar2;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infracciones);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Layouts de las imagenes
        imagen= (ImageView) findViewById(R.id.imagemId);
        imagen2= (ImageView) findViewById(R.id.imagemId2);
        imagen3= (ImageView) findViewById(R.id.imagemId3);
        //Boton que lanza el AlertDialog.
        botonCargar= (Button) findViewById(R.id.btnCargarImg);
        botonCargar2= (Button) findViewById(R.id.btnCargarImg2);

        //Metodos de Ws.
        enviarWSConsultaLicencia(URLICENCIA);
         enviarWSControlVehichular(URLVEHICULAR);
        // enviarWSConsultaInfraccion(URLINFRACCION);

        //Seteamos los valores de los Textviews.
        textViewLicencia = findViewById(R.id.tvLicenciaInfraccion);
        textViewFechaVencimiento = findViewById(R.id.tvFechaVencimientoInfraccion);
        placaInfracciones = findViewById(R.id.tvPlacaInfraccion);
        estatusPlacaInfracciones = findViewById(R.id.tvEstatusInfracciones);
        tvVigenciaTcInfraccion = findViewById(R.id.tvVigenciaTcInfraccion);
        tvVimInfraccion = findViewById(R.id.tvVimInfraccion);
        //tvInfraInfraccion = findViewById(R.id.tvInfraInfraccion);
        tvModalidadInfraccion = findViewById(R.id.tvModalidadInfraccion);
        tvSectorInfraccion = findViewById(R.id.tvSector);
        tvInfraInfraccion = findViewById(R.id.tvInfra);
        edtComentarios = findViewById(R.id.edtComentarios);

        //Bundle de actividad anterior
        Bundle bundle  = getIntent().getExtras();
        Log.d("BUNDLE","VALOR DEL BUNDLE ##############"+bundle.toString());
        //Validamos que no venga vacio
        if (bundle != null){
            //Recojemos parametros que vienen desde el LOGIN.
            usersId  = bundle.getString("usersId");
            Log.d("USERSSSSSSSS","#####################&%&%&%&%&%&%&%&%&USERSSSSSSSS"+usersId);
            username  = bundle.getString("username");
            profile  = bundle.getString("profile");
            nombreLogin  = bundle.getString("nombre");
            delegacionId  = bundle.getString("delegacionId");
            activo  = bundle.getString("activo");

            //Recojemos parametros.
            placa = bundle.getString("placa");
            Log.d("###PLACASSS","#######"+placa);
            placaInfracciones.setText(placa);
            estatus = bundle.getString("estatus");
            propietario = bundle.getString("propietario");
            Log.d("PROPIETARIO","#####################&%&%&%&%&%&%&%&%&PROPIETARIO"+propietario);
            vigencia = bundle.getString("vigencia");
            vim = bundle.getString("vim");
            marca = bundle.getString("marca");
            infracciones = bundle.getString("infracciones");
            licencia = bundle.getString("licencia");
            modalidad = bundle.getString("modalidad");
            sector = bundle.getString("sector");



/*
* || sector == "PONIENTE-TURISTICO" ||
                    sector == "PONIENTE-CENTRO" || sector == "PONIENTE-SALIDA TIJUANA" || sector == "ORIENTE/LIBRAMIENTO" || sector == "ORIENTE/CORTEZ" || sector == "ORIENTE/ESMERALDA" || sector == "SUR/PLAYAS HERMOSA" ||
                    sector == "SUR/GOBIERNO" || sector == "SUR/CHAPULTEPEC" || sector == "SUR/VILLAS" || sector == "FORANEO/MANEADERO BAJA" || sector == "FORANEO/MANEADERO ALTA" || sector == "FORANEO/BUFADORA" ||
                    sector == "FORANEO/VALLE GPE"
*
*
*
*<item>PONIENTE-TURISTICO</item>
        <item>PONIENTE-CENTRO</item>
        <item>PONIENTE-SALIDA TIJUANA</item>
        <item>ORIENTE/PRESA</item>
        <item>ORIENTE/LIBRAMIENTO</item>
        <item>ORIENTE/CORTEZ</item>
        <item>ORIENTE/ESMERALDA</item>
        <item>SUR/PLAYAS HERMOSA</item>
        <item>SUR/GOBIERNO</item>
        <item>SUR/CHAPULTEPEC</item>
        <item>SUR/VILLAS</item>
        <item>FORANEO/MANEADERO BAJA</item>
        <item>FORANEO/MANEADERO ALTA</item>
        <item>FORANEO/BUFADORA</item>
        <item>FORANEO/VALLE GPE</item>
*
* * */
            Log.d("SECOTR","$$%$%$%$%$%$%$"+sector);

            if(sector.equals("NORTE-CENTRO") || sector.equals("NORTE-OTAy") || sector.equals("NORTE-AGUA-CALIENTE") || sector.equals(">NORTE-5 Y 10") || sector.equals("SUR-NATURA") || sector.equals("SUR-BLV-BENITEZ") ||
                    sector.equals("SUR-DIAZ-ORDAZ") || sector.equals("ESTE-CARR-TECATE") || sector.equals("ESTE-LA-PRESA") || sector.equals("ESTE-INSURGENTES") || sector.equals("ESTE-FLORIDO") || sector.equals("OESTE-PACIFICO") ||
                    sector.equals("OESTE-SANTE-FE") || sector.equals("PERIFERIA-PLAYAS") || sector.equals("PERIFERIA-SOLER") || sector.equals("PERIFERIA-ROSARITO") || sector.equals("PERIFERIA-TECATE")){

                    sectorId = "2";

            }if(sector.equals("PONIENTE-TURISTICO") || sector.equals("PONIENTE-CENTRO") || sector.equals("PONIENTE-SALIDA-TIJUANA") || sector.equals("ORIENTE/LIBRAMIENTO") || sector.equals("ORIENTE/CORTEZ") ||
                    sector.equals("ORIENTE/ESMERALDA") || sector.equals("SUR/PLAYAS/HERMOSA") || sector.equals("SUR/GOBIERNO") || sector.equals("SUR/CHAPULTEPEC") || sector.equals("SUR/VILLAS") ||
                    sector.equals("FORANEO/MANEADERO/BAJA") || sector.equals("FORANEO/MANEADERO/ALTA") || sector.equals("FORANEO/BUFADORA") || sector.equals("FORANEO/VALLE/GPE")){

                     sectorId = "3";
        } else{
                sectorId = "0";
            }


            infra1 = bundle.getString("infra1");
            Log.d("INFRACCION1-2-3","#######################========>>>>>"+infra1);
            infra2 = bundle.getString("infra2");
            infra3 = bundle.getString("infra3");
            infra4 = bundle.getString("infra4");
            infra5 = bundle.getString("infra5");
            cuenta = bundle.getString("cuenta");

            Log.d("INFRA1","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+infra1);
            Log.d("INFRA2","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+infra2);
            Log.d("INFRA3","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+infra3);
            Log.d("INFRA4","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+infra4);
            Log.d("INFRA5","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+infra5);

            Log.d("CUENTA$$$","$$$$$$$$$$$$$$$$$%$%#######################"+cuenta);




            Log.d("LICENCIA-VERGAS1","$$$$$$$$$$$$$$$"+licencia);
            nombre = bundle.getString("nnombre");
            fechaVecimiento = bundle.getString("fechaVencimiento");

            tvModalidadInfraccion.setText(modalidad);
            tvSectorInfraccion.setText(sector);
            tvInfraInfraccion.setText(infra1);

            Log.d("MODALIDAD","&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+modalidad);




        }

        //ENVIAR INFO

        botonCargar2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                uploadImage();
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


    //Consulta Licencia.
    private void enviarWSConsultaLicencia(String URLICENCIA) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLICENCIA, new Response.Listener<String>() {
            @Override
            //Para mandar un post aun WS el response Listener tiene que ser de tipo  String , y despues convertir la respuesta a JsonObject.
            public void onResponse(String response) {
                //Validamos que el response no este vacio
                if (!response.isEmpty()) {
                    //Esto contiene toda la cadena de respuesta del Ws.
                    Toast.makeText(Infracciones.this, "SE MANDO PETICION CORRECTA A WS LICENCIA" + response, Toast.LENGTH_LONG).show();

                    try {
                        //Convertimos el String en JsonObject
                        JSONObject obj = new JSONObject(response);
                        Log.d("objLicencia", "###Respuesta WS licencia" + obj.toString());
                        //Accedemos al valor del Objeto deseado completo.tos


                        if (obj.has("data")){
                            JSONArray jsonarray = obj.getJSONArray("data");
                            //Obtenemos el total de elementos del objeto
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                //Accedemos a los elementos por medio de getString.
                                LICENCIA = jsonobject.getString("licencia");
                                VENCIMIENTO = jsonobject.getString("fechaVenc");
                                String paterno = jsonobject.getString("paterno");
                                String materno = jsonobject.getString("materno");
                                String nombre  = jsonobject.getString("nombre");

                                NOMBRECOMPLETO = nombre+" "+paterno+" "+materno;

                                //textViewNombre.setText(nombreCompleto);
                                textViewLicencia.setText(LICENCIA);
                                textViewFechaVencimiento.setText(VENCIMIENTO);


                            }
                        }else{
                            //textViewNombre.setText(nombreCompleto);
                            textViewLicencia.setText("NO-LICENCIA");
                            textViewFechaVencimiento.setText("NO-LICENCIA");
                            LICENCIA = "NO-LICENCIA";
                            VENCIMIENTO = "NO-LICENCIA";
                            NOMBRECOMPLETO = "NO-LICENCIA";
                        }





                    } catch (JSONException e) {
                        e.printStackTrace();
                        //Seteamos valor cuando no se ingrese licencia
                       //* textViewNombre.setText("NO-LICENCIA");
                        textViewLicencia.setText("NO-LICENCIA");
                        textViewFechaVencimiento.setText("NO-LICENCIA");
                        LICENCIA = "NO-LICENCIA";
                        VENCIMIENTO = "NO-LICENCIA";
                        NOMBRECOMPLETO = "NO-LICENCIA";

                    }

                } else {

                    Toast.makeText(Infracciones.this, "No se encontraron parametros en la consulta de licencia", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Seteamos valor cuando sobre pasa el tiempo de esepera
                //*textViewNombre.setText("LIMITE DE ESPERA");
                textViewLicencia.setText("LIMITE DE ESPERA");
                textViewFechaVencimiento.setText("LIMITE DE ESPERA");
                Toast.makeText(Infracciones.this, error.toString(), Toast.LENGTH_LONG).show();
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
        RequestQueue requesrQueue = Volley.newRequestQueue(Infracciones.this);
        requesrQueue.add(stringRequest);
    }

    //Consulta de placas.

    private void enviarWSControlVehichular(String URLVEHICULAR) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLVEHICULAR, new Response.Listener<String>() {
            @Override
            //Para mandar un post aun WS el response Listener tiene que ser de tipo  String , y despues convertir la respuesta a JsonObject.
            public void onResponse(String response) {
                //Validamos que el response no este vacio
                if (!response.isEmpty()) {
                    //Esto contiene toda la cadena de respuesta del Ws.
                    Toast.makeText(Infracciones.this, "CONSULTA" + response, Toast.LENGTH_LONG).show();

                    try {
                        //Convertimos el String en JsonObject
                        JSONObject obj = new JSONObject(response);
                        Log.d("objVehicular", "###Respuesta WS padron vehicular" + obj.toString());
                        //Accedemos al valor del Objeto deseado completo.
                        JSONArray jsonarray = obj.getJSONArray("data");

                        Log.w("jARRAYWSINFRACCIONES","#####$$$$$$$ QUE TIENE EL ARRAY?"+jsonarray.toString());



                        Log.d("SERVICIOWs","$$$$$$$$$$$$$$$$$$$$$$ Estamos entrando al ELSE de WS ..");
                        //Obtenemos el total de elementos del objeto.
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            //Accedemos a los elementos por medio de getString.


                            String PLACA = jsonobject.getString("placa");
                            placaInfracciones.setText(PLACA);
                            Log.d("###Placas-----", "Esto es lo que llega de plcas de ws infracciones" + PLACA);

                            Boolean validaEstatus = jsonobject.has("estatus");
                            Log.d("BOOLEAN####","ESTATUS VALIDAD ESTATUS"+validaEstatus);
                            Boolean validaFechaVencimiento = jsonobject.has("fechaVencimiento");
                            Log.d("BOOLEAN####","FechaVencimiento valida$$$$$$$$$$$$$$$$$$$$$$$$$"+validaFechaVencimiento);
                            String SERIE = jsonobject.getString("serie");
                            tvVimInfraccion.setText(SERIE);

                            if (validaEstatus== false) {
                                Log.d("FALSE","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                                //Valida si viene de transporte por la etiqueta estatusActuak
                                String ESTATUS = jsonobject.getString("estatusActual");
                                Log.d("VALIDASTATUS","%%%%%%%%%% LO QUE HAY DE ESTATUS TRANSPORTE PUBLICO "+ESTATUS);

                                estatusPlacaInfracciones.setText(ESTATUS);
                                String VIGENCIA = jsonobject.getString("fechaVigencia");
                                tvVigenciaTcInfraccion.setText(VIGENCIA);
                                Log.d("VIGECNCIA33","Esto es lo que trae la vigencia de transporte publico "+VIGENCIA);

                                ECONOMICO = jsonobject.getString("economico");

                            } if(validaEstatus == true){
                                //Valida si viene de transporte particular.
                                String ESTATUS = jsonobject.getString("estatus");
                                estatusPlacaInfracciones.setText(ESTATUS);
                                Log.d("VALIDASTATUS","%%%%%%%%%% LO QUE HAY DE ESTATUS TRANSPORTE PRIVADO "+ESTATUS);
                                String VIGENCIA = jsonobject.getString("fechaVencimiento");
                                tvVigenciaTcInfraccion.setText(VIGENCIA);
                                Log.d("VIGENCIA33","Esto es lo que trae la vigencia de transporte privado "+VIGENCIA);

                            }

                            if (validaFechaVencimiento == false) {
                                //Valida si vine de transporte por la etiqueta fechaVigencia
                                String VIGENCIA = jsonobject.getString("fechaVigencia");


                                Log.d("ESATUS", "### VALOR ESTATUS" + VIGENCIA);
                            } else {
                                //Validad si viene de transporte particular por la etiqueta fechaVecimineto

                            }


                        }
                        //}

                    } catch (JSONException e) {

                    }


                } else {
                    Toast.makeText(getApplicationContext(), "No se encontraron parametros en la consulta", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                Log.d("PLACA33-1","Esto es lo que imprime antes de enviar el post"+placa);
                //Log.d("PLACA33-2","Esto es lo que imprime antes de enviar el post pero del Textview"+);
                parametros.put("placa",placa);



                return parametros;
            }
        };
        RequestQueue requesrQueue = Volley.newRequestQueue(getApplicationContext());
        requesrQueue.add(stringRequest);
    }

    //Subir imagen

    public void uploadImage() {
        final ProgressDialog loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Toast.makeText(Infracciones.this, "##1"+response, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),Drawer.class);
                        intent.putExtra("usersId",usersId);
                        intent.putExtra("username",username);
                        intent.putExtra("profile",profile);
                        intent.putExtra("nombre",nombreLogin);
                        intent.putExtra("delegacionId",delegacionId);
                        intent.putExtra("activo",activo);
                        finish();
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(Infracciones.this, "##2"+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {



                String comentarios = edtComentarios.getText().toString().trim();
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
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
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
                    bitmap2.compress(Bitmap.CompressFormat.PNG, 50, stream);
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
                    bitmap3.compress(Bitmap.CompressFormat.PNG, 50, stream);
                    byte[] image=stream.toByteArray();
                    String img_str3 = Base64.encodeToString(image, 0);
                    params.put("foto3", img_str3);
                }



                params.put("usersId", usersId);
                //Aqui hay un Null pointer Exeption.
                Log.d("USERSIDINFRA","###############%%%%%%%%%%"+usersId);
                params.put("username", username);
                params.put("profile", profile);
                params.put("nombreLogin", nombreLogin);
                params.put("delegacionId", delegacionId);
                params.put("activo", activo);
                params.put("placa",placa);
                params.put("propietario",propietario);
                params.put("vigencia",vigencia);

                //LICENCIA
                params.put("noLicencia",LICENCIA);
                Log.d("LICENCIA","###############%%%%%%%%%%========================>"+LICENCIA);
                params.put("nombreLicencia",NOMBRECOMPLETO);
                params.put("fVigenciaLicencia",VENCIMIENTO);

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
                Log.d("INFRA","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$=======>"+infraConcat);
                params.put("cuenta",cuenta);
                Log.d("CUENTA","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%========>"+cuenta);

                params.put("comentarios",comentarios);

                params.put("sectorId",sectorId);
                if (ECONOMICO!=null){
                    params.put("numeroEconomico",ECONOMICO);
                }else{
                    params.put("numeroEconomico","SIN/NUMERO");
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
        //gotoBack.putExtra(USER_GLOBAL_SENDER, username_global); <-- Use this if you want to carry some data to the other activity.
        finish();
        startActivity(gotoBack);

    }


}