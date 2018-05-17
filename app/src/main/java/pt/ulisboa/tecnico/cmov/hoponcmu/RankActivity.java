package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.response.HelloResponseRank;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.Response;

import static java.util.Collections.sort;

public class RankActivity extends AllActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    private ArrayList<String> users =new ArrayList<String>();
    private ArrayList<String> scores =new ArrayList<String>();
    private ArrayList<String> correct =new ArrayList<String>();
    WifiDirect wifiDirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        wifiDirect=LogInActivity.getWifi();
        wifiDirect.receiveInfo(this);

        bottomNavigationView=(BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        SendTask task= new SendTask(RankActivity.this);
        task.execute("get_rank");

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

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return users.size();
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
            view = getLayoutInflater().inflate(R.layout.rank_layout,null);
            TextView textView_rank = (TextView)view.findViewById(R.id.rank);
            TextView textView_user = (TextView)view.findViewById(R.id.user);
            TextView textView_score = (TextView)view.findViewById(R.id.score);
            TextView textView_rightAnswers = (TextView)view.findViewById(R.id.RightAnswere);

            int lugar = position+1;
            textView_score.setText(scores.get(position));
            textView_user.setText(users.get(position));
            textView_rank.setText((""+ lugar));
            textView_rightAnswers.setText(correct.get(position));

            return view;
        }
    }

    @Override
    public void updateInterface(Response rsp) {
        if (rsp.getClass().equals(HelloResponseRank.class)) {
            HelloResponseRank hello = (HelloResponseRank) rsp;

            List<ArrayList<String>> rank=hello.getMessage();
            ArrayList<Integer> rankAux =new ArrayList<Integer>();

            //Array Aux
            for (int j=0 ;j<rank.size();j++) {
                rankAux.add(Integer.parseInt(rank.get(j).get(1)));
            }
            //Ordenar Array
            sort(rankAux, Collections.reverseOrder());
            //Apagar Elementos Repetidos
            for (int j=1 ;j<rankAux.size();j++) {
                if (rankAux.get(j).equals(rankAux.get(j-1))){
                    rankAux.remove(j);
                }
            }
            for(int i=0; i<rankAux.size();i++){
                for(int j=0; j<rank.size();j++){
                    if (rankAux.get(i).equals(Integer.parseInt((rank.get(j).get(1))))){
                        users.add(rank.get(j).get(0));
                        scores.add(rank.get(j).get(1));
                        correct.add(rank.get(j).get(2));
                    }

                }

            }

            ListView lista = (ListView)findViewById(R.id.lista);
            CustomAdapter customAdapter = new CustomAdapter();
            lista.setAdapter(customAdapter);
        }
    }

    @Override
    public void updateConnection(String net) {

        if (net.equals("get_rank")){
            ListView lista = (ListView)findViewById(R.id.lista);
            CustomAdapter customAdapter = new CustomAdapter();
            lista.setAdapter(customAdapter);
        }

        if(net.equals("logout")){
            logoutMethod();
        }
    }

    @Override
    public void actionToDO(String aux, String text) {
        if(aux.equals("info")){
            notification(text);
        }
        else if (aux.equals("servidor")){
            SendTask answers_other = new SendTask(RankActivity.this);
            String[] text_aux=text.split(",");
            answers_other.execute(text_aux[0],text_aux[1],text_aux[2],text_aux[3]);
        }
    }

    public void logoutMethod(){
        int session_log=LogInActivity.getSession();
        SendTask task= new SendTask(RankActivity.this);
        task.execute("logout",""+session_log);
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
}
