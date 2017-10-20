package com.example.chow.minigamemarathon;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by per6 on 10/18/17.
 */

public class LightsOutFragment extends Fragment implements OnGridChangeListener, AdapterView.OnItemClickListener{

    //private ArrayAdapter<String> adapter;
    private LightsOutAdapter adapter;
    private LightsOut game;
    private ArrayList<Boolean> grid1D;
    private ArrayList<String> grid1DTest;
    private GridView displayedLights;
    private String TAG = "LightsOutFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.lights_out_layout, container, false);

        game = new LightsOut(5, 5);
        grid1DTest = convertTo1DTest(game.getGrid());
        adapter = new LightsOutAdapter(getActivity(), android.R.layout.simple_list_item_1, grid1DTest);
        displayedLights = rootView.findViewById(R.id.displayed_lights_gridview);
        displayedLights.setNumColumns(game.getGrid()[0].length);
        displayedLights.setAdapter(adapter);
        Toast.makeText(getActivity(), "started", Toast.LENGTH_SHORT).show();

        return rootView;
    }

    private ArrayList<Boolean> convertTo1D(boolean[][] grid)
    {
        ArrayList<Boolean> copy = new ArrayList<>();
        for (boolean[] row : grid)
        {
            for (boolean col : row)
            {
                copy.add(col);
            }
        }
        return copy;
    }

    private ArrayList<String> convertTo1DTest(boolean[][] grid)
    {
        ArrayList<String> copy = new ArrayList<>();
        for (boolean[] row : grid)
        {
            for (boolean col : row)
            {
                copy.add(((Boolean) col).toString());
            }
        }
        return copy;
    }

    @Override
    public void onGridChange() {
        Toast.makeText(getActivity(), "flipped", Toast.LENGTH_SHORT).show();
        grid1D = convertTo1D(game.getGrid());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        game.flipSwitch(1, 1);
        Log.d(TAG, "onItemClick: switch flipped" + i);
        adapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), "switch flipped", Toast.LENGTH_SHORT);
    }

    private class LightsOutAdapter extends ArrayAdapter<String> {

        public LightsOutAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //inflate the layoutinflater
            if(convertView == null)
            {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.lights_out_root_element, parent, false);
            }
            TextView itemText = (TextView) convertView.findViewById(R.id.lights_out_root_textview);
            itemText.setText(grid1DTest.get(position));


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    game.flipSwitch(1, 1);
                    adapter.notifyDataSetChanged();
                    Log.d(TAG, "onClick: on click");
                }
            });
            return convertView;
            //return super.getView(position, convertView, parent);
        }
    }
}
