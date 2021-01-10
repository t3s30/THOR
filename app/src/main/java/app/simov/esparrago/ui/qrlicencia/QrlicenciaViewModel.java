package app.simov.esparrago.ui.qrlicencia;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QrlicenciaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public QrlicenciaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}