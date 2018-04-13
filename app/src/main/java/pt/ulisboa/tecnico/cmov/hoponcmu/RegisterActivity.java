package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {

    private String result=null;
    private String success="Create_Account_Success";
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

                SendTask tarefa= new SendTask();
                tarefa.execute("create_account",username_str,password_str).toString();

                try {
                    result=tarefa.get();
                    //Log.d("Feito", resultado);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                validate(result);

            }
        });
    }

    private void validate(String text){

        if(text.equals(success)){
            Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
            startActivity(intent);
        }

        if(text.equals(connection)){
            TextView text_connection = findViewById(R.id.connection_text_create);
            text_connection.setText(result);
        }

    }
}
