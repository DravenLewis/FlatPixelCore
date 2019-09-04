package io.infinitestrike.core.util;

public class ArrayCompare {
	public static int[] getMaxValueInArray(int[] array){
		int max = array[0];
		
		for(int i = 1; i < array.length; i++){
			if(array[i] > max){
				max = array[i];
			}
		}
		int[] values = {findIndexOfValue(max,array),max};
		return values;
	}
	
	public static int[] getMinValueInArray(int[] array){
		int min = array[0];
		
		for(int i = 1; i < array.length; i++){
			if(array[i] < min){
				min = array[i];
			}
		}
		int[] values = {findIndexOfValue(min,array),min};
		return values;
	}
	
	public static int findIndexOfValue(int v, int[] array){
		for(int i = 0; i < array.length; i++){
			if(array[i] == v){
				return i;
			}
		}
		return -1;
	}
}
