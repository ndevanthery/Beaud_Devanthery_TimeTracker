package adapter;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.beaud_devanthery_timetracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import database.async.task.DeleteTask;
import database.entity.TaskEntity;
import util.OnAsyncEventListener;


public class TaskAdapter extends ArrayAdapter<TaskEntity> {
    public static final String RED = "\033[0;31m";     // RED
    public static final String YELLOW = "\033[0;33m";  // YELLOW

    public static final String RESET = "\033[0m";  // Text Reset

    private List<TaskEntity> items;
    private int layoutResourceId;
    private Context context;
    private LayoutInflater inflater;
    private Application app;

    public TaskAdapter(Context context, int layoutResourceId, List<TaskEntity> items, LayoutInflater inflater , Application app) {
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = items;
        this.inflater = inflater;
        this.app = app;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TaskHolder holder = null;


        //LayoutInflater inflater = ((Fragment) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        holder = new TaskHolder();
        holder.task = items.get(position);
        holder.details = (Button)row.findViewById(R.id.btnDetails);
        holder.details.setTag(holder.task);

        holder.delete = (FloatingActionButton)row.findViewById(R.id.btnDelete);
        holder.delete.setTag(holder.task);

        holder.title = (TextView)row.findViewById(R.id.lblTitle);
        holder.startTime = (TextView)row.findViewById(R.id.lblStartTask);
        holder.endTime = (TextView)row.findViewById(R.id.lblEndTask);
        holder.date = (TextView)row.findViewById(R.id.lblDate);


        row.setTag(holder);

        setupItem(holder);
        return row;
    }

    private void setupItem(TaskHolder holder) {
        holder.title.setText(holder.task.getTaskname());
        int startMinute = holder.task.getStartTime()%60;
        int startHour =( holder.task.getStartTime() - startMinute) /60 ;
        holder.startTime.setText(String.format("start time :%02d:%02d", startHour, startMinute));
        int endMinute = holder.task.getEndTime()%60;
        int endHour = (holder.task.getEndTime() - endMinute)/60;
        holder.endTime.setText(String.format("end time :%02d:%02d", endHour, endMinute));
        holder.date.setText(holder.task.getDate());
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.println(Log.WARN,"DETAILS PUSHED",holder.task.getTaskname()+" asked for his details");
//                System.out.println(YELLOW +"DETAILS BUTTON WAS PUSHED ON " + holder.task.getTaskname()+RESET);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.println(Log.WARN,"DELETE PUSHED ",holder.task.getTaskname()+" asked to be deleted");
                TaskEntity itemToRemove = (TaskEntity) view.getTag();
                remove(itemToRemove);
                new DeleteTask(app, new OnAsyncEventListener() {

                    @Override
                    public void onSuccess() {
                        System.out.println("La task a bien été supprimée");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        System.out.println("La task ne s'est pas supprimée");
                    }
                }).execute(itemToRemove);
            }
        });
    }

    public static class TaskHolder {
        TaskEntity task;
        TextView title;
        TextView startTime;
        TextView endTime;
        TextView date;
        Button details;
        FloatingActionButton delete;
    }
}