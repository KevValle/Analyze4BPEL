package es.uca.webservices.analisis.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class BPELWriter {
	
	private String writePath;
	private File normalOutput;
	private File jsonOutput;
	private JSONArray allProperties;
	private final String lineJump;
	
	public BPELWriter(String compositionName) {
		
		writePath = System.getProperty("user.home") + "/metamorphic/";
		normalOutput = new File(writePath + "/normal/" + compositionName + ".txt");
		jsonOutput = new File(writePath + "/json/" + compositionName + ".json");
		lineJump = System.getProperty("line.separator");
		
		allProperties = new JSONArray();
		
		if(normalOutput.exists()) {
			normalOutput.delete();
		}
		
		if(jsonOutput.exists()) {
			jsonOutput.delete();
		}
		
		normalOutput.getParentFile().mkdirs();
		jsonOutput.getParentFile().mkdirs();
	}

	public File getNormalOutput() {
		return normalOutput;
	}
	
	public File getJSONOutput() {
		return jsonOutput;
	}
	
	
	public void writeProperties(Map<String, String> properties) {
		writeNormalProperties(properties);
		writeJSONProperties(properties);
	}
	
	public void writeJSONArray(JSONArray json) {
		writeNormalJSONProperties(json);
		writeJSONFinalProperties(json);
	}
	
	private void writeNormalProperties(Map<String, String> properties) {
		try {
			BufferedWriter writeOutput = new BufferedWriter(new FileWriter(normalOutput, true));
			for(Entry<String, String> s : properties.entrySet()) {
				writeOutput.write(s.getKey() + " - " + s.getValue());
				writeOutput.write(lineJump);
			}
			writeOutput.write("----------" + lineJump);
			writeOutput.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void writeJSONProperties(Map<String, String> properties) {
		JSONObject jsonFinal = new JSONObject();
		
		for(Entry<String,String> s : properties.entrySet()) {
			jsonFinal.put(s.getKey(), s.getValue());
		}

		allProperties.add(jsonFinal);
		
		try {
			BufferedWriter writeOutput = new BufferedWriter(new FileWriter(jsonOutput, false));

			writeOutput.write(allProperties.toJSONString());
			writeOutput.close();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	private void writeJSONFinalProperties(JSONArray json) {
		try {
			BufferedWriter writeOutput = new BufferedWriter(new FileWriter(jsonOutput, true));
			writeOutput.write(json.toJSONString());
			writeOutput.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void writeNormalJSONProperties(JSONArray json) {
		try {
			BufferedWriter writeOutput = new BufferedWriter(new FileWriter(normalOutput, true));
			JSONObject elementoJSON;
			
			for(int i = 0; i < json.size(); i++) {
				elementoJSON = (JSONObject) json.get(i);
				Set<Map.Entry<String,Object>> setJSON = elementoJSON.entrySet();
								
				for(Map.Entry<String,Object> atributoJSON : setJSON) {
					if(atributoJSON.getValue() != null) 
						writeOutput.write(atributoJSON.getKey() + " - " + atributoJSON.getValue().toString());
					writeOutput.write(lineJump);
				}
				writeOutput.write("----------" + lineJump);
			}
			
			writeOutput.close();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void writeAssign(Map<String, String> property, Map<String, String> fromProperty,
			Map<String, String> toProperty) {
		writeNormalAssign(property, fromProperty, toProperty);
		writeJsonAssign(property, fromProperty, toProperty);

	}
	
	private void writeJsonAssign(Map<String, String> property, Map<String, String> fromProperty,
			Map<String, String> toProperty) {
		JSONObject jsonFinal = new JSONObject();
		JSONObject jsonProperty = new JSONObject();
		JSONObject jsonFrom = new JSONObject();
		JSONObject jsonTo = new JSONObject();
		
		for(Entry<String,String> s : property.entrySet()) {
			jsonProperty.put(s.getKey(), s.getValue());
		}
		
		for(Entry<String,String> s : fromProperty.entrySet()) {
			jsonFrom.put(s.getKey(), s.getValue());
		}
		
		for(Entry<String,String> s : toProperty.entrySet()) {
			jsonTo.put(s.getKey(), s.getValue());
		}
		
		jsonFinal.put("activity", jsonProperty);
		jsonFinal.put("from", jsonFrom);
		jsonFinal.put("to", jsonTo);
		
		allProperties.add(jsonFinal);
	}
	
	private void writeNormalAssign(Map<String, String> property, Map<String, String> fromProperty,
			Map<String, String> toProperty) {
		
		try {
			BufferedWriter writeOutput = new BufferedWriter(new FileWriter(normalOutput, true));
			for(Entry<String,String> s : property.entrySet()) {
				writeOutput.write(s.getKey() + " - " + s.getValue());
				writeOutput.write(lineJump);
			}
			writeOutput.write("COPY\nFROM:" + lineJump);
			
			for(Entry<String,String> s : fromProperty.entrySet()) {
				writeOutput.write(s.getKey() + " - " + s.getValue());
				writeOutput.write(lineJump);
			}
			writeOutput.write("TO:" + lineJump);
			
			for(Entry<String,String> s : toProperty.entrySet()) {
				writeOutput.write(s.getKey() + " - " + s.getValue());
				writeOutput.write(lineJump);
			}
				
			writeOutput.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

}
