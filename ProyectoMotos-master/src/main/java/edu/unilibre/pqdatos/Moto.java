package edu.unilibre.pqdatos;

import java.time.LocalDateTime;

public class Moto {
    private String cedula;
    private String placa;
    private String marca;
    private LocalDateTime horaEntrada;

    public String obtenerCedula() {
        return cedula;
    }

    public void modificarCedula(String cedula) {
        this.cedula = cedula;
    }

    public String obtenerPlaca() {
        return placa;
    }

    public void modificarPlaca(String placa) {
        this.placa = placa;
    }

    public String obtenerMarca() {
        return marca;
    }

    public void modificarMarca(String marca) {
        this.marca = marca;
    }

    public LocalDateTime obtenerHoraEntrada() {
        return horaEntrada;
    }

    public void modificarHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
}