package pt.ulisboa.tecnico.cmov.hoponcmu.response;

public class HelloResponseLogout implements Response {

	private static final long serialVersionUID = 7103423619468797548L;
	private String message;
	
	public HelloResponseLogout(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
