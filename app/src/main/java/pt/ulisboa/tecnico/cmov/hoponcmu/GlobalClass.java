package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.app.Application;
public class GlobalClass extends Application{
    private String monumento;
    private Questions questions;
    private int rank;
    private int score;

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

    public String getMonumento(){
        return monumento;
    }

    public void setMonumento(String m){
        this.monumento=m;
    }

    public int getRank(){
        return rank;
    }

    public void setRank(int rank){
        this.rank=rank;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score=score;
    }
}