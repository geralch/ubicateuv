package Interfaz;
import Control.*;
import Logic.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/*Clase que se encarga de mostrar la ventana principal del proyecto*/
public class VentanaPrincipal extends JFrame implements ActionListener {
    
    private ArrayList<ArrayList<Integer>> ambiente;
    private ArrayList<ArrayList<ArrayList<Integer>>> estadosMapa;
    private Nodo ultimoNodo;
    private int tipoHeuristica;
    private int tamanoMapa;
    private int nodosExpandidos;
    private int nodosCreados;
    private double costoTotal;
    private double factorRamifi;
    private int costoActual;
    private int estadoActual;
    private Point puntoActual;
    private int energiaActual;
    private boolean boolParar = false;
    private Container contenedor;
    private JMenuItem cargarArchivo;
    private JMenuItem busquedaNoInformada;
    private JMenuItem busquedaInformadaH1;
    private JMenuItem busquedaInformadaH2;
    private JMenuItem acercaDe;
    private JPanel panelArbol;
    private JPanel panelMapa;
    private JPanel panelDatos;
    private JButton[][] bMapa;
    private JTextField tfTipoBusqueda;
    private JTextField tfNodosExpandidos;
    private JTextField tfNodosCreados;
    private JTextField tfCostoTotal;
    private JTextField tfFactorRamifi;
    private JCheckBox bParar;
    private JTextField tfCostoActual ;
    private JTextField tfPosicionActual;
    private JTextField tfEnergiaActual;
    private JButton botonSiguiente;
    private JButton botonAtras;
    private JScrollPane scrollPane;
    private NuevoCanvas canvas;
    private ControladorBusquedas controladorBusqueda;
    
    /*Constructor de la clase*/
    public VentanaPrincipal(){
          ambiente = new ArrayList();
          costoActual = 0;
          puntoActual = new Point(0,0);
          estadoActual = 0;
          boolParar=false;
          
    }
    
    /*Metodo que se encarga de mostrar la ventana y organizar sus componentes*/
    public void mostrarVentana(){
        this.setTitle(".: Ubicame UV :.");
        setIconImage (new ImageIcon(getClass().getResource("/Imagenes/robotIcon.png")).getImage());
        this.setSize(1180,580); 
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); 
        
        contenedor = this.getContentPane();
        contenedor.setLayout(new FlowLayout());
       
        crearMenu();
        espacioArbol();
        espacioMapa();
        espacioDatos();
        
        this.setVisible(true);
    }
    
    /*Metodo que se encarga de crear el menu de la ventana*/
    private void crearMenu(){
        JMenuBar menu_opciones = new JMenuBar();
        
        JMenu menuArchivo = new JMenu("Archivo");
        cargarArchivo = new JMenuItem("CargarArchivo");
        cargarArchivo.addActionListener(this);
        menuArchivo.add(cargarArchivo);
        
        JMenu menuBusqueda = new JMenu("Busqueda");
        busquedaNoInformada = new JMenuItem("Busqueda por Costo Uniforme");
        busquedaNoInformada.setEnabled(false);
        busquedaNoInformada.addActionListener(this);
        busquedaInformadaH1 = new JMenuItem("Busqueda A* - Heuristica 1");
        busquedaInformadaH1.setEnabled(false);
        busquedaInformadaH1.addActionListener(this);
        busquedaInformadaH2 = new JMenuItem("Busqueda A* - Heuristica 2");
        busquedaInformadaH2.setEnabled(false);
        busquedaInformadaH2.addActionListener(this);
        
        menuBusqueda.add(busquedaNoInformada);
        menuBusqueda.add(busquedaInformadaH1);
        menuBusqueda.add(busquedaInformadaH2);
        
        JMenu menuAyuda = new JMenu("Ayuda");
        acercaDe = new JMenuItem("Acerca De");
        acercaDe.addActionListener(this);
        menuAyuda.add(acercaDe);
                
        menu_opciones.add(menuArchivo);
        menu_opciones.add(menuBusqueda);
        menu_opciones.add(menuAyuda);
        
        setJMenuBar(menu_opciones);
    }
    
    /*Metodo que se encarga de crear el componente del arbol en la ventana*/
    private void espacioArbol(){
        panelArbol = new JPanel(new BorderLayout());
        panelArbol.setBorder(BorderFactory.createTitledBorder("Arbol")); 
        Dimension dimension = new Dimension();
        dimension.setSize(480, 480);
        panelArbol.setPreferredSize(dimension);
        contenedor.add(panelArbol);        
        
    }
    
    /*Metodo que se encarga de crear el componente del mapa en la ventana*/
    private void espacioMapa(){
        panelMapa = new JPanel(new FlowLayout());
        panelMapa.setBorder(BorderFactory.createTitledBorder("Mapa"));
        Dimension dimension = new Dimension();
        dimension.setSize(480, 480);
        panelMapa.setPreferredSize(dimension);
        contenedor.add(panelMapa);
    }
    
    /*Metodo que retorna el tipo de casilla de un elemento del mapa segun el ambiente*/
    private int buscarImagen(int x, int y, ArrayList<ArrayList<Integer>> ab){
        int tipoCasilla = ab.get(x).get(y);        
        return tipoCasilla;
    }
    
    /*Metodo que se encarga de crear el mapa y ponerlo en la ventana con las imagenes de los elementos*/
    private void crearMapa(ArrayList<ArrayList<Integer>> ab){
        tamanoMapa = ab.size();
        JPanel pMapa = new JPanel(new GridLayout(tamanoMapa,tamanoMapa,10,10));
        bMapa = new JButton [tamanoMapa][tamanoMapa];
        
        for(int i=0; i<tamanoMapa; i++){
            for(int j=0; j<tamanoMapa; j++){
                int casilla = buscarImagen(i,j,ab);
                bMapa[i][j] = new JButton();
                ImageIcon icono = new ImageIcon(getClass().getResource("/Imagenes/imagen"+casilla+".png"));
                bMapa[i][j].setIcon(icono);
                bMapa[i][j].setBorder(null);
                bMapa[i][j].setBackground(Color.white);
                pMapa.add(bMapa[i][j]);  
            }
        }
        panelMapa.removeAll();
        panelMapa.add(pMapa);
        panelMapa.updateUI();
    }
    
    /*Metodo que actualiza las imagenes del mapa*/
     private void actualizarMapa(ArrayList<ArrayList<Integer>> ab){
        tamanoMapa = ab.size();
        
        for(int i=0; i<tamanoMapa; i++){
            for(int j=0; j<tamanoMapa; j++){
                int casilla = buscarImagen(i,j,ab);
                ImageIcon icono = new ImageIcon(getClass().getResource("/Imagenes/imagen"+casilla+".png"));
                bMapa[i][j].setIcon(icono);
                bMapa[i][j].setBorder(null);
                bMapa[i][j].setBackground(Color.white);
            }
        }
    }
    
    /*Metodo que se encarga de configurar la muestra de datos*/
    private void espacioDatos(){
        panelDatos = new JPanel();
        panelDatos.setLayout(new BorderLayout());
        
        JPanel infoBusqueda = new JPanel(new GridLayout(11,1,0,0));
        infoBusqueda.setBorder(BorderFactory.createTitledBorder("Información Busqueda"));
                
        tfTipoBusqueda = new JTextField("");
        tfTipoBusqueda.setEditable(false);
        tfNodosExpandidos = new JTextField("");
        tfNodosExpandidos.setEditable(false);
        tfNodosCreados = new JTextField("");
        tfNodosCreados.setEditable(false);
        tfCostoTotal = new JTextField("");
        tfCostoTotal.setEditable(false);
        tfFactorRamifi = new JTextField("");
        tfFactorRamifi.setEditable(false);
        
        bParar = new JCheckBox("Parar si se acaba la energia",true);
        bParar.addActionListener(this);
        bParar.setSelected(false);
        bParar.setEnabled(false);
        
        infoBusqueda.add(new JLabel("Busqueda: "),0,0);
        infoBusqueda.add(tfTipoBusqueda,0,1);
        infoBusqueda.add(new JLabel("Nodos Expandidos: "),0,2);
        infoBusqueda.add(tfNodosExpandidos,0,3);
        infoBusqueda.add(new JLabel("Nodos Creados: "),0,4);
        infoBusqueda.add(tfNodosCreados,0,5);
        infoBusqueda.add(new JLabel("Costo Total Solucion:      "),0,6);
        infoBusqueda.add(tfCostoTotal,0,7);
        infoBusqueda.add(new JLabel("Factor Ramificacion: "),0,8);
        infoBusqueda.add(tfFactorRamifi,0,9);
        infoBusqueda.add(bParar,0,10);
        
        JPanel infoRecorrido = new JPanel(new GridLayout(7,1,0,0));
        infoRecorrido.setBorder(BorderFactory.createTitledBorder("Información Recorrido"));
        
        tfCostoActual = new JTextField("");
        tfCostoActual.setEditable(false);
        tfPosicionActual  = new JTextField("");
        tfPosicionActual.setEditable(false);
        tfEnergiaActual = new JTextField("");
        tfEnergiaActual.setEditable(false);
        
        infoRecorrido.add(new JLabel("Costo Actual: "),0,0);
        infoRecorrido.add(tfCostoActual,0,1);
        infoRecorrido.add(new JLabel("Posicion Actual: "),0,2);
        infoRecorrido.add(tfPosicionActual,0,3);
        infoRecorrido.add(new JLabel("Energia Actual: "),0,4);
        infoRecorrido.add(tfEnergiaActual,0,5);
        
        JPanel botonesRecorrido = new JPanel(new FlowLayout());
        botonesRecorrido.setBorder(BorderFactory.createTitledBorder("Movimientos"));
        
        botonSiguiente = new JButton();
        ImageIcon iconoSiguiente = new ImageIcon(getClass().getResource("/Imagenes/siguiente.png"));
        botonSiguiente.setIcon(iconoSiguiente);
        botonSiguiente.addActionListener(this);
        botonSiguiente.setBorder(null);
        botonSiguiente.setBackground(null);
        botonSiguiente.setEnabled(false);
        
        botonAtras = new JButton();
        ImageIcon iconoAtras = new ImageIcon(getClass().getResource("/Imagenes/atras.png"));
        botonAtras.setIcon(iconoAtras);
        botonAtras.addActionListener(this);
        botonAtras.setBorder(null);
        botonAtras.setBackground(null);
        botonAtras.setEnabled(false);
        
        botonesRecorrido.add(botonAtras);
        botonesRecorrido.add(botonSiguiente);
                       
        panelDatos.add(infoBusqueda, BorderLayout.NORTH);
        panelDatos.add(infoRecorrido, BorderLayout.CENTER);
        panelDatos.add(botonesRecorrido, BorderLayout.SOUTH);
        contenedor.add(panelDatos);
    }
    
    /*Metodo que se encarga de cargar el archivo con los datos del ambiente*/
    private void cargarArchivo(){
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            String archivoCargado = chooser.getSelectedFile().getAbsolutePath();

            ManejadorArchivo manejadorArchivo = new ManejadorArchivo();
            ambiente = manejadorArchivo.leerArchivo(archivoCargado);
            busquedaNoInformada.setEnabled(true);
            busquedaInformadaH1.setEnabled(true);
            busquedaInformadaH2.setEnabled(true);
            crearMapa(ambiente);
            estadoActual=0;
            bParar.setEnabled(true);
            tfCostoActual.setText("");
            tfPosicionActual.setText("");
            tfEnergiaActual.setText("");
            tfTipoBusqueda.setText("");
            tfNodosExpandidos.setText("");
            tfNodosCreados.setText("");
            tfCostoTotal.setText("");
            tfFactorRamifi.setText("");
            botonSiguiente.setEnabled(false);
            botonAtras.setEnabled(false);
            panelArbol.removeAll();
            panelArbol.updateUI();
        }
    }
    
    /*Metodo que se encarga de dibujar el arbol segun la busqueda seleccionada*/
    private void dibujarArbol(ArrayList<Nodo> arbol){
        DibujaArbol dibujo = new DibujaArbol(arbol, ultimoNodo);
        
        ArrayList<Point> posicionNodos = dibujo.getPosicionNodos();        
        ArrayList<Point> posicionAristas[] = dibujo.getAristas();
        int recorrido[] = dibujo.getRecorrido();
        arbol = dibujo.getArbolOrdenado();
        
        canvas = new NuevoCanvas(arbol,posicionNodos,posicionAristas,recorrido);
        canvas.setPreferredSize(new Dimension(dibujo.anchoCanvas, dibujo.altoCanvas));
        scrollPane = new JScrollPane(canvas);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        panelArbol.removeAll();
        panelArbol.add(scrollPane,GridBagConstraints.RELATIVE);
        panelArbol.updateUI();
        
        centrarNodo();
    }
    
    /*Metodo que se encarga de centrar en el panel del arbol el nodo actual del recorrido*/
    private void centrarNodo(){
        
        canvas.pintarRecorrido();
        canvas.repaint();
        
        int x = canvas.getPuntoInteres().x-(panelArbol.getSize().width/2);
        int y = canvas.getPuntoInteres().y-(panelArbol.getSize().height/2);
        
        if(y<0) y=0;
        if(x<0) x=0;
        
        Point puntoEnfoncado = new Point(x,y);
        scrollPane.getViewport().setViewPosition(puntoEnfoncado);
    }
    
    /*Metodo que se encarga de actualiza los datos de informacion segun la busqueda elegida*/
    private void actualizarDatos(String nombreHeuristica){
        tfTipoBusqueda.setText(nombreHeuristica);
        nodosExpandidos = controladorBusqueda.getNodosExpandidos();
        tfNodosExpandidos.setText(nodosExpandidos+"");
        nodosCreados = controladorBusqueda.getNodosCreados();
        tfNodosCreados.setText(nodosCreados+"");
        costoTotal = controladorBusqueda.getCostoTotal();
        tfCostoTotal.setText(costoTotal+"");
        factorRamifi = controladorBusqueda.getFactorRamific();
        tfFactorRamifi.setText(factorRamifi+"");        
        botonSiguiente.setEnabled(true);
        tfCostoActual.setText("");
        tfPosicionActual.setText("");
        tfEnergiaActual.setText("");
        
        crearEstadosMapa();
    }
    
    /*Metodo que se encarga de usar el controlador de busquedas para acceder a las busqueda no informada*/
    private void busquedaNoInformada(){
        
        controladorBusqueda = new ControladorBusquedas(ambiente,tipoHeuristica,boolParar);
        controladorBusqueda.busquedaNoInformada();
        ArrayList<Nodo> arbol = controladorBusqueda.getArbol();
        ultimoNodo = controladorBusqueda.getUltimoNodo();
        
        actualizarDatos("Costo Uniforme");
        dibujarArbol(arbol);
        actualizarMapa(ambiente);
        estadoActual=0;
    }
    
    /*Metodo que se encarga de usar el controlador de busquedas para acceder a las busqueda informada*/
    private void busquedaInformada(String nombreHeuristica){
        controladorBusqueda = new ControladorBusquedas(ambiente,tipoHeuristica,boolParar);
        controladorBusqueda.busquedaInformada();
        ArrayList<Nodo> arbol = controladorBusqueda.getArbol();
        ultimoNodo = controladorBusqueda.getUltimoNodo();
        
        actualizarDatos(nombreHeuristica);
        dibujarArbol(arbol);
        actualizarMapa(ambiente);
        estadoActual=0;
    }
    
    /*Metodo que crea los estados delmapa segun la ruta que se encontro en la busqueda elegida*/
    private void crearEstadosMapa(){
        estadosMapa = new ArrayList();
        ArrayList<ArrayList<Integer>> mapaCambiado = ambiente;
        estadosMapa.add(mapaCambiado);
        ArrayList<PuntoRuta> ruta = ultimoNodo.getRuta();
        int nix = ruta.get(0).getCoordenada().x;
        int niy = ruta.get(0).getCoordenada().y;
        
        for(int i=1; i<ruta.size(); i++){
            int x = ruta.get(i).getCoordenada().x;
            int y = ruta.get(i).getCoordenada().y; 
            mapaCambiado = new ArrayList();
            for(int j=0; j<tamanoMapa; j++){
                ArrayList<Integer> fila = new ArrayList();
                for(int k=0; k<tamanoMapa; k++){
                    if(j==x && k==y){
                        fila.add(0);
                    }
                    else if(j==nix && k==niy){
                        fila.add(8);
                    }
                    else{
                        fila.add(ambiente.get(j).get(k));
                    }
                }
                mapaCambiado.add(fila);
            }
            estadosMapa.add(mapaCambiado);
        }
    }
    
    /*Metodo que se encarga de actualuzar la informacion que se muestra segun el paso del recorrido de la ruta*/
    private void actualizarInformacionRecorrido(int estadoActual){
        PuntoRuta punto = ultimoNodo.getRuta().get(estadoActual);
        String costoActual = punto.getCosto()+"";
        String posicion = "("+punto.getCoordenada().x+","+punto.getCoordenada().y+")";
        String energia = punto.getEnergiaRobot()+"";
        tfCostoActual.setText(costoActual);
        tfPosicionActual.setText(posicion);
        tfEnergiaActual.setText(energia);
    }
    
    /*MEtodo heredado de Action Listener que establece las acciones que se hace 
     al hacer click en algunos elementos de la pantalla*/
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==cargarArchivo){
            cargarArchivo();
        }
        
        if(e.getSource()==busquedaNoInformada){
            busquedaNoInformada();
        }
        
        if(e.getSource()==busquedaInformadaH1){
            tipoHeuristica = 1;
            busquedaInformada("A* - H1");
        }
        
        if(e.getSource()==busquedaInformadaH2){
            tipoHeuristica = 2;
            busquedaInformada("A* - H2");
        }
        
        if(e.getSource()==acercaDe){
            VentanaAcercaDe acercaDe = new VentanaAcercaDe();
            acercaDe.mostrarVentana();
        }
             
        if (e.getSource()==bParar) {
            boolParar = bParar.isSelected();
        } 
        
        if(e.getSource()==botonSiguiente){
            
            estadoActual++;
            canvas.agregarIndiceRecorrido();            
            centrarNodo();
            if(estadoActual<estadosMapa.size()){
                actualizarMapa(estadosMapa.get(estadoActual));
                actualizarInformacionRecorrido(estadoActual);
                if(estadoActual==estadosMapa.size()-1){
                    botonSiguiente.setEnabled(false);
                }                    
            }
            else{
                botonSiguiente.setEnabled(false);
            }
            botonAtras.setEnabled(true);
        }
        
        if(e.getSource()==botonAtras){
            
            estadoActual--;
            canvas.restarIndiceRecorrido();
            centrarNodo();
            if(estadoActual>=0){
                actualizarMapa(estadosMapa.get(estadoActual));
                actualizarInformacionRecorrido(estadoActual);
                if(estadoActual==0){
                    botonAtras.setEnabled(false);
                }
            }
            else {
                botonAtras.setEnabled(false);
            }
            botonSiguiente.setEnabled(true);
        }
    }
    
    /*Metodo que retorna un arreglo de arreglos de enteros que componen el ambiente*/
    public ArrayList<ArrayList<Integer>> getAmbiente() {
        return ambiente;
    }
    
    /*Metodo main que inicializa la aplicacion*/
    public static void main(String args[]){
        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
        ventanaPrincipal.mostrarVentana();
    }
}