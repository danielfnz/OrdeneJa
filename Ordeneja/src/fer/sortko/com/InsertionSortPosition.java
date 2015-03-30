package fer.sortko.com;

import android.content.Context;

public class InsertionSortPosition  extends AlgorithmPosition{
	private static boolean checkOrder = true;
	private static int helpVariableIndex = 8;
	private int outerLoopIndex;
	
	public InsertionSortPosition(int i, int j, int[] currentNumbersList, int help, int outerLoopIndex, Context passedContext) {
		super(i, j, currentNumbersList, checkOrder, help, passedContext);
		this.outerLoopIndex  = outerLoopIndex;
	}
	
	@Override
	public String getHelpMessage(AlgorithmPosition userAlgorithmPosition, boolean isSwitchSuccessful){
		String sortStartMessage = String.format(super.context.getString(R.string.insert_sortStartMessage));
				
		String startMessage = String.format(super.context.getString(R.string.insert_startMessage), this.outerLoopIndex, (this.outerLoopIndex + 1)); 
		String endMessage = String.format(super.context.getString(R.string.insert_endMessage), this.outerLoopIndex); 
		String copyFirstMessage = String.format(super.context.getString(R.string.insert_copyFirstMessage), (this.outerLoopIndex + 1));
		String copyMessage = String.format(super.context.getString(R.string.insert_copyMessage), (super.getAlgorithmIndexI()+1)); 
		
		String copyErrorMessage = String.format(super.context.getString(R.string.insert_copyErrorMessage)); 
		String copyOrderErrorMessage = String.format(super.context.getString(R.string.insert_copyOrderErrorMessage)); 
		
		if (userAlgorithmPosition == null){
			return sortStartMessage;
		}
		if (isSwitchSuccessful){
			if (super.getAlgorithmIndexJ() == helpVariableIndex){
				return startMessage;
			}
			else if (super.getAlgorithmIndexI() == helpVariableIndex){
				return endMessage;
			}
			else {
				int lastIndex = super.getPreviousAlgorithmPosition().getAlgorithmIndexI();
				if (lastIndex == helpVariableIndex){
					return copyFirstMessage;
				}
				else{
					return copyMessage;
				}
			}		
		}
		else {
			if (isOrderError(userAlgorithmPosition)){
				return copyOrderErrorMessage;
			}
			else if ((Math.abs(userAlgorithmPosition.getAlgorithmIndexI() - userAlgorithmPosition.getAlgorithmIndexJ()) > 1)
						&& (userAlgorithmPosition.getAlgorithmIndexI()!= helpVariableIndex && userAlgorithmPosition.getAlgorithmIndexJ()!=helpVariableIndex)){
				return copyErrorMessage;
			}
			else {
				if (super.getAlgorithmIndexI() == 8){
					return String.format(super.context.getString(R.string.insert_errorMessage_suggestCopyHelper), (super.getAlgorithmIndexJ()+1)); 
				} else if (super.getAlgorithmIndexJ() == 8){
					return String.format(super.context.getString(R.string.insert_errorMessage_suggestCopyIntoHelper), (super.getAlgorithmIndexI()+1));  
				} else {
					return String.format(super.context.getString(R.string.insert_errorMessage_suggestCopyIntoSlot), (super.getAlgorithmIndexI()+1),(super.getAlgorithmIndexJ()+1));
				}
			}
		}
	}
	
	private boolean isOrderError(AlgorithmPosition userAlgorithmPosition){
		return ((userAlgorithmPosition.getAlgorithmIndexI() == super.getAlgorithmIndexJ()) && (userAlgorithmPosition.getAlgorithmIndexJ() == super.getAlgorithmIndexI()));
	}
}
