package pt.ulisboa.tecnico.cmov.hoponcmu;


import android.app.AlertDialog;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;

import java.io.Serializable;

import pt.inesc.termite.wifidirect.SimWifiP2pBroadcast;
import pt.inesc.termite.wifidirect.SimWifiP2pDevice;
import pt.inesc.termite.wifidirect.SimWifiP2pDeviceList;
import pt.inesc.termite.wifidirect.SimWifiP2pInfo;
import pt.inesc.termite.wifidirect.SimWifiP2pManager;
import pt.inesc.termite.wifidirect.service.SimWifiP2pService;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketManager;

import static android.os.Looper.getMainLooper;

public class WifiDirect implements SimWifiP2pManager.PeerListListener, SimWifiP2pManager.GroupInfoListener{

    private SimWifiP2pBroadcastReceiver mReceiver;
    private SimWifiP2pManager mManager = null;
    private SimWifiP2pManager.Channel mChannel = null;
    private Messenger mService = null;
    private boolean mBound = false;
    StringBuilder peersStr = new StringBuilder();
    Context app=null;
    AllActivity rec;
    Application application;
    Intent intent;

    public WifiDirect(Context i, AllActivity a, Application b){
        app=i;
        rec=a;
        application=b;
        SimWifiP2pSocketManager.Init(app);

        // register broadcast receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_STATE_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_PEERS_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_NETWORK_MEMBERSHIP_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_GROUP_OWNERSHIP_CHANGED_ACTION);
        mReceiver = new SimWifiP2pBroadcastReceiver(rec);
        app.registerReceiver(mReceiver, filter);
    }

    public void Wifi_ON(){
        intent = new Intent(rec, SimWifiP2pService.class);
        app.startService(intent);
        app.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        mBound = true;
    }

    public void Wifi_Off(){
        app.stopService(intent);
        app.unbindService(mConnection);
        mBound = false;
    }



    private ServiceConnection mConnection = new ServiceConnection() {

        // callbacks for service binding, passed to bindService()

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = new Messenger(service);
            mManager = new SimWifiP2pManager(mService);
            mChannel = mManager.initialize(application, getMainLooper(), null);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mManager = null;
            mChannel = null;
            mBound = false;
        }
    };

    public StringBuilder getPeers(){
        mManager.requestPeers(mChannel, this);
        return peersStr;
    }

    @Override
    public void onPeersAvailable(SimWifiP2pDeviceList peers) {

        // compile list of devices in range
        for (SimWifiP2pDevice device : peers.getDeviceList()) {
            String devstr = "" + device.deviceName + " (" + device.getVirtIp() + ")\n";
            if(!device.deviceName.equals(peersStr.toString())){
                peersStr.append(device.deviceName.toString());
            }
        }
    }

    @Override
    public void onGroupInfoAvailable(SimWifiP2pDeviceList devices,
                                     SimWifiP2pInfo groupInfo) {

        // compile list of network members
        StringBuilder peersStr = new StringBuilder();
        for (String deviceName : groupInfo.getDevicesInNetwork()) {
            SimWifiP2pDevice device = devices.getByName(deviceName);
            String devstr = "" + deviceName + " (" +
                    ((device == null)?"??":device.getVirtIp()) + ")\n";
            peersStr.append(devstr);

        }

        // display list of network members
        new AlertDialog.Builder(rec)
                .setTitle("Devices in WiFi Network")
                .setMessage(peersStr.toString())
                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}
