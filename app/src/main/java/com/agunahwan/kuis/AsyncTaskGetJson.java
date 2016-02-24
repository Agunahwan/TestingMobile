package com.agunahwan.kuis;

import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by InspiroPC on 09/12/2015.
 */
public class AsyncTaskGetJson extends AsyncTask<String, Integer, String> {

    @Override
    protected String doInBackground(String... params) {
        String resultJson = "";
        ServiceHandler serviceHandler = new ServiceHandler();
        if (params[1] == null)
            resultJson = serviceHandler.makeServiceCall(params[0], ServiceHandler.GET);
        else {
            List<NameValuePair> nameValuePairs = null;
            switch (params[1]) {
                case CommonEnum.LOGIN:
                    nameValuePairs = new ArrayList<NameValuePair>(3);
                    nameValuePairs.add(new BasicNameValuePair("get_login", params[1]));
                    nameValuePairs.add(new BasicNameValuePair("username", params[2]));
                    nameValuePairs.add(new BasicNameValuePair("password", params[3]));
                    break;
                case CommonEnum.GET_SOAL:
                    nameValuePairs = new ArrayList<NameValuePair>(3);
                    nameValuePairs.add(new BasicNameValuePair("get_soal", params[1]));
                    nameValuePairs.add(new BasicNameValuePair("id_tipe_soal", params[2]));
                    nameValuePairs.add(new BasicNameValuePair("urutan", params[3]));
                    break;
                case CommonEnum.COUNT:
                    nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("get_count_soal", params[1]));
                    nameValuePairs.add(new BasicNameValuePair("id_tipe_soal", params[2]));
                    break;
                case CommonEnum.SCORE:
                    nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("update_nilai", params[1]));
                    nameValuePairs.add(new BasicNameValuePair("id_user", params[2]));
                    break;
                case CommonEnum.GET_NILAI:
                    nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("get_nilai", params[1]));
                    nameValuePairs.add(new BasicNameValuePair("id_user", params[2]));
                    break;
                case CommonEnum.SAVE:
                    nameValuePairs = new ArrayList<NameValuePair>(4);
                    nameValuePairs.add(new BasicNameValuePair("insert_jawaban", params[1]));
                    nameValuePairs.add(new BasicNameValuePair("id_user", params[2]));
                    nameValuePairs.add(new BasicNameValuePair("id_soal", params[3]));
                    nameValuePairs.add(new BasicNameValuePair("jawaban", params[4]));
                    break;
            }
            resultJson = serviceHandler.makeServiceCall(params[0], ServiceHandler.GET, nameValuePairs);
        }

        return resultJson;
    }
}
