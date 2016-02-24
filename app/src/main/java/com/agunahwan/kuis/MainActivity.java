package com.agunahwan.kuis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private static String url = CommonEnum.URL;

    int noSoal;
    int jumlahSoal;

    AsyncTaskGetJson asyncTaskGetJson;
    String strJson = "";
    String idUser, idTipeSoal;
    int idSoal;
    JSONObject jsonObject;
    JSONArray jsonArray;

    //Declare Object View
    TextView txt_soal;
    RadioButton rbt_a, rbt_b, rbt_c, rbt_d;
    RadioGroup rbg_jawaban;
    Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Define object
        txt_soal = (TextView) findViewById(R.id.txt_soal);
        rbt_a = (RadioButton) findViewById(R.id.rbt_a);
        rbt_b = (RadioButton) findViewById(R.id.rbt_b);
        rbt_c = (RadioButton) findViewById(R.id.rbt_c);
        rbt_d = (RadioButton) findViewById(R.id.rbt_d);
        rbg_jawaban = (RadioGroup) findViewById(R.id.rbg_jawaban);
        btn_next = (Button) findViewById(R.id.btn_next);

        Bundle bundle = getIntent().getExtras();
        idTipeSoal = bundle.getString("tipe_soal");
        idUser = bundle.getString("id_user");

        if (bundle != null && bundle.containsKey("no_soal")) {
            noSoal = bundle.getInt("no_soal");
            jumlahSoal = bundle.getInt("jumlah_soal");
        } else {
            noSoal = 1;
            //************** Get Count Soal *****************
            try {
                asyncTaskGetJson = new AsyncTaskGetJson();
                strJson = asyncTaskGetJson.execute(url, CommonEnum.COUNT, idTipeSoal).get();

                //Get Data JSON
                jsonObject = new JSONObject(strJson);
                jumlahSoal = jsonObject.getInt("GetCountSoalResult");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //************ End Get Count Soal *****************
        }

        //*************** Get Soal **********************
        try {
            asyncTaskGetJson = new AsyncTaskGetJson();
            strJson = asyncTaskGetJson.execute(url, CommonEnum.GET_SOAL, idTipeSoal, String.valueOf(noSoal)).get();

            jsonObject = new JSONObject(strJson);
            strJson = jsonObject.getString("GetSoalResult");
            jsonArray = new JSONArray(strJson);
            jsonObject = jsonArray.getJSONObject(0);

            txt_soal.setText(jsonObject.getString("soal"));
            rbt_a.setText(jsonObject.getString("jawaban_a"));
            rbt_b.setText(jsonObject.getString("jawaban_b"));
            rbt_c.setText(jsonObject.getString("jawaban_c"));
            rbt_d.setText(jsonObject.getString("jawaban_d"));
            idSoal = jsonObject.getInt("id_soal");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //************* End Get Soal *****************

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //*************** Save Jawaban ******************
                String jawaban = "";
                int id_rbt_checked = rbg_jawaban.getCheckedRadioButtonId();

                if (id_rbt_checked == rbt_a.getId()) jawaban = "A";
                else if (id_rbt_checked == rbt_b.getId()) jawaban = "B";
                else if (id_rbt_checked == rbt_c.getId()) jawaban = "C";
                else if (id_rbt_checked == rbt_d.getId()) jawaban = "D";

                try {
                    asyncTaskGetJson = new AsyncTaskGetJson();

                    strJson = asyncTaskGetJson.execute(url, CommonEnum.SAVE, idUser, String.valueOf(idSoal), jawaban).get();
                    jsonObject = new JSONObject(strJson);
                    strJson = jsonObject.getString("InsertJawabanResult");
                    jsonArray = new JSONArray(strJson);
                    jsonObject = jsonArray.getJSONObject(0);

                    String result = jsonObject.getString("Result");

                    //Jika jawaban telah tersimpan, pindah ke halaman selanjutnya atau menampilkan nilai
                    if (result.equals("1")) {
                        noSoal++;
                        Intent intent;

                        if (noSoal > jumlahSoal) {
                            asyncTaskGetJson = new AsyncTaskGetJson();

                            //Update Nilai
                            strJson = asyncTaskGetJson.execute(url, CommonEnum.SCORE, idUser).get();

                            intent = new Intent(getApplicationContext(), ScoreFinalActivity.class);
                        } else {
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("no_soal", noSoal);
                            intent.putExtra("jumlah_soal", jumlahSoal);
                            intent.putExtra("id_user", idUser);
                            intent.putExtra("tipe_soal", idTipeSoal);
                        }
                        startActivity(intent);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
