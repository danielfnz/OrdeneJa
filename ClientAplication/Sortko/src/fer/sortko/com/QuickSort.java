package fer.sortko.com;

import android.content.Context;

public class QuickSort extends Algorithm {
	private static int helpVariableIndex = 8;
	private QuickSortPosition lastChangePosition = null;
	private QuickSortPosition positionToReturn = null;
	private int N = super.getNumbersCopy().length;
	private static boolean checkOrder = true;
	private static int algorithmDifficulty = 5;
	private boolean positionReturned = false;
	private static int cutoff = 3;
	private int[] A = null;
	int switchCount = 0;
	
	public QuickSort (int numberOfElements, Context passedContext){
		super(numberOfElements, passedContext);
		super.NEEDS_HELP_VARIABLE = true;
	}

	public AlgorithmPosition findSwitch(){
		A = super.getNumbersCopy();
		this.help = 0;
		positionReturned = false;
		switchCount = 0;
		positionToReturn = new QuickSortPosition(0, 0, A, false, 0, QsPosition.start,0,7, QsPosition.start, context);
		lastChangePosition = new QuickSortPosition(0, 0, A, false, 0, QsPosition.start,0,7, QsPosition.start, context);
		
		QSort (0, N - 1);
		return positionToReturn;
	}
	
	private void setAlgorithmPosition(int i,int j, boolean checkOrder, QsPosition qsPosition, int lijevo, int desno){
		switchCount++;
		if (super.switchNumber == switchCount && !positionReturned){			
			this.positionToReturn = new QuickSortPosition(i, j, A.clone(), checkOrder, this.help, qsPosition, lijevo, desno, lastChangePosition.getQsPosition(), context);
			positionReturned = true;
		}
		if(!positionReturned){
			this.lastChangePosition = new QuickSortPosition(i, j, A.clone(), checkOrder, this.help, qsPosition, lijevo, desno, null, context);
		}
	}

	private void QSort (int lijevo, int desno){
		int i, j;
		int stozer;
		if (lijevo + cutoff <= desno) {
			stozer = Medijan3 (lijevo, desno);
			i = lijevo; j = desno - 1;
			while (true) {
				while (A[++i] < stozer);
				while (A[--j] > stozer);
				if (i < j){
					setAlgorithmPosition(i, j, false, QsPosition.regularQs, lijevo, desno);
					Zamijeni(i, j);
				}
				else {
					break;
				}
			}
			//TODO: Problem kada vraæa na samoga sebe?
			setAlgorithmPosition(i, desno - 1, false, QsPosition.returnSt, lijevo, desno);
			Zamijeni (i, desno - 1);
			
			QSort (lijevo, i - 1);
			QSort (i + 1, desno);
		} 
		else {
			InsertionSort (lijevo, desno);
		}
	}
	private int Medijan3(int lijevo, int desno){
		int sredina = (lijevo + desno) / 2;
		if (A [lijevo] > A  [sredina]){
			setAlgorithmPosition(lijevo, sredina, false,QsPosition.median12, lijevo, desno);
			Zamijeni (lijevo, sredina);
		}
		if (A [lijevo] > A [desno]){
			setAlgorithmPosition(lijevo, desno, false, QsPosition.median13, lijevo, desno);
			Zamijeni (lijevo, desno);
		}
		if (A [sredina] > A [desno]){
			setAlgorithmPosition(sredina, desno, false, QsPosition.median23, lijevo, desno);
			Zamijeni(sredina, desno);
		}

		setAlgorithmPosition(sredina, desno-1, false, QsPosition.hidden, lijevo, desno);
		Zamijeni (sredina, desno-1);
		return A[desno - 1];
	}
	
	private void InsertionSort(int lijevo, int desno){
		int N = desno - lijevo + 1; 
		for (int i = 1 + lijevo; i < N + lijevo; i++){
			setAlgorithmPosition(i,helpVariableIndex, checkOrder,QsPosition.insertion, lijevo, desno);
			this.help = A[i];
			int j;
			for (j = i; j >= 1 && A[j-1] > help; j--){
				setAlgorithmPosition(j-1, j, checkOrder,QsPosition.insertion, lijevo, desno);
				A[j] = A[j-1];
			}
			setAlgorithmPosition(helpVariableIndex, j, checkOrder,QsPosition.insertion, lijevo, desno);
			A[j] = help;
		}
	}
	private void Zamijeni(int indexLijevo, int indexDesno){
		int pom = A[indexLijevo];
		A[indexLijevo] = A[indexDesno];
		A[indexDesno] = pom;
	}
	@Override
	public int getAlgorithmDifficulty() {
		return algorithmDifficulty;
	}
	
	public enum QsPosition {
		start, median12, median13, median23, hidden, regularQs, returnSt,insertion
	}
}