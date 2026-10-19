package juego;

public class Enemigo {
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private double velocidad;
	private int direccion; // Dirección o sentido de movimiento
	private boolean esHorizontal; // True si se mueve horizontal, false si vertical
	private boolean puedeDejarBombas; // True si es un enemigo que suelta bombas

	// Constructor
	public Enemigo(double x, double y, boolean esHorizontal, boolean puedeDejarBombas) {
		this.x = x;
		this.y = y;
		this.esHorizontal = esHorizontal;
		this.puedeDejarBombas = puedeDejarBombas;
	}

}
