package ifpr.pgua.eic.exemplodatas.model.daos;

import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.entities.Atendimento;

public interface AtendimentoDAO {
    
    Resultado registrar(Atendimento atendimento);

    Resultado<ArrayList<Atendimento>> buscarAtendimentosPorId(int idPaciente);

}
