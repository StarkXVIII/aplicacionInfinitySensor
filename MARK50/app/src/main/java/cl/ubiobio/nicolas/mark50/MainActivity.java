package cl.ubiobio.nicolas.mark50;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridLayout;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;



public class MainActivity extends AppCompatActivity {

    GridLayout mainGrid;

    TextView tv;
    Calendar mCurrentDate;
    int day, month, year;
    static String auxfecha;
    String numero="";
    int numeroAux1=0;
    int numeroAux2=0;
    int numeroAux3=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv =(TextView) findViewById(R.id.textView11);
        mCurrentDate = Calendar.getInstance();
        day=mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month=mCurrentDate.get(Calendar.MONTH);
        year=mCurrentDate.get(Calendar.YEAR);
        month=month+1;
        tv.setText(26+"0"+5+""+2018);

        auxfecha="26052018";


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfyear, int dayOfMonth) {
                        monthOfyear = monthOfyear+1;

                        if(dayOfMonth<10&&monthOfyear<10){
                            tv.setText("0"+dayOfMonth+"0"+monthOfyear+""+year);
                            auxfecha=("0"+dayOfMonth+"0"+monthOfyear+""+year);
                        }
                        if (dayOfMonth<10&&monthOfyear>=10){
                            tv.setText("0"+dayOfMonth+""+monthOfyear+""+year);
                            auxfecha=("0"+dayOfMonth+""+monthOfyear+""+year);
                        }
                        if(dayOfMonth>=10&&monthOfyear<10){
                            tv.setText(dayOfMonth+"0"+monthOfyear+""+year);
                            auxfecha=(dayOfMonth+"0"+monthOfyear+""+year);
                        }
                        if(dayOfMonth>10&&monthOfyear>10){
                            tv.setText(dayOfMonth+""+monthOfyear+""+year);
                            auxfecha=(dayOfMonth+""+monthOfyear+""+year);
                        }
                        if(dayOfMonth>26&&monthOfyear>5){
                            numero=auxfecha;
                            numeroAux1=Integer.parseInt(numero);
                            numeroAux2=Integer.parseInt(numero);
                            numeroAux3=Integer.parseInt(numero);
                            numeroAux1=numeroAux1+50000000;
                            numeroAux2=numeroAux1+50000000;
                            numeroAux3=numeroAux1+50000000;
                        }
                        if(dayOfMonth<26&&monthOfyear>5){
                            numero=auxfecha;
                            numeroAux1=Integer.parseInt(numero);
                            numeroAux2=Integer.parseInt(numero);
                            numeroAux3=Integer.parseInt(numero);
                            numeroAux1=numeroAux1+50000000;
                            numeroAux2=numeroAux1+50000000;
                            numeroAux3=numeroAux1+50000000;
                        }


                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        mainGrid = (GridLayout)findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);
    }



    private void setSingleEvent(GridLayout mainGrid) {

        for (int i = 0; i <mainGrid.getChildCount(); i++)
        {
            CardView cardView = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (finalI ==0){

                        if (numeroAux1>26052018){
                            Intent temperatura= new Intent(MainActivity.this,ErrorFecha.class);
                            startActivity(temperatura);

                        }else {
                            Intent temperatura = new Intent(MainActivity.this, BotonTemperatura.class);
                            startActivity(temperatura);
                        }
                        numeroAux1=0;
                    }if (finalI ==1){
                        if (numeroAux2>26052018){
                            Intent humedad= new Intent(MainActivity.this,ErrorFecha.class);
                            startActivity(humedad);

                        }else {
                            Intent humedad = new Intent(MainActivity.this, BotonHumedad.class);
                            startActivity(humedad);
                        }
                        numeroAux2=0;
                    }
                    if (finalI ==2){
                        if (numeroAux3>26052018){
                            Intent radiacion= new Intent(MainActivity.this,ErrorFecha.class);
                            startActivity(radiacion);

                        }else {
                            Intent radiacion = new Intent(MainActivity.this, BotonRadiacion.class);
                            startActivity(radiacion);

                        }
                        numeroAux3=0;
                    }

                     }
            });
           }

        }

}
