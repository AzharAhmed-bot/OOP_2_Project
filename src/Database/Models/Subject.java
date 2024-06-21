package Database.Models;

public class Subject {
    private int id;
    private int user_id;
    private String subject_name;

    public Subject(int id, String subject_name,int user_id) {
        this.id = id;
        this.user_id = user_id;
        this.subject_name = subject_name;
    }

    public Subject(String subject_name, int user_id) {
        this.user_id = user_id;
        this.subject_name = subject_name;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getSubject_name() {
        return subject_name;
    }
}
