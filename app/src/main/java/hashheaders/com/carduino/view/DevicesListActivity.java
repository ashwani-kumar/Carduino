package hashheaders.com.carduino.view;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import hashheaders.com.carduino.R;
import hashheaders.com.carduino.model.DeviceInfoModel;

/**
 * Created by Ashwani on 10-06-2017.
 */

public class DevicesListActivity extends Activity {
    private RecyclerView recyclerView;
    private List devicesList;
    private DeviceListAdapter deviceListAdapter;

    public static String EXTRA_ADDRESS = "device_address";

    //Bluetooth
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devices_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        devicesList = new ArrayList<>();

        deviceListAdapter = new DeviceListAdapter(this, devicesList);
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        pairedDevicesList();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(deviceListAdapter);
        recyclerView.addOnItemTouchListener(new DeviceInfoViewClickListener(getApplicationContext(), recyclerView, new DeviceInfoViewClickListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String address = ((DeviceInfoModel)devicesList.get(position)).getMacAddr();
                // Make an intent to start next activity.
                Intent intent = new Intent(getApplicationContext(), CarControlsActivity.class);

                //Change the activity.
                intent.putExtra(EXTRA_ADDRESS, address);
                startActivity(intent);
            }

        }));
    }

    private void pairedDevicesList() {
        pairedDevices = myBluetooth.getBondedDevices();

        if (pairedDevices.size()>0) {
            for(BluetoothDevice bt : pairedDevices) {
                DeviceInfoModel deviceInfoModel = new DeviceInfoModel();
                deviceInfoModel.setDeviceName(bt.getName());
                deviceInfoModel.setMacAddr(bt.getAddress());
                devicesList.add(deviceInfoModel); //Get the device's name and the address
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
