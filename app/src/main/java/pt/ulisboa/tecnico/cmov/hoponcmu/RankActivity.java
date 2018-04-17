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

public class RankActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    String[] users ={"Joao","Roberto","Panisca","Carlos","Antonio","Zedu"};
    String[] scores ={"1233","324","343","34","6543","43"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        bottomNavigationView=(BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        ListView lista = (ListView)findViewById(R.id.lista);
        CustomAdapter customAdapter = new CustomAdapter();
        lista.setAdapter(customAdapter);
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
                String username=LogInActivity.getUser();
                ListActivity.logoutMethod(username);
                startActivity(logoutIntent);
                break;
        }

        return true;
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return users.length;
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
            textView_score.setText(scores[position]);
            textView_user.setText(users[position]);
            textView_rank.setText((""+ lugar));

            return view;
        }
    }
}
