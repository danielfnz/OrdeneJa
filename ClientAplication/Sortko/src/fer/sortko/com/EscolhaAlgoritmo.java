package fer.sortko.com;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;

public class EscolhaAlgoritmo extends Activity {

	public void onCreate(Bundle b) {
		super.onCreate(b);
		this.setContentView(R.layout.escolha_algoritmo);

		Button iniciar = (Button) findViewById(R.id.btIniciar);
		iniciar.setOnClickListener(myListener);

	}

	private OnClickListener myListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			if (v.getId() == R.id.btIniciar) {
				RadioGroup radioAlgoritmo = (RadioGroup) findViewById(R.id.radioAlgoritmo);
				RadioGroup radioDificuldade = (RadioGroup) findViewById(R.id.radioDificuldade);
				int opAlgoritmo = radioAlgoritmo.getCheckedRadioButtonId(); 
				int opDificuldade = radioDificuldade.getCheckedRadioButtonId();
				
				//BubbleSort nivel iniciante
				if ((opAlgoritmo == R.id.algoritmo_bubbleSort)	&& (opDificuldade == R.id.dificuldade_iniciante)) {
				mostraalerta("Algoritmo bubble sort","Nivel iniciante");
				}
				//BubbleSort nivel intermediario
				if ((opAlgoritmo == R.id.algoritmo_bubbleSort)	&& (opDificuldade == R.id.dificuldade_intermediario)) {
					mostraalerta("Algoritmo bubble sort","Nivel intermediario");
				}
				//BubbleSort nivel avancado
				if ((opAlgoritmo == R.id.algoritmo_bubbleSort)	&& (opDificuldade == R.id.dificuldade_avancado)) {
					mostraalerta("Algoritmo bubble sort","Nivel Avancado");
				}
				
				//Insertion Sort nivel iniciante
				if ((opAlgoritmo == R.id.algoritmo_insertionSort)	&& (opDificuldade == R.id.dificuldade_iniciante)) {
				mostraalerta("Algoritmo Insertion Sort","Nivel iniciante");
				}
				//Insertion Sort intermediario
				if ((opAlgoritmo == R.id.algoritmo_insertionSort)	&& (opDificuldade == R.id.dificuldade_intermediario)) {
					mostraalerta("Algoritmo Insertion Sort","Nivel intermediario");
				}
				//Insertion Sort  avancado
				if ((opAlgoritmo == R.id.algoritmo_insertionSort)	&& (opDificuldade == R.id.dificuldade_avancado)) {
					mostraalerta("Algoritmo Insertion Sort","Nivel Avancado");
				}
				
				//QuickSort nivel iniciante
				if ((opAlgoritmo == R.id.algoritmo_quickSort)	&& (opDificuldade == R.id.dificuldade_iniciante)) {
				mostraalerta("Algoritmo QuickSort","Nivel iniciante");
				}
				//QuickSort  Sort intermediario
				if ((opAlgoritmo == R.id.algoritmo_quickSort)	&& (opDificuldade == R.id.dificuldade_intermediario)) {
					mostraalerta("Algoritmo QuickSort","Nivel intermediario");
				}
				//QuickSort Sort  avancado
				if ((opAlgoritmo == R.id.algoritmo_quickSort)	&& (opDificuldade == R.id.dificuldade_avancado)) {
					mostraalerta("Algoritmo QuickSort","Nivel Avancado");
				}
				
				//ShellSort nivel iniciante
				if ((opAlgoritmo == R.id.algoritmo_shellSort)	&& (opDificuldade == R.id.dificuldade_iniciante)) {
				mostraalerta("Algoritmo ShellSort ","Nivel iniciante");
				}
				//ShellSort  intermediario
				if ((opAlgoritmo == R.id.algoritmo_shellSort)	&& (opDificuldade == R.id.dificuldade_intermediario)) {
					mostraalerta("Algoritmo ShellSort ","Nivel intermediario");
				}
				//ShellSort  avancado
				if ((opAlgoritmo == R.id.algoritmo_shellSort)	&& (opDificuldade == R.id.dificuldade_avancado)) {
					mostraalerta("Algoritmo ShellSort ","Nivel Avancado");
				}
				
				//SelectionSort nivel iniciante
				if ((opAlgoritmo == R.id.algoritmo_selectionSort)	&& (opDificuldade == R.id.dificuldade_iniciante)) {
				mostraalerta("Algoritmo SelectionSort  ","Nivel iniciante");
				}
				//SelectionSort  intermediario
				if ((opAlgoritmo == R.id.algoritmo_selectionSort)	&& (opDificuldade == R.id.dificuldade_intermediario)) {
					mostraalerta("Algoritmo SelectionSort  ","Nivel intermediario");
				}
				//SelectionSort   avancado
				if ((opAlgoritmo == R.id.algoritmo_selectionSort)	&& (opDificuldade == R.id.dificuldade_avancado)) {
					mostraalerta("Algoritmo SelectionSort  ","Nivel Avancado");
				}
			}
		}

	};
	public void mostraalerta(String title, String mensagem)
	{
		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 builder.setTitle(title);
		 builder.setMessage(mensagem);
		 builder.create();
		 builder.show();
	}
}
