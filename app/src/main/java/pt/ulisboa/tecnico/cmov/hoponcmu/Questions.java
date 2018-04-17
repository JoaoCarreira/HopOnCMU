package pt.ulisboa.tecnico.cmov.hoponcmu;

public class Questions {
    public String mQuestions[] = {
            "Eu sou a pergunta nº1",
            "Eu sou a pergunta nº2",
            "Eu sou a pergunta nº3",
            "Eu sou a pergunta nº4",
        };
    public String mChoices[][]={
            {"Resposta1.1","Resposta1.2","Resposta1.3","Resposta1.4"},
            {"Resposta2.1","Resposta2.2","Resposta2.3","Resposta2.4"},
            {"Resposta3.1","Resposta3.2","Resposta3.3","Resposta3.4"},
            {"Resposta4.1","Resposta4.2","Resposta4.3","Resposta4.4"},
    };
    public String mCorrect[]={ "Resposta1.1","Resposta2.2","Resposta3.3","Resposta4.4"};

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