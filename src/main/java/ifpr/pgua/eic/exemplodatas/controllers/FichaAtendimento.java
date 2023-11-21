package ifpr.pgua.eic.exemplodatas.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.App;
import ifpr.pgua.eic.exemplodatas.model.entities.Agendamento;
import ifpr.pgua.eic.exemplodatas.model.entities.Atendimento;
import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.entities.Paciente;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioAgendamento;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioAtendimento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FichaAtendimento implements Initializable{
    
    @FXML
    private Label lbNome;

    @FXML
    private Label dNasc;

    @FXML
    private Label lbIdade;

    @FXML
    private Label lbData;

    @FXML
    private Label lbHora;

    @FXML
    private Label lbGenero;
    
    @FXML
    private Label lbMedico;

    @FXML
    private TextArea descricaoTA;

    private Agendamento agendamento;
    private RepositorioAgendamento repositorioAgendamento;
    private RepositorioAtendimento repositorioAtendimento;
    private LocalDate data = null;
    private LocalTime hora = null;

    public FichaAtendimento(Agendamento agendamento, RepositorioAgendamento repositorioAgendamento, RepositorioAtendimento repositorioAtendimento) {
        this.agendamento = agendamento;
        this.repositorioAgendamento = repositorioAgendamento;
        this.repositorioAtendimento = repositorioAtendimento;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {    
        carregarLabel();
    }

    private void carregarLabel(){
            lbNome.setText("NOME: "+agendamento.getPaciente().getNome()+" |");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dNasc.setText("|NASCIMENTO: " + agendamento.getPaciente().getDataNascimento().format(formatter));
            Period periodo = Period.between(agendamento.getPaciente().getDataNascimento(), LocalDate.now());
            lbIdade.setText("|IDADE: "+periodo.getYears()+" Anos, "+periodo.getMonths());
            lbData.setText("DATA: " + LocalDate.now().format(formatter));
            data = LocalDate.now();
            lbHora.setText("|HORA: "+LocalTime.now().getHour()+":"+LocalTime.now().getMinute()+":"+LocalTime.now().getSecond()+" |");
            hora = LocalTime.now();
            lbGenero.setText("|SEXO: "+agendamento.getPaciente().getGenero());
            lbMedico.setText("MÉDICO: "+agendamento.getMedico().getNome());
    }

    @FXML
    private void finalizar(ActionEvent event){
        Resultado resultado = repositorioAgendamento.alterarStatusConsulta(agendamento.getId(),"Finalizada");

        Alert alert;
        
        if(resultado.foiErro()){
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        }else{
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
        }

        String descricao = descricaoTA.getText();

        Atendimento atendimento = new Atendimento(agendamento.getPaciente(),agendamento.getMedico(), descricao, data, hora);

        Resultado rs = repositorioAtendimento.registrar(atendimento);

        if(rs.foiErro()){
            alert = new Alert(AlertType.ERROR, rs.getMsg());
        }else{
            alert = new Alert(AlertType.INFORMATION, rs.getMsg());
        }

        alert.showAndWait();
        System.out.println(atendimento);
    }

    @FXML
    private void voltar(ActionEvent event){
        App.popScreen();
    }
}
