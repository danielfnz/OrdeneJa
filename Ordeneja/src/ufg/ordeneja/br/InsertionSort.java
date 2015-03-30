package ufg.ordeneja.br;

import android.content.Context;

public class InsertionSort extends Algorithm {

	private AlgorithmPosition lastChangePosition = null;
	private AlgorithmPosition positionToReturn = null;
	private static int helpVariableIndex = 8; 
	private boolean positionReturned = false;
	private static int algorithmDifficulty = 1;
	private int switchCount = 0;
	private int[] A = null;
	private int i = 0;
	private int j = 0;
	private int N = super.getNumbersCopy().length;
	
	public InsertionSort (int numberOfElements, Context passedContext){
		super(numberOfElements, passedContext);
		super.NEEDS_HELP_VARIABLE = true;
	}

	public AlgorithmPosition findSwitch(){
		A = super.getNumbersCopy();
		switchCount = 0;
		this.help = 0;
		i = 0;
		j = 0;
		
		this.lastChangePosition = new InsertionSortPosition(0, 0, A, 0, 0, context);
		this.positionToReturn = new InsertionSortPosition(0, 0, A, 0, 0, context);
		this.positionToReturn.setPreviousAlgorithmPosition(lastChangePosition);

		for (i = 1; i < N; i++){
			SetAlgorithmPosition(i, helpVariableIndex, i);
			this.help = A[i];
			for (j = i; j >= 1 && A[j-1] > help; j--){
				SetAlgorithmPosition(j-1, j, i);
				A[j] = A[j-1];
			}
			SetAlgorithmPosition(helpVariableIndex, j, i);
			A[j] = help;
		}
		return this.positionToReturn;
	}
	private void SetAlgorithmPosition(int i, int j, int outerLoopIndex){
		switchCount++;
		if (super.switchNumber == switchCount){
			positionToReturn = new InsertionSortPosition(i, j, this.A.clone(), this.help, outerLoopIndex, context);
			positionToReturn.setPreviousAlgorithmPosition(this.lastChangePosition);
			positionReturned = true;
		}
		if(!positionReturned){
			this.lastChangePosition.setAlgorithmIndexI(i);
			this.lastChangePosition.setAlgorithmIndexJ(j);
			this.lastChangePosition.setHelpVariable(this.help);
		}
	}
	@Override
	public int getAlgorithmDifficulty() {
		return algorithmDifficulty;
	}
}
