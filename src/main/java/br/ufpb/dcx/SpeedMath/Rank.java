package br.ufpb.dcx.SpeedMath;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class Rank {

    private List<Usuario> usuarios;

    public Rank () {
        this.usuarios = new ArrayList<>();
    }

    public List<Usuario> getUsuarios () {
        return this.usuarios;
    }

    public boolean adicionarAoRank (Usuario usuario) {
        return this.usuarios.add(usuario);

    }

    public List<Usuario> atualizarPontuacao (List<Usuario> rank, List <Usuario> usuarioLogado) {
        Usuario novoUsuario = usuarioLogado.get(0);
        boolean encontrado = false;
        for (int k = 0; k < rank.size(); k++) {
            Usuario usuario = rank.get(k);
            if (usuario.getNomeUsuario().equals(novoUsuario.getNomeUsuario())) {
                encontrado = true;
                if(novoUsuario.getScore() > usuario.getScore()) {
                    rank.set(k, novoUsuario);
                }
            }
    }
        if (!encontrado) {
            rank.add(novoUsuario);
        }
        return rank;
    }


    public void  ordenarRank () {
        Collections.sort(usuarios, new Comparator<Usuario>(){

            public int compare(Usuario o1, Usuario o2) {
                return Integer.compare(o2.getScore(), o1.getScore() );

            }
        });
    }

    public boolean removerDoRank(String nomeUsuario) {
        for(int k = 0; k < usuarios.size(); k++) {
            Usuario a = usuarios.get(k);
            if(a.getNomeUsuario().equalsIgnoreCase(nomeUsuario)) {
                usuarios.remove(k);
                return true;

            }
        }
        return false;
    }

    public String exibirRank () {

        if (usuarios.size() == 0) {
            return "Ranking ainda está vazio. Jogue para entrar no rank!";
        }

        ordenarRank();
        String resultado = "";
        int posicao = 1;
        for (Usuario k : usuarios) {
            resultado += posicao + "°" + " Player: " + k.getNomeUsuario() + " - " + "pts: " + k.getScore() + "\n";
            posicao++;

        }

        return resultado;
    }

}