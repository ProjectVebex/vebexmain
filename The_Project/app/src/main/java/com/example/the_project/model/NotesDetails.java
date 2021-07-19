package com.example.the_project.model;

import android.net.Uri;
import android.view.View;

public class NotesDetails {
    String subjectName;
    Uri uri;

    public NotesDetails(String subjectName, Uri uri) {
        this.subjectName = subjectName;
        this.uri = uri;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
