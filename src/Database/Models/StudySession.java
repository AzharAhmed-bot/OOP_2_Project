package Database.Models;

import java.sql.Date;
import java.sql.Time;

public class StudySession {
    private int id;
    private int schedule_id;
    private int subject_id;
    private int user_id;
    private Date session_date;
    private Time start_time;
    private Time end_time;
    private String status;

    public StudySession(int id, int schedule_id, int subject_id, int user_id, Date session_date, Time start_time, Time end_time, String status) {
        this.id = id;
        this.schedule_id = schedule_id;
        this.subject_id = subject_id;
        this.user_id = user_id;
        this.session_date = session_date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.status = status;
    }

    public StudySession(int schedule_id, int subject_id, int user_id, Date session_date, Time start_time, Time end_time, String status) {
        this.schedule_id = schedule_id;
        this.subject_id = subject_id;
        this.user_id = user_id;
        this.session_date = session_date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public Date getSession_date() {
        return session_date;
    }

    public Time getStart_time() {
        return start_time;
    }

    public Time getEnd_time() {
        return end_time;
    }

    public String getStatus() {
        return status;
    }
}
