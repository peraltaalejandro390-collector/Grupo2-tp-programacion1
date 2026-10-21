package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Bomba {
	private double x;
	private double y;
	private int ticksParaExplotar;
	private int ticksDuracionExplosion;
	private boolean exploto;
	
	// Dimensiones del área de explosión
	private double anchoArea;
	private double altoArea;

	// Recursos gráficos
	private Image imgBomba;
	private Image imgExplosion;

	// Constructor
	public Bomba(double x, double y) {
		this.x = x;
		this.y = y;
		
		// 3 segundos  antes de estallar
		this.ticksParaExplotar = 150; 
		// La explosión se queda dibujada en pantalla 0.8 seg)
		this.ticksDuracionExplosion = 40; 
		this.exploto = false;

		// El área de daño cubre el ancho de la calle (46) y una porción de largo (100)
		this.anchoArea = 46.0;
		this.altoArea = 100.0;

		// Carga de imágenes (asegurate de tenerlas en tu carpeta de assets)
		this.imgBomba = Herramientas.cargarImagen("juego/bomba.png");
		this.imgExplosion = Herramientas.cargarImagen("juego/explosion.png");
	}

	// Actualiza el ciclo de vida de la bomba (reloj interno)
	public void actualizar() {
		if (!exploto) {
			this.ticksParaExplotar--;
			if (this.ticksParaExplotar <= 0) {
				this.exploto = true;
			}
		} else {
			if (this.ticksDuracionExplosion > 0) {
				this.ticksDuracionExplosion--;
			}
		}
	}

	// Dibuja la bomba parpadeando o el fuego de la explosión según su estado
	public void dibujarse(Entorno entorno) {
		if (!exploto) {
			// Efecto visual simple de parpadeo antes de explotar
			if (this.ticksParaExplotar > 30 || (this.ticksParaExplotar / 5) % 2 == 0) {
				if (this.imgBomba != null) {
					entorno.dibujarImagen(this.imgBomba, this.x, this.y, 0, 0.05);
				} else {
					entorno.dibujarCirculo(this.x, this.y, 15, Color.BLACK);
				}
			}
		} else if (estaActiva()) {
			// Dibuja el rango de la explosión
			if (this.imgExplosion != null) {
				entorno.dibujarImagen(this.imgExplosion, this.x, this.y, 0, 0.15);
			} else {
				// si no encuentra la imagen
				entorno.dibujarRectangulo(this.x, this.y, this.anchoArea, this.altoArea, 0, new Color(255, 0, 0, 150));
			}
		}
	}

	// Retorna verdadero si la explosión está activa causando daño en este tick
	public boolean estaActiva() {
		return this.exploto && this.ticksDuracionExplosion > 0;
	}

	// Retorna verdadero cuando la explosión terminó del todo y el objeto debe ser destruido (null)
	public boolean terminada() {
		return this.exploto && this.ticksDuracionExplosion <= 0;
	}

	// Detecta si el repartidor colisiona con el cuerpo físico de la bomba 
	public boolean colisionaCon(double repX, double repY, double repAncho, double repAlto) {
		if (!exploto) {
			// Colisión estándar con el dispositivo
			double difX = Math.abs(this.x - repX);
			double difY = Math.abs(this.y - repY);
			return difX < (repAncho / 2 + 10) && difY < (repAlto / 2 + 10);
		}
		return false;
	}

	// Detecta si el repartidor entra en el rango rectangular de daño de la explosión
	public boolean enAreaDeEfecto(double repX, double repY, double repAncho, double repAlto) {
		if (estaActiva()) {
			return (Math.abs(this.x - repX) < (this.anchoArea + repAncho) / 2.0 &&
					Math.abs(this.y - repY) < (this.altoArea + repAlto) / 2.0);
		}
		return false;
	}
	

	// Getters necesarios
	public double getX() { return this.x; }
	public double getY() { return this.y; }
	public boolean isExploto() { return this.exploto; }
}
