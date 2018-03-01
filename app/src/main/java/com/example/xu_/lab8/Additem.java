package com.example.xu_.lab8;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by xu_ on 2017/12/19.
 */

public class Additem extends AppCompatActivity {
    public myDB myDataBase;
    EditText ET_name;
    EditText ET_birth;
    EditText ET_gift;
    Button BT_add;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        ET_name = (EditText)findViewById(R.id.nameEdit);
        ET_birth = (EditText)findViewById(R.id.birthdayEdit);
        ET_gift = (EditText)findViewById(R.id.giftEdit);
        BT_add = (Button) findViewById(R.id.addBT);
        myDataBase = new myDB(Additem.this);

        BT_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ET_name.getText().toString();
                if (name.length()==0){
                    Toast.makeText(Additem.this, "名字为空，请完善", Toast.LENGTH_SHORT).show();
                }
                else{
                    Integer id = myDataBase.insert(name, ET_birth.getText().toString(), ET_gift.getText().toString());
                    Intent intent = new Intent(Additem.this, MainActivity.class);
                    intent.putExtra("id", id);
                    setResult(123, intent);
                    finish();
                }
            }
        });
    }
}
