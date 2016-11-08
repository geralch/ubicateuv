package Interfaz;
import java.awt.*;
import javax.swing.*;

/*Clase que muestra la ventana de informacion acerca del proyecto*/ 
public class VentanaAcercaDe extends JFrame{
    
    /*Metodo que se encarga de acomodar y mostrar los componentes de la ventana*/
    public void mostrarVentana(){
        Container contenedor = this.getContentPane();
        contenedor.setLayout(new FlowLayout());
        
        JPanel acercaDe = new JPanel(new BorderLayout());
        acercaDe.setBorder(BorderFactory.createTitledBorder("")); 
        Dimension dimension3 = new Dimension();
        dimension3.setSize(160, 100);
        acercaDe.setPreferredSize(dimension3);
        String info =    "Proyecto Practico\n"
                       + "Inteligencia Artificial\n"
                       + "Geraldine Caicedo Hidalgo\n"
                       + "1527691 - 3743\n"
                       + "03/Mayo/2015";
        JTextArea datosAD = new JTextArea(info);
        datosAD.setBackground(null);
        datosAD.setEditable(false);
        acercaDe.add(datosAD);
        
        JPanel pImagen = new JPanel(new FlowLayout());
        Dimension dimension4 = new Dimension();
        dimension4.setSize(100, 100);
        pImagen.setPreferredSize(dimension4);
        JButton bImagen = new JButton();
        
        ImageIcon icono = new ImageIcon(getClass().getResource("/Imagenes/acerDe.png"));
        bImagen.setIcon(icono);
        bImagen.setBorder(null);
        pImagen.add(bImagen);      
        
        contenedor.add(pImagen);
        contenedor.add(acercaDe);   
        
        this.setTitle(".: Acerca De :.");
        setIconImage (new ImageIcon(getClass().getResource("/Imagenes/robotIcon.png")).getImage());
        this.setLocation(500,200);
        this.setSize(300,150);
        this.setVisible(true);
    }
}
