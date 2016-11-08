package Logic;
import java.awt.*;
import java.util.*;

/*Clase que se encarga de realizar la busqueda informada con A* segun dos heuristica diferentes*/
public class BusquedaInformada {
    private Comparator<Nodo> comparador = new ComparadorCostos();
    private PriorityQueue<Nodo> cola = new PriorityQueue(10, comparador);
    private ArrayList<ArrayList<Integer>> ambiente = new ArrayList();
    private ArrayList<Point> nodosRecarga = new ArrayList();
    private ArrayList<Nodo> arbol = new ArrayList();
    private Point coordenadaMeta = new Point();
    private int tipoHeuristica = 0;
    private int nodosExpandidos = 0;
    private int nodosCreados = 0;
    private boolean boolParar = false;
    
    /*Constructor de la clase*/
    public BusquedaInformada(ArrayList<ArrayList<Integer>> ambiente, int heuristica, boolean boolParar){
        this.ambiente = ambiente;
        this.tipoHeuristica = heuristica;
        this.boolParar = boolParar;
    }
    
    /*Metodo que retorna un nodo que representa el ultimo nodo de la ruta elegida por la busqueda*/
    public Nodo busqueda(){
        Nodo nodoInicial = nodoInicial();
        nodoMeta();
        nodosRecarga();
        cola.add(nodoInicial);
        arbol.add(nodoInicial);
        Nodo nodoNuevo = nodoInicial;
        Nodo nodoFinal = nodoInicial();
        boolean encontrado = false;
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
    
    /*Metodo que se encarga de establecer cual es la coordenada del nodo meta*/
    public void nodoMeta(){
        for(int i=0; i<ambiente.size(); i++){
            for(int j=0; j<ambiente.size(); j++){
                if(ambiente.get(i).get(j)==7){
                    coordenadaMeta = new Point(i,j);
                }
            }
        }
    }
    
    /*Metodo que se encarga de establecer las coordenadas de los nodos de recarga en el ambiente*/
    public void nodosRecarga(){
        for(int i=0; i<ambiente.size(); i++){
            for(int j=0; j<ambiente.size(); j++){
                if(ambiente.get(i).get(j)==6){
                    nodosRecarga.add(new Point(i,j));
                }
            }
        }      
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
                    int costoCoordenada = asignarCostos(coordenada);
                    int energiaRobot = cargaRobot(coordenada, nodoPadre);
                    double acumuladoCosto = nodoPadre.getAcumuladoCosto()+costoCoordenada; 
                    double costoNodo = acumuladoCosto+elegirHeuristica(coordenada,costoCoordenada,energiaRobot,ruta);
                    int indicePadre = nodoPadre.getIndice();
                    int indice = arbol.size();
                    int profundidad = nodoPadre.getProfundidad()+1;
                    ruta.add(new PuntoRuta(costoNodo,coordenada,energiaRobot));
                    Nodo nodoHijo = new Nodo(costoNodo,acumuladoCosto,coordenada,energiaRobot,ruta,indice,indicePadre,profundidad);
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
    
    /*Metodo que retorna el valor de la heuristica elegida*/
    public double elegirHeuristica(Point coordenada, int costoCoordenada, int energiaRobot, ArrayList<PuntoRuta> ruta){
        double heuristicaElegida = 0;
        if(tipoHeuristica==1){
            heuristicaElegida = primeraHeuristica(coordenada);
        }
        if(tipoHeuristica==2){
            heuristicaElegida = segundaHeuristica(coordenada,costoCoordenada,energiaRobot,ruta);
        }        
        return heuristicaElegida;
    }
    
    /*Metodo que retorna el valor manhatan desde un punto cualquiera a una meta*/
    public double valorManhatan(Point coordenada, Point meta){
        double valor = Math.abs(coordenada.x-meta.x)+Math.abs(coordenada.y-meta.y);
        
        return valor;
    }
    
    /*Metodo que retorna el valor de la primera heuristica implementada*/
    public double primeraHeuristica(Point coordenada){
        double heuristica = 0;
        heuristica = valorManhatan(coordenada,coordenadaMeta);
        return heuristica;
    }
    
    /*Metodo que retorna la coordenada del punto de recarga mas cercano al punto actual*/
    public Point energiaCercana(Point coordenada, ArrayList<PuntoRuta> ruta){
        
        ArrayList<Point> recargasAceptadas = new ArrayList();
        boolean estaEnRuta = false;
        for(int i=0; i<nodosRecarga.size(); i++){
            estaEnRuta = false;
            Point punto1 = nodosRecarga.get(i);
            for(int j=0; j<ruta.size(); j++){
                Point punto2 = ruta.get(j).getCoordenada();
                if(punto1.x==punto2.x && punto1.y==punto2.y){
                    estaEnRuta = true;
                }
            }
            if(!estaEnRuta){
                recargasAceptadas.add(punto1);
            }
        }
         
        Point energiaCercana = new Point();
        if(recargasAceptadas.size()>1){
            energiaCercana = recargasAceptadas.get(0);
            for(int i=1; i<recargasAceptadas.size(); i++){
                if(valorManhatan(coordenada,recargasAceptadas.get(i))<valorManhatan(coordenada,energiaCercana)){
                    energiaCercana = recargasAceptadas.get(i);
                } 
            }
        }
        else if(recargasAceptadas.size()==1){
            energiaCercana = recargasAceptadas.get(0);
        }
        else if(recargasAceptadas.isEmpty()){
            energiaCercana = coordenada;
        }
        
        
        
         return energiaCercana;
    }
    
    /*Metodo que retorna el valor de la senguda heuristica implementada*/
    public double segundaHeuristica(Point coordenada, int costoCoordenada, int energiaRobot, ArrayList<PuntoRuta> ruta){
        double heuristica = 0;
        int tamanoMapa = ambiente.size();
        double valorManhatanMeta = valorManhatan(coordenada,coordenadaMeta);
        double valorManhatanRecarga = valorManhatan(coordenada,energiaCercana(coordenada,ruta));
        int energiaRestante = 6 - energiaRobot;
        heuristica = (valorManhatanMeta*(valorManhatanRecarga+energiaRestante))/((2*tamanoMapa)+(5/tamanoMapa));
        return heuristica;
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
