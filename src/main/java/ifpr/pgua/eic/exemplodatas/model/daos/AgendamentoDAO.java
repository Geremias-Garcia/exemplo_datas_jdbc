package ifpr.pgua.eic.exemplodatas.model.daos;

import java.time.LocalDate;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.entities.Agendamento;

public interface AgendamentoDAO {
    
    Resultado agendar(Agendamento agendamento);

    Resultado<ArrayList<Agendamento>> buscarIdPaciente(int id);
}
