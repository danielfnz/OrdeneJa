package ufg.ordeneja.br;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Handler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import ufg.ordeneja.br.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ResultadoActivity extends Activity   {

	public static final String Preferencias = "SortkoPrefsFile";
	private long Pontuacao = 0;
	private int metodo = 0, status = 0;
	private String username = "";
	private ArrayList<Result> results = null;
    private ResultAdapter resultsAdapter;
	private Runnable run;
	ProgressDialog resultsProgressDialog;
	HttpEntity entity = null;

	public void onCreate(Bundle b) {
		super.onCreate(b);
		Pontuacao = getIntent().getLongExtra("ufg.ordeneja.br.result", 0);
		metodo = getIntent().getIntExtra("ufg.ordeneja.br.sortTypeNumber", -1);
		status = getIntent().getIntExtra("ufg.ordeneja.br.status", 0);
		if (status == 0) {
			this.setContentView(R.layout.sucesso);
		} else
		{
			this.setContentView(R.layout.fracasso);
			Button btAjuda = (Button) findViewById(R.id.btAjuda);
			btAjuda.setOnClickListener(Listener);
		}

		TextView txPontos = (TextView) findViewById(R.id.result);
		txPontos.setText(Long.toString(Pontuacao));
		Button btAlterar = (Button) findViewById(R.id.btAlterar);


		// Criando os Listener
		btAlterar.setOnClickListener(Listener);


		// Capturando o username salvo
		if (Pontuacao != 0) {
			SharedPreferences settings = getSharedPreferences(Preferencias, 0);
			username = settings.getString("username",
					this.getString(R.string.username_Default));

			run = new Runnable() {
				@Override
				public void run() {
					postHttp(username.toString(), Pontuacao, metodo);
				}
			};
			
	        
			Thread thread = new Thread(null, run, "postHttp");
			thread.start();
			resultsProgressDialog = ProgressDialog.show(ResultadoActivity.this,
					this.getString(R.string.progress_title),
					this.getString(R.string.progress_description), true);
		}
	}


	private void postHttp(String usuario, long pontos, Integer metodo) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"http://danielleonardo.zz.vc/ordeneja/recebeRanking.php");

		try {
			ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
			valores.add(new BasicNameValuePair("usuario", usuario));
			valores.add(new BasicNameValuePair("pontos", Long.toString(pontos)));
			valores.add(new BasicNameValuePair("metodo", Integer
					.toString(metodo)));

			httpPost.setEntity(new UrlEncodedFormEntity(valores));
			final HttpResponse resposta = httpClient.execute(httpPost);
			entity = resposta.getEntity();
			resultsProgressDialog.dismiss();
			Log.e("XXX", EntityUtils.toString(entity));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}

		catch (IOException e) {

			e.printStackTrace();
		}
		runOnUiThread(queryFail);
	}

	private Runnable queryFail = new Runnable() {
		@Override
		public void run() {
			if (entity == null) {
				resultsProgressDialog.dismiss();
			}
		}
	};
	private OnClickListener Listener = new OnClickListener() {
		 public void onClick(View v) {
			if (v.getId() == R.id.btAlterar) {
				startActivity(new Intent(ResultadoActivity.this,
						EscolhaAlgoritmo.class));
					finish();
			}
			if (v.getId() == R.id.btAjuda) {
				if (metodo == 0) {
					startActivity(new Intent(ResultadoActivity.this,
							BubbleHelp.class));
				
				}
			}
		 }
	};
}
