package pt.ulisboa.tecnico.cmov.hoponcmu.response;

public class HelloResponseConnection implements Response {

    private String message;

    public HelloResponseConnection(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

