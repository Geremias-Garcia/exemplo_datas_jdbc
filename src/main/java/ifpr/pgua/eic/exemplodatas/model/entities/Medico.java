package ifpr.pgua.eic.exemplodatas.model.entities;

import java.time.LocalDate;

public class Medico extends Funcionario {

    private String especialidade;
    private String crm;

    public Medico(String nome, String cpf, String telefone, String email, LocalDate dataNascimento, String genero,
            boolean isAtive, double salario, String especialidade, String crm) {
        super(nome, cpf, telefone, email, dataNascimento, genero, isAtive, salario);
            this.especialidade = especialidade;
            this.crm = crm;
    }
    
    public Medico(int id, String nome, String cpf, String telefone, String email, LocalDate dataNascimento,
            String genero, boolean isAtive, double salario, String especialidade, String crm) {
        super(id, nome, cpf, telefone, email, dataNascimento, genero, isAtive, salario);
            this.especialidade = especialidade;
            this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    
    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    @Override
    public String toString() {
        return "Médico [nome=" + getNome() + ", CPF=" + getCpf() + ", telefone=" + getTelefone() + ", email=" + getEmail()
                + ", dataNascimento=" + getDataNascimento() + ", gênero=" + getGenero() + ", ativo=" + isAtive()
                + ", salário=" + getSalario() + ", especialidade=" + especialidade
                + ", CRM=" + crm + "]";
    }


}
