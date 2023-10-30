package ifpr.pgua.eic.exemplodatas;

import ifpr.pgua.eic.exemplodatas.controllers.CadastroFuncionario;
import ifpr.pgua.eic.exemplodatas.controllers.CadastroMedico;
import ifpr.pgua.eic.exemplodatas.controllers.CadastroPaciente;
import ifpr.pgua.eic.exemplodatas.controllers.ClinicaOpcoes;
import ifpr.pgua.eic.exemplodatas.controllers.PacienteAgendamentoConsulta;
import ifpr.pgua.eic.exemplodatas.controllers.Principal;
import ifpr.pgua.eic.exemplodatas.controllers.TelaInicialPaciente;
import ifpr.pgua.eic.exemplodatas.controllers.TelaLoginPaciente;
import ifpr.pgua.eic.exemplodatas.model.daos.FabricaConexoes;
import ifpr.pgua.eic.exemplodatas.model.daos.FuncionarioDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.JDBCFuncionarioDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.JDBCLoginDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.JDBCMedicoDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.JDBCPessoaDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.LoginDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.MedicoDAO;
import ifpr.pgua.eic.exemplodatas.model.daos.PessoaDAO;
import ifpr.pgua.eic.exemplodatas.model.entities.Pessoa;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioFuncionario;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioLogin;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioMedico;
import ifpr.pgua.eic.exemplodatas.model.repositories.RepositorioPessoa;
import io.github.hugoperlin.navigatorfx.BaseAppNavigator;
import io.github.hugoperlin.navigatorfx.ScreenRegistryFXML;

/**
 * JavaFX App
 */
public class App extends BaseAppNavigator {

    private PessoaDAO pessoaDAO = new JDBCPessoaDAO(FabricaConexoes.getInstance());
    private RepositorioPessoa repositorioPessoa = new RepositorioPessoa(pessoaDAO);

    private MedicoDAO medicoDAO = new JDBCMedicoDAO(FabricaConexoes.getInstance());
    private RepositorioMedico repositorioMedico = new RepositorioMedico(medicoDAO);

    private FuncionarioDAO funcionarioDAO = new JDBCFuncionarioDAO(FabricaConexoes.getInstance());
    private RepositorioFuncionario repositorioFuncionario = new RepositorioFuncionario(funcionarioDAO);

    private LoginDAO loginDAO = new JDBCLoginDAO(FabricaConexoes.getInstance());
    private RepositorioLogin repositorioLogin = new RepositorioLogin(loginDAO);

    private Pessoa pessoa;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public String getHome() {
        // TODO Auto-generated method stub
        return "PRINCIPAL";
    }


    @Override
    public String getAppTitle() {
        // TODO Auto-generated method stub
        return "Exemplo de datas";
    }

    @Override
    public void registrarTelas() {
        registraTela("PRINCIPAL", new ScreenRegistryFXML(App.class, "principal.fxml", o->new Principal()));

        registraTela("CLINICAOPCOES", new ScreenRegistryFXML(App.class, "clinicaOpcoes.fxml", o->new ClinicaOpcoes()));

        registraTela("CADASTRO", new ScreenRegistryFXML(App.class, "cadastroPaciente.fxml", o->new CadastroPaciente(repositorioPessoa)));

        registraTela("CADASTRARFUNCIONARIO", new ScreenRegistryFXML(App.class, "cadastrarFuncionario.fxml", o->new CadastroFuncionario(repositorioFuncionario)));

        registraTela("CADASTRARMEDICO", new ScreenRegistryFXML(App.class, "cadastrarMedico.fxml", o->new CadastroMedico(repositorioMedico)));

        registraTela("TELALOGINPACIENTE", new ScreenRegistryFXML(App.class, "telaLoginPaciente.fxml", o->new TelaLoginPaciente(repositorioMedico,repositorioLogin, repositorioPessoa)));

        registraTela("TELAINICIALPACIENTE", new ScreenRegistryFXML(App.class, "telaInicialPaciente.fxml", o->new TelaInicialPaciente(repositorioMedico, pessoa)));

        registraTela("TELAAGENDAMENTOCONSULTA", new ScreenRegistryFXML(App.class, "pacienteAgendamentoConsulta.fxml", o->new PacienteAgendamentoConsulta(repositorioMedico, pessoa)));
    }

}