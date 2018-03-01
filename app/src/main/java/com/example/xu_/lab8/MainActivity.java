package com.example.xu_.lab8;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.Manifest;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public myDB myDataBase;
    Button add;
    MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPermission();

        ListView list = (ListView) findViewById(R.id.list);
        add = (Button) findViewById(R.id.add);
        myDataBase = new myDB(MainActivity.this);
        myAdapter = new MyAdapter(MainActivity.this, myDataBase.queryAll());

        list.setAdapter(myAdapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Additem.class);
                startActivityForResult(intent, 123);
            }
        });

        myAdapter.onItemClickListener = new MyAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(final View view, int pos) {
                final Item item = myAdapter.getItem(pos);
                final View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialoglayout, null);
                ((TextView)view1.findViewById(R.id.name_dialog)).setText(item.name);
                ((TextView)view1.findViewById(R.id.birthday_dialog)).setText(item.birth);
                ((TextView)view1.findViewById(R.id.gift_dialog)).setText(item.gift);
                ((TextView)view1.findViewById(R.id.phone_dialog)).setText(getPhoneByName(item.name));

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(view1)
                        .setPositiveButton("确认修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                item.birth = ((TextView) view1.findViewById(R.id.birthday_dialog)).getText().toString();
                                item.gift = ((TextView) view1.findViewById(R.id.gift_dialog)).getText().toString();
                                myDataBase.update(item.id, item.name, item.birth, item.gift);
                                myAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("放弃修改", null)
                        .show();
            }

            @Override
            public void onItemLongClick(View view, final int pos) {
                final Item item = myAdapter.getItem(pos);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("是否删除？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myDataBase.delete(item.id);
                                myAdapter.list.remove(pos);
                                myAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();
            }
        };

    }

    private String getPhoneByName(String name){
        try{
            Cursor cursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    String.format("%s = '%s'", ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, name),
                    null, null
            );
            StringBuilder res = new StringBuilder();
            while (cursor.moveToNext()){
                res.append(cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                )));
                if(!cursor.isLast()){
                    res.append('\n');
                }
            }
            cursor.close();
            return res.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public void getPermission(){
        int permission = ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_CONTACTS);
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_CONTACTS}, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(!(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)){
            System.exit(0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==123 && data.hasExtra("id")){
            myAdapter.list.add(myDataBase.query(data.getIntExtra("id", -1)));
            myAdapter.notifyDataSetChanged();
        }
    }
}
