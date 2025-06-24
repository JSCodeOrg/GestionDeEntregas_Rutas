package com.example.GestionDeEntregas_Rutas.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.GestionDeEntregas_Rutas.models.Entrega;
import com.example.GestionDeEntregas_Rutas.services.Entregas.GeocodingService;
import com.example.GestionDeEntregas_Rutas.services.Entregas.RoutingService;

@Component
public class RoutingUtils {

    @Autowired
    private GeocodingService geocodingService;

    @Autowired
    private RoutingService routingService;

    private static final String UBICACION_INICIAL = "Carrera 10 #20-30, Bogotá";

    public List<Entrega> ordenarEntregasGeograficamente(List<Entrega> entregas) {
        System.out.println("📦 Iniciando ordenamiento geográfico de entregas...");

        Optional<double[]> puntoInicialOpt = geocodingService.geocode(UBICACION_INICIAL);
        
        if (puntoInicialOpt.isEmpty()) {
            System.out.println("⚠️ No se pudo obtener la ubicación inicial desde: " + UBICACION_INICIAL);
            return entregas;
        }

        double[] puntoInicial = puntoInicialOpt.get();
        System.out.printf("📍 Punto inicial: lat=%.6f, lon=%.6f%n", puntoInicial[0], puntoInicial[1]);

        List<Entrega> pendientes = new ArrayList<>(entregas);
        List<Entrega> ordenadas = new ArrayList<>();

        while (!pendientes.isEmpty()) {
            Entrega masCercana = null;
            double menorDistancia = Double.MAX_VALUE;

            for (Entrega e : pendientes) {
                String direccion = e.getDireccion();

                Optional<double[]> coordEntrega = geocodingService.geocode(direccion);
                if (coordEntrega.isEmpty()) {
                    System.out.println("❌ No se pudo geocodificar: " + direccion);
                    continue;
                }

                double[] coord = coordEntrega.get();
                System.out.printf("📌 Geocodificado %s → lat=%.6f, lon=%.6f%n", direccion, coord[0], coord[1]);

                Optional<Double> distanciaOpt = routingService.calcularDistancia(puntoInicial, coord);
                if (distanciaOpt.isEmpty()) {
                    System.out.println("⚠️ No se pudo calcular distancia hacia: " + direccion);
                    continue;
                }

                double distancia = distanciaOpt.get();
                System.out.printf("↔️ Distancia desde punto actual a %s: %.2f km%n", direccion, distancia);

                if (distancia < menorDistancia) {
                    menorDistancia = distancia;
                    masCercana = e;
                }
            }

            if (masCercana != null) {
                ordenadas.add(masCercana);
                System.out.println("✅ Entrega más cercana agregada: " + masCercana.getDireccion());
                puntoInicial = geocodingService
                        .geocode(masCercana.getDireccion())
                        .orElse(puntoInicial);
                pendientes.remove(masCercana);
            } else {
                System.out.println("🚫 No se encontró una entrega válida en esta iteración. Terminando.");
                break;
            }
        }

        System.out.println("📋 Orden final de entregas:");
        for (int i = 0; i < ordenadas.size(); i++) {
            System.out.printf("   %d. %s%n", i + 1, ordenadas.get(i).getDireccion());
        }

        return ordenadas;
    }
}
