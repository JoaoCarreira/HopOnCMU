package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import pt.ulisboa.tecnico.cmov.hoponcmu.response.Response;

public class ListActivity extends AllActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    String [] listaMonumentos ={"Torre de Belem","Mosteiros dos Jeronimos","Bairro Alto","Rossio",
            "Praca do Comercio","Museu Marinha","CCB","Museu Nacional do Azulejo","Principe Real"};

    int[] images = {R.drawable.torre_de_belem,R.drawable.mosteiro_dos_jeronimos,R.drawable.bairro_alto,R.drawable.rossio,R.drawable.praca,R.drawable.museu_da_marinha,R.drawable.ccb,R.drawable.museu_do_azulejo,R.drawable.principe_real};
    BottomNavigationView bottomNavigationView;

    GlobalClass globalclass;
    String monumento;
    String ssid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        globalclass= (GlobalClass) getApplicationContext();
        monumento = globalclass.getMonumento();

        final ListView lista = (ListView)findViewById(R.id.lista);
        final CustomAdapter customAdapter = new CustomAdapter();
        lista.setAdapter(customAdapter);
        final Intent quizIntent = new Intent(this,MainActivity.class);

        /*WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo;

        wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
            ssid = wifiInfo.getSSID();
        }

        Log.d("wifi", ssid);*/

        bottomNavigationView=(BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item= listaMonumentos[position];

                Bundle bundle = new Bundle();
                bundle.putString("monumento",item);
                quizIntent.putExtras(bundle);

                startActivity(quizIntent);
            }
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        final Intent quizIntent = new Intent(this,ListActivity.class);
        final Intent rankIntent = new Intent(this,RankActivity.class);
        final Intent logoutIntent = new Intent(this,LogInActivity.class);

        int id = item.getItemId();
        switch (id){
            case R.id.rank:
                startActivity(rankIntent);
                break;
            case R.id.lista:
                startActivity(quizIntent);
                break;
            case R.id.logout:
                logoutMethod();
                startActivity(logoutIntent);
                break;
        }

        return true;
    }
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_layout,null);
            ImageView imageView=(ImageView)view.findViewById(R.id.imageView);
            final TextView textView_name = (TextView)view.findViewById(R.id.textView_name);

            imageView.setImageResource(images[position]);
            textView_name.setText(listaMonumentos[position]);


            return view;
        }
    }

    public void logoutMethod(){

        int session_log= LogInActivity.getSession();
        SendTask task= new SendTask(ListActivity.this);
        task.execute("logout",""+session_log);
    }

    @Override
    public void updateInterface(Response response){

    }

    @Override
    public void updateConnection(String activ){

    }
}
