package com.example.the_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.the_project.NotesUploadActivity;
import com.example.the_project.R;

import java.util.ArrayList;

public class NotesTeacherAdapter extends RecyclerView.Adapter<NotesTeacherAdapter.ViewHolder>{

    ArrayList<String> listSubjectName;
    ArrayList<String> listSubjectId;
    Context context;

    public NotesTeacherAdapter(ArrayList<String> listSubjectName, ArrayList<String> listSubjectId, Context context)
    {
        this.listSubjectId = listSubjectId;
        this.listSubjectName = listSubjectName;
        this.context = context;
    }


    @NonNull
    @Override
    public NotesTeacherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_notes_teacher,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesTeacherAdapter.ViewHolder holder, int position) {
        String subjectName = listSubjectName.get(position);
        String subjectId = listSubjectId.get(position);
        holder.subjectName.setText(subjectName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NotesUploadActivity.class);
                intent.putExtra("SubjectName", subjectName);
                intent.putExtra("SubjectId",subjectId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSubjectName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView subjectName;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            subjectName=itemView.findViewById(R.id.IdSubjectName);
        }
    }
}
