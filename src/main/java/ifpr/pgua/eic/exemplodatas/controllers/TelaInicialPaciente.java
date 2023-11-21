package ifpr.pgua.eic.exemplodatas.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.App;
import ifpr.pgua.eic.exemplodatas.model.entities.Agendamento;
import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.entities.Paciente;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioAgendamento;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioAtendimento;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioDisponibilidadeMedico;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioLogin;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioMedico;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioPaciente;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TelaInicialPaciente implements Initializable{

    @FXML
    private Label bemVindo;

    @FXML
    private TableView<Agendamento> tbConsultas;

    @FXML
    private TableColumn<Agendamento, String> tcData;

    @FXML
    private TableColumn<Agendamento, String> tcMedico;

    @FXML
    private TableColumn<Agendamento, String> tcStatus;
    
    private Paciente paciente;
    private RepositorioMedico repositorioMedico;
    private RepositorioAgendamento repositorioAgendamento;
    private RepositorioPaciente repositorioPaciente;
    private RepositorioLogin repositorioLogin;
    private RepositorioDisponibilidadeMedico repositorioDisponibilidadeMedico;
    private RepositorioAtendimento repositorioAtendimento;

    public TelaInicialPaciente(RepositorioPaciente repositorioPaciente, RepositorioLogin repositorioLogin, RepositorioMedico repositorioMedico, Paciente paciente, RepositorioAgendamento repositorioAgendamento, RepositorioDisponibilidadeMedico repositorioDisponibilidadeMedico, RepositorioAtendimento repositorioAtendimento){
        this.repositorioPaciente = repositorioPaciente;
        this.repositorioLogin = repositorioLogin;
        this.repositorioMedico = repositorioMedico;
        this.paciente = paciente;
        this.repositorioAgendamento = repositorioAgendamento;
        this.repositorioDisponibilidadeMedico = repositorioDisponibilidadeMedico;
        this.repositorioAtendimento = repositorioAtendimento;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        textoLabel();
        loadConsultas();
        configureTableView();
    }

    public void configureTableView() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
        tcData.setCellValueFactory(cellData -> {
            LocalDate data = cellData.getValue().getData(); 
            String dataFormatada = (data != null) ? data.format(dateFormatter) : "";
            return new SimpleStringProperty(dataFormatada);
        });

        tcData.setSortType(TableColumn.SortType.ASCENDING);
        tcData.setComparator((data1, data2) -> {
            LocalDate date1 = LocalDate.parse(data1, dateFormatter);
            LocalDate date2 = LocalDate.parse(data2, dateFormatter);
            return date1.compareTo(date2);
        });
        
        tcMedico.setCellValueFactory(celula -> new SimpleStringProperty(celula.getValue().getMedico().getNome() + ""));
        tcStatus.setCellValueFactory(celula -> new SimpleStringProperty(celula.getValue().getStatus()));

        tcStatus.setCellFactory(column -> {
            return new TableCell<Agendamento, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
        
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
        
                        if ("Aguardando".equals(item)) {
                            setStyle("-fx-text-fill: blue;");
                        } else if ("Confirmada".equals(item)) {
                            setStyle("-fx-text-fill: green;");
                        } else if ("Cancelada".equals(item)) {
                            setStyle("-fx-text-fill: red;");
                        } else {
                            setStyle("");
                        }
                    }
                }
            };
        });    
    }

    public void loadConsultas() {
        Resultado rs = repositorioAgendamento.buscarIdPaciente(paciente.getId());

        if (rs.foiErro()) {
            Alert alert = new Alert(AlertType.ERROR, rs.getMsg());
            alert.showAndWait();
            return;
        }

        List<Agendamento> lista = (List) rs.comoSucesso().getObj();

        lista.sort(Comparator.comparing(Agendamento::getData));

        List<Agendamento> consultasFiltradas = lista.stream()
            .filter(agendamento -> !agendamento.getStatus().equals("Finalizada"))
            .collect(Collectors.toList());

            tbConsultas.setItems(FXCollections.observableArrayList(consultasFiltradas));
    }

    public void textoLabel(){
        if(paciente.getGenero().equals("Masculino")){
            bemVindo.setText("Bem vindo, "+paciente.getNome());
        }else{
            bemVindo.setText("Bem vinda, "+paciente.getNome());
        }
        
    }

    @FXML
    private void agendamento(ActionEvent event){
        App.pushScreen("TELAAGENDAMENTOCONSULTA",o-> new PacienteAgendamentoConsulta(repositorioMedico, paciente, repositorioAgendamento, repositorioDisponibilidadeMedico));
    }

    @FXML
    private void verAtendimentos(ActionEvent event){
        App.pushScreen("ATENDIMENTOSREALIZADOSPACIENTE",o-> new AtendimentosRealizadosPaciente(repositorioAtendimento, paciente));
    }

    @FXML
    private void configuracoes(ActionEvent event){
        App.pushScreen("DADOSPESSOAISPACIENTE",o-> new DadosPessoaisPaciente(repositorioLogin, repositorioPaciente, paciente));
    }

}
