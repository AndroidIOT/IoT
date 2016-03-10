package dpy.iot_eep;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends ActionBarActivity {


    String serverWrite="http://192.168.43.140/appserver.php";
    String serverRead="http://192.168.43.140/read.php";


int state[]=new int[10];
int noSwitches=4;

    private class HttpSend extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... str) {
            try
            {

                String get_url = serverWrite+"?q="+ str[0].replace(" ","%20");

                HttpClient Client = new DefaultHttpClient();
                HttpGet httpget;
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                httpget = new HttpGet(get_url);
                String content = Client.execute(httpget, responseHandler);

                return content;


            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            return "Cannot Connect";
        }

        protected void onPostExecute(String result) {

            // TextView tv = (TextView) findViewById(R.id.show_text);
            //tv.setText(result);
            Toast.makeText(getApplicationContext(), "SENT", Toast.LENGTH_SHORT).show();

        }

    }

    private class HttpRead extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... str) {
            try
            {

                String get_url = serverRead;
                HttpClient Client = new DefaultHttpClient();
                HttpGet httpget;
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                httpget = new HttpGet(get_url);
                String content = Client.execute(httpget, responseHandler);

                return content;


            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            return "Cannot Connect";
        }

        protected void onPostExecute(String result) {

            ///TextView tv = (TextView) findViewById(R.id.show_text);
           // tv.setText(result);
            for (int i=0;i<result.length()&& i<10;i++)
            {
                if (result.charAt(i)=='1')
               state[i]=1;
                else if (result.charAt(i)=='0')
                    state[i]=0;
            }

            setState();

        }

    }



    public void setState()
    {   Switch mySwitch;

        mySwitch= (Switch) findViewById(R.id.switch1);
        if (state[0]==1)
            mySwitch.setChecked(true);
       else
            mySwitch.setChecked(false);


        mySwitch= (Switch) findViewById(R.id.switch2);
        if (state[1]==1)
            mySwitch.setChecked(true);
        else
            mySwitch.setChecked(false);

        mySwitch= (Switch) findViewById(R.id.switch3);
        if (state[2]==1)
            mySwitch.setChecked(true);
        else
            mySwitch.setChecked(false);

        mySwitch= (Switch) findViewById(R.id.switch4);
        if (state[3]==1)
            mySwitch.setChecked(true);
        else
            mySwitch.setChecked(false);

    }



    public void setArray()
    {
         Switch mySwitch= (Switch) findViewById(R.id.switch1);
        if (mySwitch.isChecked())
            state[0]=1;
        else
            state[0]=0;


        mySwitch= (Switch) findViewById(R.id.switch2);
        if (mySwitch.isChecked())
            state[1]=1;
        else
            state[1]=0;

        mySwitch= (Switch) findViewById(R.id.switch3);
        if (mySwitch.isChecked())
            state[2]=1;
        else
            state[2]=0;

        mySwitch= (Switch) findViewById(R.id.switch4);
        if (mySwitch.isChecked())
            state[3]=1;
        else
            state[3]=0;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        HttpRead httpr = new HttpRead();
        httpr.execute(" ");
        setState();

    }

    public void on_send_click(View v)
    {

        setArray();
        String sta="";

        for (int i=0;i<noSwitches;i++)
        {
            if (state[i]==0)
                sta+='0';
            else
                sta+='1';
        }


        /*Switch mySwitch= (Switch) findViewById(R.id.switch1);
        if (mySwitch.isChecked())
            sta=sta+'1';
        else
            sta=sta+'0';


        mySwitch= (Switch) findViewById(R.id.switch2);
        if (mySwitch.isChecked())
            sta=sta+'1';
        else
            sta=sta+'0';
        */
         HttpSend httapp = new HttpSend();
         httapp.execute(sta);
         setArray();
        //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://myiotserver.netne.net/appserver.php?q="+send_txt.getText().toString())));

    }


    public void read_data(View v)
    {

        HttpRead httpr = new HttpRead();
        httpr.execute(" ");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, About.class);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /** Called when the user clicks the Send button */
    public void openLock(View view) {
       /* i++;
        TextView t1;
        t1=(TextView) findViewById(R.id.textView);
        t1.setText("Count = "+i);*/

        Intent intent = new Intent(this, lock.class);

        startActivity(intent);

    }



    /** Called when the user clicks the Send button */
    public void scheduleTask(View view) {
       /* i++;
        TextView t1;
        t1=(TextView) findViewById(R.id.textView);
        t1.setText("Count = "+i);*/

        //Intent intent = new Intent(this, SwitchSchedule.class);

        //startActivity(intent);


    }


}

