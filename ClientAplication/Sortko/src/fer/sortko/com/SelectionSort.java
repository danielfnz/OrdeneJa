package fer.sortko.com;

import android.content.Context;

public class SelectionSort extends Algorithm {

	private AlgorithmPosition lastChangePosition = null;
	private AlgorithmPosition positionToReturn = null;
	private int N = super.getNumbersCopy().length;
	private static int algorithmDifficulty = 4;
	private boolean positionReturned = false;
	private int switchCount = 0;
	private int[] A = null;
	private int i = 0;
	private int j = 0;

	public SelectionSort (int numberOfElements, Context passedContext){
		super(numberOfElements, passedContext);
	}

	public AlgorithmPosition findSwitch(){
		A = super.getNumbersCopy();
		switchCount = 0;
		this.help = 0;
		i = 0;
		j = 0;
		int min = 0;
		int help = 0;
		
		this.lastChangePosition = new SelectionSortPosition(0, 0, A, context);
		this.positionToReturn = new SelectionSortPosition(0, 0, A, context);
		this.positionToReturn.setPreviousAlgorithmPosition(lastChangePosition);

		for (i = 0; i < N; i++) {
			min = i;
			for (j = i+1; j < N; j++) {
				if (A[j] < A[min]){
					min = j;
				}
			}
			
			SetAlgorithmPosition (i, min);
			
			help = A[i];
			A[i] = A[min];
			A[min] = help;
		}
		return this.positionToReturn;
	}
	private void SetAlgorithmPosition(int i, int j){
		switchCount++;
		if (super.switchNumber == switchCount){
			positionToReturn = new SelectionSortPosition(i, j, this.A.clone(), context);
			positionToReturn.setPreviousAlgorithmPosition(this.lastChangePosition);
			positionReturned = true;
		}
		if(!positionReturned){
			this.lastChangePosition = new SelectionSortPosition(i, j, this.A.clone(), context);
		}
	}
	@Override
	public int getAlgorithmDifficulty() {
		return algorithmDifficulty;
	}
}
