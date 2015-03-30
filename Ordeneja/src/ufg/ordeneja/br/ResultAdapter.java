package ufg.ordeneja.br;

import java.util.ArrayList;

import ufg.ordeneja.br.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ResultAdapter extends ArrayAdapter<Result>{
        
	private ArrayList<Result> items;

	public ResultAdapter(Context context, int textViewResourceId, ArrayList<Result> items) {
                super(context, textViewResourceId, items);
                this.items = items;
    }
	
    public View getView(int position, View convertView, ViewGroup parent) {
    
    	View view = convertView;
        if (view == null) {
        	LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item, null);
        }
        
        Result result = items.get(position);
        
        if (result != null) {
        	TextView resultPlace = (TextView) view.findViewById(R.id.resultplace);
        	TextView resultUser = (TextView) view.findViewById(R.id.usuario);
        	TextView resultNumber = (TextView) view.findViewById(R.id.pontos);
   			
        	if (resultPlace!= null){
    			resultPlace.setText(result.getResultPlace());
    			if (resultUser!= null){
                	resultUser.setText(result.getResultUser());
    			}
                if(resultNumber!= null){
                	resultNumber.setText(result.getResultNumber());
    		    }
        	}
        }
        return view;
    }
}
