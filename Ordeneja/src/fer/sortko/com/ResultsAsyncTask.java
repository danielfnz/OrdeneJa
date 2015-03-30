package fer.sortko.com;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import android.os.AsyncTask;
import android.util.Log;

public class ResultsAsyncTask extends AsyncTask<String, Void, String>{

	@Override
	protected String doInBackground(String... params) {
		String methodName = params[0];
		String methodParams = params[1];
		return getWebService(methodName,methodParams);
	}
	
	public String getWebService(String methodName, String methodParams){
        HttpClient client = new DefaultHttpClient();
    	HttpHost targetHost = new HttpHost("nihao.fer.hr",80,"http");
    	HttpGet httpGet = new HttpGet("/SortkoService/sortkoservice.svc/"+methodName+"?"+methodParams);
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
	        if (entity!=null)
	        try {
	            entity.consumeContent();
	        } catch (IOException e) {
	        	
	        }
        }
        //if method name == resultlist
        // 		dohvati taj dio stringa, oèisti ga
        // 		pošalji ga kroz onPostExecute u metodu
        
        return result;
	} 
    protected void onProgressUpdate(Integer... progress) {
    	//TODO: osvježi liste ako je potrebno, jednu po jednu
    }
	protected void onPostExecute(String result) {
		Log.i("AsyncTask", result);
	}
}

