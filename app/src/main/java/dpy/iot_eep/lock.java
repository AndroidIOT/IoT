package dpy.iot_eep;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;


public class lock extends ActionBarActivity {


    String serverPass="http://192.168.43.140/lock.php";
    String user="Admin";
    String pass="qwerty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        setTitle("Lock");
        final Switch mySwitch = (Switch) findViewById(R.id.lockswitch);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

// attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                if (isChecked) {


                    mySwitch.setText("UNLOCK");
                } else {


                    mySwitch.setText("LOCK");
                }

            }
        });

        EditText e=(EditText) findViewById(R.id.ID);
        e.setText("Admin");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lock, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class HttpPass extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... str) {
            try
            {

                String get_url = serverPass+"?q="+ str[0].replace(" ","%20");

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

        }

    }


    public void send_lock(View v)
    {
        EditText e=(EditText) findViewById(R.id.ID);
        String username=e.getText().toString();

        e=(EditText) findViewById(R.id.PASS);
        String password=e.getText().toString();

        if (username.equalsIgnoreCase(user) && password.equalsIgnoreCase(pass))
        {

            String sta = "";

            Switch mySwitch = (Switch) findViewById(R.id.lockswitch);
            if (mySwitch.isChecked())
                sta = sta + '0';
            else
                sta = sta + '1';


            HttpPass httapp = new HttpPass();
            httapp.execute(sta);
            Toast.makeText(getApplicationContext(), "ACCESS GRANTED", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "ACCESS DENIED", Toast.LENGTH_SHORT).show();
        }



    }



}   // end class lock
