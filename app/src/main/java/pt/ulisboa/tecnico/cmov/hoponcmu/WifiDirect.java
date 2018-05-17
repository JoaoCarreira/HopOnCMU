package pt.ulisboa.tecnico.cmov.hoponcmu;


import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.ArrayList;

import pt.inesc.termite.wifidirect.SimWifiP2pBroadcast;
import pt.inesc.termite.wifidirect.SimWifiP2pDevice;
import pt.inesc.termite.wifidirect.SimWifiP2pDeviceList;
import pt.inesc.termite.wifidirect.SimWifiP2pInfo;
import pt.inesc.termite.wifidirect.SimWifiP2pManager;
import pt.inesc.termite.wifidirect.service.SimWifiP2pService;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocket;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketManager;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketServer;

import static android.content.ContentValues.TAG;
import static android.os.Looper.getMainLooper;

public class WifiDirect implements SimWifiP2pManager.PeerListListener, SimWifiP2pManager.GroupInfoListener{

    private SimWifiP2pBroadcastReceiver mReceiver;
    private SimWifiP2pSocketServer mSrvSocket = null;
    private SimWifiP2pSocket mCliSocket = null;
    private SimWifiP2pManager mManager = null;
    private SimWifiP2pManager.Channel mChannel = null;
    private Messenger mService = null;
    private boolean mBound = false;
    StringBuilder peersStr = new StringBuilder();
    Context app=null;
    AllActivity rec;
    AllActivity receiver;
    Application application;
    Intent intent;
    private ArrayList<String> peersGroup= new ArrayList<>();

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

    public ArrayList<String> getGroup() {
        mManager.requestGroupInfo(mChannel, this);
        return peersGroup;
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

        ArrayList<String> peersAuxGroup = new ArrayList<>();
        // compile list of network members
        for (String deviceName : groupInfo.getDevicesInNetwork()) {
            SimWifiP2pDevice device = devices.getByName(deviceName);
            String devstr = "" + deviceName + " (" +
                    ((device == null)?"??":device.getVirtIp()) + ")\n";
            peersAuxGroup.add(device.getVirtIp());
        }
        peersGroup=peersAuxGroup;
    }

    public class IncommingCommTask extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            Log.d(TAG, "IncommingCommTask started (" + this.hashCode() + ").");

            try {
                mSrvSocket = new SimWifiP2pSocketServer(
                        Integer.parseInt("10001"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    SimWifiP2pSocket sock = mSrvSocket.accept();
                    try {
                        BufferedReader sockIn = new BufferedReader(
                                new InputStreamReader(sock.getInputStream()));
                        String st = sockIn.readLine();
                        publishProgress(st);
                        sock.getOutputStream().write(("\n").getBytes());
                    } catch (IOException e) {
                        Log.d("Error reading socket:", e.getMessage());
                    } finally {
                        sock.close();
                    }
                } catch (IOException e) {
                    Log.d("Error socket:", e.getMessage());
                    break;
                    //e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            String[] aux=values[0].split(",");
            String action=aux[0];
            String message=aux[1];
            Toast.makeText(rec,message, Toast.LENGTH_SHORT).show();
            receiver.actionToDO(action,message);
        }
    }

    public class OutgoingCommTask extends AsyncTask<String, Void, String> {

        private String notification;
        String x=null;
        @Override
        protected void onPreExecute() {
            //mTextOutput.setText("Connecting...");
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                x=params[0];
                mCliSocket = new SimWifiP2pSocket(params[0],
                        Integer.parseInt("10001"));
                notification=params[1];
            } catch (UnknownHostException e) {
                return "Unknown Host:" + e.getMessage();
            } catch (IOException e) {
                return "IO error:" + e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            new SendCommTask().executeOnExecutor(
                    AsyncTask.THREAD_POOL_EXECUTOR,notification);
        }
    }

    public class SendCommTask extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... msg) {
            try {

                mCliSocket.getOutputStream().write((msg[0] + "\n").getBytes());
                BufferedReader sockIn = new BufferedReader(
                        new InputStreamReader(mCliSocket.getInputStream()));
                sockIn.readLine();
                mCliSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mCliSocket = null;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(rec, "enviou info", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendInfo(String why,String resp){

        WifiDirect wifi=LogInActivity.getWifi();
        ArrayList<String> group=wifi.getGroup();

        for (int i=0;i<group.size();i++) {
            new OutgoingCommTask().executeOnExecutor(
                    AsyncTask.THREAD_POOL_EXECUTOR,group.get(i),why+","+resp);
        }
    }

    public void receiveInfo(AllActivity aux){
        receiver=aux;
        if (mSrvSocket==null) {
            new IncommingCommTask().executeOnExecutor(
                    AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }
}
