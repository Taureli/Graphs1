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
			} else if(command == 3){
				addVertex();
			}
		}
		scan.close();
		
	}

	//Wczytywanie macierzy z pliku
	@SuppressWarnings({ "unchecked" })
	public static void loadMatrix() throws Exception {
		File file = new File("Macierz.txt");
		Scanner sc = new Scanner(file);
		matrix.clear();

		//Tworzê macierz
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
	@SuppressWarnings("unchecked")
	public static void addVertex(){
		@SuppressWarnings("resource")
		Scanner scanVert = new Scanner(System.in);
		int size = matrix.size() + 1;
		System.out.println("Proszê podaæ liczby kolejnych " + size + " krawêdzi (oddzielone spacj¹): ");
		String[] newData = scanVert.nextLine().trim().split(" "); 
		
		//Dodajê now¹ informacjê na koñcu ka¿dego wiersza
		for(int i = 0; i < matrix.size(); i++){
			((ArrayList<Integer>)matrix.get(i)).add(Integer.parseInt(newData[i]));
		}

		//Dodajê nowy wiersz
		matrix.add(new ArrayList<Object>());
		size = matrix.size() - 1;
		for (int i = 0; i < newData.length; i++) {
			((ArrayList<Integer>)matrix.get(size)).add(Integer.parseInt(newData[i]));
		}
		
		System.out.println("Poprawnie dodano nowy wierzcho³ek.");
		//scanVert.close();
	}
	
}
