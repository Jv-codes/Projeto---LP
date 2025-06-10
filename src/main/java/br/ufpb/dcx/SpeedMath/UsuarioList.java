package br.ufpb.dcx.SpeedMath;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioList implements UsuarioInterface {
    Rank sistemaRank = new Rank();
    public static List<Usuario> usuarios;
    public GravadorDeUsuario gravador;

    public UsuarioList() {
        this.usuarios = new ArrayList<>();
        this.gravador = new GravadorDeUsuario();
    }


    @Override
    public boolean cadastrarUsuario(String nomeUsuario, String senha) throws UsuarioJaExisteException {
        Usuario novo = new Usuario(nomeUsuario, senha);
        if (!usuarios.contains(novo)) {
            return usuarios.add(novo);
        }

        throw new UsuarioJaExisteException("Nome de usuário já está em uso.");
    }

    @Override
    public boolean removerUsuario(String nomeUsuario) throws UsuarioNaoEncontradoException {
        for (int k = 0; k < usuarios.size(); k++) {
            Usuario a = usuarios.get(k);
            if (a.getNomeUsuario().equalsIgnoreCase(nomeUsuario)) {
                usuarios.remove(k);
                return true;

            }
        }
        throw new UsuarioNaoEncontradoException("Usuário não encontrado.");

    }

    @Override
    public List<Usuario> ListaUsuario() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public Boolean autenticarUsuario(String nomeUsuario, String senha) throws SenhaInvalidaException {
        for (Usuario k : usuarios) {
            if (k.getNomeUsuario().equals(nomeUsuario) && k.getSenha().equals(senha)) {
                return true;
            }
        }
        throw new SenhaInvalidaException("Nome de usuário ou senha inválidos");
    }

    @Override
    public List<Usuario> pesquisarUsuario(String nomeUsuario) throws UsuarioNaoEncontradoException {
        List<Usuario> jogadores = new ArrayList<>();
        for (Usuario k : usuarios) {
            if (k.getNomeUsuario().equalsIgnoreCase(nomeUsuario)) {
                jogadores.add(k);
            }
        }
        if (jogadores.size() != 0) {
            return jogadores;
        }
        throw new UsuarioNaoEncontradoException("Usuário não encontrado.");
    }

    @Override
    public void salvarDados() throws IOException {
        this.gravador.gravarUsuarios(this.usuarios);
    }

    private void adicionarUsuarioDireto(Usuario usuario) {
        if (!usuarios.contains(usuario)) {
            usuarios.add(usuario);
        }
    }

    public void recuperarUsuarios() throws IOException {
        List<Usuario> usuariosRecuperados = this.gravador.carregarUsuarios();
        for (Usuario usuario : usuariosRecuperados) {
            this.adicionarUsuarioDireto(usuario);
            sistemaRank.adicionarAoRank(usuario);
        }
    }
}
