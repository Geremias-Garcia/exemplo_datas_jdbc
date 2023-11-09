package ifpr.pgua.eic.exemplodatas.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.App;
import ifpr.pgua.eic.exemplodatas.model.entities.Agendamento;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioAgendamento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class VerificarConsultas implements Initializable{

    
    @FXML
    private ListView<Agendamento> lstConsultas;
    
    @FXML
    private TextArea taDetalhes;

    @FXML
    private Button btnConfirmar;

    @FXML
    private Button btnCancelar;

    private RepositorioAgendamento repositorioAgendamento;
    private int agendamentoId = 0;
 
    public VerificarConsultas(RepositorioAgendamento repositorioAgendamento) {
        this.repositorioAgendamento = repositorioAgendamento;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        btnConfirmar.setDisable(true);
        btnCancelar.setDisable(true);

        lstConsultas.setCellFactory(param -> new ListCell<Agendamento>() {
        @Override
        protected void updateItem(Agendamento agendamento, boolean empty) {
            super.updateItem(agendamento, empty);

            if (empty || agendamento == null) {
                setText(null);
            } else {
                setText(agendamento.getPaciente().getNome());
            }
        }
    });
        listarConsultasEmAguardo();
    }

    private void listarConsultasEmAguardo() {
        lstConsultas.getItems().clear();
    
        Resultado<ArrayList<Agendamento>> resultado = repositorioAgendamento.consultasAguardandoConfirmação();
    
        if (resultado.foiErro()) {
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        } else {
            List<Agendamento> agendamentos = resultado.comoSucesso().getObj();
    
            lstConsultas.getItems().addAll(agendamentos);
        }
    }
    
    @FXML
    private void mostrarDetalhes(MouseEvent evento){
        Agendamento agendamento = lstConsultas.getSelectionModel().getSelectedItem();
       
        if(agendamento != null){
            taDetalhes.clear();
            taDetalhes.appendText("ID: "+agendamento.getId()+"\n");
            taDetalhes.appendText("Nome do paciente: "+agendamento.getPaciente().getNome()+"\n");
            taDetalhes.appendText("Nome do Médico: "+agendamento.getMedico().getNome()+"\n");
            taDetalhes.appendText("Data: "+agendamento.getData()+"\n");
            taDetalhes.appendText("Hora: "+agendamento.getHora());
            agendamentoId = agendamento.getId();

            btnConfirmar.setDisable(false);
            btnCancelar.setDisable(false);
        }else{
            btnConfirmar.setDisable(true);
            btnCancelar.setDisable(true);
        }
    }

    @FXML
    void confirmar(ActionEvent event) {
        Resultado resultado = repositorioAgendamento.alterarStatusConsulta(agendamentoId, "Confirmada");

        Alert alert;
        
        if(resultado.foiErro()){
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
        }else{
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
        }

        alert.showAndWait();
        listarConsultasEmAguardo();
    }

    @FXML
    void cancelar(ActionEvent event) {
        Resultado resultado = repositorioAgendamento.alterarStatusConsulta(agendamentoId, "Cancelada");

        Alert alert;
        
        if(resultado.foiErro()){
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
        }else{
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
        }

        alert.showAndWait();
        listarConsultasEmAguardo();
    }

    @FXML
    void voltar(ActionEvent event) {
        App.popScreen();
    }
}
