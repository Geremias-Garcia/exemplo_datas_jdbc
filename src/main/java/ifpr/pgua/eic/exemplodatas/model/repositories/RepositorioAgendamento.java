package ifpr.pgua.eic.exemplodatas.model.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.daos.AgendamentoDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.MedicoDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.PessoaDAO;
import ifpr.pgua.eic.exemplodatas.model.entities.Agendamento;
import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.entities.Pessoa;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RepositorioAgendamento {

    private AgendamentoDAO dao;
    private PessoaDAO pessoaDAO;
    private MedicoDAO medicoDAO;

    public RepositorioAgendamento(AgendamentoDAO dao,PessoaDAO pessoaDAO, MedicoDAO medicoDAO){
        this.dao = dao;
        this.pessoaDAO = pessoaDAO;
        this.medicoDAO = medicoDAO;
    }

    public Resultado agendar(Agendamento agendamento){
        LocalDate dataAtual = LocalDate.now();
        LocalDate data = agendamento.getData();

        if (data != null && data.isBefore(dataAtual)) {
            return Resultado.erro("A data selecionada é anterior a data atual. Selecione uma data válida.");
        } 
            
        return dao.agendar(agendamento);
    }

    public Resultado<ArrayList<Agendamento>> buscarIdPaciente(int id) {
        Resultado resultado = dao.buscarIdPaciente(id);

        ArrayList<Agendamento> agendamento = new ArrayList<>(); 
        if(resultado.foiSucesso()){
            List<Agendamento> lista = (List<Agendamento>)resultado.comoSucesso().getObj();
         
            for(Agendamento agendamentos:lista){
                Resultado r1 = medicoDAO.buscarPorId(agendamentos.getId_medico());
                if(r1.foiErro()){
                    return r1;
                }
                Medico medico = (Medico)r1.comoSucesso().getObj();
                agendamentos.setMedico(medico);

                Resultado r2 = pessoaDAO.buscarPorId(agendamentos.getId_paciente());
                if(r1.foiErro()){
                    return r2;
                }

                Pessoa pessoa = (Pessoa)r2.comoSucesso().getObj();
                agendamentos.setPessoa(pessoa);

                agendamento.add(agendamentos);
            }
        }   
        return Resultado.sucesso("ok", agendamento);
    }
    
}
