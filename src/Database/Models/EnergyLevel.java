package Database.Models;


public class EnergyLevel {
    private int id;
    private int user_id;
    private String time_of_day;
    private int energy_rating;

    // Constructor for retrieval, including ID
    public EnergyLevel(int id, int user_id, String time_of_day, int energy_rating) {
        this.id = id;
        this.user_id = user_id;
        this.time_of_day = time_of_day;
        this.energy_rating = energy_rating;
    }

    // Constructor for insertion, without ID
    public EnergyLevel(int user_id, String time_of_day, int energy_rating) {
        this.user_id = user_id;
        this.time_of_day = time_of_day;
        this.energy_rating = energy_rating;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getTime_of_day() {
        return time_of_day;
    }

    public int getEnergy_rating() {
        return energy_rating;
    }

    @Override
    public String toString() {
        return "EnergyLevel{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", time_of_day='" + time_of_day + '\'' +
                ", energy_rating=" + energy_rating +
                '}';
    }
}
