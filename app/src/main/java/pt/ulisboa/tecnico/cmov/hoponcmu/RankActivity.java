package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.response.HelloResponseRank;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.Response;

public class RankActivity extends AllActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    private ArrayList<String> users =new ArrayList<String>();
    private ArrayList<String> scores =new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

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
            /*case R.id.logout:
                String username=LogInActivity.getUser();
                ListActivity.logoutMethod(username);
                startActivity(logoutIntent);
                break;*/
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
            int lugar = position+1;
            textView_score.setText(scores.get(position));
            textView_user.setText(users.get(position));
            textView_rank.setText((""+ lugar));

            return view;
        }
    }

    @Override
    public void updateInterface(Response rsp) {
        if (rsp.getClass().equals(HelloResponseRank.class)) {
            HelloResponseRank hello = (HelloResponseRank) rsp;

            List<ArrayList<String>> rank=hello.getMessage();

            for(int i=0; i<rank.size();i++){

                users.add(rank.get(i).get(0));
                scores.add(rank.get(i).get(1));
            }

            ListView lista = (ListView)findViewById(R.id.lista);
            CustomAdapter customAdapter = new CustomAdapter();
            lista.setAdapter(customAdapter);
        }
    }

    @Override
    public void updateConnection(String net){

    }
}
