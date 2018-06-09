package it.polito.ami.amiclean;

import android.app.IntentService;
import android.content.Intent;


public class Servizio extends IntentService
{
    private String statusInt;
    private int updateStatus;


    public Servizio()
    {
        super("Servizio");
    }

    @Override
    protected void onHandleIntent(Intent i)
    {

        while(true)
        {

                   boolean status=REST.askUpdate();

                   Intent local = new Intent();
                   local.setAction("updateStatus");
                   if(status) {
                       updateStatus = 1;
                   }
                   else
                       updateStatus=0;

                   local.putExtra("state",updateStatus);
                   this.sendBroadcast(local);


            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e)
            { }
        }
    }

}
