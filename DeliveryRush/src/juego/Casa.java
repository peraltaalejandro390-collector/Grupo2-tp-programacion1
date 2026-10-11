package juego;

public class Casa {
	// Atributos privados
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private int numero;

	// Constructor
	public Casa(double x, double y, double ancho, double alto, int numero) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.numero = numero;
	}

	// Métodos a implementar:
	// - dibujarse(Entorno entorno)
	// - contienePunto(double px, double py)
	// - getters y setters
}
