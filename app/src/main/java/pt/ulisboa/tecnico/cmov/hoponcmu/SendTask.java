package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import pt.ulisboa.tecnico.cmov.hoponcmu.command.SendCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.HelloResponse;

public class SendTask extends AsyncTask<String, Void, String> {

    public SendTask() {

    }

    @Override
    protected String doInBackground(String[] params) {
        Socket server = null;
        String reply = null;

        SendCommand option = new SendCommand(params);

        try {
            server = new Socket("10.0.2.2", 9090);

            ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
            oos.writeObject(option);

            Log.d("Cliente", "enviou");

            ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
            HelloResponse hr = (HelloResponse) ois.readObject();
            reply = hr.getMessage();

            oos.close();
            ois.close();
            Log.d("DummyClient", reply);
        }
        catch (Exception e) {
            Log.d("Client", "Without connection..." + e.getMessage());
            e.printStackTrace();
            reply="Without connection";
        } finally {
            if (server != null) {
                try { server.close(); }
                catch (Exception e) { }
            }
        }
        return reply;
    }

}
