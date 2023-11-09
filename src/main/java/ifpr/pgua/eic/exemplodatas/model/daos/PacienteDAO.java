package ifpr.pgua.eic.exemplodatas.model.daos;

import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.entities.Paciente;

public interface PacienteDAO {
    
    Resultado criar(Paciente paciente);

    Resultado listar();

    Resultado<Paciente> buscarPorId(int id);

    Resultado<ArrayList<Paciente>> filtrarNome(String inicio);

    Resultado buscarPorCpf(String cpf);

    Resultado alterar(Paciente paciente);

}
