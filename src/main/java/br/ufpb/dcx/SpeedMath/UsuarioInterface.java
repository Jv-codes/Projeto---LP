package br.ufpb.dcx.SpeedMath;
import java.io.IOException;
import java.util.List;

public interface UsuarioInterface {

    boolean cadastrarUsuario (String nomeUsuario, String senha) throws  UsuarioJaExisteException;
    boolean removerUsuario(String nomeUsuario) throws  UsuarioNaoEncontradoException;
    List<Usuario> pesquisarUsuario(String nomeUsuario) throws UsuarioNaoEncontradoException;
    Boolean autenticarUsuario(String nomeUsuario, String senha) throws SenhaInvalidaException;
    List<Usuario> ListaUsuario();
    void salvarDados()throws IOException;
    void recuperarUsuarios() throws IOException;
}
