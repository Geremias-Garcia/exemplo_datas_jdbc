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

}
