package ifpr.pgua.eic.exemplodatas.model.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Atendimento {

    private int id;
    private int id_paciente;
    private int id_medico;
    private Paciente paciente;
    private Medico medico;
    private String descricao;
    private LocalDate data;
    private LocalTime hora;
    
    public Atendimento(int id, Paciente paciente, Medico medico, String descricao, LocalDate data, LocalTime hora) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.descricao = descricao;
        this.data = data;
        this.hora = hora;
    }

    public Atendimento(Paciente paciente, Medico medico, String descricao, LocalDate data, LocalTime hora) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.descricao = descricao;
        this.data = data;
        this.hora = hora;
    }

    public Atendimento(int id_paciente, int id_medico, String descricao, LocalDate data, LocalTime hora) {
        this.id_paciente = id_paciente;
        this.id_medico = id_medico;
        this.descricao = descricao;
        this.data = data;
        this.hora = hora;
    }

    public Atendimento(int id, int id_paciente, int id_medico, String descricao, LocalDate data, LocalTime hora) {
        this.id = id;
        this.id_paciente = id_paciente;
        this.id_medico = id_medico;
        this.descricao = descricao;
        this.data = data;
        this.hora = hora;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Agendamento{" +
            "id=" + id +
            ", Paciente=" + paciente +
            ",Paciente ID="+getId_paciente()+
            ", Medico=" + medico +
            ",Medico ID="+getId_medico()+
            ", data=" + data +
            ", hora='" + hora + '\'' +
            ", Descrição='" + descricao + '\'' +
            '}';
    }

}
