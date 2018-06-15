package cl.ubiobio.nicolas.mark50;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.itangqi.waveloadingview.WaveLoadingView;

public class BotonRadiacion extends AppCompatActivity {

    WaveLoadingView visorR ;
    WaveLoadingView visorR2 ;
    WaveLoadingView visorR3 ;

    private TextView radiacion_valor;
    private int valormayorradiacion=0;
    private int valormenorradiacion=1000;
    private int contradiacion=0;
    private int valorpromedioradiacion=0;

    String fechaRadiacion=MainActivity.auxfecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boton_radiacion);

        serviceRadiacion();
    }

    private void serviceRadiacion(){
        Log.d("LOG WS", "entre");
        String WS_URL = "http://arrau.chillan.ubiobio.cl:8075/ubbiot/web/mediciones/medicionespordia/alWDCIh3cJ/8IvrZCP3qa/"+fechaRadiacion;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(
                Request.Method.GET,
                WS_URL,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject objetoData = new JSONObject(response);
                            JSONArray listaMedicion = objetoData.getJSONArray("data");

                            for(int i = 0; i < listaMedicion.length(); i++){
                                JSONObject o = listaMedicion.getJSONObject(i);

                                String valor =o.getString("valor");

                                int valorInt=Integer.parseInt(valor);
                                if(valormayorradiacion<valorInt){
                                    valormayorradiacion=valorInt;
                                }
                                if (valormenorradiacion> valorInt) {
                                    valormenorradiacion = valorInt;
                                }
                                contradiacion+=valorInt;

                            }
                            valorpromedioradiacion=(contradiacion/listaMedicion.length());
                            mayorRadiacion();
                            menorRadiacion();
                            promedioRadiacion();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LOG WS", error.toString());
                generateToast("Error en el WEB Service");
            }
        }
        );
        requestQueue.add(request);
    }

    private void generateToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    private void mayorRadiacion(){
        //radiacion_valor=(TextView) findViewById(R.id.mayor_radiacion);
        //radiacion_valor.setText(Integer.toString(valormayorradiacion));
        visorR = findViewById(R.id.VisorRad);
        visorR.setProgressValue(valormayorradiacion/4);
        visorR.setBottomTitle("+Radiación: "+ valormayorradiacion);
        visorR.setTopTitle("");
    }
    private void menorRadiacion(){
        //radiacion_valor=(TextView) findViewById(R.id.menor_radiacion);
        //radiacion_valor.setText(Integer.toString(valormenorradiacion));
        visorR2 = findViewById(R.id.VisorRad2);
        visorR2.setProgressValue(valormenorradiacion/4);
        visorR2.setBottomTitle("-Radiación: "+ valormenorradiacion);
        visorR2.setTopTitle("");
    }
    private void promedioRadiacion(){
       // radiacion_valor=(TextView) findViewById(R.id.promedio_radiacion);
        //radiacion_valor.setText(Integer.toString(valorpromedioradiacion));
        visorR3 = findViewById(R.id.VisorRad3);
        visorR3.setProgressValue(valorpromedioradiacion/4);
        visorR3.setBottomTitle("Promedio: "+ valorpromedioradiacion);
        visorR3.setTopTitle("");
    }
}
