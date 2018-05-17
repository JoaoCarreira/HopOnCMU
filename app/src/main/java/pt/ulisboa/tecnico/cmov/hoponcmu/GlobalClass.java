package pt.ulisboa.tecnico.cmov.hoponcmu;

import android.app.Application;
import android.hardware.camera2.CaptureResult;

import java.security.KeyPair;
import java.security.PublicKey;

public class GlobalClass extends Application{
    private String monumento;
    private Questions questions;
    private int rank;
    private int score;
    private KeyPair chaves;
    private PublicKey keyServer;



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
    public KeyPair getKeypair(){
        return chaves;
    }

    public void setKeypair(KeyPair chaves){
        this.chaves=chaves;
    }

    public PublicKey getKeyServer(){
        return keyServer;
    }

    public void setKeyServer(PublicKey key){
        this.keyServer=key;
    }
}