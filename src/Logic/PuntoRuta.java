package Logic;
import java.awt.*;

/*Clase que representa al objeto PuntoRuta, que representa un punto de la ruta del nodo*/
public class PuntoRuta {
    double costo;
    Point coordenada;
    int energiaRobot;
    
    /*Constructor de la clase*/
    public PuntoRuta(double costo, Point coordenada, int energiaRobot){
        this.costo = costo;
        this.coordenada = coordenada;
        this.energiaRobot = energiaRobot;
    }

    /*Metodo que retorna el valor del costo del nodo*/
    public double getCosto() {
        return costo;
    }

    /*Metodo que se encarga de establecer el valor del costo del nodo*/
    public void setCosto(double costo) {
        this.costo = costo;
    }

    /*Metodo que retorna la coordenada del nodo*/
    public Point getCoordenada() {
        return coordenada;
    }

    /*Metodo que se encarga de establecer la coordenada del nodo*/
    public void setCoordenada(Point coordenada) {
        this.coordenada = coordenada;
    }

    /*Metodo que retorna el valor la energia del robot en el nodo*/
    public int getEnergiaRobot() {
        return energiaRobot;
    }

    /*Metodo que se encarga de establecer el valor de la energia del robot en el nodo*/
    public void setEnergiaRobot(int energiaRobot) {
        this.energiaRobot = energiaRobot;
    }
}
