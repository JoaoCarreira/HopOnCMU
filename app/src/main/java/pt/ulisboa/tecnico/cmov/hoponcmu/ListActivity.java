package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocket;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketServer;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.Response;

import static android.content.ContentValues.TAG;

public class ListActivity extends AllActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    String [] listaMonumentos ={"Torre de Belem","Mosteiros dos Jeronimos","Bairro Alto","Rossio",
            "Praca do Comercio","Museu Marinha","CCB","Museu Nacional do Azulejo","Principe Real"};

    String [] listaWifi = {"M1","M2","M3","M4","M5","M6","M7","M8","M9"};

    int[] images = {R.drawable.torre_de_belem,R.drawable.mosteiro_dos_jeronimos,R.drawable.bairro_alto,R.drawable.rossio,R.drawable.praca,R.drawable.museu_da_marinha,R.drawable.ccb,R.drawable.museu_do_azulejo,R.drawable.principe_real};
    BottomNavigationView bottomNavigationView;

    GlobalClass globalclass;
    String monumento;
    WifiDirect wifiDirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        globalclass= (GlobalClass) getApplicationContext();
        monumento = globalclass.getMonumento();
        wifiDirect=LogInActivity.getWifi();
        wifiDirect.receiveInfo(this);

        final ListView lista = (ListView)findViewById(R.id.lista);
        final CustomAdapter customAdapter = new CustomAdapter();
        lista.setAdapter(customAdapter);
        final Intent quizIntent = new Intent(this,MainActivity.class);

        bottomNavigationView=(BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = listaMonumentos[position];
                String wifi = listaWifi[position];

                Boolean state_quizz = globalclass.getState();
                ArrayList<String> already_answer=globalclass.getQuizz_answer();
                
                if(already_answer.contains(item)){
                    Toast.makeText(ListActivity.this, "Quizz already answered", Toast.LENGTH_SHORT).show();
                }

                else if (state_quizz.equals(true) && globalclass.getMonumento().equals(item)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("monumento", item);
                    quizIntent.putExtras(bundle);
                    startActivity(quizIntent);

                } else {

                    StringBuilder peersStr = wifiDirect.getPeers();

                    if (peersStr.toString().equals(wifi)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("monumento", item);
                        quizIntent.putExtras(bundle);
                        startActivity(quizIntent);
                    } else {
                        Toast.makeText(ListActivity.this, "Not in wifi range", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void actionToDO(String aux, String text){
        if(aux.equals("info")){
           notification(text);
        }
        else if (aux.equals("servidor")){
            SendTask answers_other = new SendTask(ListActivity.this);
            String[] text_aux=text.split(",");
            answers_other.execute(text_aux[0],text_aux[1],text_aux[2],text_aux[3]);
        }
    }

    public void notification(String text){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"123");
        builder.setContentTitle("HopOnCMU")
                .setContentText(text)
                .setSmallIcon(R.drawable.logo2)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo2))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVibrate(new long[]{0,300,300,300})
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
            notificationManager.notify(m, builder.build());
        }
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
                wifiDirect.Wifi_Off();
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
