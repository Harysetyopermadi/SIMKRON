package com.harysetyopermadi.simkron.ui.dashboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.harysetyopermadi.simkron.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class DashboardFragment extends Fragment {

    static ArcProgress nsuhu,nph,nnutrisi,nair;
    static TextView vssuhu,vsph,vsnutrisi,vsair;

    static int suhuValue=0;
    static int nutrisiValue=0;
    static int airValue=0;
    static float phValue=0;


    static String suhuJSON,phJSON,nutrisiJSON,airJSON;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        nsuhu=root.findViewById(R.id.pbsuhu);
        nph=root.findViewById(R.id.pbph);
        nnutrisi=root.findViewById(R.id.pbnutrisi);
        nair=root.findViewById(R.id.pbair);

        String urlsuhu= "https://api.thingspeak.com/channels/1306883/feeds/last.json?api_key=";
        String apikeysuhu="8P9N316XVZ69EAMP";
        String urlph="https://api.thingspeak.com/channels/1161055/feeds/last.json?api_key=";
        String apikeyph="QM44R0VR1B9AA0CP";
        String urlnutrisi="https://api.thingspeak.com/channels/1144805/feeds/last.json?api_key=";
        String apikeynutrisi="NVR76S2NN2FES1C4";
        String urlair="https://api.thingspeak.com/channels/1147056/feeds/last.json?api_key=";
        String apikeyair="73Y9KC3O8VSKLO2H";
        final DashboardFragment.UriApiSuhu uriApiSuhu01 = new UriApiSuhu();


        uriApiSuhu01.setUrisuhu(urlsuhu,apikeysuhu,urlph,apikeyph,urlnutrisi,apikeynutrisi,urlair,apikeyair);
        Timer timer = new Timer();
        TimerTask tasknew =new TimerTask() {
            @Override
            public void run() {
                LoadJSONsuhu task=new LoadJSONsuhu();
                task.execute(uriApiSuhu01.getUrisuhu());
                LoadJSONph taskph=new LoadJSONph();
                taskph.execute(uriApiSuhu01.getUriph());
                LoadJSONnutrisi tasknutrisi=new LoadJSONnutrisi();
                tasknutrisi.execute(uriApiSuhu01.getUrinutrisi());
                LoadJSONair taskair=new LoadJSONair();
                taskair.execute(uriApiSuhu01.getUriair());
            }
        };
        timer.scheduleAtFixedRate(tasknew,1*2000,1*2000);



        ///webviewnya air
        WebView airGraph = root.findViewById(R.id.webair);
        airGraph.getSettings().setJavaScriptEnabled(true);
        airGraph.setInitialScale(210);
        airGraph.setWebViewClient(new WebViewClient());
        airGraph.loadUrl("https://thingspeak.com/channels/1147056/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15");

        WebView phGraph = root.findViewById(R.id.webph);
        phGraph.getSettings().setJavaScriptEnabled(true);
        phGraph.setInitialScale(210);
        phGraph.setWebViewClient(new WebViewClient());
        phGraph.loadUrl("https://thingspeak.com/channels/1161055/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15");

        WebView nutrisiGraph = root.findViewById(R.id.webnutrisi);
        nutrisiGraph.getSettings().setJavaScriptEnabled(true);
        nutrisiGraph.setInitialScale(210);
        nutrisiGraph.setWebViewClient(new WebViewClient());
        nutrisiGraph.loadUrl("https://thingspeak.com/channels/1144805/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&type=line&update=15");

        WebView suhuGraph = root.findViewById(R.id.websuhu);
        suhuGraph.getSettings().setJavaScriptEnabled(true);
        suhuGraph.setInitialScale(210);
        suhuGraph.setWebViewClient(new WebViewClient());
        suhuGraph.loadUrl("https://thingspeak.com/channels/1122589/charts/1?bgcolor=%23ffffff&color=%23d62020&dynamic=true&results=60&title=Temperature&type=line");





        return root;
    }

    static class UriApiSuhu {

        private String urisuhu,urlsuhu,apikeysuhu,uriph,urlph,apikeyph,urinutrisi,urlnutrisi,apikeynutrisi,uriair,urlair,apikeyair;
        protected void setUrisuhu(String urlsuhu,String apikeysuhu,String urlph,String apikeyph,String urlnutrisi,String apikeynutrisi,String urlair,String apikeyair){
            this.urlsuhu=urlsuhu;
            this.apikeysuhu=apikeysuhu;
            this.urisuhu=urlsuhu+apikeysuhu;

            this.urlph=urlph;
            this.apikeyph=apikeyph;
            this.uriph=urlph+apikeyph;

            this.urlnutrisi=urlnutrisi;
            this.apikeynutrisi=apikeynutrisi;
            this.urinutrisi=urlnutrisi+apikeynutrisi;

            this.urlair=urlair;
            this.apikeyair=apikeyair;
            this.uriair=urlair+apikeyair;

        }
        protected String getUrisuhu(){
            return urisuhu;
        }
        protected String getUriph(){
            return uriph;
        }
        protected String getUrinutrisi(){
            return urinutrisi;
        }
        protected String getUriair(){
            return uriair;
        }
    }

    private class LoadJSONsuhu extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... urls) {
            return getText(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject json = new JSONObject(result);

                suhuJSON = String.format("%s", json.getString("field1"));


            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            nsuhu.setProgress(Integer.parseInt(""+suhuJSON+""));
            Log.d("VarX", ""+suhuJSON);

            try
            {   if(suhuJSON!=null) {
                suhuValue = Integer.parseInt(suhuJSON);
            }
            }
            catch(NumberFormatException nfe){}

        }
    }



    private class LoadJSONph extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return (String) getText(urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject json = new JSONObject(result);

                phJSON = String.format("%s", json.getString("field1"));



            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            float phd;
            phd = Float.parseFloat(""+phJSON+"");
            int a= (int) ((phd*100)/10.24);
            nph.setProgress(a);
         //   nph.setProgress((int) Float.parseFloat(""+a+""));

            Log.d("VarX", ""+phJSON);

            try
            {   if(phJSON!=null) {
                phValue = Integer.parseInt(phJSON);
            }
            }
            catch(NumberFormatException nfe){}

        }
    }


private class LoadJSONnutrisi extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... urls) {
        return getText(urls[0]);
    }

    @Override
    protected void onPostExecute(String result) {


        try {
            JSONObject json = new JSONObject(result);

            nutrisiJSON = String.format("%s", json.getString("field1"));


        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        int nutisib= Integer.parseInt((""+nutrisiJSON+""));
        int nutisic=(nutisib*100)/1024;
        nnutrisi.setProgress(nutisic);
       // nnutrisi.setProgress(Integer.parseInt(""+nutrisiJSON+""));
        Log.d("VarX", ""+nutrisiJSON);

        try
        {   if(nutrisiJSON!=null) {
            nutrisiValue = Integer.parseInt(nutrisiJSON);
        }
        }
        catch(NumberFormatException nfe){}

    }
}

    private class LoadJSONair extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... urls) {
            return getText(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {


            try {
                JSONObject json = new JSONObject(result);

                airJSON = String.format("%s", json.getString("field1"));


            } catch (JSONException e)
            {
                e.printStackTrace();
            }
            int airb= Integer.parseInt((""+airJSON+""));
            int airc=((40-airb)*100)/40;
            nair.setProgress(airc);
            // nnutrisi.setProgress(Integer.parseInt(""+nutrisiJSON+""));
            Log.d("VarX", ""+airJSON);

            try
            {   if(airJSON!=null) {
                airValue = Integer.parseInt(airJSON);
            }
            }
            catch(NumberFormatException nfe){}

        }
    }


    private static String getText(String strUrl) {
        String strResult = "";
        try {
            URL url = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            strResult = readStream(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResult;
    }

    private static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}