package juego;

import java.awt.Color;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	private Entorno entorno;

	//variables y métodos
	private Ciudad ciudad;
	private Repartidor repartidor;
	private Enemigo[] enemigos;
	private Proyectil proyectil;
	private Bomba[] bombas;
	private Pedido pedidoActual;

	//estadísticas y estados
	private int tiempoTranscurrido;
	private int pedidosEntregados;
	private int pedidosPerdidos;
	private int enemigosEliminados;

	Juego() {
		this.entorno = new Entorno(this, "Delivery Rush - Programacion I UNGS", 800, 600);
		this.repartidor = new Repartidor(400, 500);
		this.entorno.iniciar();
	}

	public void tick() {
		//movimiento
		if (entorno.estaPresionada('w') || entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
		    repartidor.moverArriba();
		}
		if (entorno.estaPresionada('s') || entorno.estaPresionada(entorno.TECLA_ABAJO)) {
		    repartidor.moverAbajo();
		}
		if (entorno.estaPresionada('a') || entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
		    repartidor.moverIzquierda();
		}
		if (entorno.estaPresionada('d') || entorno.estaPresionada(entorno.TECLA_DERECHA)) {
		    repartidor.moverDerecha();
		}
		tiempoTranscurrido++;

		//Dibujar en pantalla (repartidor):
		repartidor.dibujarse(this.entorno);
		//disparar
		if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
		    if (this.proyectil == null) {
		        this.proyectil = new Proyectil(repartidor.getX(), repartidor.getY(), repartidor.getUltimaDireccion());
		    }
		}

		//ACTUALIZAR Y DIBUJAR PROYECTIL
		if (this.proyectil != null) {
		    this.proyectil.avanzar();
		    this.proyectil.dibujarse(this.entorno);

		    //si sale de pantalla se hace null
		    if (this.proyectil.fueraDeLimites(this.entorno)) {
		        this.proyectil = null;
		    }
		}
		
		//actualizar y dibujar el pedido si existe
		if (this.pedidoActual != null) {
		    this.pedidoActual.actualizar();
		    this.pedidoActual.dibujar(this.entorno);
		}

		//clic derecho del mouse para recoger o entregar:
		if (entorno.sePresionoBoton(entorno.BOTON_DERECHO)) {
		    if (this.pedidoActual != null) {
		        //si está esperando en el negocio y el repartidor está cerca
		        if (this.pedidoActual.getEstado() == Pedido.ESTADO_ESPERANDO 
		            && this.pedidoActual.repartidorCercaNegocio(this.repartidor)) {
		            this.pedidoActual.recoger();
		        }
		        //si va en camino y el repartidor llego a la casa:
		        else if (this.pedidoActual.getEstado() == Pedido.ESTADO_EN_CAMINO 
		            && this.pedidoActual.repartidorCercaDeCasa(this.repartidor)) {
		            this.pedidoActual.entregar();
		            this.pedidosEntregados++;
		        }
		    }
		}

		entorno.cambiarFont("Arial", 12, java.awt.Color.WHITE);

		//Tiempo 
		int segTotales = this.tiempoTranscurrido / 50;
		int min = segTotales / 60;
		int seg = segTotales % 60;
		String reloj = String.format("%02d:%02d", min, seg);
		entorno.escribirTexto("Tiempo: " + reloj, 20, 25);


		//Vidas
		entorno.escribirTexto("Vidas: " + this.repartidor.getVidas(), 140, 25);

		//Entregados y Perdidos
		entorno.escribirTexto("Entregados: " + this.pedidosEntregados, 230, 25);
		entorno.escribirTexto("Perdidos: " + this.pedidosPerdidos, 360, 25);

		//Tiempo restante del pedido (si hay activo)
		if (this.pedidoActual != null) {
		    entorno.cambiarFont("Arial", 12, java.awt.Color.YELLOW);
		    entorno.escribirTexto("Tiempo Pedido: " + this.pedidoActual.getTiempoRestanteSegundos() + "s", 500, 25);
		}
		//al vencerse el tiempo:
		if (this.pedidoActual.getEstado() == Pedido.ESTADO_PERDIDO) {
		    this.pedidosPerdidos++;
		    this.pedidoActual = null; 
		}

		//al entregar
		else if (this.pedidoActual.getEstado() == Pedido.ESTADO_EN_CAMINO 
		    && this.pedidoActual.repartidorCercaDeCasa(this.repartidor)) {
		    this.pedidoActual.entregar();
		    this.pedidosEntregados++;
		    this.pedidoActual = null; 
		}
		

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}

