package com.jakub.grafy;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static ArrayList<ArrayList<?>> matrix = new ArrayList<ArrayList<?>>();
	
	public static void main(String args[]) throws Exception{
		int command = -1;
		Scanner scan = new Scanner(System.in);
		while(command != 0){
			System.out.println("\n0 - zakoñcz program;");
			System.out.println("1 - za³aduj macierz z pliku;");
			System.out.println("2 - wyœwietl macierz;");
			System.out.println("3 - dodaj wierzcho³ek i jego krawêdzie;");
			System.out.println("Wybierz polecenie: ");
			command = scan.nextInt();

			if(command == 1){
				loadMatrix();
				System.out.println("Wczytano macierz z pliku.");
			} else if(command == 2){
				for(int i = 0; i < matrix.size(); i++){
					System.out.println();
					for(int j = 0; j < ((ArrayList<?>)matrix.get(i)).size(); j++){
						System.out.print(((ArrayList<?>)matrix.get(i)).get(j) + " | ");
					}
				}
			}
		}
		scan.close();
		
	}

	//Wczytywanie macierzy z pliku
	@SuppressWarnings({ "resource", "unchecked" })
	public static void loadMatrix() throws Exception {
		File file = new File("Macierz.txt");
		Scanner sc = new Scanner(file);
		matrix.clear();

		//Tworzê macierz
		sc = new Scanner(file);

		int lineCount = 0;
		while (sc.hasNextLine()) {
			String[] currentLine = sc.nextLine().trim().split(" "); 
			matrix.add(new ArrayList<Object>());
			for (int i = 0; i < currentLine.length; i++) {
				((ArrayList<Integer>)matrix.get(lineCount)).add(Integer.parseInt(currentLine[i]));
			}
			lineCount++;
		}  
		sc.close();
	}
	
	//Dodawanie wierzcho³ka
	public static void addVertex(){
		
	}
	
}
