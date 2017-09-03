package xyz.cybersapien.recyclerelesample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
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

        recyclerELEAdapter = new RecyclerELEAdapter<SimpleListAdapter.StringListViewHolder>(listAdapter, emptyView, loadingView, errorView);
        recyclerView.setAdapter(recyclerELEAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.grid_item) {
            Intent intent = new Intent(MainActivity.this, GridActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setList(View v) {
        recyclerELEAdapter.setCurrentView(RecyclerELEAdapter.VIEW_NORMAL);
    }

    public void setEmpty(View v) {
        recyclerELEAdapter.setCurrentView(RecyclerELEAdapter.VIEW_EMPTY);
    }

    public void setError(View v) {
        recyclerELEAdapter.setCurrentView(RecyclerELEAdapter.VIEW_ERROR);
    }

    public void setLoad(View v) {
        recyclerELEAdapter.setCurrentView(RecyclerELEAdapter.VIEW_LOADING);
    }
}
