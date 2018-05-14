package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.app.Application;

import java.util.ArrayList;

public class GlobalClass extends Application{
    private String user_name;
    private String monumento;
    private Questions questions;
    private int rank;
    private int score;
    private ArrayList<String> quizz_answer= new ArrayList<>();

    private Boolean state_quizz=false;

    public String getUserName(){
        return user_name;
    }

    public void setUserName(String name){
        user_name=name;
    }

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

    public void quizzAnswer(String monumento){
        quizz_answer.add(monumento);
    }

    public ArrayList<String> getQuizz_answer(){
        return quizz_answer;
    }
}