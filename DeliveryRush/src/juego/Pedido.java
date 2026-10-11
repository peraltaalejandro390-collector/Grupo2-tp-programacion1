package juego;

public class Pedido {
	// Constantes de estados posibles
	public static final int ESTADO_REALIZADO = 0;
	public static final int ESTADO_ESPERANDO = 1;
	public static final int ESTADO_EN_CAMINO = 2;
	public static final int ESTADO_ENTREGADO = 3;
	public static final int ESTADO_PERDIDO = 4;

	// Atributos privados
	private Casa casaDestino;
	private Negocio negocioOrigen;
	private int estado;
	private int tiempoRestante;

	// Constructor
	public Pedido(Casa casaDestino, Negocio negocioOrigen) {
		this.casaDestino = casaDestino;
		this.negocioOrigen = negocioOrigen;
	}

	// Métodos a implementar:
	// - pasarAEsperando()
	// - recoger()
	// - entregar()
	// - perder()
	// - actualizar()
	// - dibujarse(Entorno entorno)
	// - repartidorCercaDeNegocio(Repartidor repartidor)
	// - repartidorCercaDeCasa(Repartidor repartidor)
	// - estaFinalizado()
	// - getters y setters
}
