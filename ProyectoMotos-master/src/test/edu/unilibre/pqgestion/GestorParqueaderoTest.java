package edu.unilibre.pqgestion;

import edu.unilibre.pqdatos.Moto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GestorParqueaderoTest {

    private GestorParqueadero gestor;

    // Se ejecuta antes de cada prueba para tener un parqueadero limpio
    @BeforeEach
    void configurar() {
        gestor = new GestorParqueadero();
    }

    @Test
    void probarCreacionMotoExitosa() {
        Moto moto = gestor.crearMoto("102030", "ABC-123", "Yamaha");

        assertNotNull(moto, "La moto no debería ser nula");
        assertEquals("ABC-123", moto.obtenerPlaca(), "La placa debe coincidir");
        assertNotNull(moto.obtenerHoraEntrada(), "Debe tener una hora de entrada asignada");
    }

    @Test
    void probarCreacionMotoConDatosNulos() {
        Moto motoInvalida = gestor.crearMoto(null, "ABC-123", "Yamaha");
        assertNull(motoInvalida, "Si falta un dato, debe retornar nulo");
    }

    @Test
    void probarLimiteCapacidad23Motos() {
        // Ingresar 23 motos (el parqueadero se llena)
        for (int i = 0; i < 23; i++) {
            Moto m = gestor.crearMoto("111", "PL" + i, "Honda");
            assertTrue(gestor.ingresarMoto(m), "La moto " + i + " debería ingresar correctamente");
        }

        // Intentar ingresar la moto 24 (debería ser rechazada)
        Moto motoExtra = gestor.crearMoto("222", "EX-999", "Suzuki");
        assertFalse(gestor.ingresarMoto(motoExtra), "La moto 24 no debe ingresar porque el cupo máximo es 23");

        assertEquals(23, gestor.obtenerCantidadMotosActivas(), "Solo deben haber 23 motos activas");
    }

    @Test
    void probarCobroMinimoUnMinuto() {
        Moto moto = gestor.crearMoto("111", "MIN-001", "KTM");
        gestor.ingresarMoto(moto);

        // Se le da salida inmediatamente (tiempo < 1 min)
        double valorCobrado = gestor.procesarSalida("MIN-001");

        assertEquals(40.0, valorCobrado, "Se debe cobrar mínimo 1 minuto (40 pesos)");
        assertEquals(0, gestor.obtenerCantidadMotosActivas(), "La moto debió salir de la lista");
    }

    @Test
    void probarCobroTiempoSimulado10Minutos() {
        Moto moto = gestor.crearMoto("111", "SIM-123", "BMW");

        // Simulamos que la moto entró hace exactamente 10 minutos
        moto.modificarHoraEntrada(LocalDateTime.now().minusMinutes(10));
        gestor.ingresarMoto(moto);

        double valorCobrado = gestor.procesarSalida("SIM-123");

        // 10 minutos * 40 pesos = 400 pesos
        assertEquals(400.0, valorCobrado, "El cobro de 10 minutos debe ser 400 pesos");
    }

    @Test
    void probarReporteAcumulado() {
        Moto m1 = gestor.crearMoto("111", "REP-1", "A");
        Moto m2 = gestor.crearMoto("222", "REP-2", "B");

        gestor.ingresarMoto(m1);
        gestor.ingresarMoto(m2);

        gestor.procesarSalida("REP-1");
        gestor.procesarSalida("REP-2");

        assertEquals(2, gestor.obtenerTotalMotosAtendidas(), "El reporte debe registrar 2 motos atendidas");
        assertEquals(80.0, gestor.obtenerDineroTotalRecaudado(), "El reporte debe registrar 80 pesos (40 x 2 motos)");
    }
}