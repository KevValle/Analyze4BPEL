package es.uca.webservices.analisis.io;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.oasisOpen.docs.wsbpel.x20.process.executable.TActivity;
import org.oasisOpen.docs.wsbpel.x20.process.executable.TAssign;
import org.oasisOpen.docs.wsbpel.x20.process.executable.TBooleanExpr;
import org.oasisOpen.docs.wsbpel.x20.process.executable.TCopy;
import org.oasisOpen.docs.wsbpel.x20.process.executable.TElseif;
import org.oasisOpen.docs.wsbpel.x20.process.executable.TForEach;
import org.oasisOpen.docs.wsbpel.x20.process.executable.TFrom;
import org.oasisOpen.docs.wsbpel.x20.process.executable.TIf;
import org.oasisOpen.docs.wsbpel.x20.process.executable.TInvoke;
import org.oasisOpen.docs.wsbpel.x20.process.executable.TReceive;
import org.oasisOpen.docs.wsbpel.x20.process.executable.TTo;
import org.oasisOpen.docs.wsbpel.x20.process.executable.TVariable;
import org.oasisOpen.docs.wsbpel.x20.process.executable.TWhile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.InputSource;
import java.io.StringReader;



import es.uca.webservices.testgen.autoseed.source.XMLUtils;

public class BPELReader {
	
	private Map<String,String> property;
	private Map<String,String> fromProperty;
	private Map<String,String> toProperty;
	
	private String type;
	private String name;
	private String condition;
	
	private BPELWriter writer;
	
	private final String activityName;
	private final String sType;
	
	public BPELReader(BPELWriter writer) {
		property = new LinkedHashMap<>();
		fromProperty = new LinkedHashMap<>();
		toProperty = new LinkedHashMap<>();
		
		this.writer = writer;
		activityName = "activityName";
		sType = "type";
	}
	
	//Si hay condicion, se obtiene
	private String getConditionExpression(TBooleanExpr expression) {
		return XMLUtils.getExpression(expression);
	}
	
	//Se va repartiendo según el tipo de actividad que sea
	public void read(TActivity activity) {
		

		
		/*Elementos que aparecen en todos los BPEL -probando- */
		if(activity instanceof TReceive) {
			read((TReceive) activity);
		}
		
		if(activity instanceof TInvoke) {
			read((TInvoke) activity);
		}
		
		//If activity
		if(activity instanceof TIf) {
			read((TIf) activity);
		}
		
		//While activity
		if(activity instanceof TWhile) {
			read((TWhile) activity);
		}
		
		
		//ForEach activity
		if(activity instanceof TForEach) {
			read((TForEach) activity);
		}
		
		//TAssign activity
		if(activity instanceof TAssign) {
			read((TAssign) activity);
		}
		
	}
	
	public void read(TVariable variable) {
		type = "VARIABLE";
		name = variable.getName();
				
		property.put(sType, type);
		property.put(activityName, name);
		
		//Se envia el map al modulo de salida
		writer.writeProperties(property);
						
		//Limpiamos el map
		property.clear();
	}
	
	private void read(TIf activity) {
		type = "IF";
		name = activity.getName();
		
		//Conjunto de atributos correspondientes a IF:
		condition = getConditionExpression(activity.getCondition());
		String existElse;

		if (activity.getElse() != null) {
			existElse = "yes";
		} else existElse = "no";
		
		//Se rellena el map correspondiente

			property.put(sType, type);
			property.put(activityName, name);
			property.put("condition", condition);
			property.put("else", existElse);
				
		//Tratamos todas sus actividades Elseif, si las hubiese
		for(TElseif act : activity.getElseifArray()) {
			read(act);
		}
		
		//Se envia el map al modulo de salida
		writer.writeProperties(property);
				
		//Limpiamos el map
		property.clear();
	}
	
	//Pruebas con elseif
	private void read(TElseif activity) {
		property.put("ElseIF", "ElseIF");
		if(activity.getIf() != null) {
			read(activity.getIf());
		}
	}
	
	private void read(TWhile activity) {
		type = "WHILE";
		name = activity.getName();
		
		//Conjunto de atributos correspondientes a while
		condition = getConditionExpression(activity.getCondition());
		
		//Se rellena el map correspondiente
		property.put(sType, type);
		property.put(activityName, name);
		property.put("condition", condition);
		
		//Se envia el map al modulo de salida
		writer.writeProperties(property);
		
		//Limpiamos el map
		property.clear();
	}
	
	private void read(TForEach activity) {
		type = "FOREACH";
		name = activity.getName();
		
		//Conjunto de atributos correaspondientes a ForEach
		String startCounterValue = XMLUtils.getExpression(activity.getStartCounterValue());
		String finalCounterValue = XMLUtils.getExpression(activity.getFinalCounterValue());
		String pararel = XMLUtils.getExpression(activity.xgetParallel());
		
		if (activity.getCompletionCondition() != null) {
			condition = XMLUtils.getExpression(activity.getCompletionCondition());
		} else condition = "no";
		
		//Se rellena el map correspondiente
		property.put(sType, type);
		property.put(activityName, name);
		property.put("startCounterValue", startCounterValue);
		property.put("finalCounterValue", finalCounterValue);
		property.put("pararel", pararel);
		property.put("completionCondition", condition);
		
		//Se envia el map al modulo de salida
		writer.writeProperties(property);
		
		//Limpiamos el map
		property.clear();
	}
	
	
	private void read(TReceive activity) {
		type = "RECEIVE";
		name = activity.getName();
		
		//Conjunto de atributos correspondientes a Receive
		String partnerLink = activity.getPartnerLink();
		String portType;
		String operation = activity.getOperation();
		String variable;
		String createInstance;
		String messageExchange;
		
		//Relleno las opcionales
		if(activity.getPortType() != null) {
			portType = activity.getPortType().toString();
		}else portType = "null";
		
		if(activity.getVariable() != null) {
			variable = activity.getVariable();
		}else variable = "null";
		
		if(activity.xgetCreateInstance() != null) {
			createInstance = XMLUtils.getExpression(activity.xgetCreateInstance());
		}else createInstance = "no";
		
		if(activity.getMessageExchange() != null) {
			messageExchange = activity.getMessageExchange();
		}else messageExchange = "null";
		
		property.put(sType, type);
		property.put(activityName, name);
		property.put("partnerLink", partnerLink);
		property.put("portType", portType);
		property.put("operation", operation);
		property.put("variable", variable);
		property.put("createInstance", createInstance);
		property.put("messageExchange", messageExchange);
		
		//Se envia el map al modulo de salida
		writer.writeProperties(property);
				
		//Limpiamos el map
		property.clear();
	}
	
	private void read(TInvoke activity) {
		type = "INVOKE";
		name = activity.getName();
		
		//Conjunto de atributos correspondientes a Invoke
		String inputVariable = activity.getInputVariable();
		String outputVariable = activity.getOutputVariable();
		String operation = activity.getOperation();
		String partnerLink = activity.getPartnerLink();
		String portType = activity.getPortType().toString();
		
		property.put(activityName, name);
		property.put(sType, type);
		property.put("inputVariable", inputVariable);
		property.put("outputVariable", outputVariable);
		property.put("operation", operation);
		property.put("partnerLink", partnerLink);
		property.put("portType", portType);
		
		//Se envia el map al modulo de salida
		writer.writeProperties(property);
				
		//Limpiamos el map
		property.clear();
	}
	
	private void read(TAssign activity) {
		type = "ASSIGN";
		name = activity.getName();
		
		property.put(activityName, name);
		property.put(sType, type);
		
		//Vamos a tratar todas las actividades Copy encontradas
		TCopy[] copyArray = activity.getCopyArray();
		
		for(TCopy copyActivity : copyArray) {
			read(copyActivity);
		}

	}
	
	private void read(TCopy activity) {
		read(activity.getFrom());
		read(activity.getTo());
		
		writer.writeAssign(property, fromProperty, toProperty);
		
		property.clear(); 
		fromProperty.clear(); 
		toProperty.clear();
	}
	
	private void read(TFrom activity) {
		read(activity.xmlText(), 0);
	}
	
	private void read(TTo activity) {
		read(activity.xmlText(), 1);
	}
	
	
	
	private void read(String activity, int type) {
		try {
			DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
			DocumentBuilder dB = dBF.newDocumentBuilder();
			Document doc = dB.parse(new InputSource(new StringReader(activity)));
			
			NodeList nodos = doc.getChildNodes();
			
			if(type == 0) {
				explore(nodos, fromProperty);
			} else 	explore(nodos, toProperty);
			
		} catch (SAXException | IOException | ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void explore(NodeList brotherNodes, Map<String, String> properties) {
		//Recorremos todos los nodos hermanos recibidos
		for(int i = 0; i < brotherNodes.getLength(); i++) {
			//Obtenemos el nodo concreto y sus datos
			Node element = brotherNodes.item(i);

			//Mostramos el elemento con su valor
			if(element.getNodeType() == Node.TEXT_NODE) {
				if(!element.getTextContent().contains("  ")) {
					//Dado que el valor es un elemento hoja y no tenemos manera de saber
					//cuando termina. Volvemos a consultar el nombre de su progenitor.
					if(properties.containsKey(element.getParentNode().getNodeName())) {
						properties.put(element.getParentNode().getNodeName() + "_" + properties.size(), element.getTextContent());
					} else properties.put(element.getParentNode().getNodeName(), element.getTextContent());
				}
			} else {
				for(int j=0; j < element.getAttributes().getLength(); j++) {
					if(!element.getAttributes().item(j).toString().contains("xmlns:")) {
						properties.put(element.getAttributes().item(j).getNodeName(), element.getAttributes().item(j).getNodeValue());
					}
				}
			}
			
			//Si tiene descendientes, se explorarán
			NodeList childNodes = element.getChildNodes();
			
			if(childNodes != null) {
				explore(childNodes, properties);
			}
			
		}
		
		
	}
	
}
