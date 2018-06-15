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

public class BotonHumedad extends AppCompatActivity {

    WaveLoadingView visorH ;
    WaveLoadingView visorH2 ;
    WaveLoadingView visorH3 ;

    private TextView humedad_valor;
    private int valormayorhumedad=0;
    private int valormenorhumedad=1000;
    private int conthumedad=0;
    private int valorpromediohumedad=0;

    String fechaHumedad=MainActivity.auxfecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boton_humedad);

        serviceHumedad();
    }

    private void serviceHumedad(){
        Log.d("LOG WS", "entre");
        String WS_URL = "http://arrau.chillan.ubiobio.cl:8075/ubbiot/web/mediciones/medicionespordia/alWDCIh3cJ/VIbSnGKyLW/"+fechaHumedad;
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
                                if(valormayorhumedad<valorInt){
                                    valormayorhumedad=valorInt;
                                }
                                if (valormenorhumedad> valorInt) {
                                    valormenorhumedad = valorInt;
                                }
                                conthumedad+=valorInt;
                            }
                            valorpromediohumedad=(conthumedad/listaMedicion.length());
                            mayorHumedad();
                            menorHumedad();
                            promedioHumedad();

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

    private void mayorHumedad(){
        //humedad_valor=(TextView) findViewById(R.id.mayor_humedad);
        //humedad_valor.setText(Integer.toString(valormayorhumedad));

        visorH = findViewById(R.id.VisorHum);
        visorH.setProgressValue(valormayorhumedad);
        visorH.setBottomTitle("+Humedad: "+ valormayorhumedad);
        visorH.setTopTitle("");
    }
    private void menorHumedad(){
       // humedad_valor=(TextView) findViewById(R.id.menor_humedad);
        //humedad_valor.setText(Integer.toString(valormenorhumedad));
        visorH2 = findViewById(R.id.VisorHum2);
        visorH2.setProgressValue(valormenorhumedad);
        visorH2.setBottomTitle("-Humedad: "+ valormenorhumedad);
        visorH2.setTopTitle("");
    }
    private void promedioHumedad(){
      //  humedad_valor=(TextView) findViewById(R.id.promedio_humedad);
        //humedad_valor.setText(Integer.toString(valorpromediohumedad));
        visorH3 = findViewById(R.id.VisorHum3);
        visorH3.setProgressValue(valorpromediohumedad);
        visorH3.setBottomTitle("Promedio: "+ valorpromediohumedad);
        visorH3.setTopTitle("");
    }

}
