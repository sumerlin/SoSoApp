package com.billy.android.soso.app.data;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.billy.android.soso.app.BR;

/**
 * Author：summer
 * Description：
 */
public class ListItemBean   extends BaseObservable{

    @Bindable
    public String name ;
    public String sex;

    public ListItemBean(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
