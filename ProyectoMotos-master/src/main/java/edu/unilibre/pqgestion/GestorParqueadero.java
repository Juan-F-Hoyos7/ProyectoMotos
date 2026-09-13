package edu.unilibre.pqgestion;

import edu.unilibre.pqdatos.Moto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GestorParqueadero {

    // Variables de estado del parqueadero
    private List<Moto> motosActivas = new ArrayList<>();
    private final int CAPACIDAD_MAXIMA = 23;

    // Reporte del día
    private int totalMotosAtendidas = 0;
    private double dineroTotalRecaudado = 0.0;


    public Moto crearMoto(String cedula, String placa, String marca) {
        if (cedula == null || placa == null || marca == null) {
            return null;
        }
        Moto nuevaMoto = new Moto();
        nuevaMoto.modificarCedula(cedula);
        nuevaMoto.modificarPlaca(placa);
        nuevaMoto.modificarMarca(marca);
        nuevaMoto.modificarHoraEntrada(LocalDateTime.now());

        return nuevaMoto;
    }


    public boolean ingresarMoto(Moto laMoto) {
        if (laMoto == null || motosActivas == null) {
            return false;
        }

        if (motosActivas.size() >= CAPACIDAD_MAXIMA) {
            return false; // No hay cupo
        }

        motosActivas.add(laMoto);
        return true;
    }


    public long calcularMinutosTranscurridos(LocalDateTime horaEntrada) {
        if (horaEntrada == null || horaEntrada.isAfter(LocalDateTime.now())) {
            return 1; // Si hay un error con la fecha, se cobra mínimo 1 minuto
        }

        long minutos = Duration.between(horaEntrada, LocalDateTime.now()).toMinutes();

        if (minutos < 1) {
            return 1;
        }
        return minutos;
    }


    public double procesarSalida(String placa) {
        if (placa == null || motosActivas == null) {
            return 0.0;
        }

        Moto motoSalida = null;

        // Buscar la moto en la lista
        for (Moto m : motosActivas) {
            if (m.obtenerPlaca().equalsIgnoreCase(placa)) {
                motoSalida = m;
                break;
            }
        }

        if (motoSalida == null) {
            return 0.0; // No se encontró la moto
        }

        // Calcular valor (40 pesos por minuto)
        long minutos = calcularMinutosTranscurridos(motoSalida.obtenerHoraEntrada());
        double valorPagar = minutos * 40.0;

        // Registrar salida y actualizar reporte
        motosActivas.remove(motoSalida);
        totalMotosAtendidas++;
        dineroTotalRecaudado += valorPagar;

        return valorPagar;
    }

    // --- Métodos para obtener datos del reporte y estado ---

    public int obtenerCantidadMotosActivas() {
        if (motosActivas != null) {
            return motosActivas.size();
        }
        return 0;
    }

    public int obtenerTotalMotosAtendidas() {
        return totalMotosAtendidas;
    }

    public double obtenerDineroTotalRecaudado() {
        return dineroTotalRecaudado;
    }
}