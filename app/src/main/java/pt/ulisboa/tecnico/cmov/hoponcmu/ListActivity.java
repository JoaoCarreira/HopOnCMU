package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    String [] listaMonumentos ={"Torre de Belem","Mosteiros dos Jeronimos","Bairro Alto","Rossio",
            "Praça do Comercio","Museu Marinha","CCB","Museu Nacional do Azulejo","Principe Real"};

    int[] images = {R.drawable.padrao,R.drawable.padrao,R.drawable.padrao,R.drawable.padrao,R.drawable.padrao,R.drawable.padrao,R.drawable.padrao,R.drawable.padrao,R.drawable.padrao};
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView lista = (ListView)findViewById(R.id.lista);
        CustomAdapter customAdapter = new CustomAdapter();
        lista.setAdapter(customAdapter);
        final Intent quizIntent = new Intent(this,MainActivity.class);

        bottomNavigationView=(BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
                String username=LogInActivity.getUser();
                Log.d("LogOut", username);
                logoutMethod(username);
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
            TextView textView_name = (TextView)view.findViewById(R.id.textView_name);

            imageView.setImageResource(images[position]);
            textView_name.setText(listaMonumentos[position]);
            return view;
        }
    }

    public static void logoutMethod(String username){

        SendTask task= new SendTask();
        task.execute("logout",username);
    }
}
