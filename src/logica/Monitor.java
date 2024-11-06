package logica;

import java.io.Serializable;

import javax.swing.JOptionPane;

public class Monitor implements Serializable {

	private static final long serialVersionUID = 1L;
	private int cantLectores;
	private boolean escribiendo = false;

	public Monitor() {
		cantLectores = 0;
		escribiendo = false;
	}

	public synchronized void comenzarLectura() {
		while (escribiendo) {
			try {
				wait();
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "Error del monitor!");
				e.printStackTrace();
			}
		}
		cantLectores = cantLectores + 1;
	}

	public synchronized void terminarLectura() {
		notify();
		cantLectores = cantLectores - 1;
	}

	public synchronized void comienzoEscritura() {
		while ((escribiendo) || (cantLectores > 0)) {
			try {
				wait();
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "Error del monitor!");
				e.printStackTrace();
			}
		}
		escribiendo = true;
	}

	public synchronized void terminoEscritura() {
		notify();
		escribiendo = false;
	}

	public synchronized int cantlectores() {
		return cantLectores;
	}
}
