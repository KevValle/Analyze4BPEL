package es.uca.webservices.analisis;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import es.uca.webservices.analisis.io.BPELWriter;

public class BPTSAnalyzer {
	private File testCases;
	private boolean validBPTS;
	private String compositionName;
	private BPELWriter writer;
	
	private Map<String, String> properties;
	
	/**
	 * Constructor principal, solo necesita recibir la ruta del BPTS
	 * @param ruta
	 */
	public BPTSAnalyzer(String ruta) {
		if(ruta.contains(".bpts")) {
			validBPTS = true;
			testCases = new File(ruta);
			compositionName = testCases.getName().substring(0, testCases.getName().length() - 5);
			
			writer = new BPELWriter(compositionName + "_testCases");
			properties = new LinkedHashMap<>();
		} else {
			validBPTS = false;
		}
	}
	
	public String getJSONoutput() {
		return System.getProperty("user.home") + "/metamorphic/json/" + compositionName + "_testCases.json";
	}
	
	/**
	 * Recorremos el árbol XML por casos de prueba
	 */
	public String readNodes() {
		//Solo se ejecuta si el fichero es válido
		if (validBPTS) {
			
			try {
				//Volcamos en memoria el fichero de casos de prueba para trabajar con él
				DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
				DocumentBuilder dB = dBF.newDocumentBuilder();
				Document doc = dB.parse(testCases);
				
				//Obtenemos todos los nodos correspondientes con los casos de prueba
				NodeList testCaseNodes = doc.getElementsByTagName("tes:testCase");
				
				explore(testCaseNodes);
				
			} catch (SAXException | IOException | ParserConfigurationException e) {
				throw new RuntimeException(e);
			}
			
			
			return "All test cases have been loaded.";
		} else return "BPTS File not found.";
	}
	
	/**
	 * Recorremos todos los nodos recibidos, así como sus descendientes.
	 * @param brotherNodes
	 */
	private void explore(NodeList brotherNodes) {
		//Recorremos todos los nodos hermanos recibidos
		for(int i = 0; i < brotherNodes.getLength(); i++) {
			//Obtenemos el nodo concreto y sus datos
			Node element = brotherNodes.item(i);
			String elementName = element.getNodeName();
			
			//Utilizamos la etiqueta de casos de prueba como bandera
			if("tes:testCase".equals(elementName)) {
				writer.writeProperties(properties);
			    properties.clear();
			}
			

			//Mostramos el elemento con su valor
			if(element.getNodeType() == Node.TEXT_NODE &&
					!element.getTextContent().contains("  ")) {
				
				//Dado que el valor es un elemento hoja y no tenemos manera de saber
				//cuando termina. Volvemos a consultar el nombre de su progenitor.
				if(properties.containsKey(element.getParentNode().getNodeName())) {
					properties.put(element.getParentNode().getNodeName() + "_" + properties.size(), element.getTextContent());
				} else properties.put(element.getParentNode().getNodeName(), element.getTextContent());
			}
			
			
			//Si tiene descendientes, se explorarán
			NodeList childNodes = element.getChildNodes();
			
			if(childNodes != null) {
				explore(childNodes);
			}
			
		}
		
		
	}
	
	
}
