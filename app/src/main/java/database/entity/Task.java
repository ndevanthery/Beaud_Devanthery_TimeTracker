package database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
    public class Task {

        @PrimaryKey
        public int id;
        @ColumnInfo(name = "Taskname")
        public String Taskname;
        @ColumnInfo(name = "Description")
        public String Description;
        @ColumnInfo(name = "StartTime")
        public int StartTime;
        @ColumnInfo(name = "EndTime")
        public int EndTime;
        public String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskname() {
        return Taskname;
    }

    public void setTaskname(String taskname) {
        Taskname = taskname;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getStartTime() {
        return StartTime;
    }

    public void setStartTime(int startTime) {
        StartTime = startTime;
    }

    public int getEndTime() {
        return EndTime;
    }

    public void setEndTime(int endTime) {
        EndTime = endTime;
    }
}

