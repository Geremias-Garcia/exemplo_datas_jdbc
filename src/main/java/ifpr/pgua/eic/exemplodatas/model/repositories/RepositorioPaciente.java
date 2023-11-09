package ifpr.pgua.eic.exemplodatas.model.repositories;

import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.daos.PacienteDAO;
import ifpr.pgua.eic.exemplodatas.model.entities.Paciente;

public class RepositorioPaciente {
    
    private PacienteDAO dao;

    public RepositorioPaciente(PacienteDAO dao){
        this.dao = dao;
    }

    public Resultado criar(Paciente paciente){

        return dao.criar(paciente);
    }

    public Resultado listar(){
        return dao.listar();
    }

    public Resultado buscarPorCpf(String cpf){
        return dao.buscarPorCpf(cpf);
    }

    public Resultado<ArrayList<Paciente>> filtrarNome(String inicio) {
        return dao.filtrarNome(inicio);
    }

}
