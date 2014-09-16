package fer.sortko.com;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends ListActivity implements OnClickListener{
    
	public static final String SORTKO_PREFS = "SortkoPrefsFile";
	
	private long sortingResult = 0;
	private int sortTypeNumber = 0;
	private String username = "";
	private String methodName;
	private String methodParams;
    private ProgressDialog resultsProgressDialog = null;
    private ArrayList<Result> results = null;
    private ResultAdapter resultsAdapter;
    private Runnable viewResults;
	//TODO: spremanje rezultata u bazu podataka tako da ako nema veze da se mo�e uploadati kasnije
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);  
        setContentView(R.layout.result);
        
        sortingResult = getIntent().getLongExtra("fer.sortko.com.result", 0);
        sortTypeNumber = getIntent().getIntExtra("fer.sortko.com.sortTypeNumber", -1);
        
        if(sortingResult != 0){
	        SharedPreferences settings = getSharedPreferences(SORTKO_PREFS, 0);
	        username = settings.getString("username", this.getString(R.string.username_Default));
	        
	        
	        methodName = "pohranirezultat";
			
	        try {
	        	methodParams = "igrac=" + URLEncoder.encode(username,"UTF-8") + "&rezultat=" + sortingResult + "&idvrstesorta=" + sortTypeNumber;
	        }
	        catch (Exception e){
	        	Log.e("URLEncoder", e.getMessage());
	        }
	        Log.i("TEST", methodParams);
        }
        
        TextView listTitle = (TextView) findViewById(R.id.listtitle);
        TextView changeList = (TextView) findViewById(R.id.changeList);
        changeList.setVisibility(4);
		changeList.setOnClickListener((OnClickListener) this);
        
        if(sortTypeNumber != -1){
    		Resources resources = getResources();
    		final CharSequence[] items = resources.getStringArray(R.array.sorts);
    		listTitle.setText(items[sortTypeNumber]);
    		changeList.setVisibility(0);
        }
        
	    TextView resultTextView = (TextView) findViewById(R.id.result);
	    resultTextView.setText(Long.toString(sortingResult));

        Button selectSort = (Button) findViewById(R.id.selectsort);
        selectSort.setOnClickListener((View.OnClickListener)this);
        
        results = new ArrayList<Result>();
        this.resultsAdapter = new ResultAdapter(this, R.layout.list_item, results);
        setListAdapter(this.resultsAdapter);
        
        viewResults = new Runnable(){
            @Override
            public void run(){
            	callWebService(methodName,methodParams);
                getResults(sortTypeNumber);
            }
        };
        
        Thread thread =  new Thread(null, viewResults, "GetResults");
        thread.start();
        resultsProgressDialog = ProgressDialog.show(ResultsActivity.this, 
        		this.getString(R.string.progress_title), this.getString(R.string.progress_description), true);   
	}
	
	@Override
	public void onClick(View view){
		if (view.getId()== R.id.selectsort){
			selectSort();
		}
		else if (view.getId() == R.id.changeList){
			changeList();
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

	private void getResults(int idVrsteSorta){
		try{
			String result = callWebService("dohvatirezultate","idvrstesorta=" + Integer.toString(idVrsteSorta));
        	
            String[] items = result.substring(result.indexOf(">")+1,result.indexOf("</string>")).split("&#xD;",50);
            // header == <string xmlns="http://schemas.microsoft.com/2003/10/Serialization/">
            // result == MarkoMarulic§139909&#xD;
            // footer == </string>
            int resultNumber = 0;
            results = new ArrayList<Result>();
            for (String item : items) {
            	
                Result r = new Result();
                r.setResultPlace(Integer.toString(++resultNumber)+".");
            	r.setResultUser(item.substring(0, item.indexOf("§")).trim());
            	r.setResultNumber(item.substring(item.indexOf("§")+1));
            	results.add(r);
            }
		}catch (Exception e) { 
			e.printStackTrace();
		}
		runOnUiThread(returnResults);
	}
	
	private Runnable returnResults = new Runnable() {
        @Override
        public void run() {
        	resultsAdapter.clear();
            if(results != null && results.size() > 0){
                resultsAdapter.notifyDataSetChanged();
                for(int i=0; i < results.size(); i++)
                	resultsAdapter.add(results.get(i));
            }
            resultsProgressDialog.dismiss();
            resultsAdapter.notifyDataSetChanged();
        }
    };
	
	private String callWebService(String methodName, String methodParams){
		HttpClient client = new DefaultHttpClient();
    	HttpHost targetHost = new HttpHost(getString(R.string.url_domain_webservice),80,"http");
    	HttpGet httpGet = new HttpGet(getString(R.string.url_path_webservice)+methodName+"?"+methodParams);
    	String result = null;
        HttpEntity entity = null;
        try {
            HttpResponse response = client.execute(targetHost, httpGet);
            entity = response.getEntity();
            result = EntityUtils.toString(entity);   
        }
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
	        if (entity!=null){
		        try {
		            entity.consumeContent();
		        } catch (IOException e) {
		        	e.printStackTrace();
		        }
	        }
        }        
        return result;	
	}
    
	private void selectSort(){
		Resources resources = getResources();
    	final CharSequence[] items = resources.getStringArray(R.array.sorts);
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle(this.getString(R.string.select_sort));
    	builder.setItems(items, new DialogInterface.OnClickListener() {
    		public void onClick(DialogInterface dialog, int item) {
  	    	  Intent sortIntent = new Intent(ResultsActivity.this,SortingActivity.class); 
	    	  sortIntent.putExtra("fer.sortko.com.sortTypeNumber", item);
	    	  startActivity(sortIntent);
	    	  finish();
        	}
    	});
    	AlertDialog alert = builder.create();
    	alert.show();
	}
	
	private void changeList(){
        TextView listTitle = (TextView) findViewById(R.id.listtitle);
        listTitle.setText(this.getString(R.string.overallresults));
        TextView changeList = (TextView) findViewById(R.id.changeList);
        changeList.setVisibility(4);
		
		viewResults = new Runnable(){
            @Override
            public void run(){
                getResults(-1);
            }
        };
        
        Thread thread =  new Thread(null, viewResults, "GetResults");
        thread.start();
        resultsProgressDialog = ProgressDialog.show(ResultsActivity.this,    
              this.getString(R.string.progress_title), this.getString(R.string.progress_title), true); 
	}
	
	private void showHomeActivity(){
		Intent resultIntent = new Intent(ResultsActivity.this, Main.class);
		startActivity(resultIntent);
		finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK){
	    	showHomeActivity();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
