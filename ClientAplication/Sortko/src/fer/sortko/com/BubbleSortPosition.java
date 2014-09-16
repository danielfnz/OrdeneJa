package fer.sortko.com;

import android.content.Context;

public class BubbleSortPosition extends AlgorithmPosition{
	
	private int outerLoopIndex;

	public BubbleSortPosition(int i, int j, int[] currentNumbersList, int indexOuterLoop, Context passedContext) {
		super(i, j, currentNumbersList, passedContext);
		this.outerLoopIndex = indexOuterLoop;
	}
	
	@Override
	public String getHelpMessage(AlgorithmPosition userAlgorithmPosition, boolean isSwitchSuccessful){
		String sortStartMessage = super.context.getString(R.string.bubble_sortStartMessage);
		String bubbleMessage = super.context.getString(R.string.bubble_bubbleMessage);
		String startMessage = String.format(super.context.getString(R.string.bubble_startMessage), this.outerLoopIndex + 1, bubbleMessage);
		String sortErrorMessage = String.format(super.context.getString(R.string.bubble_sortErrorMessage),this.getPreviousAlgorithmPosition().getAlgorithmIndexI(),this.getPreviousAlgorithmPosition().getAlgorithmIndexJ());
		String bubbleErrorMessage = String.format(super.context.getString(R.string.bubble_bubbleErrorMessage),sortErrorMessage);
		
		// if (userAP == null) poèetak sortiranja - ispisati poèetnu poruku
		// if (isSwitchSuccessful) ispisati poruku za sljedeæi potez
		// else poruka otkriva u èemu je greška
		
		if(userAlgorithmPosition == null){
			return sortStartMessage;
		}
		else if (isSwitchSuccessful){
			if (this.outerLoopIndex != ((BubbleSortPosition)this.getPreviousAlgorithmPosition()).outerLoopIndex ||
					(((BubbleSortPosition)this.getPreviousAlgorithmPosition()).outerLoopIndex == 0 && 
							((BubbleSortPosition)this.getPreviousAlgorithmPosition()).getAlgorithmIndexI() == 0)){
				return startMessage;
			}else {
				return bubbleMessage;
			}
		}
		else {
			if(Math.abs(userAlgorithmPosition.getAlgorithmIndexI()-userAlgorithmPosition.getAlgorithmIndexJ()) > 1){
				return bubbleErrorMessage;
			}
			else{
				return sortErrorMessage;
			}
		}
	}
}
