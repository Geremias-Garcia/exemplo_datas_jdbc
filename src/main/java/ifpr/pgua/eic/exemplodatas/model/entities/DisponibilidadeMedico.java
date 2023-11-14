package ifpr.pgua.eic.exemplodatas.model.entities;

public class DisponibilidadeMedico {
    
    private int id;
    private int idMedico;
    private Medico medico;
    private String diaDaSemana;
    private String periodo;

    public DisponibilidadeMedico(int id, Medico medico, String diaDaSemana, String periodo) {
        this.id = id;
        this.medico = medico;
        this.diaDaSemana = diaDaSemana;
        this.periodo = periodo;
    }

    public DisponibilidadeMedico(Medico medico, String diaDaSemana, String periodo) {
        this.medico = medico;
        this.diaDaSemana = diaDaSemana;
        this.periodo = periodo;
    }

    public DisponibilidadeMedico(int idMedico, String diaDaSemana, String periodo) {
        this.idMedico = idMedico;
        this.diaDaSemana = diaDaSemana;
        this.periodo = periodo;
    }

    public DisponibilidadeMedico(int id, int idMedico, String diaDaSemana, String periodo) {
        this.id = id;
        this.idMedico = idMedico;
        this.diaDaSemana = diaDaSemana;
        this.periodo = periodo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getDiaDaSemana() {
        return diaDaSemana;
    }

    public void setDiaDaSemana(String diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    @Override
    public String toString() {
        return "Horários{" +
            "id=" + id +
            ", Médico=" + medico +
            ", Dia=" + diaDaSemana +
            ", Periodo=" + periodo +
            '}';
    }

}
