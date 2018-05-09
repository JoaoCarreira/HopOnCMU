package pt.ulisboa.tecnico.cmov.hoponcmu;

import java.io.Serializable;

public class Questions implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	
	public String mQuestions[];
	public String mChoices[][];
	public String mCorrect[];
    
    public Questions(String monumento){
    	
    	switch (monumento) {
    	
	        case "Torre de Belem":  
	        	
	        	String answers[][] = {
	                    {"1514","1478","1986","1356"},
	                    {"20 anos","7 anos","6 anos","10 anos"},
	                    {"D.Manuel","D.Joao I","Joao II de Portugal","Vasco da Gama"},
	                    {"TagusPark","Cascais","Alges","Belem"},
	            };
	        	
	        	String correct[]={ "1514","6 anos","Joao II de Portugal","Belem"};
	        	String questions[]= {"Em que ano foi construido?","Demorou quantos anos a ser contruido?","Quem mandou contruir ?","Onde se localiza?",};
	        	setQuestions(questions);
	        	setChoices(answers);
	        	setCorrect(correct);
	        	break;

	        
	       
	    	case "Mosteiros dos Jeronimos":
	        	String answers1[][] = {
	                    {"1502","1465","1877","1550"},
	                    {"Rococo","Manuelino","Barroco","Romantico"},
	                    {"1996","2000","1983","1975"},
	                    {"Cacem","Cais Sodre","Alcantara","Belem"},
	            };
	            String correct1[]={ "1502","Manuelino","1983","Belem"};
	        	String questions1[]= {"Em que ano foi construido?","Qual o estilo dominante"," Em que ano foi classificado como Patrimonio Mundial pela UNESCO?","Onde se localiza?",};

	        	setQuestions(questions1);
	        	setChoices(answers1);
	        	setCorrect(correct1);

	            break;
	    	
	    	case "Bairro Alto":
	        	String answers2[][] = {
	                    {"Noite de lisboa","Menina de bigode","Rua Cor de rosa","Imperial barata"},
	                    {"seculo XVII","seculo XVI","seculo XV","seculo XVIII"},
	                    {"1998","2012","2010","2007"},
	                    {"Porto","Santarem","A1 do TagusPark","Centro de Lisboa"},
	            };
	            String correct2[]={ "Noite de lisboa","seculo XVI","2010","Centro de Lisboa"};
	        	String questions2[]= {"Desde os anos 80 e conhecido pela...","Foi construido a partir de que seculo ?","Em que ano foi classificado com Conjunto de Interesse Publico","Onde se localiza?",};

	        	setQuestions(questions2);
	        	setChoices(answers2);
	        	setCorrect(correct2);
	        	
	            break;
	    	

	    	case "Rossio":
	        	String answers3[][] = {
	                    {"Pedro IV de Portugal","Joao Correia","Joao Carreira","Professor de CMU"},
	                    {"Ontem","1870","1879","1976"},
	                    {"O Fundador desta aplicacao","1 Rei de Portugal","28 Rei de Portugal","O primeiro Rei Homosexual"},
	                    {"Porto","Santarem","A1 do TagusPark","Centro de Lisboa"},
	            };
	            String correct3[]={ "Pedro IV de Portugal","1870","28 Rei de Portugal","Centro de Lisboa"};
	        	String questions3[]= {"Quem e homenageado?","Quando foi inaugurada a estatua de D.Pedro IV ?","D.Pedro IV foi ...","Onde se localiza?",};

	        	setQuestions(questions3);
	        	setChoices(answers3);
	        	setCorrect(correct3);
	        	
	            break;
	    	
	    
	       case "Praca do Comercio":
	        	String answers4[][] = {
	                    {"36 000","20 000","54 000","31 000"},
	                    {"Feira da ladra","Terreiro do Paço","Praça do Joao","Torre de Belem"},
	                    {"Jose Cid","D.Afonso IV","D. Jose I","Cavaco Silva"},
	                    {"Porto","Santarem","A1 do TagusPark","Centro de Lisboa"},
	            };
	            String correct4[]={ "36 000","Terreiro do Paço","D. Jose I","Centro de Lisboa"};
	        	String questions4[]= {"Quantos metros quadrados tem ?","Qual a designaçao antiga? ","Quem esta na estatua?","Onde se localiza?",};

	        	setQuestions(questions4);
	        	setChoices(answers4);
	        	setCorrect(correct4);
	        	
	            break;

	        case "Museu da Mariha":
	        	String answers5[][] = {
	                    {"1863","2004","1998","1945"},
	                    {"233","145 000","100 000","45 000"},
	                    {"Joao Correia","D.Afonso IV","D.Luis","Roberto"},
	                    {"Porto","Santarem","A1 do TagusPark","Santa Maria de Belem"},
	            };
	            String correct5[]={ "1863","145 000","D.Luis","Santa Maria de Belem"};
	        	String questions5[]= {"Quando foi inaugurado?","Quantos visitantes leva anualmente? ","Quem mandou construir?","Onde se localiza?",};

	        	setQuestions(questions5);
	        	setChoices(answers5);
	        	setCorrect(correct5);
	        	
	            break;

	        case "CCB":
	        	String answers6[][] = {
	                    {"1993","20 000","54 000","31 000"},
	                    {"Feira da ladra","15 000","Praça do Joao","Torre de Belem"},
	                    {"Jose Cid","D.Afonso IV","1429","Cavaco Silva"},
	                    {"Alameda","Santo Estevao","A1 do TagusPark","Belem"},
	            };
	            String correct6[]={ "1993","15 000","1429","Belem"};
	        	String questions6[]= {"Quando foi inaugurado?","Quantas lampadas tem ?","Quantos lugares tem o grande auditorio?","Onde se localiza?",};

	        	setQuestions(questions6);
	        	setChoices(answers6);
	        	setCorrect(correct6);
	        	
	            break;

	         case "Museu Nacional do Azulejo":
	        	String answers7[][] = {
	                    {"D.Leonor","D.Carlos","D.Joao","D.Pedro"},
	                    {"2018","1509","1625","1542"},
	                    {"Cavalos","Cerveja artesanal","Azulejos","Pepinos"},
	                    {"Braga","Santarem","Coimbra","Lisboa"},
	            };
	            String correct7[]={ "D.Leonor","1509","Azulejos","Lisboa"};
	        	String questions7[]= {"Quem fundou o edificio?","Em que ano foi fundado?","O que se ve neste Museu?","Onde se localiza?",};

	        	setQuestions(questions7);
	        	setChoices(answers7);
	        	setCorrect(correct7);
	        	
	            break;

	        case "Principe Real":
	        	String answers8[][] = {
	                    {"Jardim Franca Borges","Principe Vaidoso","Jardim das Bananas","Jardim do Alfredo"},
	                    {"1838","1853","1927","2000"},
	                    {"Sobreiro","Pinheiro","Cedro-do-Bucaco","Jasmim"},
	                    {"Porto","Santarem","A1 do TagusPark","Lisboa"},
	            };
	            String correct8[]={ "Jardim Franca Borges","1853","Cedro-do-Bucaco","Lisboa"};
	        	String questions8[]= {"Qual o nome oficial?","Em que ano foi fundado?","Que arvore e classificada como interesse publico?","Onde se localiza?",};

	        	setQuestions(questions8);
	        	setChoices(answers8);
	        	setCorrect(correct8);
	        	
	            break;
	    	
	    	
	    

	    }
    }
    


    public void setQuestions(String[] questions){
        this.mQuestions=questions;
    }

    public void setChoices(String[][] choices){
        this.mChoices=choices;
    }

    public void setCorrect(String[] correct){
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