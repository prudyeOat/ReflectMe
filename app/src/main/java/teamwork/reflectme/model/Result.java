package teamwork.reflectme.model;

public class Result {
    private String question;
    private int position;


    public Result(){

    }

    public Result(String question, int position) {
        this.question = question;
        this.position = position;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
