package Interfaz;
import Logic.Nodo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

/*Clase que se encarga de crear un panel donde se dibujara el arbol*/
public class NuevoCanvas extends JPanel implements ActionListener{
    private int tamaño = 20;
    private int margen = 50;
    private ArrayList<Nodo> arbol;
    private ArrayList<Point> posicionNodos;
    private ArrayList<Point> posicionAristas[];
    private int recorrido[];
    private int indiceRecorrido = 0;
    private ArrayList<JButton> botones;
    private Point puntoInteres;
    
    /*Constructor de la clase, le entrar el arbol, las posiciones de nodos, las aristas y el recorrido*/
    public NuevoCanvas(ArrayList<Nodo> arbol, ArrayList<Point> nodos, ArrayList<Point> aristas[],int recorrido[])
    {
        this.posicionNodos = nodos;
        this.posicionAristas = aristas;
        this.arbol = arbol;
        this.setLayout(null);
        this.recorrido = recorrido;
        añadirBotones();
        pintarRecorrido();      
    }
    
    /*Metodo que crea y añade los botones que representan los nodos del arbol*/
    private void añadirBotones(){
                 
        botones = new ArrayList();
        
        for(int i=0; i<posicionNodos.size();i++){            
            
            Nodo nodo = arbol.get(i);
            Point punto = posicionNodos.get(i);
            JButton boton = new JButton();
            
            int x = punto.x-(tamaño/2);
            int y = margen+punto.y-(tamaño/2);
            boton.setBounds(x,y ,tamaño, tamaño);            
            boton.addActionListener(this);            
            boton.setToolTipText(generarTexto(nodo));
            boton.setBackground(Color.BLACK);
            botones.add(boton);
                
            
            JLabel label = new JLabel("("+nodo.getCoordenada().x+","+nodo.getCoordenada().y+")");
            label.setBounds(x,y+tamaño,tamaño*2, tamaño);   

            this.add(boton);
            this.add(label);
        }   
    }
    
    /*Metodo que se encarga de pintar el recorrido del arbol segun el paso a paso*/
    public void pintarRecorrido(){    
        
        for(int i=0; i<posicionNodos.size();i++){  
            
            botones.get(i).setBackground(Color.BLACK); 
            
            Nodo nodo = arbol.get(i);
            
            if(nodo.getProfundidad() <= indiceRecorrido){
                if(i==recorrido[nodo.getProfundidad()]) {
                    
                    botones.get(i).setBackground(Color.RED);
                    puntoInteres = botones.get(i).getLocation();
                }
            }        
        }
    }
    
    /*Metodo que se encarga de generar el texto de infomacion de cada nodo*/
    public String generarTexto(Nodo nodo){
        String texto = "";
        texto+="<html>";
        texto+= "Coordenada: ("+nodo.getCoordenada().x+","+nodo.getCoordenada().y+") <br>";
        texto+= "Costo: "+nodo.getCosto()+" <br>";        
        texto+= "Energia: "+nodo.getEnergiaRobot()+"<br>";
        texto+= "Indice: "+nodo.getIndice()+" <br> ";
        texto+= "IndicePadre: "+nodo.getIndicePadre()+" <br> ";
        texto+= "Profundidad: "+nodo.getProfundidad()+" <br> ";
        texto+="</html>";
        
        return texto;
    }
    
    /*Metodo que se encarga de pintar el arbol con los botonos y sus respectivas aristas*/
    @Override
    public void paint(Graphics g){ 
        
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
        for(int i=0; i<posicionAristas[0].size();i++){
              
           int puntoAX = posicionAristas[0].get(i).x;
           int puntoAY = margen+posicionAristas[0].get(i).y;
           int puntoBX = posicionAristas[1].get(i).x;
           int puntoBY = margen+posicionAristas[1].get(i).y;
           
           g2.setColor(Color.BLACK);
            
           for(int j=0; j<indiceRecorrido+1;j++){     
                if(i+1==recorrido[j]){                  
                     g2.setColor(Color.RED);
                } 
           }
           g2.drawLine(puntoAX, puntoAY,puntoBX,puntoBY);
        }
    }

    /*Metodo heredado del Action Listener*/
    @Override
    public void actionPerformed(ActionEvent e) {}
    
    /*Metodo que se encarga de aumentar el indice del recorrido*/
    public void agregarIndiceRecorrido(){
        indiceRecorrido++;
    }
    
    /*Metodo que se encarga de disminuir el indice del recorrido*/
    public void restarIndiceRecorrido(){
        indiceRecorrido--;
    }
    
    /*Metodo que retorna el punto al que se esta refiriendo en el paso a paso*/
    public Point getPuntoInteres(){
        return puntoInteres;
    }
}