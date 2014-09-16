package fer.sortko.com;

import android.content.Context;
import fer.sortko.com.ShellSort.ShellPosition;

public class ShellSortPosition extends AlgorithmPosition{
	private static boolean checkOrder = true;
	private int outerLoopIndex;
	private int step = 0;
	private int i,j;
	private ShellPosition sp;
	
	public ShellSortPosition(int i, int j, int[] currentNumbersList, int help, int outerLoopIndex, int step, ShellPosition sp, Context passedContext) {
		super(i, j, currentNumbersList, checkOrder, help, passedContext);
		this.outerLoopIndex  = outerLoopIndex;
		this.i = i;
		this.j = j;
		this.step = step;
		this.sp = sp;
	}
	
	@Override
	public String getHelpMessage(AlgorithmPosition userAlgorithmPosition, boolean isSwitchSuccessful){
		String stepStart = String.format(super.context.getString(R.string.shell_stepStart), step, (i+1));
		String stepContinue = String.format(super.context.getString(R.string.shell_stepContinue), step, (i+1));
		String stepCompare = String.format(super.context.getString(R.string.shell_stepCompare), (i+1), (j+1), (i+1), (j+1)); 
		String stepReturn = String.format(super.context.getString(R.string.shell_stepReturn)); 
		String stepInsertionSort = String.format(super.context.getString(R.string.shell_stepInsertionSort)); 
		String copyOrderErrorMessage = String.format(super.context.getString(R.string.shell_copyOrderErrorMessage)); 
		
		if (userAlgorithmPosition == null){
			return stepStart;
		}
		if (isSwitchSuccessful){
			if (step == 1) {
				return stepInsertionSort;
			}
			else {
				if (sp == ShellPosition.stepStart){
					if (outerLoopIndex == this.step){
						return stepStart;
					}
					else {
						return stepContinue;
					}
				}
				if (sp == ShellPosition.stepCompare){
					return stepCompare;
				}
				else {// (sp = ShellPosition.stepReturn){
					return stepReturn;
				}
			}
		}
		else {
			if (isOrderError(userAlgorithmPosition)){
				return copyOrderErrorMessage;
			}
			else {
				if (sp == ShellPosition.stepStart){
					if (outerLoopIndex == this.step){
						return stepStart;
					}
					else {
						return stepContinue;
					}
				}
				if (sp == ShellPosition.stepCompare){
					return stepCompare;
				}
				else {//(sp = ShellPosition.stepReturn){
					return stepReturn;
				}
			}
		}
	}
	private boolean isOrderError(AlgorithmPosition userAlgorithmPosition){
		return (userAlgorithmPosition.getAlgorithmIndexI() == super.getAlgorithmIndexJ() && userAlgorithmPosition.getAlgorithmIndexJ() == super.getAlgorithmIndexI());
	}
}
