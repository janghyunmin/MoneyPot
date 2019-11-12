package com.quantec.moneypot.activity.history;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.quantec.moneypot.activity.history.adapter.AdapterHistory;
import com.quantec.moneypot.R;

public class ActivityHistory extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ModelHistory modelHistory;
    AdapterHistory adapterHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        modelHistory = new ModelHistory();
        adapterHistory = new AdapterHistory();

        recyclerView.setAdapter(adapterHistory);
    }
}