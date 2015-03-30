package ufg.ordeneja.br;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import ufg.ordeneja.br.R;

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
import android.widget.Toast;

public class Ranking extends ListActivity implements OnClickListener {
    
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
        
        sortingResult = getIntent().getLongExtra("ufg.ordeneja.br.result", 0);
        sortTypeNumber = getIntent().getIntExtra("ufg.ordeneja.br.sortTypeNumber", -1);
        
        if(sortingResult != 0){
	        SharedPreferences settings = getSharedPreferences(SORTKO_PREFS, 0);
	        username = settings.getString("username", this.getString(R.string.username_Default));
	        
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

    
        
        results = new ArrayList<Result>();
        this.resultsAdapter = new ResultAdapter(this, R.layout.list_item, results);
        setListAdapter(this.resultsAdapter);
        
        viewResults = new Runnable(){
            @Override
            public void run(){
            	callWebService(sortTypeNumber);
                getResults(sortTypeNumber);
            }
        };
        
        Thread thread =  new Thread(null, viewResults, "GetResults");
        thread.start();
        resultsProgressDialog = ProgressDialog.show(Ranking.this, 
        		this.getString(R.string.progress_title), this.getString(R.string.progress_description), true);   
	}
		
	private void getResults(int idVrsteSorta){
		try{
			String result = callWebService(sortTypeNumber);
        	
            String[] items = result.substring(result.indexOf("&")+1,result.indexOf("fim")).split("#");

            int resultNumber = 0;
                
            results = new ArrayList<Result>();
            for (int cont=0;cont<items.length;cont++) {
            	cont++;
                Result r = new Result();
                r.setResultPlace(Integer.toString(++resultNumber)+".");
            	r.setResultUser(items[cont]);
            	r.setResultNumber(items[cont+1]);
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
	
	private String callWebService(int metodo){
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"http://danielleonardo.zz.vc/ordeneja/enviaRanking.php");
    	String result = null;
        HttpEntity entity = null;
        try {
            HttpResponse response = httpClient.execute(httpPost);
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
        resultsProgressDialog = ProgressDialog.show(Ranking.this,    
              this.getString(R.string.progress_title), this.getString(R.string.progress_title), true); 
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
}
