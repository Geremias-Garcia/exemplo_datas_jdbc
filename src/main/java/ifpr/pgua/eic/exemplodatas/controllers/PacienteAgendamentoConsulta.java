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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class PacienteAgendamentoConsulta implements Initializable{

    @FXML
    private ListView<Medico> lstMedico;

    @FXML
    private TextField tfFiltro;

    @FXML
    private ComboBox cbEspecialidades;

    private RepositorioMedico repositorioMedico;

    public PacienteAgendamentoConsulta(RepositorioMedico repositorioMedico){
        this.repositorioMedico = repositorioMedico;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cbEspecialidades.getItems().addAll("Todos", "Pediatra", "Cardiologista","Urologista","Ginecologista");
        cbEspecialidades.setValue("Todos");

        listarTodos();

        cbEspecialidades.valueProperty().addListener((observable, oldValue, newValue) -> {
            if ("Todos".equals(newValue)) {
                listarTodos();
            } else {
                String especialidade = newValue.toString();
                atualizarListaMedicosFiltrados(especialidade);
            }
        });
    }

    private void listarTodos(){
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

    private void atualizarListaMedicosFiltrados(String especialidade) {
        Resultado<ArrayList<Medico>> resultado = repositorioMedico.filtrarEspecialidade(especialidade);

        if (resultado.foiSucesso()) {
            atualizarTabela(resultado.comoSucesso().getObj());
        }
    }

    @FXML
    private void filtrar(KeyEvent evt){
        String selecao = cbEspecialidades.getValue().toString();
        if (selecao.equals("Todos") && (tfFiltro.getText().isBlank() || tfFiltro.getText().isEmpty()) ) {
                listarTodos();
        }else if(selecao.equals("Todos") && (!tfFiltro.getText().isBlank() || !tfFiltro.getText().isEmpty())){
            
            Resultado<ArrayList<Medico>> resultado = repositorioMedico.filtrarNome(tfFiltro.getText());

            if(resultado.foiSucesso()){
                atualizarTabela(resultado.comoSucesso().getObj());
            }
        }else if(!selecao.equals("Todos") && (tfFiltro.getText().isBlank() || tfFiltro.getText().isEmpty())){
            atualizarListaMedicosFiltrados(selecao);
        }else if(!selecao.equals("Todos") && (!tfFiltro.getText().isBlank() || !tfFiltro.getText().isEmpty())){
            Resultado<ArrayList<Medico>> resultado = repositorioMedico.filtrarEspecialidadeENome(selecao,tfFiltro.getText());

            if(resultado.foiSucesso()){
                atualizarTabela(resultado.comoSucesso().getObj());
            }
        }
    }
    
    private void atualizarTabela(List<Medico> medico){
        lstMedico.getItems().clear();
        lstMedico.getItems().addAll(medico);
    }
    
}
