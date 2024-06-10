package datos;

import java.util.Random;
import test.TestQuickSort;

public class Funciones {

	public static int[] generarArrayAleatorio(int tam, int min, int max) {
		// Declara e inicializa un array de enteros de tamaño n
		int[] arr = new int[tam];
		// Crea una instancia de Random
		Random random = new Random();
		// Itera desde 0 hasta tam-1
		for (int i = 0; i < tam; i++) {
			// Asigna un número aleatorio entre min y max a cada posición del array
			arr[i] = random.nextInt(max - min + 1) + min;
		}
		// Retorna el array generado
		return arr; 
	}

	public static void quickSort(int[] arr, int bajo, int alto) {
		// Verifica si el índice bajo es menor que el índice alto
		if (bajo < alto) { 
			// Divide el array y obtiene el índice del pivote
			int pivotePos = particion(arr, bajo, alto); 
			// Ordena recursivamente el subarray a la izquierda del pivote
			quickSort(arr, bajo, pivotePos - 1); 
			// Ordena recursivamente el subarray a la derecha del pivote
			quickSort(arr, pivotePos + 1, alto); 
		}
	}

	private static int particion(int[] arr, int bajo, int alto) {
		// Elige el último elemento como pivote
		int pivote = arr[alto]; 
		// Inicializa el índice del elemento más pequeño
		int i = bajo - 1; 
		for (int j = bajo; j < alto; j++) {
			// Si el elemento actual es menor que el pivote
			if (arr[j] < pivote) { 
				i++;
				// Intercambia el elemento actual con el elemento en la posición i
				intercambio(arr, i, j); 
			}
		}
		// Coloca el pivote en su posición correcta
		intercambio(arr, i + 1, alto); 
		// Retorna el índice del pivote
		return i + 1; 
	}

	private static void intercambio(int[] arr, int i, int j) {
		// Guarda el elemento en la posición i en una variable temporal
		int aux = arr[i]; 
		// Asigna el elemento en la posición j a la posición i
		arr[i] = arr[j]; 
		// Asigna el valor guardado en la variable temporal a la posición j
		arr[j] = aux; 
	}

	public static void quickSortConcurrente(Thread[] hilos, int cantHilos, int tam) {
		// Calcula el tamaño del segmento a asignar a cada hilo
		int tamParte = tam / cantHilos; 
		// Calcula el residuo para distribuirlo entre los hilos
		int resto = tam % cantHilos; 
		int inicio = 0; 
		int fin;
		
		for (int i = 0; i < cantHilos; i++) { 
			// Calcula el fin del segmento, distribuyendo el residuo entre los primeros hilos
			fin = inicio + tamParte + (i < resto ? 1 : 0) - 1; 
			// Crea e instancia cada hilo con su inicio y fin correspondientes													
			hilos[i] = new Thread(new TestQuickSort(inicio, fin)); 
			// Actualiza el inicio al valor siguiente al fin actual														
			inicio = fin + 1; 
		}
		
		long inicioHilo = System.nanoTime(); 
		
		for (Thread hilo : hilos) { 
			hilo.start(); 
		}
		
		for (Thread hilo : hilos) { 
			try {
				hilo.join(); 
			} catch (InterruptedException e) { 
				e.printStackTrace(); 
			}
		}
		
		System.out.println("	-Tardó: " + (System.nanoTime() - inicioHilo) + "ns en ordenar el array\n"); 												
	}
}
