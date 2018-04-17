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

public class MainActivity extends AppCompatActivity {

    Button resposta1 , resposta2 , resposta3 , resposta4;
    TextView score , pergunta;

    Random r;

    private Questions mQuestions = new Questions() ;
    private String mAnswer;
    private int mScore = 0;
    private int numeroPerguntas= mQuestions.mQuestions.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        pergunta.setText(mQuestions.getQuestion(a));
        resposta1.setText(mQuestions.getChoice1(a));
        resposta2.setText(mQuestions.getChoice2(a));
        resposta3.setText(mQuestions.getChoice3(a));
        resposta4.setText(mQuestions.getChoice4(a));

        mAnswer = mQuestions.getCorrect(a);
    }

}

