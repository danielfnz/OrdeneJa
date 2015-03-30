package ufg.ordeneja.br;

import ufg.ordeneja.br.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;

public class EscolhaAlgoritmo extends Activity {

	public void onCreate(Bundle b) {
		super.onCreate(b);
		this.setContentView(R.layout.escolha_algoritmo);

		Button iniciar = (Button) findViewById(R.id.btBublle);
		iniciar.setOnClickListener(myListener);

	}

	private OnClickListener myListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			if (v.getId() == R.id.btBublle) {
				RadioGroup radioAlgoritmo = (RadioGroup) findViewById(R.id.radioAlgoritmo);
				RadioGroup radioDificuldade = (RadioGroup) findViewById(R.id.radioDificuldade);
				int opAlgoritmo = radioAlgoritmo.getCheckedRadioButtonId();
				int opDificuldade = radioDificuldade.getCheckedRadioButtonId();

				// BubbleSort nivel iniciante
				if ((opAlgoritmo == R.id.algoritmo_bubbleSort)
						&& (opDificuldade == R.id.dificuldade_iniciante)) {
					Intent sortIntent = new Intent(v.getContext(),
							SortingActivity.class);
					sortIntent.putExtra("ufg.ordeneja.br.sortTypeNumber", 0);
					sortIntent.putExtra("ufg.ordeneja.br.vidas", 15);
					sortIntent.putExtra("ufg.ordeneja.br.nivel", 1);
					startActivity(sortIntent);
				}
				// BubbleSort nivel intermediario
				if ((opAlgoritmo == R.id.algoritmo_bubbleSort)
						&& (opDificuldade == R.id.dificuldade_intermediario)) {
					Intent sortIntent = new Intent(v.getContext(),
							SortingActivity.class);
					sortIntent.putExtra("ufg.ordeneja.br.sortTypeNumber", 0);
					sortIntent.putExtra("ufg.ordeneja.br.vidas", 10);
					sortIntent.putExtra("ufg.ordeneja.br.nivel", 2);
					startActivity(sortIntent);
				}

				// BubbleSort nivel avancado
				if ((opAlgoritmo == R.id.algoritmo_bubbleSort)
						&& (opDificuldade == R.id.dificuldade_avancado)) {
					Intent sortIntent = new Intent(v.getContext(),
							SortingActivity.class);
					sortIntent.putExtra("ufg.ordeneja.br.sortTypeNumber", 0);
					sortIntent.putExtra("ufg.ordeneja.br.vidas", 5);
					sortIntent.putExtra("ufg.ordeneja.br.nivel", 3);
					startActivity(sortIntent);
				}

				// Insertion Sort nivel iniciante
				if ((opAlgoritmo == R.id.algoritmo_insertionSort)
						&& (opDificuldade == R.id.dificuldade_iniciante)) {

					Intent sortIntent = new Intent(v.getContext(),
							SortingActivity.class);
					sortIntent.putExtra("ufg.ordeneja.br.sortTypeNumber", 1);
					sortIntent.putExtra("ufg.ordeneja.br.vidas", 15);
					sortIntent.putExtra("ufg.ordeneja.br.nivel", 1);
					startActivity(sortIntent);
				}
				// Insertion Sort intermediario
				if ((opAlgoritmo == R.id.algoritmo_insertionSort)
						&& (opDificuldade == R.id.dificuldade_intermediario)) {
					Intent sortIntent = new Intent(v.getContext(),
							SortingActivity.class);
					sortIntent.putExtra("ufg.ordeneja.br.sortTypeNumber", 1);
					sortIntent.putExtra("ufg.ordeneja.br.vidas", 10);
					sortIntent.putExtra("ufg.ordeneja.br.nivel", 2);
					startActivity(sortIntent);
				}
				// Insertion Sort avancado
				if ((opAlgoritmo == R.id.algoritmo_insertionSort)
						&& (opDificuldade == R.id.dificuldade_avancado)) {
					Intent sortIntent = new Intent(v.getContext(),
							SortingActivity.class);
					sortIntent.putExtra("ufg.ordeneja.br.sortTypeNumber", 1);
					sortIntent.putExtra("ufg.ordeneja.br.vidas", 5);
					sortIntent.putExtra("ufg.ordeneja.br.nivel", 3);
					startActivity(sortIntent);
				}

				// QuickSort nivel iniciante
				if ((opAlgoritmo == R.id.algoritmo_quickSort)
						&& (opDificuldade == R.id.dificuldade_iniciante)) {
					Intent sortIntent = new Intent(v.getContext(),
							SortingActivity.class);
					sortIntent.putExtra("ufg.ordeneja.br.sortTypeNumber", 2);
					sortIntent.putExtra("ufg.ordeneja.br.vidas", 15);
					sortIntent.putExtra("ufg.ordeneja.br.nivel", 1);
					startActivity(sortIntent);
				}
				// QuickSort Sort intermediario
				if ((opAlgoritmo == R.id.algoritmo_quickSort)
						&& (opDificuldade == R.id.dificuldade_intermediario)) {
					Intent sortIntent = new Intent(v.getContext(),
							SortingActivity.class);
					sortIntent.putExtra("ufg.ordeneja.br.sortTypeNumber", 2);
					sortIntent.putExtra("ufg.ordeneja.br.vidas", 10);
					sortIntent.putExtra("ufg.ordeneja.br.nivel", 2);
					startActivity(sortIntent);
				}
				// QuickSort Sort avancado
				if ((opAlgoritmo == R.id.algoritmo_quickSort)
						&& (opDificuldade == R.id.dificuldade_avancado)) {
					Intent sortIntent = new Intent(v.getContext(),
							SortingActivity.class);
					sortIntent.putExtra("ufg.ordeneja.br.sortTypeNumber", 2);
					sortIntent.putExtra("ufg.ordeneja.br.vidas", 5);
					sortIntent.putExtra("ufg.ordeneja.br.nivel", 3);
					startActivity(sortIntent);
				}

				// ShellSort nivel iniciante
				if ((opAlgoritmo == R.id.algoritmo_shellSort)
						&& (opDificuldade == R.id.dificuldade_iniciante)) {
					Intent sortIntent = new Intent(v.getContext(),
							SortingActivity.class);
					sortIntent.putExtra("ufg.ordeneja.br.sortTypeNumber", 3);
					sortIntent.putExtra("ufg.ordeneja.br.vidas", 15);
					sortIntent.putExtra("ufg.ordeneja.br.nivel", 1);
					startActivity(sortIntent);
				}
				// ShellSort intermediario
				if ((opAlgoritmo == R.id.algoritmo_shellSort)
						&& (opDificuldade == R.id.dificuldade_intermediario)) {
					Intent sortIntent = new Intent(v.getContext(),
							SortingActivity.class);
					sortIntent.putExtra("ufg.ordeneja.br.sortTypeNumber", 3);
					sortIntent.putExtra("ufg.ordeneja.br.vidas", 10);
					sortIntent.putExtra("ufg.ordeneja.br.nivel", 2);
					startActivity(sortIntent);
				}
				// ShellSort avancado
				if ((opAlgoritmo == R.id.algoritmo_shellSort)
						&& (opDificuldade == R.id.dificuldade_avancado)) {
					Intent sortIntent = new Intent(v.getContext(),
							SortingActivity.class);
					sortIntent.putExtra("ufg.ordeneja.br.sortTypeNumber", 3);
					sortIntent.putExtra("ufg.ordeneja.br.vidas", 5);
					sortIntent.putExtra("ufg.ordeneja.br.nivel", 3);
					startActivity(sortIntent);
				}

				// SelectionSort nivel iniciante
				if ((opAlgoritmo == R.id.algoritmo_selectionSort)
						&& (opDificuldade == R.id.dificuldade_iniciante)) {
					Intent sortIntent = new Intent(v.getContext(),
							SortingActivity.class);
					sortIntent.putExtra("ufg.ordeneja.br.sortTypeNumber", 4);
					sortIntent.putExtra("ufg.ordeneja.br.vidas", 15);
					sortIntent.putExtra("ufg.ordeneja.br.nivel", 1);
					startActivity(sortIntent);
				}
				// SelectionSort intermediario
				if ((opAlgoritmo == R.id.algoritmo_selectionSort)
						&& (opDificuldade == R.id.dificuldade_intermediario)) {
					Intent sortIntent = new Intent(v.getContext(),
							SortingActivity.class);
					sortIntent.putExtra("ufg.ordeneja.br.sortTypeNumber", 4);
					sortIntent.putExtra("ufg.ordeneja.br.vidas", 10);
					sortIntent.putExtra("ufg.ordeneja.br.nivel", 2);
					startActivity(sortIntent);
				}
				// SelectionSort avancado
				if ((opAlgoritmo == R.id.algoritmo_selectionSort)
						&& (opDificuldade == R.id.dificuldade_avancado)) {
					Intent sortIntent = new Intent(v.getContext(),
							SortingActivity.class);
					sortIntent.putExtra("ufg.ordeneja.br.sortTypeNumber", 4);
					sortIntent.putExtra("ufg.ordeneja.br.vidas", 5);
					sortIntent.putExtra("ufg.ordeneja.br.nivel", 3);
					startActivity(sortIntent);
				}
			}
		}

	};

	public void mostraalerta(String title, String mensagem) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(mensagem);
		builder.create();
		builder.show();
	}
}
