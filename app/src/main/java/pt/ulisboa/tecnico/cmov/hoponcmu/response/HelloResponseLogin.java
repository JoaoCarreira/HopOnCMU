package pt.ulisboa.tecnico.cmov.hoponcmu.response;

public class HelloResponseLogin implements Response {

	private static final long serialVersionUID = 734457624276534179L;
	private String message;
	
	public HelloResponseLogin(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
