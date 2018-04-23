package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.HelloResponseQuestion;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.Response;


public class MainActivity extends AllActivity {

    Button resposta1 , resposta2 , resposta3 , resposta4;
    TextView score , pergunta;


    private Questions questions_receive;
    private String mAnswer;
    private int mScore = 0;
    int numeroPerguntas= 4;
    int numeroRespostas= 0;

    Dialog mydialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent Mintent = getIntent();
        Bundle bundle = Mintent.getExtras();
        String monument = bundle.getString("monumento");


        SendTask questions = new SendTask(MainActivity.this);
        questions.execute("criar_questao",monument);

        score = (TextView) findViewById(R.id.score);
        score.setText("Score: " + mScore);


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

                    if(resposta1.getText().toString().equals(mAnswer)){
                        mScore ++ ;
                        score.setText("Score: " + mScore);
                        ShowPopUp(true);
                    }
                    else{
                        ShowPopUp(false);
                    }
                }


            });
            resposta2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(resposta2.getText().toString().equals(mAnswer)){
                        mScore ++ ;
                        score.setText("Score: " + mScore);
                        ShowPopUp(true);
                    }
                    else{
                        ShowPopUp(false);
                    }
                }
            });
            resposta3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(resposta3.getText().toString().equals(mAnswer)){
                        mScore ++ ;
                        score.setText("Score: " + mScore);
                        ShowPopUp(true);
                    }
                    else{
                        ShowPopUp(false);
                    }
                }
            });
            resposta4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(resposta4.getText().toString().equals(mAnswer)){
                        mScore ++ ;
                        score.setText("Score: " + mScore);
                        String aux="+ 1 Ponto";
                        ShowPopUp(true);
                    }
                    else{
                        ShowPopUp(false);
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
            pergunta.setText(questions_receive.getQuestion(a));
            resposta1.setText(questions_receive.getChoice1(a));
            resposta2.setText(questions_receive.getChoice2(a));
            resposta3.setText(questions_receive.getChoice3(a));
            resposta4.setText(questions_receive.getChoice4(a));

            mAnswer = questions_receive.getCorrect(a);

            numeroRespostas++;
        }
    }

    public void ShowPopUp(Boolean fin){

        String points;
        String resp;

        mydialog=new Dialog(MainActivity.this);
        mydialog.setContentView(R.layout.custompopup);

        mydialog.show();

        if (fin==true) {
            points="+ 1 Ponto";
            resp="Resposta Correta";
            mydialog.getWindow().setBackgroundDrawableResource(R.color.Green);
        }

        else{
            resp="Resposta Errada";
            points="Falta de sorte";
            mydialog.getWindow().setBackgroundDrawableResource(R.color.Red);
        }

        TextView resposta_final = mydialog.findViewById(R.id.textView4);
        resposta_final.setText(resp);

        TextView scoreText = mydialog.findViewById(R.id.scorePopup);
        scoreText.setText(points);

        Button continuar = mydialog.findViewById(R.id.continue_button);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });

        updateQuestion(numeroRespostas);
    }

    @Override
    public void updateConnection(String net){

    }
}

