package com.agunahwan.kuis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ScoreFinalActivity extends AppCompatActivity {

    //Declare object
    TextView txt_score;
    Button btn_close;

    private static String url = CommonEnum.URL;

    AsyncTaskGetJson asyncTaskGetJson;
    String idUser;
    String strJson = "";
    JSONObject jsonObject;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_final);

        //Define object
        txt_score = (TextView) findViewById(R.id.txt_score);
        btn_close = (Button) findViewById(R.id.btn_close);

        Bundle bundle = getIntent().getExtras();
        idUser = bundle.getString("id_user");

        //************* Get Score ***************
        try {
            asyncTaskGetJson = new AsyncTaskGetJson();
            strJson = asyncTaskGetJson.execute(url, CommonEnum.GET_NILAI, idUser).get();

            jsonObject = new JSONObject(strJson);
            strJson = jsonObject.getString("GetNilaiResult");
            jsonArray = new JSONArray(strJson);
            jsonObject = jsonArray.getJSONObject(0);

            txt_score.setText(jsonObject.getString("nilai"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //*************** End Get Score *******************

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score_final, menu);
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
