package ifpr.pgua.eic.exemplodatas.model.entities;

import java.time.LocalDate;

public class Pessoa {
    
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private LocalDate dataNascimento;
    private String genero;
    private boolean isAtive;

    public Pessoa(String nome, String cpf, String telefone, String email, LocalDate dataNascimento, String genero,
            boolean isAtive) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.isAtive = isAtive;
    }

    public Pessoa(int id, String nome, String cpf, String telefone, String email, LocalDate dataNascimento,
            String genero, boolean isAtive) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.isAtive = isAtive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public boolean isAtive() {
        return isAtive;
    }

    public void setAtive(boolean isAtive) {
        this.isAtive = isAtive;
    }
    
    @Override
    public String toString() {
        return "Pessoa [nome=" + nome + ", cpf=" + cpf + ", telefone=" + telefone + ", email=" + email + ", dataNascimento=" + dataNascimento + ", genero=" + genero + ", isAtive=" + isAtive + "]";
    }
    
}
