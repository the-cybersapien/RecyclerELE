package xyz.cybersapien.recyclerelesample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import xyz.cybersapien.recyclerele.RecyclerELEAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerELEAdapter recyclerELEAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> stringsList = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            stringsList.add("Item " + 1);
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        View loadingView = getLayoutInflater().inflate(R.layout.view_loading, recyclerView, false);
        View emptyView = getLayoutInflater().inflate(R.layout.view_empty, recyclerView, false);
        View errorView = getLayoutInflater().inflate(R.layout.view_error, recyclerView, false);

        SimpleListAdapter listAdapter = new SimpleListAdapter(stringsList);

        recyclerELEAdapter = new RecyclerELEAdapter(listAdapter, emptyView, loadingView, errorView);
        recyclerView.setAdapter(recyclerELEAdapter);
    }


    public void setList(View v){
        recyclerELEAdapter.setCurrentView(RecyclerELEAdapter.VIEW_NORMAL);
    }

    public void setEmpty(View v){
        recyclerELEAdapter.setCurrentView(RecyclerELEAdapter.VIEW_EMPTY);
    }

    public void setError(View v){
        recyclerELEAdapter.setCurrentView(RecyclerELEAdapter.VIEW_ERROR);
    }

    public void setLoad(View v){
        recyclerELEAdapter.setCurrentView(RecyclerELEAdapter.VIEW_LOADING);
    }
}
