package ifpr.pgua.eic.exemplodatas.model.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.daos.AgendamentoDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.MedicoDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.PacienteDAO;
import ifpr.pgua.eic.exemplodatas.model.entities.Agendamento;
import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.entities.Paciente;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RepositorioAgendamento {

    private AgendamentoDAO dao;
    private PacienteDAO pacienteDAO;
    private MedicoDAO medicoDAO;

    public RepositorioAgendamento(AgendamentoDAO dao,PacienteDAO pacienteDAO, MedicoDAO medicoDAO){
        this.dao = dao;
        this.pacienteDAO = pacienteDAO;
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

                Resultado r2 = pacienteDAO.buscarPorId(agendamentos.getId_paciente());
                if(r1.foiErro()){
                    return r2;
                }

                Paciente paciente = (Paciente)r2.comoSucesso().getObj();
                agendamentos.setPaciente(paciente);

                agendamento.add(agendamentos);
            }
        }   
        return Resultado.sucesso("ok", agendamento);
    }

    public Resultado<ArrayList<Agendamento>> buscarIdMedico(int id) {
        Resultado resultado = dao.buscarIdMedico(id);

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

                Resultado r2 = pacienteDAO.buscarPorId(agendamentos.getId_paciente());
                if(r1.foiErro()){
                    return r2;
                }

                Paciente paciente = (Paciente)r2.comoSucesso().getObj();
                agendamentos.setPaciente(paciente);

                agendamento.add(agendamentos);
            }
        }   
        return Resultado.sucesso("ok", agendamento);
    }

    public Resultado<ArrayList<Agendamento>> buscarConsultaConfirmada(int id) {
        Resultado resultado = dao.buscarConsultaConfirmada(id);

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

                Resultado r2 = pacienteDAO.buscarPorId(agendamentos.getId_paciente());
                if(r1.foiErro()){
                    return r2;
                }

                Paciente paciente = (Paciente)r2.comoSucesso().getObj();
                agendamentos.setPaciente(paciente);

                agendamento.add(agendamentos);
            }
        }   
        return Resultado.sucesso("ok", agendamento);
    }
    
    public Resultado<ArrayList<String>> verificarDisponibilidadeHorario(int medicoId, LocalDate data) {
        Resultado resultado = dao.verificarDisponibilidadeHorario(medicoId, data);
        
        ArrayList<String> horariosFormatados = new ArrayList<>(); 
        if(resultado.foiSucesso()){
            List<String> lista = (List<String>)resultado.comoSucesso().getObj();
            for(String hora : lista) {
                if(hora.length() > 0 && (hora.charAt(0) != '1' && hora.charAt(0) != '2')){
                    String horaFormatada = hora.substring(0, 4);
                    horariosFormatados.add(horaFormatada);
                }else{
                    String horaFormatada = hora.substring(0, 5);
                    horariosFormatados.add(horaFormatada);
                }
            }
            System.out.println(horariosFormatados);
            
        }
        
        return Resultado.sucesso("ok", horariosFormatados);
    }
    
    public Resultado verificarNumeroConsultasEmAguardo(){
        return dao.verificarNumeroConsultasEmAguardo();
    }

    public Resultado<ArrayList<Agendamento>> consultasAguardandoConfirmação(){
        Resultado resultado = dao.consultasAguardandoConfirmação();

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

                Resultado r2 = pacienteDAO.buscarPorId(agendamentos.getId_paciente());
                if(r1.foiErro()){
                    return r2;
                }

                Paciente paciente = (Paciente)r2.comoSucesso().getObj();
                agendamentos.setPaciente(paciente);

                agendamento.add(agendamentos);
            }
        }   
        return Resultado.sucesso("ok", agendamento);
    }

    public Resultado alterarStatusConsulta(int id, String status){
        return dao.alterarStatusConsulta(id, status);
    }

}
