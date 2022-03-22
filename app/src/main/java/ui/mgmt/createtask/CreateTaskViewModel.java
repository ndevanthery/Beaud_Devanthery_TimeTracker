package ui.mgmt.createtask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateTaskViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CreateTaskViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the add menu");
    }

    public LiveData<String> getText() {
        return mText;
    }
}