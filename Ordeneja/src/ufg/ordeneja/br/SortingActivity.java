package ufg.ordeneja.br;

import java.util.ArrayList;
import java.util.List;

import ufg.ordeneja.br.R;

import android.R.bool;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class SortingActivity extends Activity implements OnClickListener {
	private long points = 0;
	private int vidas = 0;
	private int nivel; 
	private int NO_NUMBERS = 8;
	private int selectedButton = -1;
	private int sortTypeNumber = 0;
	public int teste = 0;
	private List<Button> list;
	private Button helpVariable;
	private TextView sortPoints, sortName, sortHelpMessage,quantVidas,varNivel,varAux;
	private ImageView setaAux,logo;
	MediaPlayer somErro,somAcerto,somConcluido,somVidasEsgotadas;
	public Handler handler = new Handler();	
	Algorithm sort;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
		WindowManager.LayoutParams.FLAG_FULLSCREEN);  
		

		setContentView(R.layout.sort);

		quantVidas = (TextView) findViewById(R.id.varTentativas);
		varNivel = (TextView) findViewById(R.id.varNivel);
		sortPoints = (TextView) findViewById(R.id.sortpoints);
		sortName = (TextView) findViewById(R.id.sortname);
		sortHelpMessage = (TextView) findViewById(R.id.sortHelpMessage); 
		logo = (ImageView) findViewById(R.id.logo);
		
		//Recebe o conteudo passado pelo intent e armazena nas variaveis
		sortTypeNumber = getIntent().getIntExtra("ufg.ordeneja.br.sortTypeNumber", 0);
		vidas = getIntent().getIntExtra("ufg.ordeneja.br.vidas", 0);
		nivel = getIntent().getIntExtra("ufg.ordeneja.br.nivel", 1);
		
		//Seta no layout a quantidade de vidas, passada por referencia
		quantVidas.setText(Integer.toString(this.vidas));
		somErro = MediaPlayer.create(SortingActivity.this, R.raw.erro);
		
		//Instancia os sons do aplicativo
		somErro = MediaPlayer.create(SortingActivity.this, R.raw.erro);
		somAcerto = MediaPlayer.create(SortingActivity.this, R.raw.somacerto);
		somVidasEsgotadas = MediaPlayer.create(SortingActivity.this, R.raw.vidasesgotadas);
		
		//Deixa o logo com opcao de voltar ao inicio
		logo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		
				mostraalerta("Voltar ao inicio", "Deseja Voltar para o inicio?");
				
			}
		});
		startSort();

		//Botao para mudar de sort
		TextView changeSort = (TextView) findViewById(R.id.changeSort);
		changeSort.setId(3000);
		changeSort.setOnClickListener((OnClickListener) this);

		//Cria uma lista, para criar os numeros
		list = new ArrayList<Button>();

		final LinearLayout buttonList = (LinearLayout) findViewById(R.id.buttonlist);
		final LinearLayout posicoesVetor = (LinearLayout) findViewById(R.id.posicoesVetor);
		LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		for (int i = 0; i < NO_NUMBERS; i++){
			//Cria o objeto TextView do vetor
			TextView txVetor = new TextView(this);
			//Cria o objeto TextView do Botao
			Button btn = new Button(this);
			
			//Seta o layout de cada botao
	        btn = (Button) layoutInflater.inflate(R.layout.button, buttonList, false);
	        //Seta o layout de cada TextView
	        txVetor = (TextView) layoutInflater.inflate(R.layout.vetor, buttonList, false);
	        
	        //Atualiza o id de cada botao, para que seja possivel selecionalos com switch case
			btn.setId(2000+i);
			
			//Gera numeros randomicos e cria uma copia
			Integer randomNumber = sort.getNumbersCopy()[i];
						
			//Cria o ouvinte de cada botao
			btn.setOnClickListener((OnClickListener) this);
			
			//Seta o valor do botao com o valor gerado randomicamente
			btn.setText(randomNumber.toString());
			//Seta o valor de cada posicao do vetor
			txVetor.setText(Integer.toString(i));
			
			//Adiciona cada botao no LinearLayout
			buttonList.addView(btn);
			//Adiciona cada textView no LinearLayout
			posicoesVetor.addView(txVetor);
			
			//Adicona o botao na lista
			list.add(btn);
	
		}
		
		//Cria o botao auxiliar
				helpVariable = (Button) findViewById(R.id.helpvariable);
				varAux = (TextView) findViewById(R.id.txVarAuxiliar);
				setaAux = (ImageView) findViewById(R.id.imgSeta);
				
				
				if (sort.NEEDS_HELP_VARIABLE){
					helpVariable.setId(2008);
					helpVariable.setText("0");
					helpVariable.setVisibility(View.VISIBLE);
					helpVariable.setOnClickListener((OnClickListener) this);
					
					//Faz aparecer o textview varAux e a seta
					varAux.setVisibility(View.VISIBLE);
					setaAux.setVisibility(View.VISIBLE);
					list.add(helpVariable);
					
					//Atualizar imagem da seta
					
			         Runnable runnable = new Runnable() {
			            int i=0;
			            int []setaVetor={R.drawable.setavaraux3,R.drawable.setavaraux2,R.drawable.setavaraux1};
			            public void run() {
			                setaAux.setImageResource(setaVetor[i]);
			                i++;
			                if(i>setaVetor.length-1)
			                {
			             
			                i=0;    
			                }
			                handler.postDelayed(this, 400);  //delay das trocas de imagem
			            }
			        };
			        handler.postDelayed(runnable, 10); //delay inicial
			    }
		
		AlgorithmPosition ap = sort.findSwitch();
		refreshHelpMessage(ap,null,true);
		
		if(sort.isFinished(ap)){
			disableButtons();
			displayMessage(getResources().getString(R.string.sortingover));
			showResultActivity();
		}
	}
	private void startSort(){
		Resources res = getResources();
		final CharSequence[] items = res.getStringArray(R.array.sorts);
		sortName.setText(items[sortTypeNumber]);
		
		//Seta no layout o nivel de dificuldade
		if(nivel==1) { varNivel.setText("Iniciante");}
		if(nivel==2) { varNivel.setText("Intermediario");}
		if(nivel==3) { varNivel.setText("Avançado"); 
		disableHelpMessage();
		}

		switch (sortTypeNumber){
		case 0: sort = new BubbleSort(NO_NUMBERS, this);
		break;
		case 1: sort = new InsertionSort(NO_NUMBERS, this);
		break;
		case 2: sort = new QuickSort(NO_NUMBERS, this);
		break;
		case 3: sort = new ShellSort(NO_NUMBERS, this);
		break;
		case 4: sort = new SelectionSort(NO_NUMBERS, this);
		break;
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case 2000: selectButton(0);
		break;
		case 2001: selectButton(1);
		break;
		case 2002: selectButton(2);
		break;
		case 2003: selectButton(3);
		break;
		case 2004: selectButton(4);
		break;
		case 2005: selectButton(5);
		break;
		case 2006: selectButton(6);
		break;
		case 2007: selectButton(7);
		break;
		case 2008: selectButton(8);
		break;
		case 3000: changeSort();
		break;
		}
	}
	//criando menu
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.options_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
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
	
	private void changeSort(){
		Resources resources = getResources();
		startActivity(new Intent(this,EscolhaAlgoritmo.class));
		finish();
		
	}
	private void selectButton(int buttonNumber){
		
		AlgorithmPosition ap = sort.findSwitch();
		
		if (selectedButton == -1){
			selectedButton = buttonNumber;
			list.get(buttonNumber).setBackgroundResource(R.drawable.selected_button);
		}
		else if (selectedButton == buttonNumber){
			if (ap.getAlgorithmIndexI() == ap.getAlgorithmIndexJ()){
				goToNextAlgStepUpdateUI(buttonNumber);
			}
			selectedButton = -1;
			list.get(buttonNumber).setBackgroundResource(R.drawable.button);

		}
		else {
			// pokušaj zamjeniti brojeve, ako uspije vraæa bodove i updejta UI
			//mostraalerta("Posicao Selecionada ", Integer.toString(ap.getAlgorithmIndexJ()));
			//mostraalerta("Posicao correta ", Integer.toString(ap.getAlgorithmIndexI()));
			goToNextAlgStepUpdateUI(buttonNumber);
		}
	}
	
	private void goToNextAlgStepUpdateUI(int buttonNumber) {
		AlgorithmPosition ap;
		AlgorithmPosition userAlgorithmPosition = new AlgorithmPosition(selectedButton, buttonNumber, null, this);
		long newPoints = sort.goToNextPosition(userAlgorithmPosition);

		ap = sort.findSwitch();

		boolean isSwitchSuccessful = false;
		
		if(newPoints > 0){
			isSwitchSuccessful = true;
			points += newPoints;
			somAcerto.start();
		}
		else {
			points -= Algorithm.NEGATIVE_POINTS;
			vidas -= 1;
			quantVidas.setText(Integer.toString(this.vidas));
			if (vidas>=1)
			{
				somErro.start();
			}
			
		
			//TODO: dodati da negativne bodove raèuna algoritam (10-20% ovisno o algoritmu)
			displayMessage(getResources().getString(R.string.sortingFault));
		}
		
		refreshButtons(ap);
		refreshHelpMessage(ap, userAlgorithmPosition, isSwitchSuccessful);
		
		list.get(buttonNumber).setBackgroundResource(R.drawable.button);
		list.get(selectedButton).setBackgroundResource(R.drawable.button);
		selectedButton = -1;
		
		if(sort.isFinished(sort.findSwitch())){
			
			disableButtons();
			disableHelpMessage();
			displayMessage(getResources().getString(R.string.sortingover));
			showSucesso();
		}
		if(vidas==0)
		{
			
			somVidasEsgotadas.start();
			disableButtons();
			disableHelpMessage();
			showFracasso();
		}
	}
	
	private void disableHelpMessage() {
		this.sortHelpMessage.setVisibility(TextView.INVISIBLE);
	}
	private void disableButtons(){
		for (int i = 0; i < 8; i++){
			list.get(i).setBackgroundResource(R.drawable.selected_button);
		}	
	}
	private void refreshHelpMessage(AlgorithmPosition ap, AlgorithmPosition userAP, boolean isSwitchSuccessful) {
		String message = ap.getHelpMessage(userAP, isSwitchSuccessful);
		this.sortHelpMessage.setText(message);
	}
	private void refreshButtons(AlgorithmPosition ap) {
		int[] newNumbers = ap.getCurrentNumbersList();
		for (int i = 0; i<8; i++){
			list.get(i).setText(Integer.toString(newNumbers[i]));
		}
		if (sort.NEEDS_HELP_VARIABLE){
			helpVariable.setText(Integer.toString(ap.getHelpVariable()));
		}
		sortPoints.setText(Long.toString(this.points));
	}
	private void displayMessage(String string) {
		Toast toast = Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.TOP, 0, 50);
		toast.show();
	}
	
	private void showResultActivity(){
		Intent resultIntent = new Intent(SortingActivity.this, Ranking.class);
		resultIntent.putExtra("fer.sortko.com.result", points);
		resultIntent.putExtra("fer.sortko.com.sortTypeNumber", sortTypeNumber);
		startActivity(resultIntent);
		finish();
	}
	
	private void showHomeActivity(){
		Intent resultIntent = new Intent(SortingActivity.this, Main.class);
		startActivity(resultIntent);
		finish();
	}
	private void showSucesso(){
		Intent sucesso = new Intent(SortingActivity.this, ResultadoActivity.class);
		sucesso.putExtra("ufg.ordeneja.br.result", points);
		sucesso.putExtra("ufg.ordeneja.br.sortTypeNumber", sortTypeNumber);
		sucesso.putExtra("ufg.ordeneja.br.status", 0);
		startActivity(sucesso);
		finish();
	}
	private void showFracasso(){
		Intent fracasso = new Intent(SortingActivity.this, ResultadoActivity.class);
		fracasso.putExtra("ufg.ordeneja.br.result", points);
		fracasso.putExtra("ufg.ordeneja.br.sortTypeNumber", sortTypeNumber);
		fracasso.putExtra("ufg.ordeneja.br.status", 1);
		startActivity(fracasso);
		finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    mostraalerta("Atenção!", "Deseja sair da ordenação?");

	    
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onConfigurationChanged(final Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
	}
	
	public void mostraalerta(String title, String mensagem)
	{
		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 builder.setTitle(title);
		 builder.setMessage(mensagem);
		 builder.setIcon(R.drawable.icon_atencao);
		 builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			@Override
				public void onClick(DialogInterface dialog, int which) {
			  	finish();
				
			
				} });
		builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
			@Override
		        public void onClick(DialogInterface dialog, int which) {

		          dialog.cancel();
		      } });

		 builder.create();
		 builder.show();
	}
	
}
