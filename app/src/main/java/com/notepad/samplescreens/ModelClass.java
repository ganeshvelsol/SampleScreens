package com.notepad.samplescreens;

/**
 * Created by user4 on 2/5/2018.
 */

public class ModelClass
{
    String f_name;
    String dates;

    public ModelClass(String f_name)
    {
        this.f_name = f_name;

    }

    public ModelClass(String f_name, String dates) {
        this.f_name = f_name;
        this.dates = dates;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }
}
