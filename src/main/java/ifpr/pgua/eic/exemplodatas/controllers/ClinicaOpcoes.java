package ifpr.pgua.eic.exemplodatas.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.exemplodatas.App;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioAgendamento;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioMedico;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class ClinicaOpcoes implements Initializable{

    @FXML
    private Label lbConsultas;
    private boolean isLabelRed = true;
    private Timeline timeline;

    private RepositorioAgendamento repositorioAgendamento;
    private RepositorioMedico repositorioMedico;

    public ClinicaOpcoes(RepositorioAgendamento repositorioAgendamento){
        this.repositorioAgendamento = repositorioAgendamento;
    }
 
    @FXML
    private void cadastrarPacientes(){
        App.pushScreen("CADASTRO");
    }

    @FXML
    private void cadastrarFuncionario(){
        System.out.println("Tela igual as outras de listagem e cadastro");
    }

    @FXML
    private void cadastrarEnfermeiro(){
        System.out.println("Tela igual as outras de listagem e cadastro");
    }

    @FXML
    private void cadastrarMedico(){
        App.pushScreen("CADASTRARMEDICO");
    }

    @FXML
    private void verificarConsultas(){
        App.pushScreen("VERIFICARCONSULTAS",o-> new VerificarConsultas(repositorioAgendamento));
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        verificarConsultasEmAguardo();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> verificarConsultasEmAguardo()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void verificarConsultasEmAguardo() {
        Resultado<Integer> resultado = repositorioAgendamento.verificarNumeroConsultasEmAguardo();
    
        if (resultado.foiSucesso()) {
            Integer count = resultado.comoSucesso().getObj();
    
            if (count > 0) {
                if (isLabelRed) {
                    lbConsultas.setTextFill(Color.RED);
                } else {
                    lbConsultas.setTextFill(Color.BLACK);
                }
                isLabelRed = !isLabelRed;
    
                lbConsultas.setFont(Font.font(null, FontWeight.BOLD, 14));
                lbConsultas.setText(count + " consultas aguardando confirmação");
            } else {
                lbConsultas.setTextFill(Color.BLACK);
                lbConsultas.setFont(Font.font(null, FontWeight.NORMAL, 14));
                lbConsultas.setText(count+" consultas aguardando confirmação");
            }
        }
    }

    @FXML
    private void telaHorariosDeAtendimento(){
        App.pushScreen("ATUALIZARHORARIOSDEATENDIMENTO");
    }
}
