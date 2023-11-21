package ifpr.pgua.eic.exemplodatas.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
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
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class CadastroMedico implements Initializable {

    @FXML
    private ListView<Medico> lstMedico;

    @FXML
    private Accordion accordion;

    @FXML
    private TitledPane cadastrar;

    @FXML
    private TitledPane listar;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfCpf;

    @FXML
    private TextField tfTelefone;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfSalario;

    @FXML
    private TextField tfCRM;

    @FXML
    private TextField tfFiltro;

    @FXML
    private DatePicker date;

    @FXML
    private ComboBox cbGenero;

    @FXML
    private ComboBox cbEspecialidade;

    @FXML
    private TextArea detalhes;

    @FXML
    private Button edit;

    private RepositorioMedico repositorioMedico;
    private RepositorioLogin repositorioLogin;
    private Medico medico;

    public CadastroMedico(RepositorioMedico repositorioMedico, RepositorioLogin repositorioLogin){
        this.repositorioMedico = repositorioMedico;
        this.repositorioLogin = repositorioLogin;
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
            List<Medico> lista = (List)resultado.comoSucesso().getObj();
            Collections.sort(lista, Comparator.comparing(Medico::getNome));
            lstMedico.getItems().addAll(lista);

            /*for (Medico medico : lista) {
                criarLoginParaMedico(medico.getCpf());
            }*/
        }
         
    }

    private void criarLoginParaMedico(String cpf) {
        String senhaPadrao = "ok";
    
        Resultado rs = repositorioLogin.criarLoginMedico(cpf, senhaPadrao);
    
        if (rs.foiErro()) {
            Alert alert = new Alert(AlertType.ERROR, rs.getMsg());
            alert.showAndWait();
        } else {
            System.out.println("Login criado para o CPF: " + cpf);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cbGenero.getItems().addAll("Masculino", "Feminino");
        cbEspecialidade.getItems().addAll("Pediatra", "Cardiologista","Urologista","Ginecologista");

        listar();
        criarMedicos();
        edit.setDisable(true);
    }

    @FXML
    void cadastrar(ActionEvent event){
        String nome = tfNome.getText();
        String cpf = tfCpf.getText();
        String telefone = tfTelefone.getText();
        String email = tfEmail.getText();
        LocalDate data = date.getValue();
        String genero = (String) cbGenero.getValue();
        String especialidade = (String) cbEspecialidade.getValue();
        String crm = tfCRM.getText();
        String salarioString = tfSalario.getText();
        salarioString = salarioString.replace(",", "."); 
        double salario = Double.parseDouble(salarioString);

        Medico medico = new Medico(nome, cpf, telefone, email, data, genero, true, salario, especialidade, crm);
        Resultado resultado = repositorioMedico.criar(medico);

        Alert alert;
        if(resultado.foiErro()){
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
        }else{
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
        }


        atualizar();
        alert.showAndWait();

    }

    @FXML
    private void atualizar(){
        Resultado<ArrayList<Medico>> resultado = repositorioMedico.listar();

        if(resultado.foiSucesso()){
            atualizarTabela(resultado.comoSucesso().getObj());
        }
    }

    @FXML
    private void mostrarDetalhes(MouseEvent evento){
        medico = lstMedico.getSelectionModel().getSelectedItem();
       
        if(medico != null){
            detalhes.clear();
            detalhes.appendText("ID: "+medico.getId()+"\n");
            detalhes.appendText("Nome: "+medico.getNome()+"\n");
            detalhes.appendText("Especialidade: "+medico.getEspecialidade()+"\n");
            detalhes.appendText("CPF: "+medico.getCpf()+"\n");
            detalhes.appendText("Telefone: "+medico.getTelefone()+"\n\n");
            detalhes.appendText("Email: "+medico.getEmail()+"\n");
            detalhes.appendText("Data de nascimento: "+medico.getDataNascimento()+"\n");
            boolean cadastro = medico.isAtive();
            if(cadastro==true){
                detalhes.appendText("Situação do cadastro: Ativo");
            }
            else{
                detalhes.appendText("Situação do cadastro: Inativo");
            }
            edit.setDisable(false);
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
    private void limparCampos(ActionEvent event) {
        tfNome.clear();
        tfCpf.clear();
        tfTelefone.clear();
        tfEmail.clear();
        tfSalario.clear();
        tfCRM.clear();
        tfFiltro.clear();

        cbGenero.getSelectionModel().clearSelection();
        cbEspecialidade.getSelectionModel().clearSelection();

        date.setValue(null);

        detalhes.clear();
    }


    @FXML
    void voltar(ActionEvent event) {
        App.popScreen();
    }

    private void criarMedicos() {
        ArrayList<Medico> lista = new ArrayList<>();
        
        /*lista.add(new Medico("Mariana Oliveira", "12345678903", "555-555-5560", "mariana@example.com", criarData(), "Feminino", true, 5000, "Pediatra", "1234"));
        lista.add(new Medico("Carlos Lima", "98765432111", "555-555-5561", "carlos@example.com", criarData(), "Masculino", true, 6000, "Cardiologista", "5678"));
        lista.add(new Medico("Luisa Pereira", "55555555557", "555-555-5562", "luisa@example.com", criarData(), "Feminino", true, 5500, "Ginecologista", "9012"));
        lista.add(new Medico("Fábio Silva", "11111111113", "555-555-5563", "fabio@example.com", criarData(), "Masculino", true, 7000, "Cardiologista", "3456"));
        lista.add(new Medico("Juliana Souza", "99999999997", "555-555-5564", "juliana@example.com", criarData(), "Feminino", true, 6300, "Pediatra", "7890"));
        lista.add(new Medico("Rafael Santos", "12345678904", "555-555-5565", "rafael@example.com", criarData(), "Masculino", true, 5800, "Urologista", "2345"));
        lista.add(new Medico("Renata Oliveira", "98765432112", "555-555-5566", "renata@example.com", criarData(), "Feminino", true, 6200, "Ginecologista", "6789"));
        lista.add(new Medico("Gustavo Lima", "55555555558", "555-555-5567", "gustavo@example.com", criarData(), "Masculino", true, 5700, "Cardiologista", "1234"));
        lista.add(new Medico("Vitória Silva", "11111111114", "555-555-5568", "vitoria@example.com", criarData(), "Feminino", true, 6700, "Urologista", "5678"));
        lista.add(new Medico("Maurício Souza", "99999999996", "555-555-5569", "mauricio@example.com", criarData(), "Masculino", true, 5800, "Ginecologista", "9012"));
        
        for (Medico medico : lista) {
            repositorioMedico.criar(medico);
        }*/
    }

    private LocalDate criarData() {
        Random random = new Random();
        LocalDate dataNascimento = LocalDate.of(1990 + random.nextInt(30), random.nextInt(12) + 1, random.nextInt(28) + 1);
        return dataNascimento;
    }

    @FXML
    private void editarInformacoes(ActionEvent event){
        App.pushScreen("EDITARINFORMACOESMEDICO",o-> new EditarInformacoesMedico(repositorioMedico, medico, repositorioLogin));
    }
}
