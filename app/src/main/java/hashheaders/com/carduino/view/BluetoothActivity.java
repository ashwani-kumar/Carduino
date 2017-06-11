package hashheaders.com.carduino.view;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Set;

import hashheaders.com.carduino.R;

/**
 * Created by Ashwani on 10-06-2017.
 */

public class BluetoothActivity extends Activity {

    private Button btnPaired;
    //Bluetooth
    private BluetoothAdapter myBluetooth = null;
    @Override
    public void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.bluetooth_view);
        btnPaired = (Button)findViewById(R.id.pair_btn);

        //if the device has bluetooth
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if(myBluetooth == null) {
            //Show a mensag. that the device has no bluetooth adapter
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();

        }
        else if(!myBluetooth.isEnabled()) {
            //Ask to the user turn the bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon,1);
        }

        btnPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //start device list activity from here
                Intent deviceListIntent = new Intent(BluetoothActivity.this, DevicesListActivity.class);
                startActivity(deviceListIntent);
            }
        });

    }

}
