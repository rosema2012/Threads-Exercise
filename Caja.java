package Ej2_Supermercado_v2;

import java.util.Random;

public class Caja {
    private static final int MAX_TIME = 1000;
    class ClienteCola {
        int idCliente;
        ClienteCola sig;
    }

    ClienteCola inicioCola, finCola;
    int idCaja;

    public Caja(int id) {
        inicioCola = null;
        finCola = null;
        this.idCaja = id;
    }

    private boolean estaColaVacia() {
        if (inicioCola == null)
            return true;
        else
            return false;
    }

    synchronized public void esperarEnCola(int id_cliente) throws InterruptedException {
        // Añado el cliente a la cola de la caja
        ClienteCola nuevo;
        nuevo = new ClienteCola();
        nuevo.idCliente = id_cliente;
        nuevo.sig = null;

        // Si la cola está vacia, lo inserto al inicio, si no lo referencio desde el ultimo
        if (estaColaVacia()) {
            inicioCola = nuevo;
            finCola = nuevo;
        } else {
            finCola.sig = nuevo;
            finCola = nuevo;
        }
        while (inicioCola.idCliente != id_cliente) {
            // Me bloqueo hasta que sea mi turno
            wait();
        }
    }

    synchronized public void atender(int importePago) throws InterruptedException {
        // Si he terminado de atender, vacio la cola, si no, modifico el siguiente cliente a atender
        if (inicioCola == finCola) {
            inicioCola = null;
            finCola = null;
        } else {
            inicioCola = inicioCola.sig;
        }
        int tiempo_atencion = new Random().nextInt(MAX_TIME);
        Thread.sleep(tiempo_atencion);
        ResultadosTotales.ganancias += importePago;
        // Acabo de atender, y despierto al siguiente cliente esperando
        notify();
    }

    synchronized public void imprimir() {
        // Se imprimen los clientes en la cola
        ClienteCola reco = inicioCola;
        while (reco != null) {
            System.out.print(reco.idCliente + " - ");
            reco = reco.sig;
        }
        System.out.println();
    }

}
