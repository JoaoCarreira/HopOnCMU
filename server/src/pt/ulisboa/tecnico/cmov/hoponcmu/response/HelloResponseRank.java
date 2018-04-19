package pt.ulisboa.tecnico.cmov.hoponcmu.response;

import java.util.ArrayList;
import java.util.List;

public class HelloResponseRank implements Response {
	
	private static final long serialVersionUID = 4558278551725745770L;
	private List<ArrayList<String>> message;
	
	public HelloResponseRank(List<ArrayList<String>> message) {
		this.message = message;
	}
	
	public List<ArrayList<String>> getMessage() {
		return this.message;
	}
}


