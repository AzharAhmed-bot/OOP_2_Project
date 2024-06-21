package Database;

import java.sql.Date;

public class AcademicGoal {
    private int id;
    private int user_id;
    private String goal_description;
    private Date target_date;
    private int priority_level;
    private String status;

    // Constructor for retrieval, including ID
    public AcademicGoal(int id, int user_id, String goal_description, Date target_date, int priority_level, String status) {
        this.id = id;
        this.user_id = user_id;
        this.goal_description = goal_description;
        this.target_date = target_date;
        this.priority_level = priority_level;
        this.status = status;
    }

    // Constructor for insertion, without ID
    public AcademicGoal(int user_id, String goal_description, Date target_date, int priority_level, String status) {
        this.user_id = user_id;
        this.goal_description = goal_description;
        this.target_date = target_date;
        this.priority_level = priority_level;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getGoal_description() {
        return goal_description;
    }

    public Date getTarget_date() {
        return target_date;
    }

    public int getPriority_level() {
        return priority_level;
    }

    public String getStatus() {
        return status;
    }
}
