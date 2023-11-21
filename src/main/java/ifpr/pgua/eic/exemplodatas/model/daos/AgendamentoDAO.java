package ifpr.pgua.eic.exemplodatas.model.daos;

import java.time.LocalDate;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.entities.Agendamento;

public interface AgendamentoDAO {
    
    Resultado agendar(Agendamento agendamento);

    Resultado<ArrayList<Agendamento>> buscarIdPaciente(int id);

    Resultado<ArrayList<Agendamento>> buscarIdMedico(int id);

    Resultado<ArrayList<Agendamento>> buscarConsultaConfirmada(int id);

    Resultado<ArrayList<String>> verificarDisponibilidadeHorario(int medicoId, LocalDate data);

    Resultado verificarNumeroConsultasEmAguardo();

    Resultado<ArrayList<Agendamento>> consultasAguardandoConfirmação();

    Resultado alterarStatusConsulta(int id, String status);
}
