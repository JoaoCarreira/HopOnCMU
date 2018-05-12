package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.UnknownHostException;

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

public class OutgoingCommTask extends AsyncTask<String, Void, String> {

    private SimWifiP2pSocket mCliSocket = null;

    @Override
    protected void onPreExecute() {
        //mTextOutput.setText("Connecting...");
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            mCliSocket = new SimWifiP2pSocket(params[0],
                    Integer.parseInt("10001"));
        } catch (UnknownHostException e) {
            return "Unknown Host:" + e.getMessage();
        } catch (IOException e) {
            return "IO error:" + e.getMessage();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            //guiUpdateDisconnectedState();
            //mTextOutput.setText(result);
        } else {
            /*findViewById(R.id.idDisconnectButton).setEnabled(true);
            findViewById(R.id.idConnectButton).setEnabled(false);
            findViewById(R.id.idSendButton).setEnabled(true);
            mTextInput.setHint("");
            mTextInput.setText("");
            mTextOutput.setText("");*/
        }
    }
}