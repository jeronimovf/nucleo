package br.jus.trt23.nucleo.handlers;

import static br.jus.trt23.nucleo.handlers.Texto.limparStringCaracteresLetras;

public class CnpjCpf {

    private static final int[] PESOS_CPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] PESOS_CNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;

        return soma > 9 ? 0 : soma;
    }

    public static boolean isCpf(String cpf) {
        if (cpf == null) {
            return false;
        } else {
            cpf = limparStringCaracteresLetras(cpf);
        }
        return cpf.length() == 11;
    }

    public static boolean isCpfValido(String cpf) {
        if (cpf == null) {
            return false;
        } else {
            cpf = limparStringCaracteresLetras(cpf);
        }
        if (cpf.length() != 11) {
            return false;
        }
        if (cpf.replace(cpf.substring(0, 1), "").length() == 0) {
            return false;
        }
        Integer digito1 = calcularDigito(cpf.substring(0, 9), PESOS_CPF);
        Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, PESOS_CPF);

        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    public static boolean isCnpjValido(String cnpj) {
        if (cnpj == null) {
            return false;
        } else {
            cnpj = limparStringCaracteresLetras(cnpj);
        }
        if (cnpj.length() != 14) {
            return false;
        }
        if (cnpj.replace(cnpj.substring(0, 1), "").length() == 0) {
            return false;
        }
        Integer digito1 = calcularDigito(cnpj.substring(0, 12), PESOS_CNPJ);
        Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, PESOS_CNPJ);

        return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
    }

    public static String aplicarMascaraCpf(String cpf) {
        String cpfMascara = limparStringCaracteresLetras(cpf);
        return String.format("%s.%s.%s-%s", cpfMascara.substring(0, 3), cpfMascara.substring(3, 6), cpfMascara.substring(6, 9), cpfMascara.substring(9));
    }

    public static String aplicarMascaraCnpj(String cnpj) {
        String cnpjMascara = limparStringCaracteresLetras(cnpj);
        return String.format("%s.%s.%s/%s-%s", cnpjMascara.substring(0, 2), cnpjMascara.substring(2, 5), cnpjMascara.substring(5, 8), cnpjMascara.substring(8, 12), cnpjMascara.substring(12));
    }


}
