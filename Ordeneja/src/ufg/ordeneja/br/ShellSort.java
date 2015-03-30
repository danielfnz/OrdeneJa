package ufg.ordeneja.br;

import android.content.Context;

public class ShellSort extends Algorithm{
	private AlgorithmPosition lastChangePosition = null;
	private AlgorithmPosition positionToReturn = null;
	private int N = super.getNumbersCopy().length;
	private static int helpVariableIndex = 8;
	private static int algorithmDifficulty = 1;
	private boolean positionReturned = false;
	private int switchCount = 0;
	private int[] A = null;
	private int korak = 0;
	private int i = 0;
	private int j = 0;
	
	public ShellSort (int numberOfElements, Context passedContext){
		super(numberOfElements, passedContext);
		super.NEEDS_HELP_VARIABLE = true;
	}
	
	public AlgorithmPosition findSwitch(){
		A = super.getNumbersCopy();
		switchCount = 0;
		this.help = 0;
		i = 0;
		j = 0;
		korak = 0;
		
		this.lastChangePosition = new ShellSortPosition(0, 0, A, 0, 0, 0, ShellPosition.stepStart, context);
		this.positionToReturn = new ShellSortPosition(0, 0, A, 0, 0, 0, ShellPosition.stepStart, context);
		this.positionToReturn.setPreviousAlgorithmPosition(lastChangePosition);

		for (korak = N / 2; korak > 0; korak /= 2) {
			for (i = korak; i < N; i++) {
				SetAlgorithmPosition(i, helpVariableIndex, i, korak, ShellPosition.stepStart);
				help = A [i];
				
				for (j = i; j >= korak && A[j-korak] > help; j -= korak) {
					SetAlgorithmPosition(j - korak, j, i, korak, ShellPosition.stepCompare);
					A [j] = A [j - korak];
				}
				
				SetAlgorithmPosition(helpVariableIndex, j, i, korak, ShellPosition.stepReturn);
				A [j] = help;
			}
		}
		return this.positionToReturn;
	}
	private void SetAlgorithmPosition(int i, int j, int outerLoopIndex, int korak, ShellPosition sp){
		switchCount++;
		if (super.switchNumber == switchCount){
			positionToReturn = new ShellSortPosition(i, j, this.A.clone(), this.help, outerLoopIndex, korak, sp, context);
			positionToReturn.setPreviousAlgorithmPosition(this.lastChangePosition);
			positionReturned = true;
		}
		if(!positionReturned){
			this.lastChangePosition = new ShellSortPosition(i, j, this.A.clone(), this.help, outerLoopIndex, korak, sp, context);
		}
	}
	@Override
	public int getAlgorithmDifficulty() {
		return algorithmDifficulty;
	}
	public enum ShellPosition {
		stepStart, stepCompare, stepReturn
	}
}
