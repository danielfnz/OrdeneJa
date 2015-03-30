package ufg.ordeneja.br;

import ufg.ordeneja.br.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class ComoOrdenar extends Activity {


	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		this.setContentView(R.layout.como_ordenar);
		
		
		//Criando Botao Como Ordenar e seu listener
        Button btBublle = (Button)findViewById(R.id.btBubble);
        btBublle.setOnClickListener(myListener);
        
        
        Button btQuick = (Button)findViewById(R.id.btQuick);
        btQuick.setOnClickListener(myListener);
        
        Button btSelect = (Button)findViewById(R.id.btSelection);
        btSelect.setOnClickListener(myListener);
        
        Button btShell = (Button)findViewById(R.id.btShell);
        btShell.setOnClickListener(myListener);
        
        Button btInsert = (Button)findViewById(R.id.btInsertion);
        btInsert.setOnClickListener(myListener);
              
           
        
			}
	//Aqui fica ouvindo qual botao foi precionado
	private OnClickListener myListener = new OnClickListener() {
	    public void onClick(View v) {
	    	if (v.getId()==R.id.btBubble){
	    		startActivity(new Intent(ComoOrdenar.this, BubbleHelp.class));
	    	}
	    	
	    	if (v.getId()==R.id.btInsertion){
	    		startActivity(new Intent(ComoOrdenar.this, InsertionHelp.class));
	    		
	    	} 
	       	if (v.getId()==R.id.btSelection){
	    		startActivity(new Intent(ComoOrdenar.this, SelectionHelp.class));
	    		
	    	} 
	    	if (v.getId()==R.id.btQuick){
	    		startActivity(new Intent(ComoOrdenar.this, BubbleHelp.class));
	    		
	    	} 
		    if (v.getId()==R.id.btShell){
	    		startActivity(new Intent(ComoOrdenar.this, BubbleHelp.class));
	    		
	    	} 
	    	
	 	    	
	      }
	    

	
	};
	
}
