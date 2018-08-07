package com.notepad.samplescreens;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class LoginFragment extends Fragment
{
    EditText login_number;
    Button login_2;
    TextView forgot_pass;
    String number_sql;
    SQLiteDatabase sdb;
    boolean b=true;
    ImageView mhide_password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v1= inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        login_number=v1.findViewById(R.id.login_number);
        forgot_pass=v1.findViewById(R.id.forgot_password);
        //mhide_password=v1.findViewById(R.id.hide_password);
        login_2=v1.findViewById(R.id.login_2);
        login_2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //code for login data
                login_data();
            }
        });
        forgot_pass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //code for writing the forgopt password
                forgotPass();

            }
        });
        return v1;
    }
    public void login_data()
    {
        String number1=login_number.getText().toString().trim();
        if (number1.isEmpty())
        {
            login_number.requestFocus();
            login_number.setError("please provide mobile number");
        }
        else
        {
            //read mobile number form sqlitedb compare with number1 if success call MainActivity...
            MyDatadb mm=new MyDatadb(getActivity());
            sdb=mm.getWritableDatabase();
            String read="select password from password";
            Cursor c=sdb.rawQuery(read,null);
            boolean b=c.moveToFirst();
            if (b==true)
            {
                do
                {
                    number_sql=c.getString(0);
                    if (number1.equals(number_sql))
                    {
                        //here login succes so call mainActivity
                        //getActivity().finish();
                        startActivity(new Intent(getActivity(),MainActivity.class));
                    }
                    else
                    {
                        Toast.makeText(getContext(), "please provide valid password number", Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    }

                }while (c.moveToNext());
            }
            else
            {
                Toast.makeText(getContext(), "please register !", Toast.LENGTH_SHORT).show();
            }
            c.close();
        }
    }
    public void forgotPass()
    {
        //code for showing the forgot password
        AlertDialog.Builder al=new AlertDialog.Builder(getContext());
        al.setTitle("enter registered number");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View mView = inflater.inflate(R.layout.dialog_layout, null);
        al.setView(mView);
        al.setNegativeButton("ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {

                EditText num=(EditText)mView.findViewById(R.id.dialog_edit);
                String num1=num.getText().toString().trim();
                MyDatadb m1=new MyDatadb(getActivity());
                SQLiteDatabase ss=m1.getWritableDatabase();
                String read1="select password from password where number='"+num1+"'";
                Cursor c1=ss.rawQuery(read1,null);
                if (c1.moveToFirst())
                {
                    do {
                        String pass2=c1.getString(0);
                        AlertDialog.Builder al1=new AlertDialog.Builder(getContext());
                        al1.setTitle("your password is");
                        LayoutInflater inflater = getActivity().getLayoutInflater();
                        final View mView = inflater.inflate(R.layout.dialog_layout, null);
                        al1.setView(mView);
                        EditText num6=(EditText)mView.findViewById(R.id.dialog_edit);
                        num6.setText(pass2);
                        al1.show();
                    }while (c1.moveToNext());
                }
                else
                {
                    Snackbar sn=Snackbar.make((getActivity()).findViewById(android.R.id.content),"register first",Snackbar.LENGTH_LONG);
                    sn.show();
                }
            }
        });
        al.show();
    }

}
