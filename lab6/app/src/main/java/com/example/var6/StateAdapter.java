package com.example.var6;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StateAdapter  extends RecyclerView.Adapter<StateAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<State> states;
    Context context;

    StateAdapter(Context context, List<State> states) {
        this.states = states;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StateAdapter.ViewHolder holder, int position) {
        State state = states.get(position);
        holder.nameView.setText(state.getName());
        holder.descriptionView.setText(state.getDescription());
        holder.dateView.setText(state.getDate());
        holder.timeView.setText(state.getTime());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                states.remove(holder.getPosition());
                SQLiteDatabase db = context.openOrCreateDatabase("zadachnik1.db", context.MODE_PRIVATE, null);
                db.execSQL("DELETE FROM zadachi WHERE name='" + state.getName() + "'");
                notifyDataSetChanged();
            }
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SecondActivity.class);

                intent.putExtra("name", state.getName());
                intent.putExtra("description", state.getDescription());
                intent.putExtra("date", state.getDate().equals("") == true ? "--.--.----" : state.getDate());
                intent.putExtra("time", state.getTime().equals("") == true ? "--:--" : state.getTime());
                intent.putExtra("priority", state.getPriority());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView, descriptionView, dateView, timeView;
        final Button editButton, deleteButton;

        ViewHolder(View view) {
            super(view);
            nameView = view.findViewById(R.id.name);
            descriptionView = view.findViewById(R.id.description);
            dateView = view.findViewById(R.id.date);
            timeView = view.findViewById(R.id.time);
            editButton = view.findViewById(R.id.buttonedit);
            deleteButton = view.findViewById(R.id.buttondelete);
        }
    }
}