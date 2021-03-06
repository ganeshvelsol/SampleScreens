package com.notepad.samplescreens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    ListView mlist;
    List<ModelClass> employeeList= new ArrayList<>();
    public static MyBaseAdapter aa;
    SQLiteDatabase sdb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mlist=findViewById(R.id.mlist);

        showData();
    }
    public final void showData()
    {
        MyDatadb mm=new MyDatadb(this);
        sdb=mm.getWritableDatabase();
        String read="select name,dates from sample";
        Cursor c=sdb.rawQuery(read,null);
        if (c.moveToFirst())
        {
            do
            {
                //write code for displaying the data present in table
                employeeList.add(new ModelClass(c.getString(0),c.getString(1)));

            }while (c.moveToNext());
            c.close();
            //calling adapter class
            aa=new MyBaseAdapter(employeeList,this);
            mlist.setAdapter(aa);
            mlist.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    //here code for on list position click listener action
                    TextView mtext=(TextView) view.findViewById(R.id.list_one);
                    String d=mtext.getText().toString().trim();


                    Intent i1=new  Intent(MainActivity.this,ListShow.class);
                    i1.putExtra("k1",d);
                    startActivity(i1);
                    finish();
                }
            });
        }
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
         int id=item.getItemId();
         switch (id)
         {
             case R.id.another:
             {
                 //this icon is used to create a new file in the dtatabase
                 startActivity(new Intent(this,Create.class));
                 finish();
             }
         }
        return true;
    }
    public class MyBaseAdapter extends BaseAdapter
    {
        Context context;
        List<ModelClass> modelClassList;
        public MyBaseAdapter(List<ModelClass> modelClassList,Context context)
        {
            this.modelClassList = modelClassList;
            this.context=context;
        }

        @Override
        public int getCount() {
            return modelClassList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            View mview = getLayoutInflater().inflate(R.layout.cost_items, null, false);

            final ModelClass employee = employeeList.get(i);
            //getting views
            TextView textViewName = mview.findViewById(R.id.list_one);
            // TextView textViewDept = mview.findViewById(R.id.rate);
            TextView text_dates = mview.findViewById(R.id.dates);
            ImageView image=(ImageView)mview.findViewById(R.id.options_data);

            //adding data to views
            textViewName.setText(employee.getF_name());
            // textViewDept.setText(employee.getPrice());
            text_dates.setText(employee.getDates());
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    AlertDialog.Builder ad=new AlertDialog.Builder(MainActivity.this);
                    ad.setTitle("Sure to delete");
                    ad.setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            dialogInterface.dismiss();
                        }
                    });
                    ad.setNegativeButton("delete", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try
                            {
                                String remove_qry = "delete from sample where name='" + employee.getF_name() + "'";
                                sdb.execSQL(remove_qry);
                                Toast.makeText(MainActivity.this, "removed succesfully", Toast.LENGTH_SHORT).show();
                                mlist.notifyAll();
                                aa.notifyDataSetChanged();
                            }catch (Exception e)
                            {

                            }
                        }
                    });
                    ad.show();
                }
            });
            return mview;
        }
    }
}
