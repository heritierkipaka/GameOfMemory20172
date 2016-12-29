package fr.kipaka.com.gameofmemory2017;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by nassim on 29/12/16.
 */

public class ListOfMapArrayAdapter extends ArrayAdapter<Map<String,Object>> {

    private Context context;
    private int layoutResourceId;
    private List<Map<String, Object>> data;

    public ListOfMapArrayAdapter(Context context, int layoutResourceId, List<Map<String, Object>> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ScoreHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ScoreHolder();
            holder.txtName = (TextView)row.findViewById(R.id.txtName);
            holder.txtScore = (TextView)row.findViewById(R.id.txtScore);
          //  holder.txtTurns = (TextView)row.findViewById(R.id.txtTurns);
          //  holder.txtDuration = (TextView)row.findViewById(R.id.txtDuration);

            row.setTag(holder);
        }
        else
        {
            holder = (ScoreHolder)row.getTag();
        }

        Map<String, Object> scoreMap = data.get(position);
        holder.txtName.setText((String)scoreMap.get(DBHelper.COMPTEUR_COLUMN_NAME));
        holder.txtScore.setText(String.valueOf(scoreMap.get(DBHelper.COMPTEUR_COLUMN_SCORE)));
        //holder.txtTurns.setText(String.valueOf(scoreMap.get(DBHelper.COMPTEUR_COLUMN_TURNS)));
        //holder.txtDuration.setText(String.valueOf(scoreMap.get(DBHelper.COMPTEUR_COLUMN_DURATION)));

        return row;
    }

    static class ScoreHolder
    {
        TextView txtName;
        TextView txtScore;
        TextView txtTurns;
        TextView txtDuration;
  }
}
