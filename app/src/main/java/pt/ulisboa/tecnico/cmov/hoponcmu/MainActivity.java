package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Date;

import pt.ulisboa.tecnico.cmov.hoponcmu.response.HelloResponseQuestion;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.Response;


public class MainActivity extends AllActivity {

    Button resposta1 , resposta2 , resposta3 , resposta4;
    TextView score , pergunta;

    private Questions questions_receive;
    private String mAnswer;
    private float mScore = 0;
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
    float elapsedMillis;
    WifiDirect wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent Mintent = getIntent();
        Bundle bundle = Mintent.getExtras();
        monument = bundle.getString("monumento");

        wifi=LogInActivity.getWifi();
        globalclass= (GlobalClass) getApplicationContext();
        score = (TextView) findViewById(R.id.score);
        score.setText("Right Answers " + mScore);

        resposta1 = (Button)findViewById(R.id.resposta1);
        resposta2 = (Button)findViewById(R.id.resposta2);
        resposta3 = (Button)findViewById(R.id.resposta3);
        resposta4 = (Button)findViewById(R.id.resposta4);

        pergunta = (TextView) findViewById(R.id.pergunta);

        chronometer = (Chronometer) findViewById(R.id.chronometer);

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
            wifi.sendInfo("info",globalclass.getUserName()+": "+resp);
            globalclass.setState(false);
            globalclass.quizzAnswer(monument);
            int certas=(int) mScore;
            int score= (int) ((mScore*100000)/elapsedMillis);
            SendTask answers = new SendTask(MainActivity.this);
            answers.execute("update_score",""+ LogInActivity.getSession(),""+score,""+certas);
            globalclass.setRank(score);
            globalclass.setScore(certas);

            final Intent intent = new Intent(this,ListActivity.class);
            startActivity(intent);

        }

        else{
            wifi.sendInfo("info",globalclass.getUserName()+": "+resp);
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
            elapsedMillis = (float) (SystemClock.elapsedRealtime() - chronometer.getBase());
            Log.d("cronometro",""+elapsedMillis);
        }

        mydialog=new Dialog(MainActivity.this);
        mydialog.setContentView(R.layout.custompopup);

        mydialog.show();

        if (already_answer.equals(false)) {
            if (fin == true) {
                mScore ++ ;
                score.setText("Right Answers: " + mScore);
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
                globalclass.setQuestions(questions_receive);
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
            //enviar resultado para outro telemovel e ele faz o upload
            String enviar="update_score,"+""+LogInActivity.getSession()+","+mScore+","+mScore;
            wifi.sendInfo("servidor",enviar);

        }
    }

    @Override
    public void actionToDO(String aux, String text){
        if(aux.equals("info")){
            notification(text);
        }
        else if (aux.equals("servidor")){
            SendTask answers_other = new SendTask(MainActivity.this);
            String[] text_aux=text.split(",");
            answers_other.execute(text_aux[0],text_aux[1],text_aux[2],text_aux[3]);
        }
    }

    public void notification(String text){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"123");
        builder.setContentTitle("HopOnCMU")
                .setContentText(text)
                .setSmallIcon(R.drawable.logo2)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo2))
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVibrate(new long[]{0,300,300,300})
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
            notificationManager.notify(m, builder.build());
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

