package ifpr.pgua.eic.exemplodatas.model.entities;

import java.time.LocalDate;

public class Paciente extends Pessoa{

    public Paciente(int id, String nome, String cpf, String telefone, String email, LocalDate dataNascimento,
            String genero, boolean isAtive) {
        super(id, nome, cpf, telefone, email, dataNascimento, genero, isAtive);
    }
    
    public Paciente(String nome, String cpf, String telefone, String email, LocalDate dataNascimento,
            String genero, boolean isAtive) {
        super(nome, cpf, telefone, email, dataNascimento, genero, isAtive);
    }

    @Override
    public String toString() {
        return "Pessoa [nome=" + super.getNome() + ", cpf=" + super.getCpf() + ", telefone=" + super.getTelefone() + 
        ", email=" + super.getEmail() + ", dataNascimento=" + super.getDataNascimento() +
         ", genero=" + super.getGenero() + ", isAtive=" + super.isAtive() + "]";
    }

}
