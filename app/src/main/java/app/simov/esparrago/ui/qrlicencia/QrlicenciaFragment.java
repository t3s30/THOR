package app.simov.esparrago.ui.qrlicencia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import app.simov.esparrago.R;

public class QrlicenciaFragment extends Fragment {

    TextView qrResult;
    private QrlicenciaViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(QrlicenciaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_qrlicencia, container, false);

        return root;
    }
}