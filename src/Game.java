public class Game {

    public String getGameResult(Options choseOne, Options choseTwo) {
        if (choseOne == choseTwo) {
            return "0"; // Empate
        } else if ((choseOne == Options.PEDRA && choseTwo == Options.TESOURA)
                || (choseOne == Options.PAPEL && choseTwo == Options.PEDRA)
                || (choseOne == Options.TESOURA && choseTwo == Options.PAPEL)) {
            return "1"; // jogador Options.PAPEL vence
        } else if ((choseTwo == Options.PEDRA && choseOne == Options.TESOURA)
                || (choseTwo == Options.PAPEL && choseOne == Options.PEDRA)
                || (choseTwo == Options.TESOURA && choseOne == Options.PAPEL)) {
            return "2"; // jogador "2" vence
        } else {
            return "3"; // Jogada fora do padrão
        }
    }
}
