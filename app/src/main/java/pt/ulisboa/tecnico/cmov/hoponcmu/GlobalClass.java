package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.app.Application;

public class GlobalClass extends Application{

    private Questions questions;
    private Boolean state_quizz=false;

    public Questions getQuestions(){
        return questions;
    }

    public void setQuestions(Questions quizz){
        this.questions=quizz;
    }

    public Boolean getState(){
        return state_quizz;
    }

    public void setState(Boolean state){
        this.state_quizz=state;
    }
}