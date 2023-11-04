package ifpr.pgua.eic.exemplodatas.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.App;
import ifpr.pgua.eic.exemplodatas.model.entities.Agendamento;
import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.entities.Pessoa;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioAgendamento;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioMedico;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    
    private Pessoa pessoa;
    private RepositorioMedico repositorioMedico;
    private RepositorioAgendamento repositorioAgendamento;

    public TelaInicialPaciente(RepositorioMedico repositorioMedico, Pessoa pessoa, RepositorioAgendamento repositorioAgendamento){
        this.repositorioMedico = repositorioMedico;
        this.pessoa = pessoa;
        this.repositorioAgendamento = repositorioAgendamento;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        textoLabel();
        loadConsultas();
        configureTableView();
    }

    public void configureTableView() {
        tcData.setCellValueFactory(celula -> new SimpleStringProperty(celula.getValue().getData().toString()));
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
        Resultado rs = repositorioAgendamento.buscarIdPaciente(pessoa.getId());

        if (rs.foiErro()) {
            Alert alert = new Alert(AlertType.ERROR, rs.getMsg());
            alert.showAndWait();
            return;
        }

        List<Agendamento> lista = (List) rs.comoSucesso().getObj();

        tbConsultas.setItems(FXCollections.observableArrayList(lista));
    }

    public void textoLabel(){
        if(pessoa.getGenero().equals("Masculino")){
            bemVindo.setText("Bem vindo, "+pessoa.getNome());
        }else{
            bemVindo.setText("Bem vinda, "+pessoa.getNome());
        }
        
    }

    @FXML
    private void agendamento(){
        App.pushScreen("TELAAGENDAMENTOCONSULTA",o-> new PacienteAgendamentoConsulta(repositorioMedico, pessoa, repositorioAgendamento));
    }

}
