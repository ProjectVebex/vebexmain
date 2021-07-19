package com.example.the_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.the_project.R;
import com.example.the_project.model.NotesDetails;

import java.util.ArrayList;

public class NotesUploadAdapter extends RecyclerView.Adapter<NotesUploadAdapter.ViewHolder> {

    ArrayList<NotesDetails> list;
    Context context;

    public NotesUploadAdapter(ArrayList<NotesDetails> list, Context context)
    {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NotesUploadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_notes_upload,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesUploadAdapter.ViewHolder holder, int position) {
        NotesDetails notesDetails = list.get(position);
        holder.subjectName.setText(notesDetails.getSubjectName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView subjectName;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            subjectName=itemView.findViewById(R.id.IdSubjectNameNotesUpload);
        }
    }
}
