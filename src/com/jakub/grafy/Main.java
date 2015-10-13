package com.jakub.grafy;

import java.io.File;
import java.util.Scanner;

public class Main {
	static int[][] matrix = {{1}, {2}};
	static int matrixLength = 0;
	
	public static void main(String args[]) throws Exception{
		int command;
		System.out.println("1 - za³aduj macierz z pliku;");
		System.out.println("Wybierz polecenie: ");
		Scanner scan = new Scanner(System.in);
		command = scan.nextInt();
		scan.close();
		
		if(command == 1){
			loadMatrix();
			
			System.out.println("Wczytano macierz z pliku: ");
			for(int i = 0; i < matrixLength; i++){
				System.out.println();
				for(int j = 0; j < matrixLength; j++){
					System.out.print(matrix[i][j] + " | ");
				}
			}
		}
		
	}

	public static void loadMatrix() throws Exception {
		File file = new File("Macierz.txt");
		Scanner sc = new Scanner(file);

		//Sprawdzam wielkoœæ macierzy
		String[] line = sc.nextLine().trim().split(" ");
		for (int i = 0; i < line.length; i++) {
			matrixLength++;
		}
		sc.close();

		//Tworzê macierz
		matrix = new int[matrixLength][matrixLength];
		sc = new Scanner(file);

		int lineCount = 0;
		while (sc.hasNextLine()) {
			String[] currentLine = sc.nextLine().trim().split(" "); 
			for (int i = 0; i < currentLine.length; i++) {
				matrix[lineCount][i] = Integer.parseInt(currentLine[i]);    
			}
			lineCount++;
		}  
		sc.close();
	}
	
}
