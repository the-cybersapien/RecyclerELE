package xyz.cybersapien.recyclerelesample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ogcybersapien on 3/7/2017.
 * Simple List Adapter for our Strings list
 */

public class SimpleListAdapter extends RecyclerView.Adapter<SimpleListAdapter.StringListViewHolder> {

    private List<String> strings;

    public SimpleListAdapter(List<String> strings) {
        this.strings = new ArrayList<>();
        if (!strings.isEmpty())
            this.strings.addAll(strings);
    }

    @Override
    public StringListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.string_item, parent, false);
        return new StringListViewHolder(tv);
    }

    @Override
    public void onBindViewHolder(StringListViewHolder holder, int position) {
        holder.string = strings.get(position);
        holder.textView.setText(position + " " + holder.string);
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public class StringListViewHolder extends RecyclerView.ViewHolder {

        public String string;
        public TextView textView;

        public StringListViewHolder(TextView t) {
            super(t);
            textView = t;
        }
    }
}
