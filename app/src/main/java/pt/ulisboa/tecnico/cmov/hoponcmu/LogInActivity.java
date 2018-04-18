package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import pt.ulisboa.tecnico.cmov.hoponcmu.response.HelloResponseLogin;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.Response;

public class LogInActivity extends AllActivity {

    private String success="Login_Success";
    private String unsuccess="Login_Failed";
    private String connection="Without connection";
    private static String session=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = findViewById(R.id.username_login);
                EditText password = findViewById(R.id.password_login);
                String username_str= username.getText().toString();
                String password_str= password.getText().toString();

                SendTask task= new SendTask(LogInActivity.this);
                task.execute("login",username_str,password_str);

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

    public static String getUser(){
        return session;
    }

    public String setSession(String id){
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

    public void evaluate(String result){

        if(result.equals(success)){
           // setSession(username_str);
            Intent intent = new Intent(LogInActivity.this, ListActivity.class);
            startActivity(intent);
        }

        if(result.equals(unsuccess)){
            TextView text_connection = findViewById(R.id.connection_text);
            text_connection.setText("Invalid parameters");
        }

        if(result.equals(connection)){
            TextView text_connection = findViewById(R.id.connection_text);
            text_connection.setText(result);
        }

    }
}
