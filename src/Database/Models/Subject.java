package Database.Models;

public class Subject {
    private int id;
    private int user_id;
    private String subject_name;
    private int priority_level;

    public Subject(int id, String subject_name,int user_id,int priority_level) {
        this.id = id;
        this.user_id = user_id;
        this.subject_name = subject_name;
        this.priority_level=priority_level;
    }

    public Subject(String subject_name, int user_id,int priority_level) {
        this.user_id = user_id;
        this.subject_name = subject_name;
        this.priority_level=priority_level;
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
    public int getPriority_level(){
        return priority_level;
    }
    public void print(){
        System.out.println(subject_name+" "+user_id+" "+priority_level);

    }

    @Override
    public String toString() {
        return "Subject{" +
                ", subject_name='" + subject_name + '\'' +
                ", priority_level=" + priority_level +
                '}';
    }
}
