package pt.ulisboa.tecnico.cmov.hoponcmu.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.command.CommandHandler;
import pt.ulisboa.tecnico.cmov.hoponcmu.command.SendCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.HelloResponseLogin;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.HelloResponseLogout;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.Questions;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.HelloResponseQuestion;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.HelloResponseRank;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.HelloResponseRegister;

public class CommandHandlerImpl implements CommandHandler {
	
	private List<ArrayList<String>> users= new ArrayList<ArrayList<String>>();
	private List<ArrayList<String>> rank= new ArrayList<ArrayList<String>>();
	private HashMap<Integer, String> login_map = new HashMap<Integer, String>();
	
	private String resposta = null;
	private Questions question;

	String login="login";
	String create_account="create_account";
	String logout="logout";
	String questao="criar_questao";
	String update_score="update_score";
	String get_rank="get_rank";
	Integer id=0;
	
	//@Override
	public Response handle(SendCommand hc) {
		
		ArrayList<String> recebido = hc.getMessage();
		
		if(recebido.get(0).equals(login)){
			recebido.remove(0);
			login(recebido);
			return new HelloResponseLogin(resposta);
		}
		
		if(recebido.get(0).equals(create_account)){
			recebido.remove(0);
			create_account(recebido);
			return new HelloResponseRegister(resposta);
		}
		
		if(recebido.get(0).equals(logout)){
			recebido.remove(0);
			logout(recebido);
			return new HelloResponseLogout(resposta);
		}
		
		if(recebido.get(0).equals(questao)){
			recebido.remove(0);
			create_question(recebido);
			return new HelloResponseQuestion(question);
		}
		
		if(recebido.get(0).equals(update_score)){
			recebido.remove(0);
			updateRank(recebido);
		}
		
		if(recebido.get(0).equals(get_rank)){
			recebido.remove(0);
			return new HelloResponseRank(rank);
		}
		
		return null;
	}
	
	public String login(ArrayList<String> user){
		
		resposta="Login_Failed";
		
		for(int i=0;i<users.size();i++){
			if(user.equals(users.get(i))){
				id=id+1;
				resposta="Login_Success,"+id;
				login_map.put(id, user.get(0));
			}
		}

		return resposta;
	}
	
	public String create_account(ArrayList<String> user){
		
		boolean success=true;
		resposta="Create_Account_Failed";
		
		if(users.size()==0){
			users.add(user);
			resposta="Create_Account_Success";
			ArrayList<String> aux= new ArrayList<String>();
			aux.add(user.get(0));
			aux.add(""+0);
			aux.add(""+0);
			rank.add(aux);
		}
		
		else{
			for(int i=0;i<users.size();i++){
				ArrayList<String> utilizador= users.get(i);
				if(user.get(0).equals(utilizador.get(0))){
					success = false;
				}
			}
			
			for(int i=0;i<users.size();i++){
				ArrayList<String> utilizador= users.get(i);
				if(user.get(1).equals(utilizador.get(1))){
					success = false;
				}
			}
			
			if(success==true){
				users.add(user);
				resposta="Create_Account_Success";
				ArrayList<String> aux= new ArrayList<String>();
				aux.add(user.get(0));
				aux.add(""+0);
				aux.add(""+0);
				rank.add(aux);
				
			}
		}
		return resposta;
	}
	
	public String logout(ArrayList<String> user){
		
		resposta="Logout_Failed";
		int aux= Integer.parseInt(user.get(0));
		
		if(login_map.containsKey(aux)){
			resposta="Logout_Success";
			login_map.remove(aux);
		}

		return resposta;
	}
	
	public Questions create_question(ArrayList<String> monument){
		
		question = new Questions(monument.get(0));
		return question;
	}
	
	public void updateRank(ArrayList<String> score){
		
		int session_id=Integer.parseInt(score.get(0));
		
		if(login_map.containsKey(session_id)){
			String user_aux=login_map.get(session_id);
			for(int i=0;i<rank.size();i++){
				ArrayList<String> score_user=rank.get(i);
				if(score_user.get(0).equals(user_aux)){
					int aux = Integer.parseInt(score_user.get(1));
					int aux_score = Integer.parseInt(score.get(1));
					int final_score=aux+aux_score;
					int aux_correct = Integer.parseInt(score_user.get(2));
					int aux_rec_correct = Integer.parseInt(score.get(2));
					int final_correct=aux_correct+aux_rec_correct;
					score_user.set(1, ""+final_score);
					score_user.set(2, ""+final_correct);
				}
			}
		}
		
	}
}
	

