package Ej2_Supermercado_v2;

import java.util.Random;
import java.util.Scanner;

public class Supermarket {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca numero de clientes");
        int M = sc.nextInt();
        System.out.println("Introduzca numero de cajas");
        int N = sc.nextInt();

        Caja cajas[] = new Caja[N];
        for (int i = 0; i < N; i++) {
            cajas[i] = new Caja(i);
        }
        Cliente clientes[] = new Cliente[M];
        for (int i = 0; i < M; i++) {
            // Seleccionamos ya en qué caja se situara
            int j = new Random().nextInt(N);
            clientes[i] = new Cliente(i, cajas[j]);
            clientes[i].start();
        }
        try {
            for (int i = 0; i < M; i++) {
                clientes[i].join();
            }
        } catch (InterruptedException ex) {
            System.out.println("Hilo principal interrumpido.");
        }
        System.out.println("Supermercado cerrado.");
        System.out.println("Ganancias: " + ResultadosTotales.ganancias);
    }
}
