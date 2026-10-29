package juego;

import java.awt.Image;

import entorno.Entorno;

public class Pedido {
	//estados 
	public static int ESTADO_REALIZADO = 0;
	public static int ESTADO_ESPERANDO = 1;
	public static int ESTADO_EN_CAMINO = 2;
	public static int ESTADO_ENTREGADO = 3;
	public static int ESTADO_PERDIDO = 4;

	private Casa casaDestino;
	private Negocio negocioOrigen;
	private int estado;
	private int tiempoRestante;
	private double escalaFlechas;
	
	//imagenes flechas
    private final Image flechaVerde = entorno.Herramientas.cargarImagen("juego/flecha_verde.png");
    private final Image flechaRoja = entorno.Herramientas.cargarImagen("juego/flecha_roja.png");
	// Constructor
	public Pedido(Casa casaDestino, Negocio negocioOrigen) {
		this.casaDestino = casaDestino;
		this.negocioOrigen = negocioOrigen;
		this.estado=ESTADO_ESPERANDO;
		this.tiempoRestante=1500; //30seg
		this.escalaFlechas=0.16;
		
	}
	
	public void recoger() {
		if(this.estado==ESTADO_ESPERANDO) {
			this.estado=ESTADO_EN_CAMINO;
			this.tiempoRestante=1500;
		}
	}
	
	public void entregar() {
		if(this.estado==ESTADO_EN_CAMINO) {
			this.estado=ESTADO_ENTREGADO;
		}
	}
	public void perder() {
		this.estado=ESTADO_PERDIDO;
	}
	
	public void actualizar() {
	    //solo descuenta si esta en camino
	    if (this.estado == ESTADO_EN_CAMINO) {
	        if (this.tiempoRestante > 0) {
	            this.tiempoRestante--;
	            if (this.tiempoRestante == 0) {
	                perder(); //se vencio el tiempo de entrega
	            }
	        }
	    }
	}
	
	public void dibujar(Entorno entorno) {
		if(this.estado==ESTADO_ESPERANDO && this.negocioOrigen!=null) {
			double flechaX=this.negocioOrigen.getX();
			double flechaY=this.negocioOrigen.getY()-28;
			entorno.dibujarImagen(this.flechaVerde, flechaX, flechaY,0,this.escalaFlechas);
			
		}
        else if (this.estado == ESTADO_EN_CAMINO && this.casaDestino != null) {
            //dibujar la flecha roja
            double flechaX = this.casaDestino.getX();
            double flechaY = this.casaDestino.getY() - 28;
            entorno.dibujarImagen(this.flechaRoja, flechaX, flechaY, 0, this.escalaFlechas);
        }
		
	}
	//retorna true si el repartidor esta cerca del negocio
	public boolean repartidorCercaNegocio(Repartidor repartidor) {
		if(repartidor==null || this.negocioOrigen==null) return false;
		double difX=Math.abs(repartidor.getX()-this.negocioOrigen.getX());
		double difY=Math.abs(repartidor.getY()-this.negocioOrigen.getY());
		return difX <= 50 && difY <= 50;
	}
	
	//true si esta cerca de casa
	public boolean repartidorCercaDeCasa(Repartidor repartidor) {
	    if (repartidor == null || this.casaDestino == null) {
	        return false;
	    }
	    double difX = Math.abs(repartidor.getX() - this.casaDestino.getX());
	    double difY = Math.abs(repartidor.getY() - this.casaDestino.getY());
	    
	    return difX <= 50 && difY <= 50;
	}
	
	public boolean interactuar(Repartidor repartidor) {
	    if (this.estado == ESTADO_ESPERANDO && repartidorCercaNegocio(repartidor)) {
	        recoger();
	        return false; // No se entrego aun, se recogio
	    } else if (this.estado == ESTADO_EN_CAMINO && repartidorCercaDeCasa(repartidor)) {
	        entregar();
	        return true; //Se completo la entrega
	    }
	    return false;
	}
	
    public int getEstado() {
        return this.estado;
    }
    public Casa getCasaDestino() {
        return this.casaDestino;
    }
    public Negocio getNegocioOrigen() {
        return this.negocioOrigen;
    }
    public int getTiempoRestanteSegundos() {
        return this.tiempoRestante / 50;
    }
    public boolean estaFinalizado() {
        return this.estado == ESTADO_ENTREGADO || this.estado == ESTADO_PERDIDO;
    }



}


