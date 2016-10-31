package es.uca.webservices.analisis.design;

import java.awt.FlowLayout;

import javax.swing.JFrame;

public class Main {
	
	//Constructor por recomendación de Sonar
	private Main() {}
	
	//Función principal
	public static void main(String[] args) {
		WindowFileSelector ventana = new WindowFileSelector();
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setSize(500,150);
		ventana.setLocationRelativeTo(null); //Centro el frame
		ventana.setVisible(true); //Se hace visible
	}
}
