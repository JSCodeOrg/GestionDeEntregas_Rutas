package com.example.GestionDeEntregas_Rutas.services.Entregas;

import java.util.Optional;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeocodingService {

    private static final String API_KEY = "pk.74730f8fc7a98eb63e83bc024aa82d1f";
    private final RestTemplate restTemplate = new RestTemplate();

    public Optional<double[]> geocode(String direccionOriginal) {
        if (direccionOriginal == null || direccionOriginal.trim().isEmpty()) {
            System.out.println("❌ Dirección vacía o nula.");
            return Optional.empty();
        }

        String direccion = direccionOriginal.trim();

        System.out.println("🗺️🗺️🗺️ Consultando dirección para " + direccion);

        return consultarLocationIQ(direccion);
    }

    private Optional<double[]> consultarLocationIQ(String direccion) {
        try {
            Thread.sleep(600);
            String url = UriComponentsBuilder
                    .fromUriString("https://us1.locationiq.com/v1/search")
                    .queryParam("key", API_KEY)
                    .queryParam("q", direccion)
                    .queryParam("format", "json")   
                    .queryParam("limit", 1)
                    .queryParam("countrycodes", "co")
                    .build()
                    .encode()
                    .toUriString();

            System.out.println("🌐 Consultando LocationIQ con URL: " + url);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("User-Agent", "Mozilla/5.0");
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            System.out.println("🔍 Estado HTTP: " + response.getStatusCode());
            System.out.println("📄 Respuesta: " + response.getBody());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode results = mapper.readTree(response.getBody());

            if (results.isArray() && results.size() > 0) {
                JsonNode location = results.get(0);
                if (location.has("lat") && location.has("lon")) {
                    double lat = Double.parseDouble(location.get("lat").asText());
                    double lon = Double.parseDouble(location.get("lon").asText());
                    System.out.printf("📍 Coordenadas para \"%s\": (%.6f, %.6f)%n", direccion, lat, lon);
                    return Optional.of(new double[] { lat, lon });
                } else {
                    System.out.println("⚠️ Resultado no contiene coordenadas");
                }
            }

            System.out.println("⚠️ No se encontraron coordenadas para: " + direccion);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("❌ Interrupción durante espera para limitar frecuencia de peticiones.");
        } catch (Exception e) {
            System.out.println("❌ Error durante consulta LocationIQ de: " + direccion);
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
