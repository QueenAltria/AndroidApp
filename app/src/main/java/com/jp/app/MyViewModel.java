package com.jp.app;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel  extends ViewModel {
    private MutableLiveData<String> name;
    public LiveData<String> getName(){
        if(name==null){
            name=new MutableLiveData<>();
            addName();
        }
        return name;
    }
    private void addName(){
        name.setValue("Android进阶解密");
    }

    public String getUserName(){
        return name.getValue();
    }

    public void updateName(){
        name.setValue("Android进阶解密------update");
    }
}
