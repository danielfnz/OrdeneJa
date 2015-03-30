package ufg.ordeneja.br;

import ufg.ordeneja.br.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Main extends Activity{
	
	public static final String SORTKO_PREFS = "SortkoPrefsFile";
	
	private String username = "";
	private EditText usernameedit;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//Criando acivity
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        WindowManager.LayoutParams.FLAG_FULLSCREEN);  
        //Definindo tela inicial
        setContentView(R.layout.main);
        
     
        //Criando Botao Escolha Metodo e seu listener
        Button sortit = (Button)findViewById(R.id.escolhaMetodo);
        sortit.setOnClickListener(myListener);
        
        //Criando Botao Como Ordenar e seu listener
        Button btComoOrdenar = (Button)findViewById(R.id.btComoOrdenar);
        btComoOrdenar.setOnClickListener(myListener);
        
        //Criando Botao Melhores Pontuacoes e seu listener
        Button ranking = (Button)findViewById(R.id.ranking);
        ranking.setOnClickListener(myListener);
        
        
        //Criando visualizacao campo username
        usernameedit = (EditText)findViewById(R.id.editusername);
        SharedPreferences settings = getSharedPreferences(SORTKO_PREFS, 0);
        username = settings.getString("username",getResources().getString(R.string.username_Default));
        //Verifica se ja tem algum username default
        if (username != getResources().getString(R.string.username_Default)){
        	usernameedit.setText(username);
        }
	}  
	
	private OnClickListener myListener = new OnClickListener() {
	    public void onClick(View v) {
	    	savePreferences();
	    	if (v.getId()==R.id.btComoOrdenar){
	    		startActivity(new Intent(Main.this,ComoOrdenar.class));
	    	}
	    	if (v.getId()==R.id.escolhaMetodo){
	    		startActivity(new Intent(Main.this,EscolhaAlgoritmo.class));
	    		
	    	} if (v.getId()==R.id.ranking) {
	    		startActivity(new Intent(Main.this,Ranking.class));
	      }
	    }

		private void savePreferences() {
		      SharedPreferences settings = getSharedPreferences(SORTKO_PREFS, 0);
		      SharedPreferences.Editor editor = settings.edit();
		      editor.putString("username", usernameedit.getText().toString());
		      editor.commit();
		}
	};
	
	//Criando menu de opcoes
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.options_menu, menu);
	    return true;
	}
	//switch case selecionado, fazer
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Adicionando botoes no menu
			switch (item.getItemId()) {
	        case R.id.menu_help:
	        
	            return true;
	        case R.id.menu_ordenacao:
	        	
	            return true;
	        case R.id.menu_about:
	        	
	            return true;
	   	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
	


}