package com.example.demo.model.dto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class NSApiRestController {

	@Autowired
	private RestTemplate restTemplate;

	String subscriptionKey = SubscriptionKey.subscriptionKey;

	List<String> titles = new ArrayList<>();
	
	Map<String, String> listaDatosId = new HashMap<>();

	@GetMapping("/about-us")
	public String muestraAbout() {

		return "about-us/about-us";
	}

	public List<Map<String, Object>> sacaIDDisruptions() {
		String apiUrl = "https://gateway.apiportal.ns.nl/reisinformatie-api/api/v3/disruptions?isActive=true";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Ocp-Apim-Subscription-Key", subscriptionKey);
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity,
					String.class);
			String jsonResponse = responseEntity.getBody();
			// System.out.println(jsonResponse);

			// Convertir el JSON a un Map
			ObjectMapper objectMapper = new ObjectMapper();
			List<Map<String, Object>> resultList = objectMapper.readValue(jsonResponse,
					new TypeReference<List<Map<String, Object>>>() {
					});

			// Imprimir el Map resultante
			//System.out.println("Map resultante: ");
			//System.out.println(resultList);
			//System.out.println("Termina sMap resultante: ");
			return resultList;
		} catch (HttpStatusCodeException e) {

			// Manejar cualquier excepción HTTP
			System.err.println("Error HTTP: " + e.getStatusCode());
			System.err.println("Mensaje: " + e.getResponseBodyAsString());

		} catch (IOException e) {
			// Manejar cualquier excepción IO aquí
			e.printStackTrace();
		}
		return null;
	}

	// -----------------------------------------
	public Map<String, String> extraerIdsCompletos(List<Map<String, Object>> list) {

		Map<String, String> idTituloMap = new HashMap<>();
		
		
		
		

		for (Map<String, Object> mapa : list) {
			String id = (String) mapa.get("id");
			String title = (String) mapa.get("title");
			String type = (String) mapa.get("type");
			String description = null;
			Map<String, Object> situation;
			Map<String, Object> cause;
			String situationLabel = null;
			String causeLabel = null;
			List<Map<String, Object>> publicationSections2 = (ArrayList) mapa.get("publicationSections");
			List<Map<String, Object>> timespans = (ArrayList) mapa.get("timespans");
			
			
			System.out.println("id: " + id);
			System.out.println("type: " + type);
			
	
	        for (Map<String, Object> section : publicationSections2) {
	            // Obtener el mapa 'consequence' dentro del mapa actual
	            Map<String, Object> consequence = (Map<String, Object>) section.get("consequence");

	            // Obtener la descripción del mapa 'consequence'
	            description = (String) consequence.get("description");

	            // Imprimir la descripción
	            System.out.println("Descripciónnn: " + description);
	        }
	 
	        
	        for (Map<String, Object> section : timespans) {
	            // Obtener el mapa 'situation' dentro del mapa actual
	             situation = (Map<String, Object>) section.get("situation");
	             
	            // Obtener el mapa 'cause' dentro del mapa actual
	             cause = (Map<String, Object>) section.get("cause");
	            
	         // Obtener la descripción del mapa 'label'
	             situationLabel = (String) situation.get("label");
	            
	            // Obtener la descripción del mapa 'label'
	            causeLabel = (String) cause.get("label");

	            // Imprimir la descripción
	            System.out.println("Situatuion Label: " + situationLabel);
	            // Imprimir la descripción
	            System.out.println("Cause Label: " + causeLabel);
	        }
			
			

			if (id.length() < 9) {
				// System.out.println("Id limpiado: "+id);
				idTituloMap.put(id, title);
				listaDatosId.put("situationLabel", situationLabel);
				listaDatosId.put("type", type);
				listaDatosId.put("causeLabel", causeLabel);
				listaDatosId.put("description", description);
			}

		}

		return idTituloMap;
	}

//-----------------------------------------

	public Map<String, String> extraerIds(List<Map<String, Object>> list) {

		Map<String, String> idTituloMap = new HashMap<>();

		for (Map<String, Object> mapa : list) {
			String id = (String) mapa.get("id");
			String title = (String) mapa.get("title");

			if (id.length() < 9) {
				// System.out.println("Id limpiado: "+id);
				idTituloMap.put(id, title);
			}

		}

		return idTituloMap;
	}

	// Ruta por defecto
	@GetMapping("/")
	public String sacaGEOJsonDefecto(Model model) {

		// Obtener el mapa de IDs y títulos
		Map<String, String> idTitulosMap = extraerIdsCompletos(sacaIDDisruptions());

		model.addAttribute("datos", idTitulosMap);

		return "home/index";
	}

	// Ruta con indice para mostrar vias
	@GetMapping("/{index}")
	public String sacaGEOJson(@PathVariable(required = false) int index, Model model) {

		// Obtener el mapa de IDs y títulos
		Map<String, String> idTitulosMap = extraerIdsCompletos(sacaIDDisruptions());

		model.addAttribute("datos", idTitulosMap);
		model.addAttribute("datosExtra", listaDatosId);

		String apiUrl;
		String indexString = String.valueOf(index);

		if (indexString != null) {
			//System.out.println("Con Indice");
			apiUrl = "https://gateway.apiportal.ns.nl/Spoorkaart-API/api/v1/storingen/" + index;

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Ocp-Apim-Subscription-Key", subscriptionKey);
			HttpEntity<String> requestEntity = new HttpEntity<>(headers);

			try {
				ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, requestEntity,
						String.class);
				String jsonResponse = responseEntity.getBody();

				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode rootNode = objectMapper.readTree(jsonResponse);
				JsonNode payloadNode = rootNode.get("payload");

				//System.out.println("geoJason");
				//System.out.println(payloadNode);

				model.addAttribute("content", payloadNode);

				// Convertir el JSON a un Map

//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, Object> resultMap = objectMapper.readValue(jsonResponse, new TypeReference<Map<String,Object>>() {});

				// Imprimir el Map resultante
				// System.out.println(resultMap);
			} catch (HttpStatusCodeException e) {

				System.err.println("Error HTTP: " + e.getStatusCode());
				System.err.println("Mensaje: " + e.getResponseBodyAsString());

			} catch (IOException e) {

				e.printStackTrace();
			}

		} // Cierro IF
		return "home/index";
	}
}
