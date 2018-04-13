package pt.ulisboa.tecnico.cmov.hoponcmu.command;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.hoponcmu.response.Response;

public class SendCommand implements Command {

	private static final long serialVersionUID = -8807331723807741905L;
	private ArrayList<String> conteudo= new ArrayList<String>();

	public SendCommand(String[] message) {

		for(int i=0;i<message.length;i++ ){
			conteudo.add(message[i]);
		}
	}

	//@Override
	public Response handle(CommandHandler chi) {
		return chi.handle(this);
	}

	public ArrayList<String> getMessage() {
		return this.conteudo;
	}
}