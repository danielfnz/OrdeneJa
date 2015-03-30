package fer.sortko.com;

import android.content.Context;

/*
 * 1.	Kreni u iti korak algoritma i traži najmanji element polja od desno od itog elementa.
 * 2.	Zamjeni najmanji element polja desno od i-tog elementa s i-tim elementom.
 * Moguæe poruke pogreške
 * 1.	Mjenja se s prvim najmanjim brojem.
 */

public class SelectionSortPosition extends AlgorithmPosition{
	private int outerLoopIndex;
	
	public SelectionSortPosition(int i, int j, int[] currentNumbersList, Context passedContext) {
		super(i, j, currentNumbersList, passedContext);
		this.outerLoopIndex  = i;
	}
	
	@Override
	public String getHelpMessage(AlgorithmPosition userAlgorithmPosition, boolean isSwitchSuccessful){
		String sortStartMessage = String.format(super.context.getString(R.string.select_sortStartMessage));
		String mainMessage = String.format(super.context.getString(R.string.select_mainMessage), (this.outerLoopIndex + 1), (this.outerLoopIndex + 1));		
		String errorMessage = String.format(super.context.getString(R.string.select_errorMessage), (this.outerLoopIndex + 1), (this.outerLoopIndex + 1), (this.outerLoopIndex + 1));
		
		if (userAlgorithmPosition == null){
			return sortStartMessage;
		}
		else if (isSwitchSuccessful){
			return mainMessage;
		}
		else {
			return errorMessage;
		}
	}
}
