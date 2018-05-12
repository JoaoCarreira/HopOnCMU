package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
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
    Dialog initdialog;
    Boolean already_answer=false;
    String points="";
    String resp="";
    String monument;
    GlobalClass globalclass;
    Boolean save_quizz;
    Chronometer chronometer;
    int elapsedMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent Mintent = getIntent();
        Bundle bundle = Mintent.getExtras();
        monument = bundle.getString("monumento");


        score = (TextView) findViewById(R.id.score);
        score.setText("Score: " + mScore);

        resposta1 = (Button)findViewById(R.id.resposta1);
        resposta2 = (Button)findViewById(R.id.resposta2);
        resposta3 = (Button)findViewById(R.id.resposta3);
        resposta4 = (Button)findViewById(R.id.resposta4);

        pergunta = (TextView) findViewById(R.id.pergunta);

        chronometer = (Chronometer) findViewById(R.id.chronometer);

        globalclass= (GlobalClass) getApplicationContext();
        globalclass.setMonumento(monument);
        save_quizz=globalclass.getState();

        if (save_quizz.equals(true)){
            questions_receive=globalclass.getQuestions();
            getVisible();
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            updateQuestion(numeroRespostas);
            create_quizz();
        }

        else {
            SendTask questions = new SendTask(MainActivity.this);
            questions.execute("criar_questao", monument);
        }
    }

    @Override
    public void updateInterface(Response rsp) {
        if (rsp.getClass().equals(HelloResponseQuestion.class)) {
            HelloResponseQuestion hello = (HelloResponseQuestion) rsp;
            questions_receive=hello.getMessage();

            if (questions_receive==null) {
                final Intent quizIntent = new Intent(this,ListActivity.class);
                startActivity(quizIntent);
            }


            ShowPopUpLater();

            create_quizz();

        }
    }

    public void create_quizz(){

        resposta1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                if(resposta1.getText().toString().equals(mAnswer)){
                    ShowPopUp(true);
                }
                else{
                    ShowPopUp(false);
                }
                already_answer=true;
            }


        });
        resposta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resposta2.getText().toString().equals(mAnswer)){
                    ShowPopUp(true);
                }
                else{
                    ShowPopUp(false);
                }
                already_answer=true;
            }
        });
        resposta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resposta3.getText().toString().equals(mAnswer)){
                    ShowPopUp(true);
                }
                else{
                    ShowPopUp(false);
                }
                already_answer=true;
            }
        });
        resposta4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resposta4.getText().toString().equals(mAnswer)){
                    ShowPopUp(true);
                }
                else{
                    ShowPopUp(false);
                }
                already_answer=true;
            }
        });
    }

    private void updateQuestion(int a) {

        if (a==numeroPerguntas){

            globalclass.setState(false);
            globalclass.quizzAnswer(monument);
            SendTask answers = new SendTask(MainActivity.this);
            answers.execute("update_score",""+ LogInActivity.getSession(),""+mScore,""+mScore);
            globalclass.setRank(mScore);
            globalclass.setScore(mScore);

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

            already_answer=false;
        }
    }

    public void ShowPopUp(Boolean fin){

        if(numeroRespostas==numeroPerguntas){
            chronometer.stop();
            elapsedMillis = (int) (SystemClock.elapsedRealtime() - chronometer.getBase());
            Log.d("cronometro",""+elapsedMillis);
        }

        mydialog=new Dialog(MainActivity.this);
        mydialog.setContentView(R.layout.custompopup);

        mydialog.show();

        if (already_answer.equals(false)) {
            if (fin == true) {
                mScore ++ ;
                score.setText("Score: " + mScore);
                points = "+ 1 Ponto";
                resp = "Resposta Correta";
                mydialog.getWindow().setBackgroundDrawableResource(R.color.Green);

            } else {
                resp = "Resposta Errada";
                points = "Falta de sorte";
                mydialog.getWindow().setBackgroundDrawableResource(R.color.Red);
            }
        }

        else{
            if (points.equals("+ 1 Ponto")){
                mydialog.getWindow().setBackgroundDrawableResource(R.color.Green);
            }
            else{
                mydialog.getWindow().setBackgroundDrawableResource(R.color.Red);
            }
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
                updateQuestion(numeroRespostas);
            }
        });
    }

    public void ShowPopUpLater() {

        initdialog = new Dialog(MainActivity.this);
        initdialog.setContentView(R.layout.laterquizz_layout);
        initdialog.show();

        Button now = initdialog.findViewById(R.id.now_button);
        Button later = initdialog.findViewById(R.id.later_button);

        now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVisible();
                updateQuestion(numeroRespostas);
                initdialog.dismiss();
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
            }
        });

        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalclass.setQuestions(questions_receive);
                globalclass.setState(true);
                goBack();
                initdialog.dismiss();
            }

        });
    }

    @Override
    public void updateConnection(String net) {

        if (net.equals("criar_questao")){

        }

        if (net.equals("update_score")) {
            SendTask answers = new SendTask(MainActivity.this);
            answers.execute("update_score", "" + mScore, ""+ LogInActivity.getSession());

        }
    }

    @Override
    public void onBackPressed() {
        // não chame o super desse método
    }

    public void goBack(){
        final Intent intent = new Intent(this,ListActivity.class);
        startActivity(intent);
    }

    public void getVisible(){
        pergunta.setVisibility(TextView.VISIBLE);
        resposta1.setVisibility(Button.VISIBLE);
        resposta2.setVisibility(Button.VISIBLE);
        resposta3.setVisibility(Button.VISIBLE);
        resposta4.setVisibility(Button.VISIBLE);
    }

}

