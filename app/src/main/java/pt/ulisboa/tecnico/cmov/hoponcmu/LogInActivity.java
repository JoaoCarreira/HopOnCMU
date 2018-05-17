package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import pt.ulisboa.tecnico.cmov.hoponcmu.response.HelloResponseLogin;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.Response;

public class LogInActivity extends AllActivity {

    private static Integer session=null;
    private String username_str;
    private String password_str;
    private static WifiDirect wifi_aux;
    private GlobalClass globalClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = findViewById(R.id.username_login);
                EditText password = findViewById(R.id.password_login);
                username_str= username.getText().toString();
                password_str= password.getText().toString();
                globalClass= (GlobalClass) getApplicationContext();
                final WifiDirect wifiDirect= new WifiDirect(getApplicationContext(),LogInActivity.this,getApplication());
                wifi_aux=wifiDirect;
                wifiDirect.Wifi_ON();

                SendTask task= new SendTask(LogInActivity.this);
                task.execute("login",username_str,password_str);
                globalClass.setUserName(username_str);
            }
        });

        findViewById(R.id.create_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }

    public static Integer getSession(){
        return session;
    }

    public Integer setSession(Integer id){
        this.session=id;
        return session;
    }


    @Override
    public void updateInterface(Response rsp) {
        if (rsp.getClass().equals(HelloResponseLogin.class)) {
            HelloResponseLogin hello = (HelloResponseLogin) rsp;
            evaluate(hello.getMessage());
        }
    }

    @Override
    public void updateConnection(String net){

        String connection="Without connection";
        if (net.equals("login")){
            TextView text_connection = findViewById(R.id.connection_text);
            text_connection.setText(connection);
        }

    }

    @Override
    public void actionToDO(String aux, String notification) {

    }

    public void evaluate(String res){

        String success="Login_Success";
        String failure="Login_Failed";
        String[] compare=res.split(",");
        String result= compare[0];

        TextView text_connection = findViewById(R.id.connection_text);

        if(result.equals(success)){
            Integer id_session= Integer.parseInt(compare[1]);
            setSession(id_session);
            text_connection.setText("");
            Intent intent = new Intent(LogInActivity.this, ListActivity.class);
            startActivity(intent);

        }

        if(result.equals(failure)){

            text_connection.setText("Invalid parameters");
        }

    }

    public static WifiDirect getWifi(){
        return wifi_aux;
    }
}
