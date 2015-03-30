package ufg.ordeneja.br;

import android.content.Context;

public class BubbleSort extends Algorithm{
	private BubbleSortPosition lastChangePosition = null;
	private BubbleSortPosition positionToReturn = null;
	private static int algorithmDifficulty = 1;
	private int switchCount = 0;
	private Boolean positionReturned = false;
	private int N = super.getNumbersCopy().length;
	
	public BubbleSort (int numberOfElements, Context passedContext){
		super(numberOfElements, passedContext);
	}
	
	public AlgorithmPosition findSwitch(){
		positionReturned = false;
		switchCount = 0;
		int[] A = super.getNumbersCopy();
		int i = 0, j = 0, pom = 0;
		
		this.lastChangePosition = new BubbleSortPosition(0, 0, A, 0, super.context);
		this.positionToReturn = new BubbleSortPosition(0, 0, A, 0, super.context);
		this.positionToReturn.setPreviousAlgorithmPosition(lastChangePosition);
		
		for (i = 0; i < N-1; i++) {
			for (j = 0; j < N-1-i; j++) {
				if (A[j+1] < A[j]){					
					SetAlgorithmPosition(j,j+1,A,i);					
					pom = A[j];
					A[j] = A[j+1];
					A[j+1] = pom;
				}
			}
		}
		
		return this.positionToReturn;
	}
	private void SetAlgorithmPosition(int i, int j, int[]A, int outerLoopIndex){
		switchCount++;
		if (super.switchNumber == switchCount){
			this.positionToReturn = new BubbleSortPosition(i, j, A.clone(), outerLoopIndex, context);
			positionToReturn.setPreviousAlgorithmPosition(this.lastChangePosition);
			positionReturned = true;
		}
		if(!positionReturned){
			this.lastChangePosition = new BubbleSortPosition(i, j, A.clone(), outerLoopIndex, context);
		}
	}

	@Override
	public int getAlgorithmDifficulty() {
		return algorithmDifficulty;
	}
}
