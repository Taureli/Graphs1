package com.jakub.grafy;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
	static ArrayList<ArrayList<?>> matrix = new ArrayList<ArrayList<?>>();
	
	@SuppressWarnings("resource")
	public static void main(String args[]) throws Exception{
		int command = -1;
		int vert, deg, size;
		int deg2[];
		String[] newData;
		ArrayList<Integer> degList = new ArrayList<Integer>();
		ArrayList<ArrayList<?>> tempMatrix = new ArrayList<ArrayList<?>>();
		Scanner scan = new Scanner(System.in);
		Scanner scanVert = new Scanner(System.in);
		
		while(command != 0){
			System.out.println("\n0 - zako�cz program;");
			System.out.println("1 - za�aduj macierz z pliku;");
			System.out.println("2 - wy�wietl macierz;");
			System.out.println("3 - dodaj wierzcho�ek i jego kraw�dzie;");
			System.out.println("4 - usu� wierzcho�ek i jego kraw�dzie;");
			System.out.println("5 - podaj stopie� wierzcho�ka;");
			System.out.println("6 - podaj stopie� minimalny i maksymalny grafu;");
			System.out.println("7 - wy�wietl posortowan� list� stopni;");
			System.out.println("8 - wy�wietl ilo�� cykli C3;");
			System.out.println("9 - sprawd�, czy podany ci�g jest graficzny;");
			System.out.println("Wybierz polecenie: ");
			command = scan.nextInt();

			switch(command){
				case(1):
					loadMatrix();
					System.out.println("Wczytano macierz z pliku.");
					break;
				case(2):
					for(int i = 0; i < matrix.size(); i++){
						System.out.println();
						for(int j = 0; j < ((ArrayList<?>)matrix.get(i)).size(); j++){
							System.out.print(((ArrayList<?>)matrix.get(i)).get(j) + " | ");
						}
					}
					break;
				case(3):
					size = matrix.size() + 1;
					System.out.println("Prosz� poda� liczby kolejnych " + size + " kraw�dzi (oddzielone spacj�): ");
					newData = scanVert.nextLine().trim().split(" "); 
					if(newData.length == size){
						addVertex(newData);
						System.out.println("Dodano nowy wierzcho�ek.");
					} else {
						System.out.println("Podano z�e warto�ci!");
					}
					newData = null;
					break;
				case(4):
					System.out.println("Prosz� poda� nr wierzcho�ka do usuni�cia: ");
					vert = scanVert.nextInt() - 1;
					if(vert < 0 || vert > matrix.size()){
						System.out.println("Podano z�� warto��!");
					} else {
						removeVertex(vert);
						System.out.println("Usuni�to podany wierzcho�ek.");
					}
					break;
				case(5):
					System.out.println("Prosz� poda� nr wierzcho�ka: ");
					vert = scanVert.nextInt() - 1;
					if(vert < 0 || vert > matrix.size()){
						System.out.println("Podano z�� warto��!");
					} else {
						deg = vertDeg(vert);
						System.out.println("Stopie� wierzcho�ka wynosi: " + deg);
					}
					break;
				case(6):
					deg2 = minMaxDeg();
					System.out.println("Stopie� minimalny: " + deg2[0]);
					System.out.println("Stopie� maksymalny: " + deg2[1]);
					break;
				case(7):
					degList = sortedDeg();
					System.out.println("Posortowana lista stopni: ");
					for(int i = 0; i < degList.size(); i++){
						System.out.print(degList.get(i) + " ");
					}
					degList.clear();
					break;
				case(8):
					tempMatrix = matrixMultiply(matrixMultiply(matrix));
					checkCycles(tempMatrix);
					tempMatrix.clear();
					break;
				case(9):
					System.out.println("Prosz� poda� kolejne liczby ci�gu (oddzielone spacj�): ");
					newData = scanVert.nextLine().trim().split(" ");
					for (int i = 0; i < newData.length; i++) {
						degList.add(Integer.parseInt(newData[i]));
					}
					isGraphic(degList);
					degList.clear();
					newData = null;
					break;
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

		//Tworz� macierz
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
	
	//Dodawanie wierzcho�ka
	@SuppressWarnings("unchecked")
	public static void addVertex(String[] newData){		
		//Dodaj� now� informacj� na ko�cu ka�dego wiersza
		for(int i = 0; i < matrix.size(); i++){
			((ArrayList<Integer>)matrix.get(i)).add(Integer.parseInt(newData[i]));
		}

		//Dodaj� nowy wiersz
		matrix.add(new ArrayList<Object>());
		int size = matrix.size() - 1;
		for (int i = 0; i < newData.length; i++) {
			((ArrayList<Integer>)matrix.get(size)).add(Integer.parseInt(newData[i]));
		}
	}
	
	//Usuwanie wierzcho�ka
	@SuppressWarnings("unchecked")
	public static void removeVertex(int vert){		
		matrix.remove(vert);
		for(int i = 0; i < matrix.size(); i++){
			((ArrayList<Integer>)matrix.get(i)).remove(vert);
		}
	}
	
	//Stopie� wierzcho�ka
	@SuppressWarnings("unchecked")
	public static int vertDeg(int vert){
		int deg;
		int loops = 0;
		int edges = 0;
		
		for(int i = 0; i < matrix.size(); i++){
			if(i == vert)
				loops = ((ArrayList<Integer>)matrix.get(vert)).get(i);
			else
				edges += ((ArrayList<Integer>)matrix.get(vert)).get(i);
		}
		
		deg = 2 * loops + edges;
		return deg;
	}
	
	//Stopie� minimalny i maksymalny
	public static int[] minMaxDeg(){
		int min = vertDeg(0);
		int max = vertDeg(0);
		int deg[] = new int[2];
		int temp;
		
		for(int i = 0; i < matrix.size(); i++){
			temp = vertDeg(i);
			if(temp < min)
				min = temp;
			else if(temp > max)
				max = temp;
		}
		
		deg[0] = min;
		deg[1] = max;
		return deg;
	}
	
	//Posortowana malej�co lista stopni
	public static ArrayList<Integer> sortedDeg(){
		ArrayList<Integer> sortedList = new ArrayList<>();
		int temp;
		
		//Licz� wszystkie stopnie
		for(int i = 0; i < matrix.size(); i++){
			temp = vertDeg(i);
			sortedList.add(temp);
		}
		
		Collections.sort(sortedList);
		Collections.reverse(sortedList);
		return sortedList;
	}
	
	//Mno�enie macierzy
	@SuppressWarnings("unchecked")
	public static ArrayList<ArrayList<?>> matrixMultiply(ArrayList<ArrayList<?>> givenMatrix){
		ArrayList<ArrayList<?>> resultMatrix = new ArrayList<ArrayList<?>>();
		int temp = 0;
		
		for(int i = 0; i < matrix.size(); i++){
			resultMatrix.add(new ArrayList<Object>());
			for(int j = 0; j < matrix.size(); j++){
				temp = 0;
				for(int k = 0; k < matrix.size(); k++){
					temp += ((ArrayList<Integer>)givenMatrix.get(i)).get(k) * ((ArrayList<Integer>)matrix.get(j)).get(k);
				}
				((ArrayList<Integer>)resultMatrix.get(i)).add(temp);
			}
		}
		return resultMatrix;
	}
	
	//Szukanie C3 w macierzy s�siedztwa
	@SuppressWarnings("unchecked")
	public static void checkCycles(ArrayList<ArrayList<?>> tempMatrix) {
		int diagSum = 0;
		
		for(int i = 0; i < tempMatrix.size(); i++){
			diagSum += ((ArrayList<Integer>)tempMatrix.get(i)).get(i);
		}
		
		if(diagSum == 0){
			System.out.println("W aktualnym grafie nie wyst�puj� cykle C3");
		} else {
			//Dla graf�w nieskierowanych
			System.out.println("Ilo�� cykli C3 w aktualnym grafie: " + (diagSum/6));
		}
	}
	
	//Wczytanie ci�gu i sprawdzenie czy jest graficzny
	public static void isGraphic(ArrayList<Integer> degList){
		int len = degList.size();
		int sum = 0;
		int temp;
		boolean result = true;
		
		Collections.sort(degList);
		Collections.reverse(degList);
		
		//Sprawdzam czy suma jest parszysta(musi by�)
		for(int i = 0; i < len; i++){
			sum += degList.get(i);
		}
		if(sum%2 == 0){
			while(degList.get(0) != 0 && result){				
				temp = 0;
				for(int i = 1; i < len; i++){
					if(degList.get(i) != 0){
						temp++;
					}
				}
				//Je�li nie mamy wystarczaj�cej ilo�ci dod. element�w - nie jest graficzny
				if(degList.get(0) > temp){
					result = false;
				} else{
					for(int i = 1; i <= degList.get(0); i++){
						degList.set(i, degList.get(i) - 1);
					}
				}
				
				degList.set(0, 0);
				Collections.sort(degList);
				Collections.reverse(degList);
			}
		} else {
			result = false;
		}
		
		if(result)
			System.out.println("Podany ci�g jest graficzny.");
		else
			System.out.println("Podany ci�g nie jest graficzny.");
	}
	
}
