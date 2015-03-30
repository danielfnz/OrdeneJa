package ufg.ordeneja.br;

import java.util.Random;

import android.content.Context;
import android.text.format.Time;

public abstract class Algorithm {
	private int[] numbers;
	public int help;
	int switchNumber = 1;
	int numberOfElements = 0;
	public static int NEGATIVE_POINTS = 1000;
	public boolean NEEDS_HELP_VARIABLE = false;
	private boolean addBonus = true;
	private int cumulativeBonus = 0;
	private double algorithmDifficulty = 1;
	private Time lastSwitchTime = new Time();
	private Time startTime = new Time();
	protected Context context;

	public Algorithm(int numberOfElements, Context passedContext){
		this.setNumbers(randomList(numberOfElements));
		this.numberOfElements = numberOfElements;
		this.startTime.setToNow();
		this.lastSwitchTime.setToNow();
		this.algorithmDifficulty = (double)getAlgorithmDifficulty();
		this.context = passedContext;
	}
	
	public abstract AlgorithmPosition findSwitch();
	
	public abstract int getAlgorithmDifficulty();
	
	public boolean isNextPosition(AlgorithmPosition positionToCheck){
		return findSwitch().equals(positionToCheck);
	}
	public boolean isFinished(AlgorithmPosition ap){
		return (ap.getAlgorithmIndexI() == 0 && ap.getAlgorithmIndexJ() == 0);
	}
	
	public long goToNextPosition(AlgorithmPosition userPosition){
		
		if(isNextPosition(userPosition)){
			switchNumber++;
			cumulativeBonus++;
			return calculatePoints();
		}
		else {
			cumulativeBonus = 0;
			return 0;
		}
	}
	private long calculatePoints(){
		
		Time currentTime = new Time();
		currentTime.setToNow();
		
		double switchDuration =  (currentTime.toMillis(true) - lastSwitchTime.toMillis(true))/1000;
		
		if (switchDuration > 10){
			algorithmDifficulty *= 0.9;
			if (switchDuration > 20){
				addBonus = false;
			}
		}
		
		double points = 1000 + algorithmDifficulty * 10000 * 1./Math.pow(2.0 -(algorithmDifficulty/10.0),switchDuration);
    	
    	if (addBonus){
    		double bonus = 1.0 + cumulativeBonus/5.0;
    		points = points * bonus;
    	}
    	
    	lastSwitchTime.setToNow();
    	
    	return (long)Math.floor(points);	
	}
	protected void setNumbers(int[] numbers) {
		this.numbers = numbers;
	}
	protected int[] getNumbersCopy(){
		int[] a = new int[numberOfElements];
		for(int i=0; i < numberOfElements; i++ ){
			a[i] = numbers[i];
		}
		return a;
	}
	//Geração dos números
	private int[] randomList(int numberOfElements){
		int generatedNumbersCount = 0;
		int number;
    	Random randomGenerator = new Random();
    	int[] list = new int[8];
    	int[] digitCount = new int[10];
    	
    	while (generatedNumbersCount < 8){
    		number = randomGenerator.nextInt(10);
    		if (digitCount[number] < 2){
    			list[generatedNumbersCount] = number;
    			digitCount[number]++;
    			generatedNumbersCount++;
    		}
        }
        return list;
    }
}
