package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import pt.ulisboa.tecnico.cmov.hoponcmu.response.HelloResponseQuestion;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.Response;

public class MainActivity extends AllActivity {

    Button resposta1 , resposta2 , resposta3 , resposta4;
    TextView score , pergunta;

    Random r;

    private Questions questions_receive;
    private String mAnswer;
    private int mScore = 0;
    private int numeroPerguntas= questions_receive.mQuestions.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent Mintent = getIntent();
        Bundle bundle = Mintent.getExtras();
        String monument = bundle.getString("monumento");

        SendTask questions = new SendTask(MainActivity.this);
        questions.execute("criar_questao",monument);

        r = new Random();

        resposta1 = (Button)findViewById(R.id.resposta1);
        resposta2 = (Button)findViewById(R.id.resposta2);
        resposta3 = (Button)findViewById(R.id.resposta3);
        resposta4 = (Button)findViewById(R.id.resposta4);

        score = (TextView) findViewById(R.id.score);
        pergunta = (TextView) findViewById(R.id.pergunta);

        updateQuestion(r.nextInt(numeroPerguntas));

        final Intent intent = new Intent(this,ListActivity.class);

        resposta1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
               startActivity(intent);
                if(resposta1.getText().toString()== mAnswer){
                    mScore ++ ;
                    score.setText("Score: " + mScore);
                    updateQuestion(r.nextInt(numeroPerguntas));
                }
                else{
                    resposta1.setBackgroundResource(R.color.Red);
                    updateQuestion(r.nextInt(numeroPerguntas));
                    //resposta1.setBackgroundResource(R.color.wl_gray);
                }
            }


        });
        resposta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resposta2.getText().toString()==mAnswer){
                    mScore ++ ;
                    score.setText("Score: " + mScore);
                    updateQuestion(r.nextInt(numeroPerguntas));
                }
                else{

                }
            }
        });
        resposta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resposta3.getText().toString()==mAnswer){
                    mScore ++ ;
                    score.setText("Score: " + mScore);
                    updateQuestion(r.nextInt(numeroPerguntas));
                }
                else{

                }
            }
        });
        resposta4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resposta4.getText().toString()==mAnswer){
                    mScore ++ ;
                    score.setText("Score: " + mScore);
                    updateQuestion(r.nextInt(numeroPerguntas));
                }
                else{

                }
            }
        });



    }

    private void updateQuestion(int a) {
        pergunta.setText(questions_receive.getQuestion(a));
        resposta1.setText(questions_receive.getChoice1(a));
        resposta2.setText(questions_receive.getChoice2(a));
        resposta3.setText(questions_receive.getChoice3(a));
        resposta4.setText(questions_receive.getChoice4(a));

        mAnswer = questions_receive.getCorrect(a);
    }

    @Override
    public void updateInterface(Response rsp) {
        if (rsp.getClass().equals(HelloResponseQuestion.class)) {
            HelloResponseQuestion hello = (HelloResponseQuestion) rsp;
            questions_receive=hello.getMessage();
        }
    }
}

