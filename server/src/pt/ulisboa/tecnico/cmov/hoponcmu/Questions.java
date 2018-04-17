package pt.ulisboa.tecnico.cmov.hoponcmu;

public class Questions {
    public String mQuestions[] = {
            "Em que ano foi construído?",
            "Demorou quantos anos a ser contruído?",
            "Eu sou a pergunta nº3",
            "Eu sou a pergunta nº4",
        };
    public String mChoices[][];
    public String mCorrect[]={ "Resposta1.1","Resposta2.2","Resposta3.3","Resposta4.4"};
    
    public Questions(String monumento){
    	
    	switch (monumento) {
    	
        case "Torre de Belem":  
        	String [][] answers= {
                    {"Resposta1.1","Resposta1.2","Resposta1.3","Resposta1.4"},
                    {"Resposta2.1","Resposta2.2","Resposta2.3","Resposta2.4"},
                    {"Resposta3.1","Resposta3.2","Resposta3.3","Resposta3.4"},
                    {"Resposta4.1","Resposta4.2","Resposta4.3","Resposta4.4"},
            };
        	setChoices(answers);
        	break;
        	
        case "Mosteiros dos Jeronimos":
        	String [][] answers1= {
                    {"Resposta1.1","Resposta1.2","Resposta1.3","Resposta1.4"},
                    {"Resposta2.1","Resposta2.2","Resposta2.3","Resposta2.4"},
                    {"Resposta3.1","Resposta3.2","Resposta3.3","Resposta3.4"},
                    {"Resposta4.1","Resposta4.2","Resposta4.3","Resposta4.4"},
            };
        	setChoices(answers1);
            break;
    	}
    }
    
    public void setQuestions(String[] questions){
        this.mQuestions=questions;
    }

    public void setChoices(String[][] choices){
        this.mChoices=choices;
    }

    public void setmCorrect(String[] correct){
        this.mCorrect=correct;
    }

    public String[] getQuestion(){
        return mQuestions;
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