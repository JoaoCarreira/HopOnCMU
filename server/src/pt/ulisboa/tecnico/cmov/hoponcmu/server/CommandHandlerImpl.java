package pt.ulisboa.tecnico.cmov.hoponcmu.server;

import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.command.CommandHandler;
import pt.ulisboa.tecnico.cmov.hoponcmu.command.SendCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.HelloResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.response.Response;

public class CommandHandlerImpl implements CommandHandler {
	
	private List<ArrayList<String>> users= new ArrayList<ArrayList<String>>();
	
	private String resposta = null;
	String login="login";
	String create_account="create_account";
	String logout="logout";
	
	//@Override
	public Response handle(SendCommand hc) {
		
		ArrayList<String> recebido = hc.getMessage();
		
		if(recebido.get(0).equals(login)){
			recebido.remove(0);
			login(recebido);
		}
		
		if(recebido.get(0).equals(create_account)){
			recebido.remove(0);
			create_account(recebido);
		}
		
		if(recebido.get(0).equals(logout)){
			recebido.remove(0);
			logout(recebido);
		}
		
		System.out.println(users);
		return new HelloResponse(resposta);
	}
	
	public String login(ArrayList<String> user){
		
		resposta="Login_Failed";
		
		for(int i=0;i<users.size();i++){
			if(user.equals(users.get(i))){
				resposta="Login_Success";
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
			}
		}
		return resposta;
	}
	
	public String logout(ArrayList<String> user){
		
		resposta="Logout_Failed";
		
		for(int i=0;i<users.size();i++){
			ArrayList<String> utilizador=users.get(i);
			System.out.println(utilizador.get(0));
			if(user.get(0).equals(utilizador.get(0))){
				resposta="Logout_Success";
			}
		}

		return resposta;
	}
}
