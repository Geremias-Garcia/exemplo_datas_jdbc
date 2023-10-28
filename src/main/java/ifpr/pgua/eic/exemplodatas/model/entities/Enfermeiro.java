package ifpr.pgua.eic.exemplodatas.model.entities;

import java.time.LocalDate;

public class Enfermeiro extends Funcionario {

    private String turno;
    private String coren;

    public Enfermeiro(String nome, String cpf, String telefone, String email, LocalDate dataNascimento, String genero,
            boolean isAtive, double salario, String turno, String coren) {
        super(nome, cpf, telefone, email, dataNascimento, genero, isAtive, salario);
            this.turno = turno;
            this.coren = coren;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getCoren() {
        return coren;
    }

    public void setCoren(String coren) {
        this.coren = coren;
    }


}
