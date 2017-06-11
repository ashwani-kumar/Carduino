package hashheaders.com.carduino.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

import hashheaders.com.carduino.R;
import hashheaders.com.carduino.model.DeviceInfoModel;

/**
 * Created by Ashwani on 10-06-2017.
 */

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.DeviceInfoViewHolder> {

    private final Context mContext;
    private final List<DeviceInfoModel> deviceList;

    public DeviceListAdapter(Context mContext, List<DeviceInfoModel> deviceList) {
        this.mContext = mContext;
        this.deviceList = deviceList;
    }

    @Override
    public DeviceInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_info_card, parent, false);

        return new DeviceInfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DeviceInfoViewHolder holder, int position) {
        DeviceInfoModel device = deviceList.get(position);
        holder.deviceName.setText(device.getDeviceName());
        holder.macAddr.setText(device.getMacAddr());
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public class DeviceInfoViewHolder extends RecyclerView.ViewHolder{
        public TextView deviceName;
        public TextView macAddr;

        public DeviceInfoViewHolder(View view) {
            super(view);
            deviceName = (TextView) view.findViewById(R.id.device_name);
            macAddr = (TextView) view.findViewById(R.id.mac_address);
        }
    }
}
