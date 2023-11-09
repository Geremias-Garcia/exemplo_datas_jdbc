package ifpr.pgua.eic.exemplodatas.model.entities;

import java.time.LocalDate;

public class Agendamento {
    
    private int id;
    private int id_paciente;
    private int id_medico;
    private Paciente paciente;
    private Medico medico;
    private LocalDate data;
    private String hora;
    private String status;

    public Agendamento(int id, int id_paciente, int id_medico, LocalDate data, String hora, String status) {
        this.id = id;
        this.id_paciente = id_paciente;
        this.id_medico = id_medico;
        this.data = data;
        this.hora = hora;
        this.status = status;
    }

    public Agendamento(int id, Paciente paciente, Medico medico, LocalDate data, String hora, String status) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.data = data;
        this.hora = hora;
        this.status = status;
    }

    public Agendamento(Paciente paciente, Medico medico, LocalDate data, String hora, String status) {
        this.paciente = paciente;
        this.medico = medico;
        this.data = data;
        this.hora = hora;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public int getId_medico() {
        return id_medico;
    }

    public void setId_medico(int id_medico) {
        this.id_medico = id_medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
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

    @Override
    public String toString() {
        return "Agendamento{" +
            "id=" + id +
            ", Paciente=" + paciente +
            ", Medico=" + medico +
            ", data=" + data +
            ", hora='" + hora + '\'' +
            ", status='" + status + '\'' +
            '}';
    }

}
