package br.ufpb.dcx.SpeedMath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Questao {

    private int numb1;
    private int numb2;
    private String operacao;
    private int alternativaCerta;
    private List<Integer> alternativas;

    public Questao() {
        gerarQuestaoValidas();
        this.alternativaCerta = verificarResultado();
        this.alternativas = gerarAlternativas();
    }

    public int getAlternativaCerta () {
        return alternativaCerta;
    }

    public void gerarQuestaoValidas() {
        Random random = new Random();
        boolean quetoesValidas = false;
        while (!quetoesValidas) {
            this.numb1 = randomNumero();
            this.numb2 = randomNumero();
            this.operacao = randomOperacao();

            if (!operacao.equals("/") || (numb2 != 0 && numb1 % numb2 == 0)){
                quetoesValidas = true;
            }
        }

    }

    public String verificarQuestao (int repostaUsuario, int alternativaCerta, Usuario usuario) {
        if (repostaUsuario == alternativaCerta) {
            usuario.adicionarScore(10);
            return "Resposta correta! Você ganhou pontos. Pontuação: " + usuario.getScore();
        }else{
            return "Resposta errada. Pontução: " + usuario.getScore();
        }
    }

    public int randomNumero() {
        Random random = new Random();
        int numb = random.nextInt(20)+1;
        return numb;
    }

    public String randomOperacao() {
        Random random = new Random();
        int operacao = random.nextInt(4)+1;
        switch (operacao) {
            case 1:
                return "+";
            case 2:
                return "-";
            case 3:
                return "*";
            case 4:
                return "/";
            default:
        }
        return "";
    }

    private int verificarResultado() {
        switch (operacao) {
            case "+":
                return numb1 + numb2;
            case "-":
                return numb1 - numb2;
            case "*":
                return numb1 * numb2;
            case "/":
                return numb1 / numb2;
            default:
                return 0;
        }
    }

    public List<Integer> gerarAlternativas() {
        List<Integer> alternativas = new ArrayList<>();
        alternativas.add(alternativaCerta);
        alternativas.add(alternativaCerta + 2);
        alternativas.add(alternativaCerta - 3);
        alternativas.add(alternativaCerta + 4);
        Collections.shuffle(alternativas);

        return alternativas;
    }

    public String toString() {
        return "Qual o resultado: " + this.numb1 + " " + this.operacao + " " + this.numb2 + " = ? \n" +
                "alternativas: \n\n"
                +"["+ alternativas.get(0) +"]    "
                +"[ "+ alternativas.get(1) +"]    "
                +"[ "+ alternativas.get(2) +"]    "
                +"[ "+ alternativas.get(3) +"]    \n\n";
    }

}

