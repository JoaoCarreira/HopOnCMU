package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import pt.ulisboa.tecnico.cmov.hoponcmu.response.HelloResponseRegister;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.Response;

public class RegisterActivity extends AllActivity {

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

        findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void validate(String text){

        String success="Create_Account_Success";
        String failure="Create_Account_Failed";

        TextView text_connection = findViewById(R.id.connection_text_create);

        if(text.equals(success)){
            text_connection.setText("");
            Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
            startActivity(intent);
        }

        if(text.equals(failure)){
            text_connection.setText("Invalid Parameters");
        }
    }

    @Override
    public void updateInterface(Response rsp) {
        if (rsp.getClass().equals(HelloResponseRegister.class)) {
            HelloResponseRegister hello = (HelloResponseRegister) rsp;
            validate(hello.getMessage());
        }
    }

    @Override
    public void updateConnection(String net){

        String connection="Without connection";

        if(net.equals("create_account")){

            TextView text_connection = findViewById(R.id.connection_text_create);
            text_connection.setText(connection);
        }

    }

    @Override
    public void actionToDO(String aux, String notification) {

    }
}
