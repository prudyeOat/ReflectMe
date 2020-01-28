package teamwork.reflectme.model;

public class Note {
    private String name;
    private String date;
    private String content;
    private String imagePath;

    public Note(String name, String date, String content, String imagePath) {
        this.name = name;
        this.date = date;
        this.content = content;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
