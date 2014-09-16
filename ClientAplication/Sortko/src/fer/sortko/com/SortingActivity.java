package fer.sortko.com;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SortingActivity extends Activity implements OnClickListener {
	private long points = 0;
	private int NO_NUMBERS = 8;
	private int selectedButton = -1;
	private int sortTypeNumber = 0;
	private List<Button> list;
	private Button helpVariable;
	private TextView sortPoints, sortName, sortHelpMessage;
	Algorithm sort;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
				WindowManager.LayoutParams.FLAG_FULLSCREEN);  

		setContentView(R.layout.sort);


		sortPoints = (TextView) findViewById(R.id.sortpoints);
		sortName = (TextView) findViewById(R.id.sortname);
		sortHelpMessage = (TextView) findViewById(R.id.sortHelpMessage); 

		sortTypeNumber = getIntent().getIntExtra("fer.sortko.com.sortTypeNumber", 0);
		startSort();

		TextView changeSort = (TextView) findViewById(R.id.changeSort);
		changeSort.setId(3000);
		changeSort.setOnClickListener((OnClickListener) this);

		list = new ArrayList<Button>();
		final LinearLayout buttonList = (LinearLayout) findViewById(R.id.buttonlist);
		LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		for (int i = 0; i < NO_NUMBERS; i++){

			Button btn = new Button(this);
            btn = (Button) layoutInflater.inflate(R.layout.button, buttonList, false);
			btn.setId(2000+i);
			Integer randomNumber = sort.getNumbersCopy()[i];
			btn.setText(randomNumber.toString());
			btn.setOnClickListener((OnClickListener) this);
			buttonList.addView(btn);
			list.add(btn);
		}
		
		helpVariable = (Button) findViewById(R.id.helpvariable);
		if (sort.NEEDS_HELP_VARIABLE){
			helpVariable.setId(2008);
			helpVariable.setText("0");
			helpVariable.setVisibility(View.VISIBLE);
			helpVariable.setOnClickListener((OnClickListener) this);
			list.add(helpVariable);
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
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.options_menu, menu);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
		Intent browserIntent;
		switch (item.getItemId()) {
	        case R.id.menu_help:
	        	browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_help)));
	        	startActivity(browserIntent);
	            return true;
	        case R.id.menu_about:
	        	browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_about)));
	        	startActivity(browserIntent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void changeSort(){
		Resources resources = getResources();
		final CharSequence[] items = resources.getStringArray(R.array.sorts);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(this.getString(R.string.select_sort));
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				Intent sortIntent = new Intent(SortingActivity.this,SortingActivity.class); 
				sortIntent.putExtra("fer.sortko.com.sortTypeNumber", item);
				startActivity(sortIntent);
				finish();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
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
		}
		else {
			points -= Algorithm.NEGATIVE_POINTS;
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
			showResultActivity();
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
		Intent resultIntent = new Intent(SortingActivity.this, ResultsActivity.class);
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	showHomeActivity();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onConfigurationChanged(final Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
	}
}
