package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    EditText et1;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9;
    Button b1;
    int count=0;
    ImageView iv1,iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=(EditText) findViewById(R.id.idEDcity);
        iv1 = (ImageView) findViewById(R.id.idIVIcon);
        tv1=(TextView) findViewById(R.id.tvTemp);
        tv2=(TextView)findViewById(R.id.tvicn);
        tv3=(TextView) findViewById(R.id.tvicooon);
        tv4=(TextView) findViewById(R.id.currentloc);
        tv5=(TextView) findViewById(R.id.co);
        tv6=(TextView) findViewById(R.id.humi);
        tv7=(TextView) findViewById(R.id.sunrise);
        tv8=(TextView) findViewById(R.id.sunset);
        tv9=(TextView) findViewById(R.id.currenttime);

    }

        public void get(View v){

         String api_key1="0903eca886c922976b2af72a95443d36";
         String city=et1.getText().toString();

            String outcap = city.substring(0, 1).toUpperCase() + city.substring(1);

         tv4.setText(outcap);
         String url1="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=0903eca886c922976b2af72a95443d36";

            RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray=response.getJSONArray("weather");
                        JSONObject weather= jsonArray.getJSONObject(0);

                        String icn=weather.getString("icon");
                        Picasso.with(MainActivity.this).load("https://openweathermap.org/img/wn/"+icn+"@2x.png").into(iv1);

                        String condition=weather.getString("main");
                        tv3.setText(condition);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        JSONObject object1= response.getJSONObject("main");
                        String tempera=object1.getString("temp");
                        String hum=object1.getString("humidity");
                        tv6.setText(hum+"%");

                        Double tem=Double.parseDouble(tempera)-272.15;

                        tv1.setText(tem.toString().substring(0,5)+"°C");

                        try {

                            tv1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    count++;

                                    if (count == 1) {
                                        tv1.setText(tempera.substring(0, 5) + "K");


                                    } else if (count == 2) {
                                        double faren = tem * (9 / 5) + 32;
                                        String farestr = String.valueOf(faren).substring(0, 5) + "°F";
                                        tv1.setText(farestr);

                                    } else if (count == 3) {
                                        tv1.setText(tem.toString().substring(0, 5) + "°C");
                                        count = 0;
                                    }

                                }
                            });
                        }

                        catch (Exception e){
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                        JSONObject object2= response.getJSONObject("wind");
                        String wind=object2.getString("speed");
                        tv2.setText(wind+"km");


                        JSONObject object6= response.getJSONObject("sys");
                        String sunrise  =object6.getString("sunrise");
                        String sunset  =object6.getString("sunset");

                        //sunrise time
                        Long snrise=Long.parseLong(sunrise);

                        //convert seconds to milliseconds
                        Date date = new Date(snrise*1000L);
                        // format of the date
                        SimpleDateFormat jdf = new SimpleDateFormat("HH:mm aa");
                        jdf.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                        String java_date = jdf.format(date);
                        tv7.setText(java_date);

                        //sunset timing
                        Long snset=Long.parseLong(sunset);

                        //convert seconds to milliseconds
                        Date date1 = new Date(snset*1000L);
                        // format of the date
                        SimpleDateFormat jdf1 = new SimpleDateFormat("HH:mm aa");
                        jdf1.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                        String java_date1 = jdf1.format(date1);
                        tv8.setText(java_date1);

                       //Current timing
                        String currenttime  =response.getString("dt");

                        //   Integer curint=Integer.parseInt(currenttime);
                        Long currentlong= Long.valueOf(currenttime);

                        //convert seconds to milliseconds
                        Date date2 = new Date(currentlong*1000L);
                        // format of the date
                        SimpleDateFormat jdf2 = new SimpleDateFormat("HH:mm aa");
                        jdf2.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                        String java_date2 = jdf2.format(date2);
                        tv9.setText(java_date2);

                        JSONObject object3= response.getJSONObject("coord");
                        String lon=object3.getString("lon");
                        String lat=object3.getString("lat");

                        String api_key1="0903eca886c922976b2af72a95443d36";
                        String url2="https://api.openweathermap.org/data/2.5/air_pollution/forecast?lat="+lat+"&lon="+lon+"&appid=0903eca886c922976b2af72a95443d36";
                        RequestQueue queue2= Volley.newRequestQueue(getApplicationContext());
                        JsonObjectRequest request2=new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {


                                try {
                                    JSONArray jsonArray2=response.getJSONArray("list");

                                    JSONObject lists= jsonArray2.getJSONObject(0);

                                    JSONObject object5= lists.getJSONObject("components");

                                    String co=object5.getString("co");
                                    String no2=object5.getString("no2");
                                    String o3=object5.getString("o3");
                                    String nh3=object5.getString("nh3");
                                    String so2=object5.getString("so2");
                                    String pm2_5=object5.getString("pm2_5");
                                    String pm10=object5.getString("pm10");

                                  String output =" CO : "+ co
                                            +"\n No2  :"+ no2
                                            +"\n O3 :"+ o3
                                            +"\n NH3 :"+ nh3
                                            +"\n SO2 :"+ so2
                                            +"\n PM2.5 :"+ pm2_5
                                            +"\n PM10 :"+ pm10 ;

                                    tv5.setText(output);

                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                        queue2.add(request2);
                    } catch (JSONException e) {

                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            queue.add(request);
    }
}