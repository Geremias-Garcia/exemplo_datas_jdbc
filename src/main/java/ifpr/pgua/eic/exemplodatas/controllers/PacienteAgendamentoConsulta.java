package ifpr.pgua.eic.exemplodatas.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.App;
import ifpr.pgua.eic.exemplodatas.model.entities.Agendamento;
import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.entities.Paciente;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioAgendamento;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioMedico;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class PacienteAgendamentoConsulta implements Initializable{

    @FXML
    private ListView<Medico> lstMedico;

    @FXML
    private TextField tfFiltro;

    @FXML
    private ComboBox cbEspecialidades;

    @FXML
    private ComboBox cbHorarios;

    @FXML
    private DatePicker date;

    private RepositorioMedico repositorioMedico;
    private RepositorioAgendamento repositorioAgendamento;
    private Paciente paciente;
    private Medico medico;

    public PacienteAgendamentoConsulta(RepositorioMedico repositorioMedico, Paciente paciente, RepositorioAgendamento repositorioAgendamento){
        this.repositorioMedico = repositorioMedico;
        this.paciente = paciente;
        this.repositorioAgendamento = repositorioAgendamento;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cbEspecialidades.getItems().addAll("Todos", "Pediatra", "Cardiologista","Urologista","Ginecologista");
        cbEspecialidades.setValue("Todos");

        cbHorarios.setPromptText("Selecione um horário");

        listarTodos();

        cbEspecialidades.valueProperty().addListener((observable, oldValue, newValue) -> {
            if ("Todos".equals(newValue)) {
                listarTodos();
            } else {
                String especialidade = newValue.toString();
                atualizarListaMedicosFiltrados(especialidade);
            }
        });

        date.setDisable(true);
        cbHorarios.setDisable(true);
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

    @FXML
    private void pegarId(MouseEvent evento){
        medico = lstMedico.getSelectionModel().getSelectedItem();

        date.setDisable(false);
    }
    
    private void atualizarTabela(List<Medico> medico){
        lstMedico.getItems().clear();
        lstMedico.getItems().addAll(medico);
    }

    @FXML
    void agendar(ActionEvent event) {
        LocalDate data = date.getValue();
        String hora = (String) cbHorarios.getValue();
        hora = hora+":00";
        String status = "Aguardando";

        Agendamento agendamento = new Agendamento(paciente, medico, data, hora, status);

        Resultado resultado = repositorioAgendamento.agendar(agendamento);

        Alert alert;
        
        if(resultado.foiErro()){
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
        }else{
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
        }

        alert.showAndWait();
    }

    @FXML
    void consultar(ActionEvent event) {
        Resultado<ArrayList<String>> resultado = repositorioAgendamento.verificarDisponibilidadeHorario(medico.getId(), date.getValue());

        if (resultado.foiSucesso()) {
            List<String> horariosIndisponiveis = resultado.comoSucesso().getObj();
            System.out.println(horariosIndisponiveis);

            List<String> horariosDisponiveis = new ArrayList<>(Arrays.asList(
                "8:00", "8:30", "9:00", "9:30", "10:00",
                "10:30", "11:00", "11:30", "12:00", "12:30",
                "13:00", "13:30", "14:00", "14:30", "15:00",
                "15:30", "16:00", "16:30", "17:00"
            ));

            horariosDisponiveis.removeAll(horariosIndisponiveis);
            
            cbHorarios.getItems().setAll(horariosDisponiveis);
            cbHorarios.setPromptText("Selecione um horário");
        }

        cbHorarios.setDisable(false);
    }   

    @FXML
    void voltar(ActionEvent event) {
        App.popScreen();
    }
    
}
