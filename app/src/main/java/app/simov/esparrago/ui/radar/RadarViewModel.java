package app.simov.esparrago.ui.radar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RadarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RadarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("EN CONSTRUCCION");
    }

    public LiveData<String> getText() {
        return mText;
    }
}