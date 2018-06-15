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

public class BotonTemperatura extends AppCompatActivity {
    WaveLoadingView visor ;
    WaveLoadingView visor2 ;
    WaveLoadingView visor3 ;

    private TextView temperatura_valor;
    private int valormayortemperatura=0;
    private int valormenortemperatura=100;
    private int conttemperatura=0;
    private int valorpromediotemperatura=0;

    String fechaTemperatura=MainActivity.auxfecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boton_temperatura);

        serviceTemperatura();
    }

    private void serviceTemperatura(){

        Log.d("LOG WS", "entre");
        String WS_URL = "http://arrau.chillan.ubiobio.cl:8075/ubbiot/web/mediciones/medicionespordia/alWDCIh3cJ/E1yGxKAcrg/"+fechaTemperatura;
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
                                if(valormayortemperatura<valorInt){
                                    valormayortemperatura=valorInt;
                                }
                                if (valormenortemperatura> valorInt) {
                                    valormenortemperatura = valorInt;
                                }
                                conttemperatura+=valorInt;
                                /*Temperatura temp = new Temperatura();
                                temp.setFecha(o.getString("fecha"));
                                temp.setHora(o.getString("hora"));
                                temp.setValor(o.getString("valor"));
                                medicionTemperatura.add(temp);*/
                            }
                            valorpromediotemperatura=(conttemperatura/listaMedicion.length());
                            mayorTemperatura();
                            menorTemperatura();
                            promedioTemperatura();

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

    private void mayorTemperatura(){
     //   temperatura_valor=(TextView) findViewById(R.id.mayor_temperatura);
       // temperatura_valor.setText(Integer.toString(valormayortemperatura));
        visor = findViewById(R.id.VisorTemp);
        visor.setProgressValue(valormayortemperatura*3);
        visor.setBottomTitle("+Temperatura: "+ valormayortemperatura+ "°");
        visor.setTopTitle("");
    }
    private void menorTemperatura(){
       // temperatura_valor=(TextView) findViewById(R.id.menor_temperatura);
        //temperatura_valor.setText(Integer.toString(valormenortemperatura));
        visor2 = findViewById(R.id.VisorTemp2);
        visor2.setProgressValue(valormenortemperatura*3);
        visor2.setBottomTitle("-Temperatura: "+ valormenortemperatura+ "°");
        visor2.setTopTitle("");
    }
    private void promedioTemperatura(){
        //temperatura_valor=(TextView) findViewById(R.id.promedio_temperatura);
        //temperatura_valor.setText(Integer.toString(valorpromediotemperatura));
        visor3 = findViewById(R.id.VisorTemp3);
        visor3.setProgressValue(valorpromediotemperatura*3);
        visor3.setBottomTitle("Promedio : "+ valorpromediotemperatura+ "°");
        visor3.setTopTitle("");
    }
}
