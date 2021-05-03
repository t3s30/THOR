package app.simov.esparrago;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WsgobConsulta extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback{
    Double parserLat;
    Double parserLongt;

    private final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);

    private Marker markerPerth;
    private Marker markerSydney;
    private Marker markerBrisbane;



    String latitud;
    String longitud;

    LatLng infraccionLatLong;
    String placa;
    String placaWS;
    String estatus;
    String propietario;
    String vigencia;
    String vim;
    String marca;
    String economico;
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
    TextView textViewNumeroInfracciones;
    TextView textViewMotivoInfracciones;

    String usersId;
    String username;
    String profile;
    String nombreLogin;
    String delegacionId;
    String activo;

    //

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

//

    TextView tvplacaQR;
    TextView tvserialQR;
    TextView tvdelegacionIdQR;
    TextView tveconomicoQR;
    TextView tvserieQR;
    TextView tvmarcaQR;
    TextView tvmodeloQR;
    TextView tvtipoQR;
    TextView tvcolorQR;
    TextView tvpadronQR;
    TextView tvmodalidadQR;
    TextView tvfechaAltaQR;
    TextView tvprorrogaQR;
    TextView tvfechaProrrojgaQR;
    TextView tvestatusQR;
    TextView tvcoberturaSeguroQR;
    TextView tvvigenciaPolizaQR;
    TextView tvperiodoQR;
    TextView tvobservacionesQR;
    TextView tvrevisionQR;

//

    TextView tvplacarm2;
    TextView tvserialrm2;
    TextView tveconomicorm2;
    TextView tvserierm2;
    TextView tvmarcarm2;
    TextView tvmodelorm2;
    TextView tvtiporm2;
    TextView tvcolorrm2;
    TextView tvpadronrm2;
    TextView tvmodalidadrm2;
    TextView tvfechaAltarm2;
    TextView tvprorrogarm2;
    TextView tvfechaProrrojgarm2;
    TextView tvestatusrm2;
    TextView tvcoberturaSegurorm2;
    TextView tvvigenciaPolizarm2;
    TextView tvperiodorm2;
    TextView tvobservacionesrm2;
    TextView tvrevisionrm2;

    //
    TextView tvplacaFolio;
    TextView tvfoliofolio;
    TextView tvdelegacionFolio;
    TextView tvnombrePlataformaFolio;
    TextView tvnumeroPolizaFolio;
    TextView tvnombrePropietarioFolio;
    TextView tvserieFolio;
    TextView tvmarcaFolio;
    TextView tvtipoFolio;
    TextView tvcolorFolio;
    TextView tvmodeloFolio;
    TextView tvfechaVigenciaFolio;
    TextView tvfechaAltaFolio;
    //TextView fechaVigenciaFoliio;
    TextView tvnombreSocioFolio;
    TextView tvestatusFolio;

    //

    TextView tvlnumeroTarjeton;
    TextView tvlicenciaTarjeton;
    TextView tvtipoChoferTarjeton;
    TextView tvfolioTarjeton;
    TextView tvmaternoTarjeton;
    TextView tvpaternoTarjerton;
    TextView tvnombreTarjeton;
    TextView tvfechaAltaTarjeton;
    TextView tvfechaVigenciaTarjeton;
    TextView tvfechaLabTarjerton;
    TextView tvestatusTarjerton;

    //
    TableLayout placasPlataformaTabla;
    TableLayout placasRMTabla;
    TableLayout tablaQR;
    TableLayout plataformaFolio;
    TableLayout tarjetonFolio;
//

    TextView tituloPlacasPlataforma;
    TextView tituloDatosQR;
    TextView tituloPlacasRM;
    TextView tituloPlacasFolio;
    TextView tituloTarjetonFolio;


    TextView tvfolioGafeteQR;
    TextView tvdelegacionGafeteQR;
    TextView tvmodalidadGafeteQR;
    TextView tvserieRegistroGafeteQR;
    TextView tvvigenciaGafeteQR;
    TextView tvEconomicos;

    TableLayout tblIfracciones;
    TableLayout tblLicencia;
    TextView tvTituloInfracciones;
    TextView tvTituloLicencia;

    String color;
    String agrupacion;
    String rutaSitio;

    TextView tvColor;
    TextView tvAgrupacion;
    TextView tvRutaSitio;

    //INFRACCIONES
    String infracc;
    String fechaInfracion;

    Button btnMapa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wsgob_consulta);
        Toolbar toolbar = findViewById(R.id.toolbar);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Orientacion de pantalla.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setSupportActionBar(toolbar);
        String URLICENCIA = getResources().getString(R.string.URL_LICENCIA);;
        String URLINFRACCION = getResources().getString(R.string.URL_INFRACCION);


        enviarWSConsultaLicencia(URLICENCIA);
       // enviarWSConsultaInfraccion(URLINFRACCION);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.



        tvTituloLicencia = findViewById(R.id.tvTituloLicencia);

        //Tablas
        tblIfracciones = findViewById(R.id.tblIfracciones);
        tblLicencia = findViewById(R.id.tblLicencia);
       // tblIfracciones.setVisibility(View.GONE);
        tvTituloInfracciones = findViewById(R.id.tvTituloInfracciones);
       // tvTituloInfracciones.setVisibility(View.GONE);

        //Gafete
        tvfolioGafeteQR =  findViewById(R.id.tvGafeteFolioQR2);
        tvdelegacionGafeteQR = findViewById(R.id.tvGafeteMunicipioQR) ;
        tvmodalidadGafeteQR = findViewById(R.id.tvGafeteModalidadQR) ;
        tvserieRegistroGafeteQR = findViewById(R.id.tvGafeteRegistroQR);
        tvvigenciaGafeteQR =  findViewById(R.id.tvGafeteVigenciaQR);

        tvEconomicos =  findViewById(R.id.tvEconomicos);

        //TaBLAS

        placasPlataformaTabla = findViewById(R.id.placasPlataformaTabla);
        placasRMTabla = findViewById(R.id.placasRMTablaLay);
        tablaQR = findViewById(R.id.tablaQRTabla);
        plataformaFolio = findViewById(R.id.plataformaFolioTabla);
        tarjetonFolio = findViewById(R.id.tarjetonFolioTabla);

        tituloPlacasPlataforma =findViewById(R.id.tituloPlacasPlataforma);
        tituloDatosQR = findViewById(R.id.tituloDatosQR);
        tituloPlacasRM = findViewById(R.id.tituloPlacasRM);
        tituloPlacasFolio = findViewById(R.id.tituloPlacasFolio);
        tituloTarjetonFolio = findViewById(R.id.tituloTarjetonFolio);




        TextView textViewPlaca = (TextView)findViewById(R.id.tvPlaca);
        TextView textViewEstatus = (TextView)findViewById(R.id.tvEstatus);
        TextView textViewPropietario = (TextView)findViewById(R.id.tvPropietario);
        TextView textViewVigencia = (TextView)findViewById(R.id.tvVigencia);
        TextView textViewVim = (TextView)findViewById(R.id.tvVim);
        TextView textViewMarca = (TextView)findViewById(R.id.tvMarca);
        textViewInfracciones = (TextView)findViewById(R.id.tvInfracciones);
        textViewLicencia = (TextView)findViewById(R.id.tvNumeroLicencia);
        textViewNombre = (TextView)findViewById(R.id.tvNombre);
        textViewFechaVencimiento = (TextView)findViewById(R.id.tvFechaVencimiento);
        textViewFechaInfracciones = (TextView)findViewById(R.id.tvFechaInfraccion);
        textViewNumeroInfracciones = (TextView)findViewById(R.id.tvNumeroInfracciones);
        textViewMotivoInfracciones = (TextView)findViewById(R.id.tvMotivoInfraccion);

        //QRSERIAL


        tvplacaQR = findViewById(R.id.placasQR);
        tvserialQR = findViewById(R.id.QRQR);
        tvdelegacionIdQR = findViewById(R.id.delegacionIdQR);
        tveconomicoQR = findViewById(R.id.economicoQR);
        tvserieQR = findViewById(R.id.serieQR);
        tvmarcaQR = findViewById(R.id.marcaQR);
        tvmodeloQR = findViewById(R.id.modeloQR);
        tvtipoQR = findViewById(R.id.tipoQR);
        tvcolorQR = findViewById(R.id.colorQR);
        tvpadronQR = findViewById(R.id.padronQR);
        tvmodalidadQR = findViewById(R.id.modalidadQR);
        tvfechaAltaQR = findViewById(R.id.fechaAltaQR);
        tvprorrogaQR = findViewById(R.id.prorrogaQR);
        tvestatusQR = findViewById(R.id.estatusQR);
        tvcoberturaSeguroQR = findViewById(R.id.coberturaQR);
        tvvigenciaPolizaQR = findViewById(R.id.vigenciaPolizaQR);
        tvperiodoQR = findViewById(R.id.periodoQR);
        tvobservacionesQR = findViewById(R.id.observacionesQR);
        tvrevisionQR = findViewById(R.id.revisionQR);

        tvplacarm2 = findViewById(R.id.placasrm2);
        tvserialrm2 = findViewById(R.id.QRRM2);
        tvserierm2 = findViewById(R.id.serierm2);

        tveconomicorm2 = findViewById(R.id.numeroeconomicorm2);
        tvmarcarm2 = findViewById(R.id.marcarm2);
        tvmodelorm2 = findViewById(R.id.modelorm2);
        tvtiporm2 = findViewById(R.id.tiporm2);
        tvcolorrm2 = findViewById(R.id.colorrm2);
        tvpadronrm2 = findViewById(R.id.padronrm2);
        tvmodalidadrm2 = findViewById(R.id.modalidadrm2);
        //tvfechaAltarm2 = findViewById(R.id.fechaAlta);
        tvprorrogarm2 = findViewById(R.id.prorrogarm2);
        tvestatusrm2 = findViewById(R.id.estatusrm2);
        tvcoberturaSegurorm2 = findViewById(R.id.coberturasegurorm2);
        tvvigenciaPolizarm2 = findViewById(R.id.vigenciapolizarm2);
        tvperiodorm2 = findViewById(R.id.periodorm2);
        tvobservacionesrm2 = findViewById(R.id.observacionesrm2);
        tvrevisionrm2 = findViewById(R.id.revisionrm2);




        //FOLIO
        tvplacaFolio = findViewById(R.id.placasplataformafolio);
        tvfoliofolio = findViewById(R.id.folioplataformafolio);
        tvdelegacionFolio = findViewById(R.id.delegacionidplataformafolio);
        tvnombrePlataformaFolio = findViewById(R.id.plataformaplataformafolio);
        tvnumeroPolizaFolio = findViewById(R.id.numeropolizaplataformafolio);
        tvnombrePropietarioFolio = findViewById(R.id.propietarioplataformafolio);
        tvserieFolio = findViewById(R.id.serieplataformafolio);
        tvmarcaFolio = findViewById(R.id.marcaplataformafolio);
        tvtipoFolio = findViewById(R.id.tipoplataformafolio);
        tvcolorFolio = findViewById(R.id.colorplataformafolio);
        tvmodeloFolio = findViewById(R.id.modeloplataformafolio);
        tvfechaVigenciaFolio = findViewById(R.id.fechavigenciaplataformafolio);
        tvfechaAltaFolio = findViewById(R.id.fechaaltaplataformafolio);
        tvnombreSocioFolio = findViewById(R.id.nombresocioplataformafolio);
        tvestatusFolio = findViewById(R.id.estatusplataformafolio);
        tvestatusFolio = findViewById(R.id.estatusplataformafolio);


        //TARJETON


        tvlnumeroTarjeton = findViewById(R.id.lnumerotarjetonfolio);
        tvlicenciaTarjeton = findViewById(R.id.licenciatarjetonfolio);
        tvtipoChoferTarjeton = findViewById(R.id.tipochofertarjetonfolio);
        tvfolioTarjeton = findViewById(R.id.foliotarjetonfolio);
        tvmaternoTarjeton = findViewById(R.id.maternotarjetonfolio);
        tvpaternoTarjerton = findViewById(R.id.paternotarjetonfolio);
        tvnombreTarjeton = findViewById(R.id.nombretarjetonfolio);
        tvfechaAltaTarjeton = findViewById(R.id.fechaaltatarjetonfolio);
        tvfechaVigenciaTarjeton = findViewById(R.id.fechavigenciatarjtonfolio);
        tvfechaLabTarjerton = findViewById(R.id.fechalaboratoriotarjetonfolio);
        tvestatusTarjerton = findViewById(R.id.estatusfoliotarjetonfolio);


        tvColor = findViewById(R.id.tvColor);
        tvAgrupacion = findViewById(R.id.tvAgrupacion);
        tvRutaSitio = findViewById(R.id.tvRutaSitio);


        //RM
        tvplacasRM = findViewById(R.id.placasRM);
        tvdelegacionRM = findViewById(R.id.delegacionRM);
        tvplataformaRM = findViewById(R.id.plataformaRM);
        tvpolizaRM = findViewById(R.id.propietarioRM);
        tvpropietarioRM = findViewById(R.id.propietarioRM);
        tvserieRM = findViewById(R.id.serieRM);
        tvmarcaRM = findViewById(R.id.marcaRM);
        tvtipoRM = findViewById(R.id.tipoRM);
        tvcolorRM = findViewById(R.id.colorRM);
        tvmodeloRM = findViewById(R.id.modeloRM);
        tvvigenciaRM = findViewById(R.id.vigenciaRM);
        tvsocioRM = findViewById(R.id.socioRM);
        tvstatusRM = findViewById(R.id.estatusRM);

        Bundle bundle  = getIntent().getExtras();
        //Validamos que no venga vacio
        if (bundle != null){

            //Recojemos parametros.
            placa = bundle.getString("placa");

            Log.d("BUNDLE-1","Placa que recojemos del intent anterior <:> "+placa);
            estatus = bundle.getString("estatus");
            Log.d("###PLACASSS","#######"+estatus);
            propietario = bundle.getString("propietario");
            Log.d("###PLACASSS","#######"+propietario);
            vigencia = bundle.getString("vigencia");
            Log.d("###PLACASSS","#######"+vigencia);
            vim = bundle.getString("vim");
            Log.d("###PLACASSS","#######"+vim);
            marca = bundle.getString("marca");
            Log.d("###PLACASSS","#######"+marca);


            color  = bundle.getString("colorW");
            agrupacion  = bundle.getString("agrupacionW");
            rutaSitio  = bundle.getString("rutaSitioW");

            Log.d("datoswsInserta2","###################"+color);


            tvColor.setText(color);
            tvAgrupacion.setText(agrupacion);
            tvRutaSitio.setText(rutaSitio);


            economico = bundle.getString("economico");
            Log.d("ECONOMICO","El numero economico"+ economico);

            tvEconomicos.setText(economico);
            infracciones = bundle.getString("infracciones");
            licencia = bundle.getString("licencia");

            Log.d("LICENCIA-VERGAS1","$$$$$$$$$$$$$$$"+licencia);
            nombre = bundle.getString("nnombre");
            fechaVecimiento = bundle.getString("fechaVencimiento");

            Log.d("MI-PLACA","lA PLACA ES: "+ placa);






            usersId  = bundle.getString("usersId");
            Log.d("USERSSSSSSSS","#####################&%&%&%&%&%&%&%&%&USERSSSSSSSS"+usersId);
            username  = bundle.getString("username");
            profile  = bundle.getString("profile");
            nombreLogin  = bundle.getString("nombre");
            delegacionId  = bundle.getString("delegacionId");
            activo  = bundle.getString("activo");
            textViewPlaca.setText(placa);




            if (propietario==null){
                textViewEstatus.setText("NO HAY DATA EN WS");
                tvEconomicos.setText("NO HAY DATA EN WS");
                textViewPropietario.setText("NO HAY DATA EN WS");
                textViewVigencia.setText("NO HAY DATA EN WS");
                textViewVim.setText("NO HAY DATA EN WS");
                textViewMarca.setText("NO HAY DATA EN WS");
                tvColor.setText("NO HAY DATA EN WS");
                tvAgrupacion.setText("NO HAY DATA EN WS");
                tvRutaSitio.setText("NO HAY DATA EN WS");
            }else {
                String banderaLic=  bundle.getString("bandera");
                textViewEstatus.setText(estatus);
                tvEconomicos.setText(economico);
                textViewPropietario.setText(propietario);
                textViewVigencia.setText(vigencia);
                textViewVim.setText(vim);
                textViewMarca.setText(marca);
                tvColor.setText(color);
                tvAgrupacion.setText(agrupacion);
                tvRutaSitio.setText(rutaSitio);
            }

            //Infracciones

            String numeroInfracciones = bundle.getString("numeroInfracciones");
            String fechInfraccion = bundle.getString("fechInfraccion");
            String motivoInfraccion = bundle.getString("motivoInfraccion");
            String baderaInfraccion = bundle.getString("baderaInfraccion");
            latitud = bundle.getString("latitud");
            longitud = bundle.getString("longitud");

            Log.d("BUNDLEiNFRACCIONES-1","Valor Numero Infracciones <:>"+ numeroInfracciones);
            Log.d("BUNDLEiNFRACCIONES-2","Valor Fecha Infraccion <:>"+ fechInfraccion);
            Log.d("BUNDLEiNFRACCIONES-3","Valor Motivo de Infraccion <:>"+ motivoInfraccion);
            Log.d("BUNDLEiNFRACCIONES-4","Valor Bandera de Infraccion <:>"+ baderaInfraccion);
            Log.d("BUNDLEiNFRACCIONES-5","Valor Latitud <:>"+ latitud);
            Log.d("BUNDLEiNFRACCIONES-6","Valor Longitud <:>"+ longitud);


            try {
                if (numeroInfracciones.equals("No hay datos") || numeroInfracciones.equals(null) ){
                    textViewNumeroInfracciones.setText("SIN INFRACCIONES");
                    textViewFechaInfracciones.setText("SIN INFRACCIONES");
                    textViewMotivoInfracciones.setText("SIN INFRACCIONES");
                    tvTituloInfracciones.setVisibility(View.GONE);
                    tblIfracciones.setVisibility(View.GONE);

                }else{

                    textViewNumeroInfracciones.setText(numeroInfracciones);
                    textViewFechaInfracciones.setText(fechInfraccion);
                    textViewMotivoInfracciones.setText(motivoInfraccion);
                }
            }catch (Exception e){

            }




            //QR





            String placaQR = bundle.getString("placaQR");
            String serialQR = bundle.getString("qr_serial");
            Log.d("SERIALQRCONSULTA","#################################"+serialQR);
            String delegacionIdQR = bundle.getString("delegacionIdQR");

            String economicoQR = bundle.getString("economicoQR");
            String serieQR = bundle.getString("serieQR");
            Log.d("serieQRRCONSULTA","#################################"+serieQR);
            String marcaQR = bundle.getString("marcaQR");
            String modeloQR = bundle.getString("modeloQR");
            String tipoQR = bundle.getString("tipoQR");
            String colorQR = bundle.getString("colorQR");
            String padronQR = bundle.getString("padronQR");
            String modalidadQR = bundle.getString("modalidadQR");
            String fechaAltaQR = bundle.getString("fechaAltaQR");
            String prorrogaQR = bundle.getString("prorrogaQR");
            String fechaProrrojgaQR = bundle.getString("fechaProrrojgaQR");
            String estatusQR = bundle.getString("estatusQR");
            String coberturaSeguroQR = bundle.getString("coberturaSeguroQR");
            String vigenciaPolizaQR = bundle.getString("vigenciaPolizaQR");
            String periodoQR = bundle.getString("periodoQR");
            String observacionesQR = bundle.getString("observacionesQR");
            String revisionQR = bundle.getString("revisionQR");



            String folioGafeteQR = bundle.getString("folioGafeteQR");
            String delegacionGafeteQR = bundle.getString("delegacionGafeteQR");
            String modalidadGafeteQR = bundle.getString("modalidadGafeteQR");
            String serieRegistroGafeteQR = bundle.getString("serieRegistroGafeteQR");
            String vigenciaGafeteQR = bundle.getString("vigenciaGafeteQR");

            tvfolioGafeteQR.setText(folioGafeteQR);
            tvdelegacionGafeteQR.setText(delegacionGafeteQR);
            tvmodalidadGafeteQR.setText(modalidadGafeteQR);
            tvserieRegistroGafeteQR.setText(serieRegistroGafeteQR);
            tvvigenciaGafeteQR.setText(vigenciaGafeteQR);

            if (serieQR!=null) {
                tvplacaQR.setText(placaQR);
                tveconomicoQR.setText(economicoQR);
                tvserieQR.setText(serieQR);
                tvserialQR.setText(serialQR);
                tvdelegacionIdQR.setText(delegacionIdQR);
                tvmarcaQR.setText(marcaQR);
                tvmodeloQR.setText(modeloQR);
                tvtipoQR.setText(tipoQR);
                tvcolorQR.setText(colorQR);
                tvpadronQR.setText(padronQR);
                tvmodalidadQR.setText(modalidadQR);
                tvfechaAltaQR.setText(fechaAltaQR);
                tvprorrogaQR.setText(prorrogaQR);
                //tvfechaProrrojgaQR.setText(fechaProrrojgaQR);
                tvestatusQR.setText(estatusQR);
                tvcoberturaSeguroQR.setText(coberturaSeguroQR);
                tvvigenciaPolizaQR.setText(vigenciaPolizaQR);
                tvperiodoQR.setText(periodoQR);
                tvobservacionesQR.setText(observacionesQR);
                tvrevisionQR.setText(revisionQR);

                tablaQR.setVisibility(View.VISIBLE);
                tituloDatosQR.setVisibility(View.VISIBLE);
            }



            //PLACASRM2

            String placarm2 = bundle.getString("placarm2");
            String serialrm2 = bundle.getString("serialrm2");
            String economicorm2 = bundle.getString("economicorm2");
            String serierm2 = bundle.getString("serierm2");
            String marcarm2 = bundle.getString("marcarm2");
            String modelorm2 = bundle.getString("modelorm2");
            String tiporm2 = bundle.getString("tiporm2");
            String colorrm2 = bundle.getString("colorrm2");
            String padronrm2 = bundle.getString("padronrm2");
            String modalidadrm2 = bundle.getString("modalidadrm2");
            String fechaAltarm2 = bundle.getString("fechaAltarm2");
            String prorrogarm2 = bundle.getString("prorrogarm2");
            String fechaProrrojgarm2 = bundle.getString("fechaProrrojgarm2");
            String estatusrm2 = bundle.getString("estatusrm2");
            String coberturaSegurorm2 = bundle.getString("coberturaSegurorm2");
            String vigenciaPolizarm2 = bundle.getString("vigenciaPolizarm2");
            String periodorm2 = bundle.getString("periodorm2");
            String observacionesrm2 = bundle.getString("observacionesrm2");
            String revisionrm2 = bundle.getString("revisionrm2");



            if (serialrm2!=null){
                tvplacarm2.setText(placarm2);
                tveconomicorm2.setText(economicorm2);
                tvserialrm2.setText(serialrm2);
                tvserierm2.setText(serierm2);
                tvmarcarm2.setText(marcarm2);
                tvmodelorm2.setText(modelorm2);
                tvtiporm2.setText(tiporm2);
                tvcolorrm2.setText(colorrm2);
                tvpadronrm2.setText(padronrm2);
                tvmodalidadrm2.setText(modalidadrm2);
                //tvfechaAltarm2.setText(fechaAltarm2);




                //tvfechaProrrojgaQR.setText(fechaProrrojgaQR);
                tvestatusrm2.setText(estatusrm2);
                tvcoberturaSegurorm2.setText(coberturaSegurorm2);
                tvvigenciaPolizarm2.setText(vigenciaPolizarm2);
                tvperiodorm2.setText(periodorm2);
                tvobservacionesrm2.setText(observacionesrm2);
                tvrevisionrm2.setText(revisionrm2);

                placasRMTabla.setVisibility(View.VISIBLE);
                tituloPlacasRM.setVisibility(View.VISIBLE);

            }



            //PLACAS RM

            String placaPlataforma = bundle.getString("placaPlataforma");
            String delegacionPlataforma = bundle.getString("delegacionPlataforma");
            String nombrePlataforma = bundle.getString("nombrePlataforma");
            String polizaPlataforma = bundle.getString("polizaPlataforma");
            String propietarioPlataforma = bundle.getString("propietarioPlataforma");
            String seriePlataforma = bundle.getString("seriePlataforma");
            String marcaPlataforma = bundle.getString("marcaPlataforma");
            String tipoPlataforma = bundle.getString("tipoPlataforma");
            String colorPlataforma = bundle.getString("colorPlataforma");
            String modeloPlataforma = bundle.getString("modeloPlataforma");
            String vigenciaPlataforma = bundle.getString("vigenciaPlataforma");
            String socioPlataforma = bundle.getString("socioPlataforma");
            String estatusPlataforma = bundle.getString("estatusPlataforma");

            Log.d("RMWSGOB------%","VALOR"+placaPlataforma);

            if (delegacionPlataforma!=null){
                tvplacasRM.setText(placaPlataforma);
                tvdelegacionRM.setText(delegacionPlataforma);
                tvplataformaRM.setText(nombrePlataforma);
                tvpolizaRM.setText(polizaPlataforma);
                tvpropietarioRM.setText(propietarioPlataforma);
                tvserieRM.setText(seriePlataforma);
                tvmarcaRM.setText(marcaPlataforma);
                tvtipoRM.setText(tipoPlataforma);
                tvcolorRM.setText(colorPlataforma);
                tvmodeloRM.setText(modeloPlataforma);
                tvvigenciaRM.setText(vigenciaPlataforma);
                tvsocioRM.setText(socioPlataforma);
                tvstatusRM.setText(estatusPlataforma);

                placasPlataformaTabla.setVisibility(View.VISIBLE);
                tituloPlacasPlataforma.setVisibility(View.VISIBLE);

            }





            String placaFolio = bundle.getString("placaFolio");
            String foliofolio = bundle.getString("foliofolio");
            String delegacionFolio = bundle.getString("delegacionFolio");
            String nombrePlataformaFolio = bundle.getString("nombrePlataformaFolio");
            String numeroPolizaFolio = bundle.getString("numeroPolizaFolio");
            String nombrePropietarioFolio = bundle.getString("nombrePropietarioFolio");
            String serieFolio = bundle.getString("serieFolio");
            String marcaFolio = bundle.getString("marcaFolio");
            String tipoFolio = bundle.getString("tipoFolio");
            String colorFolio = bundle.getString("colorFolio");
            String modeloFolio = bundle.getString("modeloFolio");
            String fechaVigenciaFolio = bundle.getString("fechaVigenciaFolio");

            String fechaAltaFolio = bundle.getString("fechaAltaFolio");

            String nombreSocioFolio = bundle.getString("nombreSocioFolio");
            String estatusFolio = bundle.getString("estatusFolio");

            if (foliofolio!=null){
                tvplacaFolio.setText(placaFolio);
                tvfoliofolio.setText(foliofolio);
                tvdelegacionFolio.setText(delegacionFolio);
                tvnombrePlataformaFolio.setText(nombrePlataformaFolio);
                tvnumeroPolizaFolio.setText(numeroPolizaFolio);
                tvnombrePropietarioFolio.setText(nombrePropietarioFolio);
                tvserieFolio.setText(serieFolio);
                tvmarcaFolio.setText(marcaFolio);
                tvtipoFolio.setText(tipoFolio);
                tvcolorFolio.setText(colorFolio);
                tvmodeloFolio.setText(modeloFolio);
                tvfechaVigenciaFolio.setText(fechaVigenciaFolio);
                tvfechaAltaFolio.setText(fechaAltaFolio);
                tvnombreSocioFolio.setText(nombreSocioFolio);
                tvestatusFolio.setText(estatusFolio);

                plataformaFolio.setVisibility(View.VISIBLE);

                tituloPlacasFolio.setVisibility(View.VISIBLE);
            }






            String lnumeroTarjeton = bundle.getString("lnumeroTarjeton");
            String licenciaTarjeton = bundle.getString("licenciaTarjeton");
            String tipoChoferTarjeton = bundle.getString("tipoChoferTarjeton");
            String folioTarjeton = bundle.getString("folioTarjeton");
            String maternoTarjeton = bundle.getString("maternoTarjeton");
            String paternoTarjerton = bundle.getString("paternoTarjerton");
            String nombreTarjeton = bundle.getString("nombreTarjeton");
            String fechaAltaTarjeton = bundle.getString("fechaAltaTarjeton");
            String fechaVigenciaTarjeton = bundle.getString("fechaVigenciaTarjeton");
            String fechaLabTarjerton = bundle.getString("fechaLabTarjerton");
            String estatusTarjerton = bundle.getString("estatusTarjerton");

            if (lnumeroTarjeton!=null){
                tvlnumeroTarjeton.setText(lnumeroTarjeton);
                tvlicenciaTarjeton.setText(licenciaTarjeton);
                tvtipoChoferTarjeton.setText(tipoChoferTarjeton);
                tvfolioTarjeton.setText(folioTarjeton);
                tvmaternoTarjeton.setText(maternoTarjeton);
                tvpaternoTarjerton.setText(paternoTarjerton);
                tvnombreTarjeton.setText(nombreTarjeton);
                tvfechaAltaTarjeton.setText(fechaAltaTarjeton);
                tvfechaVigenciaTarjeton.setText(fechaVigenciaTarjeton);
                tvfechaLabTarjerton.setText(fechaLabTarjerton);
                tvestatusTarjerton.setText(estatusTarjerton);
                tituloTarjetonFolio.setVisibility(View.VISIBLE);
                tarjetonFolio.setVisibility(View.VISIBLE);
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
                        tblLicencia.setVisibility(View.GONE);
                        tvTituloLicencia.setVisibility(View.GONE);
                    }

                } else {

                    Toast.makeText(WsgobConsulta.this, "No se encontraron parametros en la consulta de licencia", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //OCULTAMOS VISTA CUANDO FALLA LA CONSULTA DE LA RESPUESTA DEL WS.
                tblLicencia.setVisibility(View.GONE);
                tvTituloLicencia.setVisibility(View.GONE);
                //Seteamos valor cuando sobre pasa el tiempo de esepera
                textViewNombre.setText("LIMITE DE ESPERA");
                textViewLicencia.setText("LIMITE DE ESPERA");
                textViewFechaVencimiento.setText("LIMITE DE ESPERA");
                //Error de Voley para cuando falla o hay un dato nulo
                Toast.makeText(WsgobConsulta.this, " SIN DATOS DE LICENCIA", Toast.LENGTH_LONG).show();
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
        gotoBack.putExtra("licencia",licencia);
        if (placa==null){
            gotoBack.putExtra("placa","");
        }else{
            gotoBack.putExtra("placa",placa);
        }

        gotoBack.putExtra("licencia",licencia);
        //gotoBack.putExtra(USER_GLOBAL_SENDER, username_global); <-- Use this if you want to carry some data to the other activity.
        finish();
        startActivity(gotoBack);
    }


  /*  public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney and move the camera

        parserLat = Double.parseDouble(latitud);
        parserLongt = Double.parseDouble(longitud);

        infraccionLatLong = new LatLng(parserLat,parserLongt);
        *//*Log.d("MAPA-INFRACCION-1","Valor de lat al generar Mapa <:> "+latitudDecimal);
        Log.d("MAPA-INFRACCION-2","Valor de longitud al generar al Mapa <:> "+ longitudDecimal);*//*
        mMap.addMarker(new MarkerOptions().position(infraccionLatLong).title("ZONA INFRACCION"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(infraccionLatLong));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(infraccionLatLong,20));
    }*/

    /*private void animarMadrid()
    {
       GoogleMap mMap = null;

        LatLng infraccionLatLong = new LatLng(40.417325, -3.683081);
        mMap.addMarker(new MarkerOptions().position(infraccionLatLong).title("ZONA INFRACCION"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(infraccionLatLong));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(infraccionLatLong,20));
    }
*/


    public void onMapReady(GoogleMap map) {
        // Add some markers to the map, and add a data object to each marker.
        try {
            Double paserLat = Double.parseDouble(latitud);
            Double parserLong = Double.parseDouble(longitud);

            LatLng INFRACCIONESWS = new LatLng(paserLat, parserLong);
            markerPerth = map.addMarker(new MarkerOptions().position(INFRACCIONESWS).title("Perth"));
            map.moveCamera(CameraUpdateFactory.newLatLng(INFRACCIONESWS));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(INFRACCIONESWS,17));
            markerPerth.setTag(0);


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

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }


}