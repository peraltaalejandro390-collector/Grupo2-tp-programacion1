package juego;

public class Negocio {
	// Atributos privados
	private double x;
	private double y;
	private double ancho;
	private double alto;
	private String nombre;
	private int numero;

	// Constructor
	public Negocio(double x, double y, double ancho, double alto, int numero, String nombre) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.numero = numero;
		this.nombre = nombre;
	}

	// Métodos a implementar:
	// - dibujarse(Entorno entorno)
	// - contienePunto(double px, double py)
	// - getters y setters
}
