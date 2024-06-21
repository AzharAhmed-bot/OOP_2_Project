package Database;

import java.sql.Timestamp;

public class StudySchedule {
    private int id;
    private int user_id;
    private Timestamp created_at;
    private Timestamp updated_at;

    // Constructor for retrieval, including ID
    public StudySchedule(int id, int user_id, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.user_id = user_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    // Constructor for insertion, without ID
    public StudySchedule(int user_id, Timestamp created_at, Timestamp updated_at) {
        this.user_id = user_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    @Override
    public String toString() {
        return "StudySchedule{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
