package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import pt.inesc.termite.wifidirect.SimWifiP2pBroadcast;
import pt.inesc.termite.wifidirect.SimWifiP2pDevice;
import pt.inesc.termite.wifidirect.SimWifiP2pInfo;
import pt.inesc.termite.wifidirect.SimWifiP2pManager;
import pt.inesc.termite.wifidirect.SimWifiP2pManager.Channel;
import pt.inesc.termite.wifidirect.service.SimWifiP2pService;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocket;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketManager;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketServer;
import pt.inesc.termite.wifidirect.SimWifiP2pDeviceList;
import pt.inesc.termite.wifidirect.SimWifiP2pManager.PeerListListener;
import pt.inesc.termite.wifidirect.SimWifiP2pManager.GroupInfoListener;

public class SendCommTask extends AsyncTask<String, String, Void> {

    private SimWifiP2pSocket mCliSocket = null;

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

    }
}