package fer.sortko.com;

import android.content.Context;
import fer.sortko.com.QuickSort.QsPosition;

/*
Quicksort
1.	Izraèunajte medijan od 1. elementa, (lijevog) srednjeg  i 8. tako da najprije usporedite 1. i srednji i zatim 1. i 8..
2.	Sakrijte stožer.
3.	Pronaðite prvi broj veæi od stožera tako da krenete u desno od elementa na poziciji x. Pronaðite i prvi broj manji od stožera gledano lijevo od elementa na poziciji y. Zamjenite pronaðene brojeve po potrebi ili vratite stožer.
4.	Sortirajte podniz (od 1. do 7. elementa) prema Insertion sort algoritmu.
*/

public class QuickSortPosition extends AlgorithmPosition{
	private QuickSort.QsPosition qsPosition;
	private QuickSort.QsPosition previousQsPosition;
	private int lijevo = 0;
	private int desno = 0;
	
	public QuickSortPosition(int i, int j, int[] currentNumbersList, boolean checkOrder, int help, QuickSort.QsPosition qsPosition, int lijevo, int desno, QuickSort.QsPosition previousQsposition, Context passedContext){
		super(i, j, currentNumbersList, checkOrder, help, passedContext);
		this.setQsPosition(qsPosition);
		this.setPreviousQsPosition(previousQsPosition);
		this.lijevo = lijevo;
		this.desno = desno;
	}
	
	@Override
	public String getHelpMessage(AlgorithmPosition userAlgorithmPosition, boolean isSwitchSuccessful){
		String sortStartMessage = String.format(super.context.getString(R.string.quick_sortStartMessage));
		String median = String.format(super.context.getString(R.string.quick_median),(lijevo+1),(desno+1)); 
		String hidden = String.format(super.context.getString(R.string.quick_hidden)); 
		String regularQs = String.format(super.context.getString(R.string.quick_regularQs),(lijevo + 1),desno);
		String returnSt = String.format(super.context.getString(R.string.quick_returnSt), desno); 
		String insertion = String.format(super.context.getString(R.string.quick_insertion), (lijevo+1), (desno+1)); 
		
		if (userAlgorithmPosition == null){
			return sortStartMessage;
		}
		else {
			switch(this.qsPosition){
				case start:
					return sortStartMessage;
				case median12 :
					if (!isSwitchSuccessful){
						return String.format(super.context.getString(R.string.quick_median_12), median);
					} else return median;
				case median13 :
					if (!isSwitchSuccessful){
						return String.format(super.context.getString(R.string.quick_median_13), median);
					} else return median;
				case median23 :
					if (!isSwitchSuccessful){
						return String.format(super.context.getString(R.string.quick_median_23), median);
					} else return median;
				case hidden :
					if (!isSwitchSuccessful){
						return String.format(super.context.getString(R.string.quick_hidden_hint), hidden, desno);
					} else return hidden;
				case insertion:
					return insertion;
				case regularQs:
					if (this.previousQsPosition == QsPosition.regularQs){
						return String.format(super.context.getString(R.string.quick_regularQs_continue), regularQs);
					} else {
						return regularQs;
					}
				default:
					if (!isSwitchSuccessful){
					return returnSt;
					}
					else{
						if (this.previousQsPosition == QsPosition.regularQs){
							return returnSt;
						} else {
							return regularQs;
						}
					}
		    }
		}
	}

	private void setQsPosition(QuickSort.QsPosition qsPosition) {
		this.qsPosition = qsPosition;
	}

	public QuickSort.QsPosition getQsPosition() {
		return qsPosition;
	}

	private void setPreviousQsPosition(QuickSort.QsPosition previousQsPosition) {
		this.previousQsPosition = previousQsPosition;
	}

	public QuickSort.QsPosition getPreviousQsPosition() {
		return previousQsPosition;
	}
}
