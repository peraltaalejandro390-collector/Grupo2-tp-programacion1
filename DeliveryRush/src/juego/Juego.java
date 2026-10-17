package juego;

import java.awt.Color;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	private Entorno entorno;

	private Ciudad ciudad;
	private Repartidor repartidor;
	private Enemigo[] enemigos;
	private Proyectil proyectil;
	private Bomba[] bombas;
	private Pedido pedidoActual;

	// Estadísticas y estados
	private int tiempoTranscurrido;
	private int pedidosEntregados;
	private int pedidosPerdidos;
	private int enemigosEliminados;

	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Delivery Rush - Programacion I UNGS", 800, 600);

		this.repartidor = new Repartidor(400, 500);
		this.ciudad = new Ciudad();
		
		
		
		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick() {
		entorno.dibujarRectangulo(400, 300, 800, 600, 0, new Color(60, 60, 60));
		ciudad.dibujarse(entorno);     
		repartidor.dibujarse(entorno);

		// Control por teclado en Juego:
		if (entorno.estaPresionada('w') || entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
		    repartidor.moverArriba(ciudad);
		}
		if (entorno.estaPresionada('s') || entorno.estaPresionada(entorno.TECLA_ABAJO)) {
		    repartidor.moverAbajo(ciudad);
		}
		if (entorno.estaPresionada('a') || entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
		    repartidor.moverIzquierda(ciudad);
		}
		if (entorno.estaPresionada('d') || entorno.estaPresionada(entorno.TECLA_DERECHA)) {
		    repartidor.moverDerecha(ciudad);
		}

		// Dibujar en pantalla:
		//repartidor.dibujarse(this.entorno);
		// 1. DISPARAR (Clic izquierdo del mouse y solo si no hay otro proyectil volando)
		if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
		    if (this.proyectil == null) {
		        this.proyectil = new Proyectil(repartidor.getX(), repartidor.getY(), repartidor.getUltimaDireccion());
		    }
		}

		// 2. ACTUALIZAR Y DIBUJAR PROYECTIL
		if (this.proyectil != null) {
		    this.proyectil.avanzar();
		    this.proyectil.dibujarse(this.entorno);

		    // Si salió de la pantalla, se destruye volviéndose null
		    if (this.proyectil.fueraDeLimites(this.entorno)) {
		        this.proyectil = null;
		    }
		}

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
