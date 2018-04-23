package pt.ulisboa.tecnico.cmov.hoponcmu.response;

public class HelloResponseRegister implements Response {

	private static final long serialVersionUID = 734457624276534179L;
	private String message;

	public HelloResponseRegister(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
