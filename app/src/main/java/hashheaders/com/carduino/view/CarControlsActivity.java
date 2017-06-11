package hashheaders.com.carduino.view;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import hashheaders.com.carduino.R;
import hashheaders.com.carduino.controller.CarController;

public class CarControlsActivity extends AppCompatActivity {

    private ImageButton forwardBtn;
    private ImageButton backwardBtn;
    private ImageButton leftBtn;
    private ImageButton rightBtn;
    private ImageButton disconnectBtn;
    private SeekBar speedCtrl;
    private String address = null;
    private ProgressDialog progress;
    private ImageView connectionIndicator;
    private BluetoothAdapter myBluetooth = null;
    private BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private CarController carController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent newint = getIntent();
        address = newint.getStringExtra(DevicesListActivity.EXTRA_ADDRESS);
        ConnectBT connectBT = new ConnectBT();
        connectBT.execute();
        updateIndicator(connectionIndicator);

        forwardBtn = (ImageButton) findViewById(R.id.button_forward);
        backwardBtn = (ImageButton) findViewById(R.id.button_back);
        leftBtn = (ImageButton) findViewById(R.id.button_left);
        rightBtn = (ImageButton) findViewById(R.id.button_right);
        disconnectBtn = (ImageButton) findViewById(R.id.button_disconnect);
        speedCtrl = (SeekBar) findViewById(R.id.seekBar);
        connectionIndicator =  (ImageView)findViewById(R.id.connection_indicator);

        updateIndicator(connectionIndicator);

        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carController.moveForward();
            }
        });

        backwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carController.moveBackwards();
            }
        });

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carController.moveLeft();
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carController.moveRight();
            }
        });

        disconnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carController.disconnect();
                finish(); //return to the first layout
            }
        });

        speedCtrl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                carController.changeSpeed(progress, fromUser);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    protected void onResume(){
        super.onResume();
    }
    public void updateIndicator(ImageView view) {
        // if not grayed
        if(null != view) {
            if (isBtConnected) {
                view.setColorFilter(null);
            } else {
                view.setColorFilter(Color.argb(150, 200, 200, 200));
            }
        }
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(CarControlsActivity.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    //BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            } catch (IOException e) {
                ConnectSuccess = false;//if the try failed, you can check the exception here
                Log.e(CarControlsActivity.class.getSimpleName(), e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            } else {
                msg("Connected.");
                isBtConnected = true;
                carController = new CarController(CarControlsActivity.this, btSocket);
                updateIndicator(connectionIndicator);
            }
            progress.dismiss();
        }
    }

    // fast way to call Toast
    public void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}
