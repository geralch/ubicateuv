package Logic;
import java.awt.*;
import java.util.*;

/*Clase que se encarga de realizar la busqueda no informada por costo uniforme*/
public class BusquedaNoInformada {
    private Comparator<Nodo> comparador = new ComparadorCostos();
    private PriorityQueue<Nodo> cola = new PriorityQueue(10, comparador);
    private ArrayList<ArrayList<Integer>> ambiente = new ArrayList();
    private ArrayList<Nodo> arbol = new ArrayList();
    private int nodosExpandidos = 0;
    private int nodosCreados = 0;
    private boolean boolParar = false;
    
    /*Constructor de la clase*/
    public BusquedaNoInformada(ArrayList<ArrayList<Integer>> ambiente, boolean boolParar){
        this.ambiente = ambiente;
        this.boolParar = boolParar;
    }
        
    /*Metodo que retorna un nodo que representa el ultimo nodo de la ruta elegida por la busqueda*/
    public Nodo busqueda(){
        Nodo nodoInicial = nodoInicial();
        Nodo nodoFinal = nodoInicial();
        boolean encontrado = false;
        cola.add(nodoInicial);
        arbol.add(nodoInicial);
        Nodo nodoNuevo = nodoInicial;
        
        while (cola.size()>0 )
        {
            nodoNuevo = cola.remove();
            if(tipoNodo(nodoNuevo) != 7){
                if(nodoNuevo.getEnergiaRobot()<=0){
                    if(!encontrado){
                        nodoFinal = nodoNuevo;
                        encontrado = true;
                    }
                    if(boolParar){
                        break;
                    }
                }
                else{
                    buscarNodosHijos(nodoNuevo);
                    nodosExpandidos = nodosExpandidos+1;   
                }
            }
            else{
                nodoFinal = nodoNuevo;
                break;
            }  
        }
        return nodoFinal;
    }
    
    /*Metodo que retorna el nodo inicial segun los datos del ambiente */
    public Nodo nodoInicial(){
        Point coordenadaInicial = new Point();
        for(int i=0; i<ambiente.size(); i++){
            for(int j=0; j<ambiente.size(); j++){
                if(ambiente.get(i).get(j)==0){
                    coordenadaInicial = new Point(i,j);
                }
            }
        }
        
        ArrayList<PuntoRuta> ruta = new ArrayList();
        ruta.add(new PuntoRuta(0,coordenadaInicial,6));
        
        Nodo nodoInicial = new Nodo(0,0,coordenadaInicial,6,ruta,0,-1,0);
        
        return nodoInicial;
    }
    
    /*Metodo que se encarga de buscar los hijos que puede terner un nodo*/
    public void buscarNodosHijos(Nodo nodoPadre){
        Point coordenadaArriba = new Point((int)nodoPadre.getCoordenada().getX()-1, (int)nodoPadre.getCoordenada().getY());
        Point coordenadaAbajo = new Point((int)nodoPadre.getCoordenada().getX()+1, (int)nodoPadre.getCoordenada().getY());
        Point coordenadaDerecha = new Point((int)nodoPadre.getCoordenada().getX(), (int)nodoPadre.getCoordenada().getY()+1);
        Point coordenadaIzquierda = new Point((int)nodoPadre.getCoordenada().getX(), (int)nodoPadre.getCoordenada().getY()-1);
        
        agregarHijos(coordenadaArriba, nodoPadre);
        agregarHijos(coordenadaAbajo, nodoPadre);
        agregarHijos(coordenadaDerecha, nodoPadre);
        agregarHijos(coordenadaIzquierda, nodoPadre);
    }
    
    /*Metodo que se encarga de crear y agregar los nodos hijos de un nodo a la 
    cola de prioridad y al arbol de la busqueda segun cumpla con las condiciones
    de expansion*/
    public void agregarHijos(Point coordenada, Nodo nodoPadre){
        if(coordenada.x>=0 && coordenada.y>=0 && coordenada.x<ambiente.size() && coordenada.y<ambiente.size()){
            if(ambiente.get(coordenada.x).get(coordenada.y)!=1){
                if(evitaDevolverse(nodoPadre, coordenada)){
                    nodosCreados = arbol.size();
                    ArrayList<PuntoRuta> ruta = copiarRuta(nodoPadre.getRuta());
                    double costoNodo = nodoPadre.getCosto()+asignarCostos(coordenada);
                    int indicePadre = nodoPadre.getIndice();
                    int indice = arbol.size();
                    int profundidad = nodoPadre.getProfundidad()+1;
                    int energiaRobot = cargaRobot(coordenada, nodoPadre);
                    ruta.add(new PuntoRuta(costoNodo,coordenada,energiaRobot));
                    Nodo nodoHijo = new Nodo(costoNodo,0,coordenada,energiaRobot,ruta,indice,indicePadre,profundidad);
                    arbol.add(nodoHijo);
                    cola.add(nodoHijo);
                }
            }
        }
    }
    
    /*Metodo que se encarga de compiar un arreglo*/
    public ArrayList<PuntoRuta> copiarRuta(ArrayList<PuntoRuta> ruta){
        ArrayList<PuntoRuta> nuevaRuta = new ArrayList();
        for(int i=0; i<ruta.size(); i++){
            nuevaRuta.add(ruta.get(i));
        }
        return nuevaRuta;
    }
    
    /*Metodo que retorna un valor booleano buscando si la coordenada a buscar
    ya se encuentra en la ruto del nodo padre*/
    public boolean evitaDevolverse(Nodo nodo, Point coordenada){
        for(int i=0; i<nodo.getRuta().size(); i++){
            Point punto = nodo.getRuta().get(i).getCoordenada();
            if(coordenada.x==punto.x && coordenada.y==punto.y){
                return false;
            }
        }
        return true;
    }
    
    /*Metodo que retorna el costo de la coordenada entrante segun el ambiente*/
    public int asignarCostos(Point coordenada){
        int tipoNodo = ambiente.get(coordenada.x).get(coordenada.y);
        int costo = 0;
        
        if(tipoNodo==2){
            costo = 1;
        }
        if(tipoNodo==3){
            costo = 3;
        }
        if(tipoNodo==4){
            costo = 4;
        }
        if(tipoNodo==5){
            costo = 6;
        }
        if(tipoNodo==6){
            costo = 5;
        }
        if(tipoNodo==7){
            costo = 1;
        }
        
        return costo;
    }
    
    /*Metodo que retorno el valor de la energia del robot segun se mueva o se
    ponga en un espacio de recarga*/
    public int cargaRobot(Point coordenada, Nodo nodo){
        int tipoNodo = ambiente.get(coordenada.x).get(coordenada.y);
        int recargaRobot = 0;
        if(tipoNodo==6){
            recargaRobot = 6;
        }
        else{
            recargaRobot = nodo.getEnergiaRobot()-1;
        }
        
        return recargaRobot;
    }
    
    /*Metodo que retorna el tipo de nodo segun el ambiente*/
    public int tipoNodo(Nodo nodo){
        int tipo = 0;
        tipo = ambiente.get(nodo.getCoordenada().x).get(nodo.getCoordenada().y);
        
        return tipo;
    }
    
    /*Metodo que retorna un arreglo de nodos que equivalen al arbol generado*/
    public ArrayList<Nodo> arbolExpandido(){
        return arbol;
    }
    
    /*Metodo que retorna el valor de los nodos creados*/
    public int getNodosCreados() {
        return nodosCreados;
    }
    
    /*Metodo que retorna el valor de los nodos expandidos */
    public int getNodosExpandidos(){
        return nodosExpandidos;
    }
}