package app.simov.esparrago.ui.qrlicencia;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Arrays;
import java.util.List;

import app.simov.esparrago.R;
import app.simov.esparrago.ui.slideshow.SlideshowFragment;

public class QrlicenciaFragment extends Fragment {

    TextView qrResult;
    private QrlicenciaViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(QrlicenciaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_qrlicencia, container, false);


        final Button bntQr = root.findViewById(R.id.btnQrLicencia);

        //Boton Iniciar QR
        bntQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escanear();
            }
        });


        return root;
    }



    //Clase para scanear el codigo QR
    public void escanear(){

        IntentIntegrator intent = IntentIntegrator.forSupportFragment(QrlicenciaFragment.this);
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
                //Aqui Ya tenemos la url de la licencia de la camara.

                String urlLicencia = result.getContents();

                //Aqui empieza el alert##########################################################################################
                // create an alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("DATOS LICENCIA");

                // set the custom layout
                final View layOutLicencia = getLayoutInflater().inflate(R.layout.custom_layout_qr_licencia, null);
                builder.setView(layOutLicencia);

                final WebView webview  = (WebView) layOutLicencia.findViewById(R.id.wvLicencia);

                webview.getSettings().setAllowFileAccess(true);
                webview.getSettings().setBuiltInZoomControls(true);
                webview.getSettings().setJavaScriptEnabled(true);
                //webview.setWebChromeClient(new WebChromeClient());
                webview.setWebViewClient(new WebViewClient());
                webview.loadUrl(urlLicencia);



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




               /* Intent i = new Intent(CameraTestActivity.this,WebViewActivity.class);
                i.putExtra("mUrl",value);
                startActivity(i)*/


            }
        }else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

}