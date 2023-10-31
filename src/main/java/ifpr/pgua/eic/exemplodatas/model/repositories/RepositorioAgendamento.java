package ifpr.pgua.eic.exemplodatas.model.repositories;

import java.time.LocalDate;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.daos.AgendamentoDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.MedicoDAO;
import ifpr.pgua.eic.exemplodatas.model.entities.Agendamento;
import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RepositorioAgendamento {

    private AgendamentoDAO dao;

    public RepositorioAgendamento(AgendamentoDAO dao){
        this.dao = dao;
    }

    public Resultado agendar(Agendamento agendamento){
        LocalDate dataAtual = LocalDate.now();
        LocalDate data = agendamento.getData();

        if (data != null && data.isBefore(dataAtual)) {
            return Resultado.erro("A data selecionada é anterior a data atual. Selecione uma data válida.");
        } 
            
        return dao.agendar(agendamento);
    }
    
}
