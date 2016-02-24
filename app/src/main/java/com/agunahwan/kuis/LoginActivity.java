package com.agunahwan.kuis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    public static String url = CommonEnum.URL;

    //Declare Object
    TextView tv_username, tv_password;
    EditText edt_username, edt_password;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Declare Object
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_password = (TextView) findViewById(R.id.tv_password);
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUsername = edt_username.getText().toString();
                String strPassword = edt_password.getText().toString();
                String strJson = "";
                String idUser = "";
                JSONObject jsonObject;
                JSONArray jsonArray;

                AsyncTaskGetJson asyncTaskGetJson = new AsyncTaskGetJson();
                try {
                    strJson = asyncTaskGetJson.execute(url, CommonEnum.LOGIN, strUsername, strPassword).get();

                    jsonObject = new JSONObject(strJson);
                    strJson = jsonObject.getString("GetLoginResult");
                    jsonArray = new JSONArray(strJson);
                    jsonObject = jsonArray.getJSONObject(0);
                    idUser = jsonObject.getString("id_user");
                    String strNilai = jsonObject.getString("nilai");

                    Intent intent;
                    if (strNilai == "null") {
                        intent = new Intent(getApplicationContext(), TipeSoalActivity.class);
                    } else {
                        intent = new Intent(getApplicationContext(), ScoreFinalActivity.class);
                    }
                    intent.putExtra("id_user", idUser);
                    startActivity(intent);
                    finish();
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
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }
}
