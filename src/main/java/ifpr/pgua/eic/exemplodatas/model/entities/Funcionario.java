package ifpr.pgua.eic.exemplodatas.model.entities;

import java.time.LocalDate;

public class Funcionario extends Pessoa{

    private double salario;

    public Funcionario(String nome, String cpf, String telefone, String email, LocalDate dataNascimento, String genero,
            boolean isAtive, double salario) {
        super(nome, cpf, telefone, email, dataNascimento, genero, isAtive);
        this.salario = salario;
    }
    
    public Funcionario(int id, String nome, String cpf, String telefone, String email, LocalDate dataNascimento, String genero,
            boolean isAtive, double salario) {
        super(id, nome, cpf, telefone, email, dataNascimento, genero, isAtive);
        this.salario = salario;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
    
}
