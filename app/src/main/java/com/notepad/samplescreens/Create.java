package com.notepad.samplescreens;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Create extends AppCompatActivity
{
    EditText f_name, save_data;
    Button m_save;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //methods for initializing the components
        initViews();
    }

    public void initViews()
    {

        f_name = findViewById(R.id.file_name);
        save_data = findViewById(R.id.content);
        m_save = findViewById(R.id.save);
        m_save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                saveDataLogic();
            }
        });

    }
    public void saveDataLogic()
    {
        String file_name = f_name.getText().toString().trim();
        String data = save_data.getText().toString().trim();
        if (file_name.isEmpty())
        {

            f_name.requestFocus();
            f_name.setError("enter title");
        } else if (data.isEmpty())
        {

            save_data.requestFocus();
            save_data.setError("enter data ");
        } else
            {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            String d1 = dateFormat.format(cal.getTime());
            String qry = "insert into sample values('" + file_name + "','" + data + "','" + d1 + "')";
            MyDatadb db = new MyDatadb(this);
            SQLiteDatabase sdb = db.getWritableDatabase();
            sdb.execSQL(qry);
                Snackbar ss=Snackbar.make(this.findViewById(android.R.id.content),"succesfully inserted data",Snackbar.LENGTH_LONG);
                ss.show();

        }


        //String read="select name from sample";
//        Cursor c=sdb.rawQuery(read,null);
//        if (c.moveToFirst())
//        {
//            do {
//                String colmn_name=c.getString(0);
//                if (file_name==colmn_name)
//                {
//                    //write code for updating
//                }
//                else
//                {
//                    //write code for creating the data
//                }
//            }while (c.moveToNext());
//
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onPrepareOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id)
        {
            case R.id.another:
                {
                //this icon is used to create a new file in the dtatabase
                startActivity(new Intent(this, Create.class));
            }
            case android.R.id.home:
            {
                startActivity(new Intent(this,MainActivity.class));
                this.finish();
                //return  true;
            }
                // app icon in action bar clicked; goto parent activity.
        }
        return true;
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this,MainActivity.class));
    }
}