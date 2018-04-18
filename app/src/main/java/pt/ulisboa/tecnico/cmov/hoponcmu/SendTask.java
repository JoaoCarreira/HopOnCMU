package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import pt.ulisboa.tecnico.cmov.hoponcmu.command.SendCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.Response;

public class SendTask extends AsyncTask<String, Void, Response> {

    AllActivity activity;

    public SendTask(AllActivity activity) {
        this.activity=activity;
    }

    @Override
    protected Response doInBackground(String[] params) {
        Socket server = null;
        //String reply;
        Response response=null;
        SendCommand option = new SendCommand(params);

        try {
            server = new Socket("10.0.2.2", 9090);

            ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
            oos.writeObject(option);

            Log.d("Cliente", "enviou");

            ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
            response = (Response) ois.readObject();

            oos.close();
            ois.close();
            //Log.d("Resposta", reply);
        }
        catch (Exception e) {
            Log.d("Client", "Without connection..." + e.getMessage());
            e.printStackTrace();
            //reply="Without connection";
        } finally {
            if (server != null) {
                try { server.close(); }
                catch (Exception e) { }
            }
        }
        return response;
    }

    //@Override
    protected void onPostExecute(Response o) {
        if (o != null) {
            activity.updateInterface(o);
        }
    }

}
