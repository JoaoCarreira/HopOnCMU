package pt.ulisboa.tecnico.cmov.hoponcmu;

import java.io.Serializable;

public class Questions implements Serializable{
    private static final long serialVersionUID = 1L;

    public String mQuestions[];
    public String mChoices[][];
    public String mCorrect[];


    public String getQuestion(int a){
        String Question = mQuestions[a];
        return Question;
    }
    public String getChoice1(int a ){
        String Choice = mChoices[a][0];
        return Choice ;
    }
    public String getChoice2(int a ){
        String Choice = mChoices[a][1];
        return Choice ;
    }
    public String getChoice3(int a ){
        String Choice = mChoices[a][2];
        return Choice ;
    }
    public String getChoice4(int a ){
        String Choice = mChoices[a][3];
        return Choice ;
    }
    public String getCorrect(int a){
        String Correct = mCorrect[a];
        return Correct;
    }
}