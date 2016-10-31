package es.uca.webservices.analisis.match;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import es.uca.webservices.analisis.io.BPELWriter;

public class ReportMatcher {
	
	private String bpelPath;
	private String bptsPath;
	private BPELWriter writer;
		
	public ReportMatcher(String bpelPath, String bptsPath) {
		File checkBPEL = new File(bpelPath);
		File checkBPTS = new File(bptsPath);
		
		this.bpelPath = bpelPath;
		this.bptsPath = bptsPath;
		writer = new BPELWriter(bpelPath.substring(bpelPath.lastIndexOf('/'), bpelPath.length() - 5) + "_final");
		
	}
	
	public File getOutput() {
		return writer.getNormalOutput();
	}
	
	private JSONArray getJSONbpel() {
		return getMatchArray(bpelPath);
	}
	
	private JSONArray getJSONbpts() {
		return getMatchArray(bptsPath);
	}
	
	private JSONArray getMatchArray(String path) {
		JSONParser parser = new JSONParser();
		JSONArray collection = new JSONArray();
		Object parsed = new Object();
		
		//Obtenemos el array de JSON del objeto una vez parseado
		try {
			parsed = parser.parse(new FileReader(path));
			collection = (JSONArray) parsed;

			} catch (IOException | ParseException e) {
				throw new RuntimeException(e);
		}
		
		return collection;

	}
	
	public String getFinalResult() {
		//Creamos el map y el matchresult para almacenar los resultados
		//que enviaremos a la impresora
		
		JSONArray bpelArray = getJSONbpel();
		JSONArray bptsArray = getJSONbpts();
		JSONArray matchResult = new JSONArray();
		
		JSONObject elementoBPEL;
		JSONObject elementoBPTS;
		
		Set<Map.Entry<String,Object>> bpelSet;
		Set<Map.Entry<String,Object>> bptsSet;
		
		/**
		 * Se recorrerá el resultado del fichero BPEL con los
		 * resultados de los casos de prueba, quedándonos
		 * sólo con aquellos que coinciden.
		 */
		
		//Bucle de BPEL
		for(int i = 0; i < bpelArray.size(); i++) {
			elementoBPEL = (JSONObject) bpelArray.get(i);
			
			bpelSet = elementoBPEL.entrySet();				
			
			//Recorremos todos los elementos del JSON obtenido
			for(Map.Entry<String, Object> BPELEntry : bpelSet) {
				
				//Y lo comparamos con todos los casos de prueba
				for(int j = 0; j < bptsArray.size(); j++) {
					elementoBPTS = (JSONObject) bptsArray.get(j);
					bptsSet = elementoBPTS.entrySet();
					
					//Recorriendo todos sus elementos
					for(Map.Entry<String, Object> BPTSEntry : bptsSet) {
						
						/**
						 * Queremos cruzar los elementos por valores y etiquetas.
						 * En ocasiones, las etiquetas tienen nombre de atributos,
						 * por lo que resulta necesario ver todas las combinaciones.
						 * 
						 * {A = B1} {B2 = C}
						 * 
						 * ¿A = B2? ¿B1 = B2? ¿A = C? ¿B1 = C?
						 * 
						 */

						if(BPTSEntry.getValue() != null && BPELEntry.getValue() != null) {
						if ((BPELEntry.getKey().contains(BPTSEntry.getKey()) ||
								   BPELEntry.getValue().toString().contains(BPTSEntry.getKey()) ||
								   BPELEntry.getKey().contains(BPTSEntry.getValue().toString()) || 
								   BPELEntry.getValue().toString().contains(BPTSEntry.getValue().toString())) &&
								!matchResult.contains(elementoBPEL)) {
							//Si estamos aquí es porque hay un valor coincidente, luego lo metemos
							matchResult.add(elementoBPEL);
						}} //FIN IF DEL MATCH		
					} //FIN DEL FOR DE ELEMENTOS BPTS
				} //FIN DEL FOR DE ARRAY DE BPTS
			} //FIN DEL FOR DE ELEMENTOS BPEL
		} //FIN DEL ARRAY DE BPEL

		
		writer.writeJSONArray(matchResult);
		
		return "Final result obtained.";

	}

}
