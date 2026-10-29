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

	private int tiempoTranscurrido;
	private int pedidosEntregados;
	private int pedidosPerdidos;
	private int enemigosEliminados;
	private int ticksParaCerrar = 150;

	Juego() {
		this.entorno = new Entorno(this, "Delivery Rush - Programacion I UNGS", 800, 600);
		this.ciudad = new Ciudad();
		this.repartidor = new Repartidor(400, 584);

		this.enemigos = new Enemigo[4];
		for (int i = 0; i < this.enemigos.length; i++) {
			this.enemigos[i] = new Enemigo();
		}
this.bombas = new Bomba[10];
		Casa destino = this.ciudad.obtenerCasaAleatoria();
		Negocio origen = this.ciudad.obtenerNegocioAleatorio();
		if (destino != null && origen != null) {
			this.pedidoActual = new Pedido(destino, origen);
		}

		this.entorno.iniciar();
	}

	public void tick() {
		if (FinDelJuego()) {
			return;
		}

		this.tiempoTranscurrido++;
		this.repartidor.actualizar();

		movimiento();
		mouse();
		actualizarPedido();
		actualizarEnemigos();
		actualizarProyectil();
actualizarBombas();
		dibujar();
	}

	//condiciones de victoria y derrota
	private boolean FinDelJuego() {
		// Condicion de derrota
		if (this.repartidor.getVidas() <= 0 || this.pedidosPerdidos >= 5) {
			entorno.dibujarRectangulo(400, 300, 800, 600, 0, Color.BLACK);
			entorno.cambiarFont("Arial", 36, Color.RED);
			entorno.escribirTexto("¡HAS PERDIDO!", 260, 280);
			entorno.cambiarFont("Arial", 16, Color.WHITE);
			if (this.repartidor.getVidas() <= 0) {
				entorno.escribirTexto("Te quedaste sin vidas.", 315, 330);
			} else {
				entorno.escribirTexto("Perdiste demasiados pedidos.", 290, 330);
			}

			this.ticksParaCerrar--;
			if (this.ticksParaCerrar <= 0) {
				System.exit(0);
			}
			return true;
		}
		//Condición de victoria
		if (this.pedidosEntregados >= 5) {
			entorno.dibujarRectangulo(400, 300, 800, 600, 0, Color.BLACK);
			entorno.cambiarFont("Arial", 36, Color.GREEN);
			entorno.escribirTexto("¡HAS GANADO!", 270, 280);
			entorno.cambiarFont("Arial", 16, Color.WHITE);
			entorno.escribirTexto("¡Completaste 5 pedidos con éxito!", 275, 330);

			this.ticksParaCerrar--;
			if (this.ticksParaCerrar <= 0) {
				System.exit(0);
			}
			return true;
		}

		return false;
	}

	//Manejo de teclado
	private void movimiento() {
		if (entorno.estaPresionada('w') || entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			this.repartidor.moverArriba(this.ciudad);
		}
		if (entorno.estaPresionada('s') || entorno.estaPresionada(entorno.TECLA_ABAJO)) {
			this.repartidor.moverAbajo(this.ciudad);
		}
		if (entorno.estaPresionada('a') || entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			this.repartidor.moverIzquierda(this.ciudad);
		}
		if (entorno.estaPresionada('d') || entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			this.repartidor.moverDerecha(this.ciudad);
		}
	}

	//Manejo de mouse
	private void mouse() {
		if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
			if (this.proyectil == null) {
				this.proyectil = new Proyectil(this.repartidor.getX(), this.repartidor.getY(), this.repartidor.getUltimaDireccion());
			}
		}
		if (entorno.sePresionoBoton(entorno.BOTON_DERECHO) && this.pedidoActual != null) {
			if (this.pedidoActual.interactuar(this.repartidor)) {
				this.pedidosEntregados++;
				this.pedidoActual = null;
			}
		}
	}

	// Actualizacion del ciclo de vida del pedido
	private void actualizarPedido() {
		if (this.pedidoActual == null) {
			Casa destino = this.ciudad.obtenerCasaAleatoria();
			Negocio origen = this.ciudad.obtenerNegocioAleatorio();
			if (destino != null && origen != null) {
				this.pedidoActual = new Pedido(destino, origen);
			}
		} else {
			this.pedidoActual.actualizar();

			if (this.pedidoActual.getEstado() == Pedido.ESTADO_PERDIDO) {
				this.pedidosPerdidos++;
				this.pedidoActual = null;
			}
		}
	}

	//Actualizacion y colisiones de los enemigos
	private void actualizarEnemigos() {
		for (int i = 0; i < this.enemigos.length; i++) {
			if (this.enemigos[i] == null) {
				this.enemigos[i] = new Enemigo();
			} else {
				this.enemigos[i].moverse();
//GESTIÓN DE BOMBAS
				Bomba nuevaBomba = this.enemigos[i].intentarSoltarBomba();
				if (nuevaBomba != null) {
				    boolean bombaGuardada = false; // Bandera para controlar el bucle
				    
				    for (int j = 0; j < this.bombas.length && !bombaGuardada; j++) {
				        if (this.bombas[j] == null) {
				            this.bombas[j] = nuevaBomba;
				            bombaGuardada = true;
				        }
				    }
				}
				//Colision enemigo con repartidor
				if (this.enemigos[i].colisionaCon(this.repartidor.getX(), this.repartidor.getY())) {
					if (!this.repartidor.esInvulnerable()) {
						this.repartidor.perderVidas();

						//Si llevaba un pedido en camino, lo pierde
						if (this.pedidoActual != null && this.pedidoActual.getEstado() == Pedido.ESTADO_EN_CAMINO) {
							this.pedidosPerdidos++;
							this.pedidoActual = null;
						}
					}
				}
			}
		}
	}

	//Actualizacion y colisiones del proyectil
	private void actualizarProyectil() {
		if (this.proyectil != null) {
			this.proyectil.avanzar();

			//Colision de proyectil con enemigos
			for (int i = 0; i < this.enemigos.length; i++) {
				if (this.enemigos[i] != null && this.enemigos[i].colisionaCon(this.proyectil.getX(), this.proyectil.getY())) {
					this.enemigos[i] = null;
					this.proyectil = null;
					this.enemigosEliminados++;
					break;
				}
			}

			if (this.proyectil != null && this.proyectil.fueraDeLimites(this.entorno)) {
				this.proyectil = null;
			}
			//Si chocó contra las casas 
			if (this.proyectil != null && this.ciudad.hayColisiones(
			        this.proyectil.getX(), this.proyectil.getY(),5,5)) {
			    this.proyectil = null;
			}
		}
	}
private void actualizarBombas() {
		for (int i = 0; i < this.bombas.length; i++) {
			if (this.bombas[i] != null) {
				this.bombas[i].actualizar();

					boolean tocoBomba = this.bombas[i].colisionaCon(repartidor.getX(), repartidor.getY(), repartidor.getAncho(), repartidor.getAlto());
				boolean alcanzadoPorFuego = this.bombas[i].enAreaDeEfecto(repartidor.getX(), repartidor.getY(), repartidor.getAncho(), repartidor.getAlto());
				
				if (tocoBomba || alcanzadoPorFuego) {
					this.repartidor.morir();
				}

					if (this.bombas[i].terminada()) {
					this.bombas[i] = null;
				}
			}
		}
	}
	private void dibujar() {
		this.ciudad.dibujarse(this.entorno);

		if (this.pedidoActual != null) {
			this.pedidoActual.dibujar(this.entorno);
		}

		for (int i = 0; i < this.enemigos.length; i++) {
			if (this.enemigos[i] != null) {
				this.enemigos[i].dibujarse(this.entorno);
			}
		}
for (int i = 0; i < this.bombas.length; i++) {
			if (this.bombas[i] != null) {
				this.bombas[i].dibujarse(this.entorno);
			}
		if (this.proyectil != null) {
			this.proyectil.dibujarse(this.entorno);
		}

		this.repartidor.dibujarse(this.entorno);

		barraSuperior();
	}

	//Barra superior de estadísticas
	private void barraSuperior() {
		entorno.dibujarRectangulo(400, 20, 800, 40, 0, Color.BLACK);
		entorno.cambiarFont("Arial", 12, Color.WHITE);

		//Tiempo transcurrido
		int segTotales = this.tiempoTranscurrido / 50;
		int min = segTotales / 60;
		int seg = segTotales % 60;
		String reloj = String.format("%02d:%02d", min, seg);
		entorno.escribirTexto("Tiempo: " + reloj, 15, 25);

		//Vidas
		entorno.escribirTexto("Vidas: " + this.repartidor.getVidas(), 130, 25);

		//Pedidos entregados y perdidos
		entorno.escribirTexto("Entregados: " + this.pedidosEntregados, 215, 25);
		entorno.escribirTexto("Perdidos: " + this.pedidosPerdidos, 335, 25);

		//Enemigos eliminados
		entorno.escribirTexto("Eliminados: " + this.enemigosEliminados, 445, 25);

		//Estado del pedido actual
		if (this.pedidoActual != null) {
			entorno.cambiarFont("Arial", 12, Color.YELLOW);
			if (this.pedidoActual.getEstado() == Pedido.ESTADO_ESPERANDO) {
				entorno.escribirTexto("Pedido: Esperando (" + this.pedidoActual.getTiempoRestanteSegundos() + "s)", 560, 25);
			} else if (this.pedidoActual.getEstado() == Pedido.ESTADO_EN_CAMINO) {
				entorno.escribirTexto("Pedido: En camino (" + this.pedidoActual.getTiempoRestanteSegundos() + "s)", 560, 25);
			}
		} else {
			entorno.cambiarFont("Arial", 12, Color.GRAY);
			entorno.escribirTexto("Pedido: Ninguno", 560, 25);
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
