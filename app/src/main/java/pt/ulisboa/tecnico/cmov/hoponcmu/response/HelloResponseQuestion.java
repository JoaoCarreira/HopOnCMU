package pt.ulisboa.tecnico.cmov.hoponcmu.response;

import pt.ulisboa.tecnico.cmov.hoponcmu.Questions;

public class HelloResponseQuestion implements Response {

    private static final long serialVersionUID = 734457624276534179L;
    private Questions question;

    public HelloResponseQuestion(Questions message) {
        this.question = message;
    }

    public Questions getMessage() {
        return this.question;
    }
}
