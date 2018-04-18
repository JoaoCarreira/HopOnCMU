package pt.ulisboa.tecnico.cmov.hoponcmu;


import android.support.v7.app.AppCompatActivity;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.Response;

public abstract class AllActivity extends AppCompatActivity{

    public abstract void updateInterface(Response response);
}
