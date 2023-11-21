package ifpr.pgua.eic.exemplodatas.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.App;
import ifpr.pgua.eic.exemplodatas.model.entities.Agendamento;
import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.entities.Paciente;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioAgendamento;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioAtendimento;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioMedico;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioPaciente;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TelaInicialMedico implements Initializable{

    @FXML
    private Label bemVindo;

    @FXML
    private TableView<Agendamento> tbConsultas;

    @FXML
    private TableColumn<Agendamento, String> tcData;

    @FXML
    private TableColumn<Agendamento, String> tcHora;

    @FXML
    private TableColumn<Agendamento, String> tcPaciente;

    @FXML
    private Button btAtendimento;
    
    private RepositorioAgendamento repositorioAgendamento;
    private RepositorioMedico repositorioMedico;
    private RepositorioPaciente repositorioPessoa;
    private Medico medico;
    private Agendamento agendamento;
    private RepositorioAtendimento repositorioAtendimento;

    public TelaInicialMedico(RepositorioAgendamento repositorioAgendamento, RepositorioMedico repositorioMedico, RepositorioPaciente repositorioPessoa, Medico medico, RepositorioAtendimento repositorioAtendimento){
        this.repositorioAgendamento = repositorioAgendamento;
        this.repositorioMedico = repositorioMedico;
        this.repositorioPessoa = repositorioPessoa;
        this.medico = medico;
        this.repositorioAtendimento = repositorioAtendimento;
    }   

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        textoLabel();
        loadConsultas();
        configureTableView();
        btAtendimento.setDisable(true);
    }

    public void textoLabel(){
        if(medico.getGenero().equals("Masculino")){
            bemVindo.setText("Bem vindo, "+medico.getNome());
        }else{
            bemVindo.setText("Bem vinda, "+medico.getNome());
        }
    }

    public void configureTableView() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
        tcData.setCellValueFactory(cellData -> {
            LocalDate data = cellData.getValue().getData(); 
            String dataFormatada = (data != null) ? data.format(dateFormatter) : "";
            return new SimpleStringProperty(dataFormatada);
        });
    
        tcHora.setCellValueFactory(celula -> new SimpleStringProperty(celula.getValue().getHora() + ""));
        tcPaciente.setCellValueFactory(
                celula -> new SimpleStringProperty(celula.getValue().getPaciente().getNome() + ""));
    }

    public void loadConsultas() {
        Resultado rs = repositorioAgendamento.buscarConsultaConfirmada(medico.getId());

        if (rs.foiErro()) {
            Alert alert = new Alert(AlertType.ERROR, rs.getMsg());
            alert.showAndWait();
            return;
        }

        List<Agendamento> lista = (List) rs.comoSucesso().getObj();

        lista.sort(Comparator.comparing(Agendamento::getData));

        tbConsultas.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    private void selecionaAtendimento(MouseEvent event){
        agendamento = tbConsultas.getSelectionModel().getSelectedItem();
        btAtendimento.setDisable(false);
    }

    @FXML
    private void atendimento(ActionEvent event){
        agendamento = tbConsultas.getSelectionModel().getSelectedItem();
        if (agendamento != null) {
            Paciente paciente = agendamento.getPaciente();
            System.out.println(paciente);
            App.pushScreen("FICHAATENDIMENTO", o -> new FichaAtendimento(agendamento, repositorioAgendamento, repositorioAtendimento));
        } else {
            btAtendimento.setDisable(true);
            Alert alert = new Alert(AlertType.INFORMATION, "Nenhum agendamento selecionado.");
            alert.showAndWait();
        }
    }

}
