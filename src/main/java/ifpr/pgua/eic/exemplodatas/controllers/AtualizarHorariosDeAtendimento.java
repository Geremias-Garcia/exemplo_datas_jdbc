package ifpr.pgua.eic.exemplodatas.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioMedico;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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

    private RepositorioMedico repositorioMedico;

    public AtualizarHorariosDeAtendimento(RepositorioMedico repositorioMedico){
        this.repositorioMedico = repositorioMedico;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cbDia.getItems().addAll("Segunda-feira", "Terça-feira","Quarta-feira","Quinta-feira","Sexta-feira","Sábado");
        cbPeriodo.getItems().addAll("Manhã","Tarde","Dia todo");
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
}
