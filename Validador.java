public class Validador {
    private Validador() {
    }

    public static boolean campoPreenchido(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }

    public static boolean descricaoValida(String descricao) {
        if (!campoPreenchido(descricao)) {
            return false;
        }

        String texto = descricao.trim();
        String[] palavras = texto.split("\\s+");
        return texto.length() >= 15 && palavras.length >= 3;
    }

    public static boolean cpfValido(String cpf) {
        if (!campoPreenchido(cpf) || !cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            return false;
        }

        String numeros = cpf.replaceAll("\\D", "");
        if (todosDigitosIguais(numeros)) {
            return false;
        }

        return digitoValido(numeros, 9) == Character.getNumericValue(numeros.charAt(9))
                && digitoValido(numeros, 10) == Character.getNumericValue(numeros.charAt(10));
    }

    private static boolean todosDigitosIguais(String valor) {
        char primeiro = valor.charAt(0);

        for (int i = 1; i < valor.length(); i++) {
            if (valor.charAt(i) != primeiro) {
                return false;
            }
        }

        return true;
    }

    private static int digitoValido(String cpf, int tamanhoBase) {
        int soma = 0;
        int peso = tamanhoBase + 1;

        for (int i = 0; i < tamanhoBase; i++) {
            int numero = Character.getNumericValue(cpf.charAt(i));
            soma += numero * (peso - i);
        }

        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }
}
