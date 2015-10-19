package com.jakub.grafy;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static ArrayList<ArrayList<?>> matrix = new ArrayList<ArrayList<?>>();
	
	@SuppressWarnings("resource")
	public static void main(String args[]) throws Exception{
		int command = -1;
		int vert, deg, size;
		int deg2[];
		String[] newData;
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
					addVertex(newData);
					System.out.println("Dodano nowy wierzcho�ek.");
					break;
				case(4):
					System.out.println("Prosz� poda� nr wierzcho�ka do usuni�cia: ");
					vert = scanVert.nextInt() - 1;
					removeVertex(vert);
					System.out.println("Usuni�to podany wierzcho�ek.");
					break;
				case(5):
					System.out.println("Prosz� poda� nr wierzcho�ka: ");
					vert = scanVert.nextInt() - 1;
					deg = vertDeg(vert);
					System.out.println("Stopie� wierzcho�ka wynosi: " + deg);
					break;
				case(6):
					deg2 = minMaxDeg();
					System.out.println("Stopie� minimalny: " + deg2[0]);
					System.out.println("Stopie� maksymalny: " + deg2[1]);
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
	
}
