package ifpr.pgua.eic.exemplodatas.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.App;
import ifpr.pgua.eic.exemplodatas.model.entities.Agendamento;
import ifpr.pgua.eic.exemplodatas.model.entities.Atendimento;
import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.entities.Paciente;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioAtendimento;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class AtendimentosRealizadosPaciente implements Initializable{
    
    @FXML
    private ListView<Atendimento> lstAtendimentos;

    @FXML
    private TextArea taDetalhes;

    private RepositorioAtendimento repositorioAtendimento;
    private Paciente paciente;
    
    public AtendimentosRealizadosPaciente(RepositorioAtendimento repositorioAtendimento, Paciente paciente) {
        this.repositorioAtendimento = repositorioAtendimento;
        this.paciente = paciente;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("ok");
        configurarListView();
        listarAtendimentos();
    }

    private void listarAtendimentos(){
        Resultado resultado = repositorioAtendimento.buscarAtendimentosPorId(paciente.getId());

        if (resultado.foiErro()) {
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
            return;
        }else{
            List<Atendimento> lista = (List)resultado.comoSucesso().getObj();
            Collections.sort(lista, Comparator.comparing(Atendimento::getData));
            Collections.reverse(lista);
            lstAtendimentos.getItems().addAll(lista);
        }
    }

    private void configurarListView() {
        lstAtendimentos.setCellFactory(new Callback<ListView<Atendimento>, ListCell<Atendimento>>() {
            @Override
            public ListCell<Atendimento> call(ListView<Atendimento> param) {
                return new ListCell<Atendimento>() {
                    @Override
                    protected void updateItem(Atendimento item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        }
                    }
                };
            }
        });
    }

    @FXML
    private void mostrarDetalhes(MouseEvent event){
        Atendimento atendimento = lstAtendimentos.getSelectionModel().getSelectedItem();

        if(atendimento != null){
            taDetalhes.clear();
            taDetalhes.appendText("ID da consulta: "+atendimento.getId()+"\n");
            taDetalhes.appendText("Nome do Paciente: "+atendimento.getPaciente().getNome()+"\n");
            taDetalhes.appendText("Data de nascimento paciente: "+atendimento.getPaciente().getDataNascimento()+"\n");
            Period periodo = Period.between(atendimento.getPaciente().getDataNascimento(), atendimento.getData());
            taDetalhes.appendText("|Idade do paciente: "+periodo.getYears()+" Anos, "+periodo.getMonths()+"\n");
            taDetalhes.appendText("Nome do médico: "+atendimento.getMedico().getNome()+"\n");
            taDetalhes.appendText("Data: "+atendimento.getData()+"\n");
            taDetalhes.appendText("Hora: "+atendimento.getHora()+"\n\n");
            taDetalhes.appendText("Descrição do atendimento: "+atendimento.getDescricao()+"\n");
        }
        System.out.println(atendimento.getHora());
    }

    @FXML
    private void voltar(ActionEvent event){
        App.popScreen();
    }

    @FXML
    private void email(ActionEvent event){
        Alert alert = new Alert(AlertType.INFORMATION, "Cópia da consulta enviada para "+paciente.getEmail());
        alert.showAndWait();
    }

    @FXML
    private void imprimir(ActionEvent event){
        Alert alert = new Alert(AlertType.INFORMATION, "Fica de olho na impressora");
        alert.showAndWait();
    }
}
