package ufg.ordeneja.br;

import android.content.Context;

public class AlgorithmPosition implements Comparable<AlgorithmPosition>{
	private int algorithmIndexI;
	private int algorithmIndexJ;
	private int helpVariable;
	private int[] currentNumbersList;
	private boolean checkOrder = false;
	private AlgorithmPosition previousAlgorithmPosition = null;
	protected Context context;


	public AlgorithmPosition(int i, int j,int[] currentNumbersList, Context passedContext){
		this.setAlgorithmIndexI(i);
		this.setAlgorithmIndexJ(j);
		this.currentNumbersList = currentNumbersList;
		this.context = passedContext;
	}
	
	public String getHelpMessage(AlgorithmPosition userAlgorithmPosition, boolean isSwitchSuccessful){
		//if (userAP == null) iniciar a classificação - imprimir a mensagem original
		// Se (isSwitchSuccessful) para imprimir uma mensagem para o próximo movimento
		// Else tentar descobrir o que é o erro
		return "Base class message!";
	}

	public AlgorithmPosition(int i, int j,int[] currentNumbersList, boolean checkOrder, int helpVariable, Context passedContext){
		this.setAlgorithmIndexI(i);
		this.setAlgorithmIndexJ(j);
		this.currentNumbersList = currentNumbersList;
		this.setCheckOrder(checkOrder);
		this.setHelpVariable(helpVariable);
		this.context = passedContext;
	}

	public void setAlgorithmIndexJ(int algorithmIndexJ) {
		this.algorithmIndexJ = algorithmIndexJ;
	}
	public int getAlgorithmIndexJ() {
		return algorithmIndexJ;
	}
	public void setAlgorithmIndexI(int algorithmIndexI) {
		this.algorithmIndexI = algorithmIndexI;
	}
	public int getAlgorithmIndexI(){
		return algorithmIndexI;
	}
	public void setCheckOrder(boolean checkOrder){
		this.checkOrder = checkOrder;
	}

	public boolean isCheckOrder(){
		return checkOrder;
	}
	
	@Override
	public boolean equals(Object checkValueObject){
		if (this == checkValueObject) return true;
		if (!(checkValueObject instanceof AlgorithmPosition)) return false;

		AlgorithmPosition checkValue = (AlgorithmPosition) checkValueObject;

		if (!isCheckOrder()){
			if (this.algorithmIndexI == checkValue.algorithmIndexI && this.algorithmIndexJ == checkValue.algorithmIndexJ 
					|| this.algorithmIndexI == checkValue.algorithmIndexJ && this.algorithmIndexJ == checkValue.algorithmIndexI ){
				return true;
			}
			else{
				
				return false;
			}
		} else {
			if (this.algorithmIndexI == checkValue.algorithmIndexI && this.algorithmIndexJ == checkValue.algorithmIndexJ){
				return true;
			}
			else{
				return false;
			}
		}
	}

	public int hashcode(){
		return 0;
	}

	@Override
	public int compareTo(AlgorithmPosition another) {
		return this.equals(another) == true ? 1 : 0;
	}

	public int[] getCurrentNumbersList() {
		return currentNumbersList;
	}

	public void setHelpVariable(int helpVariable) {
		this.helpVariable = helpVariable;
	}

	public int getHelpVariable() {
		return helpVariable;
	}

	public void setPreviousAlgorithmPosition(AlgorithmPosition previousAlgorithmPosition) {
		this.previousAlgorithmPosition = previousAlgorithmPosition;
	}

	public AlgorithmPosition getPreviousAlgorithmPosition(){
		return previousAlgorithmPosition;
	}
}
