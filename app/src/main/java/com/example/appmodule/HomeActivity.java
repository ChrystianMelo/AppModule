package com.example.appmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class HomeActivity extends AppCompatActivity {

    String[] title;
    String[] content;
    int images[] = {R.drawable.logo,R.drawable.logo};
    RecyclerView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        view = findViewById(R.id.rv_home_view);

        title = getCount(title,2);
        content = getCount(title,2);

        FeedAdapter adapter = new FeedAdapter(this, title,content,images);
        view.setAdapter(adapter);
        view.setLayoutManager(new LinearLayoutManager(this));

    }

    String[] getCount(String[] str, int size){
        str = new String[size];
        for(int i =0; i<size;i++)
            str[i] = "info"+Integer.toString(i);
        return str;
    }
}
