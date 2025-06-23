package com.example.GestionDeEntregas_Rutas.services.Entregas;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RoutingService {
    
    private final RestTemplate restTemplate = new RestTemplate();


    public Optional<Double> calcularDistancia(double[] origen, double[] destino) {
        String url = String.format(
            "https://router.project-osrm.org/route/v1/driving/%f,%f;%f,%f?overview=false",
            origen[1], origen[0], destino[1], destino[0]
        );

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            if (root.has("routes") && root.get("routes").isArray()) {
                double distanciaEnMetros = root.get("routes").get(0).get("distance").asDouble();
                return Optional.of(distanciaEnMetros);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
}
