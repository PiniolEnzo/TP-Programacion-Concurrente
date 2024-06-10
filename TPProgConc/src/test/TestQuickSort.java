package test;

import java.util.Arrays;

import datos.Funciones;

public class TestQuickSort implements Runnable {
	private static int array[];
	private static long tiempoInicio;
	private int inicio;
	private int fin;
	
	public TestQuickSort(int inicio, int fin) {
		super();
		this.inicio = inicio;
		this.fin = fin;
	}

	@Override
	public void run() {
		Funciones.quickSort(Arrays.copyOf(array, array.length), inicio, fin);
	}

	public static void main(String[] args) {
		// Estableciendo el tamaño del array
		int tam;
		tam = 100;
//		tam = 1000;
//		tam = 10000;	
//		tam = 100000;
//		tam = 1000000;
//		tam = 10000000;
//		tam = 50000000;

		// Inicialización del array con valores aleatorios dentro de un rango (0 a 50000)
		array = Funciones.generarArrayAleatorio(tam, 0, 50000);
		
		//*******SECUENCIAL*******
		System.out.println("QuickSort secuencial para " + tam + " elementos");
		tiempoInicio = System.nanoTime();
		// Llamada al método de ordenamiento QuickSort de manera secuencial, enviando una copia del array
		Funciones.quickSort(Arrays.copyOf(array, array.length), 0, array.length - 1);
		// Indico cuanto tardo
		System.out.println("	-Tardó: " + (System.nanoTime() - tiempoInicio) + "ns en ordenar el array\n");

		//*******CONCURRENTE*******
		System.out.println("QuickSort concurrente para " + tam + " elementos");
		// Obteniendo la cantidad de procesadores lógicos disponibles para crear la misma cantidad de hilos
		int cant = Runtime.getRuntime().availableProcessors();
		Thread[] hilos = new Thread[cant];
		// Llamada a la función para ejecutar el algoritmo de manera concurrente
		Funciones.quickSortConcurrente(hilos, cant, array.length);
	}
}
