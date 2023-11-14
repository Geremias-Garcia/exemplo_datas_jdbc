package ifpr.pgua.eic.exemplodatas.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.spi.DirStateFactory.Result;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.entities.DisponibilidadeMedico;
import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioDisponibilidadeMedico;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioMedico;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class AtualizarHorariosDeAtendimento implements Initializable {

    @FXML
    private ListView<Medico> lstMedico;

    @FXML
    private ComboBox<String> cbDia;

    @FXML
    private TextField tfFiltro;

    @FXML
    private ComboBox<String> cbPeriodo;

    @FXML
    private Button confirmaMedico;

    private RepositorioDisponibilidadeMedico repositorioHorariosIndisponiveis;
    private RepositorioMedico repositorioMedico;
    private Medico medico;

    public AtualizarHorariosDeAtendimento(RepositorioMedico repositorioMedico, RepositorioDisponibilidadeMedico repositorioHorariosIndisponiveis){
        this.repositorioMedico = repositorioMedico;
        this.repositorioHorariosIndisponiveis = repositorioHorariosIndisponiveis;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cbDia.getItems().addAll("Segunda-feira", "Terça-feira","Quarta-feira","Quinta-feira","Sexta-feira","Sábado");
        cbPeriodo.getItems().addAll("Manhã","Tarde","Dia todo");
        
        lstMedico.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        cbDia.setDisable(true);
        cbPeriodo.setDisable(true);
        confirmaMedico.setDisable(true);
        
        listar();
    }
    
    private void listar(){
        lstMedico.getItems().clear();
        lstMedico.setCellFactory(new Callback<ListView<Medico>, ListCell<Medico>>() {
        @Override
        public ListCell<Medico> call(ListView<Medico> listView) {
            return new ListCell<Medico>() {
                @Override
                protected void updateItem(Medico medico, boolean empty) {
                    super.updateItem(medico, empty);
                    if (medico != null) {
                        setText(medico.getNome());
                    } else {
                        setText(null);
                    }
                    }
                };
            }
        });

        Resultado resultado = repositorioMedico.listar();

        if(resultado.foiErro()){
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        }else{
            List lista = (List)resultado.comoSucesso().getObj();
            Collections.sort(lista, Comparator.comparing(Medico::getNome));
            lstMedico.getItems().addAll(lista);
        }
         
    }

    @FXML
    private void filtrar(KeyEvent evt){
        System.out.println(tfFiltro.getText());
        if(!tfFiltro.getText().isBlank() || !tfFiltro.getText().isEmpty()){
            
            Resultado<ArrayList<Medico>> resultado = repositorioMedico.filtrarNome(tfFiltro.getText());

            if(resultado.foiSucesso()){
                atualizarTabela(resultado.comoSucesso().getObj());
            }
        }else{
           listar();
        }
    }

    private void atualizarTabela(List<Medico> medico){
        lstMedico.getItems().clear();
        lstMedico.getItems().addAll(medico);
    }

    @FXML
    private void registrarIndisponibilidade(ActionEvent event){
        List<Medico> selecionados = lstMedico.getSelectionModel().getSelectedItems();
        String dia = cbDia.getValue();
        String periodo = cbPeriodo.getValue();

        Resultado resultado = null;
        DisponibilidadeMedico horariosIndisponiveis = null;
        for(Medico medico: selecionados){
            horariosIndisponiveis = new DisponibilidadeMedico(medico, dia, periodo);
            resultado = repositorioHorariosIndisponiveis.registrarHorarios(horariosIndisponiveis);
        }

        Alert alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
        
        alert.showAndWait();

    }

    @FXML
    private void verificarMedico(MouseEvent evento){
        medico = lstMedico.getSelectionModel().getSelectedItem();

        if (medico != null) {
            cbDia.setDisable(false);
        }
    }

    @FXML
    private void dia(ActionEvent event){
        String dia = cbDia.getValue();

        if(!dia.isEmpty() || !dia.isBlank()){
            cbPeriodo.setDisable(false);
        }
    }

    @FXML
    private void periodo(ActionEvent event){
        String periodo = cbDia.getValue();

        if(!periodo.isEmpty() || !periodo.isBlank()){
            confirmaMedico.setDisable(false);
        }
    }
}
