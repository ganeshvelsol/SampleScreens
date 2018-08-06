package com.notepad.samplescreens;

import android.content.Intent;
import android.database.Cursor;
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
import android.widget.LinearLayout;
import android.widget.Toast;

public class ListShow extends AppCompatActivity
{
    EditText f_name1,data1;
    Button save1;
    SQLiteDatabase sdbs;
    LinearLayout ll;
    String name,file_name,data_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_list_show);
        //for adding back button
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try
        {
            f_name1=findViewById(R.id.file_name1);
            data1=findViewById(R.id.content1);
            ll=findViewById(R.id.linear);
            Intent m=getIntent();
            Bundle b=m.getExtras();
            //name contains list position name from mainActitvity
            name=b.getString("k1");
            //calling database code for reading perticilar data from intents variables
            MyDatadb mi=new MyDatadb(this);
            sdbs=mi.getWritableDatabase();
            f_name1.setText(name);
            String reading="select data from sample where name='"+name+"'";
            Cursor c1=sdbs.rawQuery(reading,null);
            if (c1.moveToFirst())
            {
                do {
                    data1.setText( c1.getString(0));
                }while (c1.moveToNext());
            }
            save1=findViewById(R.id.save1);
            save1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    saveData();
                }
            });

        }catch (Exception e)
        {
            Toast.makeText(this, "error in main activity", Toast.LENGTH_SHORT).show();
        }

    }
    public void saveData()
    {
         file_name=f_name1.getText().toString().trim();
         data_edit=data1.getText().toString().trim();
        try
        {
            if (file_name.isEmpty())
            {
                f_name1.requestFocus();
                f_name1.setError("enter title");
            }
            else if (data_edit.isEmpty())
            {
                data1.requestFocus();
                data1.setError("enter data");
            }
            else if (file_name!=name)
            {
                Toast.makeText(this, "your not allowed here to change file name", Toast.LENGTH_SHORT).show();
            }
            else
            {
                String qry="update  sample set data='"+data_edit+"'where name='"+file_name+"'";
                sdbs.execSQL(qry);
                Toast.makeText(this, "updated"+file_name, Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e)
        {
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
         super.onCreateOptionsMenu(menu);
             MenuInflater inflater = getMenuInflater();
             inflater.inflate(R.menu.delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.info:
            {
                Snackbar snackbar = Snackbar.make(ll, "Developer : Ganesh munigala", Snackbar.LENGTH_LONG);

                snackbar.show();

                return true;
            }

            case R.id.delete :
            {
                //write code for delete data in database and listview
                String remove_qry="delete from sample where name='"+name+"'";
                sdbs.execSQL(remove_qry);
                Toast.makeText(this, "removed succesfully", Toast.LENGTH_SHORT).show();
                f_name1.setText("");
                data1.setText("");
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
