package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import pt.ulisboa.tecnico.cmov.hoponcmu.response.HelloResponseQuestion;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.Response;

import static java.lang.Thread.sleep;

public class MainActivity extends AllActivity {

    Button resposta1 , resposta2 , resposta3 , resposta4;
    TextView score , pergunta;

    Random r;

    private Questions questions_receive;
    private String mAnswer;
    private int mScore = 0;
    int numeroPerguntas= 4;
    int numeroRespostas= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent Mintent = getIntent();
        Bundle bundle = Mintent.getExtras();
        String monument = bundle.getString("monumento");

        SendTask questions = new SendTask(MainActivity.this);
        questions.execute("criar_questao",monument);

    }

    @Override
    public void updateInterface(Response rsp) {
        if (rsp.getClass().equals(HelloResponseQuestion.class)) {
            HelloResponseQuestion hello = (HelloResponseQuestion) rsp;
            questions_receive=hello.getMessage();

            resposta1 = (Button)findViewById(R.id.resposta1);
            resposta2 = (Button)findViewById(R.id.resposta2);
            resposta3 = (Button)findViewById(R.id.resposta3);
            resposta4 = (Button)findViewById(R.id.resposta4);

            score = (TextView) findViewById(R.id.score);
            pergunta = (TextView) findViewById(R.id.pergunta);

            updateQuestion(numeroRespostas);

            resposta1.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    sleepTime();
                    resposta1.setBackgroundResource(R.color.Yellow);

                    sleepTime();

                    if(resposta1.getText().toString().equals(mAnswer)){
                        resposta1.setBackgroundResource(R.color.Green);
                        mScore ++ ;
                        score.setText("Score: " + mScore);
                        sleepTime();
                        updateQuestion(numeroRespostas);
                    }
                    else{
                        resposta1.setBackgroundResource(R.color.Red);
                        sleepTime();
                        updateQuestion(numeroRespostas);
                    }
                }


            });
            resposta2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(resposta2.getText().toString().equals(mAnswer)){
                        resposta2.setBackgroundResource(R.color.Green);
                        mScore ++ ;
                        score.setText("Score: " + mScore);
                        sleepTime();
                        updateQuestion(numeroRespostas);
                    }
                    else{
                        resposta2.setBackgroundResource(R.color.Red);
                        sleepTime();
                        updateQuestion(numeroRespostas);
                    }
                }
            });
            resposta3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(resposta3.getText().toString().equals(mAnswer)){
                        resposta3.setBackgroundResource(R.color.Green);
                        mScore ++ ;
                        score.setText("Score: " + mScore);
                        sleepTime();
                        updateQuestion(numeroRespostas);
                    }
                    else{
                        resposta3.setBackgroundResource(R.color.Red);
                        sleepTime();
                        updateQuestion(numeroRespostas);
                    }
                }
            });
            resposta4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(resposta4.getText().toString().equals(mAnswer)){
                        resposta4.setBackgroundResource(R.color.Green);
                        mScore ++ ;
                        score.setText("Score: " + mScore);
                        sleepTime();
                        updateQuestion(numeroRespostas);
                    }
                    else{
                        resposta4.setBackgroundResource(R.color.Red);
                        sleepTime();
                        updateQuestion(numeroRespostas);
                    }
                }
            });
        }
    }

    private void updateQuestion(int a) {

        if (a==numeroPerguntas){

            SendTask answers = new SendTask(MainActivity.this);
            answers.execute("update_score",""+mScore,LogInActivity.getUser());

            Log.d("User",LogInActivity.getUser());

            final Intent intent = new Intent(this,ListActivity.class);
            startActivity(intent);

        }
        else{
            resposta1.setBackgroundResource(R.color.wl_gray);
            resposta2.setBackgroundResource(R.color.wl_gray);
            resposta3.setBackgroundResource(R.color.wl_gray);
            resposta4.setBackgroundResource(R.color.wl_gray);
            pergunta.setText(questions_receive.getQuestion(a));
            resposta1.setText(questions_receive.getChoice1(a));
            resposta2.setText(questions_receive.getChoice2(a));
            resposta3.setText(questions_receive.getChoice3(a));
            resposta4.setText(questions_receive.getChoice4(a));

            mAnswer = questions_receive.getCorrect(a);

            numeroRespostas++;
        }
    }

    public void sleepTime (){
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

