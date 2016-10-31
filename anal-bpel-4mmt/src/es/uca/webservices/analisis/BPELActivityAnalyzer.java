package es.uca.webservices.analisis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.io.File;
import java.io.IOException;

import javax.wsdl.WSDLException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.xmlbeans.XmlException;
import org.oasisOpen.docs.wsbpel.x20.process.executable.TActivity;
import org.oasisOpen.docs.wsbpel.x20.process.executable.TVariable;
import org.xml.sax.SAXException;

import es.uca.webservices.testgen.api.parsers.ParserException;
import es.uca.webservices.testgen.autoseed.source.BPELSource;
import es.uca.webservices.analisis.io.*;
import es.uca.webservices.bpel.InvalidProcessException;

public class BPELActivityAnalyzer {
	
	//BPEL Process
	private BPELSource bpel;
	
	//Paths
	private String readPath;
	
	//Writer
	private BPELWriter writer;
	
	//Reader
	private BPELReader reader;
	
	//Others
	private String compositionName;
	private boolean validComposition;
	
	//Constructor que prepara todo para la ejecución
	public BPELActivityAnalyzer(String readPath) {
		if(readPath.contains(".bpel")) {			
			//Comprobamos si es un bpel
			validComposition = true;
			
			//Abre la composicion
			this.readPath = readPath;
			try {
				bpel = new BPELSource(this.readPath);
			} catch (XPathExpressionException | XmlException | IOException | ParserConfigurationException | SAXException
					| InvalidProcessException | WSDLException e) {
				throw new RuntimeException(e);
			}
			compositionName = bpel.getModel().getName();
						
			//Se inicializa la impresora
			writer = new BPELWriter(compositionName);
			reader = new BPELReader(writer);
		} else {
			
			//No es valido
			validComposition = false;
		}
	}
	
	//Obtiene fichero de salida
	public File getOutput() {
		return writer.getNormalOutput();
	}
	
	public File getJSONOutput() {
		return writer.getJSONOutput();
	}
	
	public BPELWriter getWriter() {
		return writer;
	}
	
	//Lee las actividades
	public String readActivities() {
		//Solo se ejecuta si la composicion es valida
		if (validComposition) {
			List<TActivity> activities = bpel.getActivities();
						
			for(TActivity activity : activities) {
				reader.read(activity);
			}
			
			return "All activities have been loaded.";
		} else return "BPEL Composition not found.";
	}
	
	//Lee las variables
	public String readVariables() {
		
		if(validComposition) {
			try {
				Set<TVariable> vSet = bpel.getVariables();
				List<TVariable> variables = new ArrayList<>();
				
				variables.addAll(vSet);
				
				for(TVariable variable : variables) {
					reader.read(variable);
				}
				
				return "All variables have been loaded";
				
			} catch (ParserException | SAXException | IOException | ParserConfigurationException e) {
				throw new RuntimeException(e);
			}
		} else return "BPEL Composition not found.";
	}
}
