package com.jakub.grafy;

import java.io.File;
import java.util.Scanner;

public class Main {
	static int[][] matrix = {{1}, {2}};
	static int matrixLength = 0;
	
	public static void main(String args[]) throws Exception{
		int command = -1;
		Scanner scan = new Scanner(System.in);
		while(command != 0){
			System.out.println("\n0 - zakoñcz program;");
			System.out.println("1 - za³aduj macierz z pliku;");
			System.out.println("Wybierz polecenie: ");
			command = scan.nextInt();

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
		scan.close();
		
	}

	//Wczytywanie macierzy z pliku
	public static void loadMatrix() throws Exception {
		File file = new File("Macierz.txt");
		Scanner sc = new Scanner(file);
		matrixLength = 0;

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
