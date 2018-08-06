package com.notepad.samplescreens;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class RegistrationFragment extends Fragment
{
    EditText p_number,p_password;
    Button save_data;
    TextView login;
    ImageView mshow_pass;
    int count=0;
    boolean b=true;
    SQLiteDatabase sdb;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_registration, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
       // mshow_pass=v.findViewById(R.id.show_password);
        p_number=v.findViewById(R.id.number);
        p_password=v.findViewById(R.id.password);

        login=v.findViewById(R.id.login);
        save_data=v.findViewById(R.id.save_data);
        save_data.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //code for clicking the button
                savePassword();

            }
        });
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //code for login data
                loginFromdb();

            }
        });
        return v;
    }
    public void savePassword()
    {
        String number = p_number.getText().toString().trim();
        String pass = p_password.getText().toString().trim();
        if (number.isEmpty())
        {
            p_number.requestFocus();
            p_number.setError("enter mobile number");

        } else if (pass.isEmpty())
        {
            p_password.requestFocus();
            p_password.setError("provide password");
        }
        else
            {
            MyDatadb mm1 = new MyDatadb(getActivity());
            sdb = mm1.getWritableDatabase();
            String read = "select counter from count";
            Cursor c = sdb.rawQuery(read, null);
            boolean b = c.moveToFirst();
            if (b == false)
            {
                count++;
                //write code for saving data into database
                MyDatadb mm = new MyDatadb(getActivity());
                SQLiteDatabase sdb = mm.getWritableDatabase();
                String save_qry = "insert into password values('" + number + "','" + pass + "')";
                sdb.execSQL(save_qry);
                String counter1="insert into count values('"+count+"')";
                sdb.execSQL(counter1);

                Toast.makeText(getContext(), "succesfully credentials are added", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
            else
            {
                Toast.makeText(getContext(), "already registered ", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void loginFromdb()
    {
        //call loginIntent here

        RegisterActivity.viewPager.setCurrentItem(1);
        LoginFragment nextFrag= new LoginFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .commit();


        //startActivity(new Intent(getActivity(),LoginFragment.class));
    }

}
