package ifpr.pgua.eic.exemplodatas.model.entities;

import java.time.LocalDate;

public class Agendamento {
    
    private int id;
    private int idPaciente;
    private int idMedico;
    private LocalDate data;
    private String hora;
    private String status;

    public Agendamento(int id, int idPaciente, int idMedico, LocalDate data, String hora) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.data = data;
        this.hora = hora;
    }

    public Agendamento(int idPaciente, int idMedico, LocalDate data, String hora) {
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.data = data;
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
