package com.example.appmodule.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.appmodule.utils.FeedAdapter;
import com.example.appmodule.R;
import com.example.appmodule.utils.NavBottom;

public class HomeActivity extends NavBottom {

    String[] title;
    String[] content;
    int[] images = {R.drawable.logo,R.drawable.logo};
    RecyclerView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        view = findViewById(R.id.rv_home_view);

        title = getCount("title",2);
        content = getCount("Content",2);

        FeedAdapter adapter = new FeedAdapter(this, title,content,images);
        view.setAdapter(adapter);
        view.setLayoutManager(new LinearLayoutManager(this));

    }

    String[] getCount(String str, int size){
        String [] strSet = new String[size];
        for(int i =0; i<size;i++)
            strSet[i] = str + i;
        return strSet;
    }
}
