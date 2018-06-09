package it.polito.ami.amiclean;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class MainActivity extends AppCompatActivity {


    ToggleButton button;
    TextView status;
    RatingBar rating;
    Button ratingButton;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, Servizio.class));
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.ONOFF);
        status = findViewById(R.id.status);
        rating = findViewById(R.id.rating);
        ratingButton=findViewById(R.id.ratingButton);

         class UpdateReceiver extends BroadcastReceiver {
             IntentFilter filter = new IntentFilter();


            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                int state = intent.getExtras().getInt("state");



                if(action.equals("updateStatus")){
                    if(state == 1){
                       button.setChecked(true);
                       status.setText("EXECUTING");

                    }
                    else{
                       button.setChecked(false);
                       status.setText("STAND-BY");
                    }

                }


            }
        }



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button.isChecked()){
                    new AsyncTask<Void, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(Void... voids) {
                            try {

                                boolean x=REST.turnOn();

                                return x;
                            }
                            catch (Throwable t){
                                t.printStackTrace();
                                return false;
                            }
                        }

                        @Override
                        protected void onPostExecute(Boolean result){
                            if(result) {
                                status.setText("EXECUTING");
                            }
                            else{
                                Toast toast = Toast.makeText(getApplicationContext(), "Si sono verificati errori", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    }.execute();
                }
                else{
                    new AsyncTask<Void, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(Void... voids) {
                            try {
                                boolean x=REST.turnOff();

                                return x;
                            }
                            catch (Throwable t){
                                t.printStackTrace();
                                return false;
                            }
                        }

                        @Override
                        protected void onPostExecute(Boolean result){
                            if(result) {
                                status.setText("STAND-BY");
                            }
                            else{
                                Toast toast = Toast.makeText(getApplicationContext(), "Si sono verificati errori", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    }.execute();
                }

            }
        });


        ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    new AsyncTask<Void, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(Void... voids) {
                            try {
                                float stars=rating.getRating();

                                REST.sendRating(stars);
                                return true;
                            }
                            catch (Throwable t){
                                t.printStackTrace();
                                return false;
                            }
                        }

                        @Override
                        protected void onPostExecute(Boolean result){
                            if(result) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Rating inviato", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            else{
                                Toast toast = Toast.makeText(getApplicationContext(), "Si sono verificati errori", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    }.execute();



            }
        });

        UpdateReceiver receiver=new UpdateReceiver();
        registerReceiver(receiver,new IntentFilter("updateStatus"));

    }
}
