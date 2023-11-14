package ifpr.pgua.eic.exemplodatas.model.entities;

public class AgendaHorarios {
    
    private int id;
    private int idMedico;
    private String diaDaSemana;
    private String periodo;

    public AgendaHorarios(int idMedico, String diaDaSemana, String periodo) {
        this.idMedico = idMedico;
        this.diaDaSemana = diaDaSemana;
        this.periodo = periodo;
    }

    public AgendaHorarios(int id, int idMedico, String diaDaSemana, String periodo) {
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

}
