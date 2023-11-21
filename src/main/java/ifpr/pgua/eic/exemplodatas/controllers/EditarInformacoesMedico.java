package ifpr.pgua.eic.exemplodatas.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.App;
import ifpr.pgua.eic.exemplodatas.model.entities.Medico;
import ifpr.pgua.eic.exemplodatas.model.entities.Paciente;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioLogin;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioMedico;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class EditarInformacoesMedico implements Initializable{

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfCpf;

    @FXML
    private TextField tfTelefone;

    @FXML
    private TextField tfEmail;

    @FXML
    private DatePicker date;

    @FXML
    private ComboBox cbGenero;

    @FXML
    private ComboBox cbEspecialidade;

    @FXML
    private TextField tfCrm;

    @FXML
    private TextField tfSalario;

    private RepositorioMedico repositorioMedico;
    private Medico medico;
    private RepositorioLogin repositorioLogin;
    

    public EditarInformacoesMedico(RepositorioMedico repositorioMedico, Medico medico, RepositorioLogin repositorioLogin) {
        this.repositorioMedico = repositorioMedico;
        this.medico = medico;
        this.repositorioLogin = repositorioLogin;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        System.out.println(medico);
        atualizar();
    }

    private void atualizar(){
        Resultado<Medico> resultado = repositorioMedico.buscarPorId(medico.getId());

        medico = resultado.comoSucesso().getObj();

        if (resultado.foiSucesso()) {
            tfNome.setText(medico.getNome());
            tfCpf.setText(medico.getCpf());
            tfTelefone.setText(medico.getTelefone());
            tfEmail.setText(medico.getEmail());
            date.setValue(medico.getDataNascimento());
            cbGenero.setValue(medico.getGenero());
            cbEspecialidade.setValue(medico.getEspecialidade());
            tfCrm.setText(medico.getCrm());
            tfSalario.setText(medico.getSalario()+"");
        }
    }

    @FXML
    private void salvar(ActionEvent event){
        String nome = tfNome.getText();
        String novoCpf = tfCpf.getText();
        String telefone = tfTelefone.getText();
        String email = tfEmail.getText();
        LocalDate data = date.getValue();
        String genero = (String) cbGenero.getValue();
        String salarioString = tfSalario.getText();
        salarioString = salarioString.replace(",", "."); 
        double salario = Double.parseDouble(salarioString);

        Medico medicoNovo = new Medico(medico.getId(), nome, novoCpf, telefone, email, data, genero, true, salario, cbEspecialidade.getValue().toString(), tfCrm.getText());
        
        Resultado resultado;

        Alert alert;

        if (!novoCpf.equals(medico.getCpf())) {
            Resultado<Paciente> rs = repositorioMedico.buscarPorCpf(novoCpf);

            Paciente pacienteRetorno = rs.comoSucesso().getObj();

            if(pacienteRetorno == null){
                resultado = repositorioMedico.alterarDados(medicoNovo);
                resultado = repositorioLogin.alterarCpf(medicoNovo.getCpf(), novoCpf);
                alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
            }else{
                resultado = repositorioMedico.alterarDados(medicoNovo);
                alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
            }
        }else{
            resultado = repositorioMedico.alterarDados(medicoNovo);
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
        }

        atualizar();
        alert.showAndWait();
    }

    @FXML
    private void voltar(ActionEvent event){
        App.popScreen();
    }
    
}
