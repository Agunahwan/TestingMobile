package com.agunahwan.kuis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class TipeSoalActivity extends AppCompatActivity {

    Intent intent;
    //Declare Tipe Soal
    Button btn_programmer, btn_ba, btn_qc, btn_sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipe_soal);

        //Set Object
        btn_programmer = (Button) findViewById(R.id.btn_programmer);
        btn_ba = (Button) findViewById(R.id.btn_ba);
        btn_qc = (Button) findViewById(R.id.btn_qc);
        btn_sa = (Button) findViewById(R.id.btn_sa);

        Bundle bundle = getIntent().getExtras();

        intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("id_user", bundle.getString("id_user"));

        btn_programmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("tipe_soal", "1");
                startActivity(intent);
                finish();
            }
        });

        btn_ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("tipe_soal", "2");
                startActivity(intent);
                finish();
            }
        });

        btn_qc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("tipe_soal", "3");
                startActivity(intent);
                finish();
            }
        });

        btn_sa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("tipe_soal", "4");
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tipe_soal, menu);
        return true;
    }
}
