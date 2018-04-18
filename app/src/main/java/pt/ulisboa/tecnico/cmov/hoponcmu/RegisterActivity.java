package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import pt.ulisboa.tecnico.cmov.hoponcmu.response.HelloResponseRegister;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.Response;

public class RegisterActivity extends AllActivity {

    private String result=null;
    private String success="Create_Account_Success";
    private String unsuccess="Create_Account_Failed";
    private String connection="Without connection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = findViewById(R.id.username_register);
                EditText password = findViewById(R.id.password_register);
                String username_str= username.getText().toString();
                String password_str= password.getText().toString();

                TextView text_connection = findViewById(R.id.connection_text_create);
                text_connection.setText("");

                SendTask tarefa= new SendTask(RegisterActivity.this);
                tarefa.execute("create_account",username_str,password_str);

            }
        });
    }

    private void validate(String text){

        if(text.equals(success)){
            Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
            startActivity(intent);
        }

        if(text.equals(unsuccess)){
            TextView text_connection = findViewById(R.id.connection_text_create);
            text_connection.setText("Invalid Parameters");
        }

        if(text.equals(connection)){
            TextView text_connection = findViewById(R.id.connection_text_create);
            text_connection.setText(result);
        }

    }

    @Override
    public void updateInterface(Response rsp) {
        if (rsp.getClass().equals(HelloResponseRegister.class)) {
            HelloResponseRegister hello = (HelloResponseRegister) rsp;
            validate(hello.getMessage());
        }
    }
}
