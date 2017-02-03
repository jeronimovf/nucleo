package br.jus.trt23.nucleo.handlers;

import java.text.Normalizer;
import java.util.Arrays;

public class Texto {
    public static boolean isStringValida(final String param) {
        return param != null && !param.equals("");
    }

    public static String limparStringCaracteresLetras(final String texto) {
        String resultado = null;
        if (texto != null) {
            resultado = texto.replaceAll("\\D+", "");
        }
        return resultado;
    }

    public static String removerAcentos(String str) {
        String resultado = null;
        if (str != null) {
            resultado = Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        }
        return resultado;
    }
    
    public String compactaString(final String str, final int ncaracteres) {
        int espacoLivre;
        //a string não necessita de compactação ou a compactação não é viável
        if (null == str || ncaracteres <= 0 || str.length() <= ncaracteres) {
            return str;
        } else {
            String[] aResultado = str.split("\\s");
            int ultimoIndice = aResultado.length - 1;
            int primeiroTamanho = aResultado[0].length();
            int ultimoTamanho = aResultado[ultimoIndice].length();
            switch (aResultado.length) {
                //a string tem apenas um token
                case 1:
                    return str.substring(0, ncaracteres - 3).concat("...");
                case 2:
                    espacoLivre = ncaracteres - primeiroTamanho;
                    //a string tem dois tokens, mas não há espaço para compactar
                    //o segundo
                    if (espacoLivre <= 3) {
                        return str.substring(0, ncaracteres - 3).concat("...");
                    } //a string tem dois tokens devendo constar o primeiro
                    //na íntegra e os últimos caracteres do último token
                    //até o limite
                    else {
                        return aResultado[0].concat("...").concat(aResultado[ultimoIndice].substring((espacoLivre + 3) - ultimoTamanho));
                    }
                default:
                    espacoLivre = ncaracteres - aResultado[0].length();
                    //a string tem mais de dois tokens, mas não há espaço para
                    //compactar os outros tokens
                    if (espacoLivre <= 3) {
                        return str.substring(0, ncaracteres - 3).concat("...");
                    } //a string tem dois tokens devendo constar o primeiro
                    //na íntegra e os últimos caracteres do último token
                    //até o limite
                    else if (espacoLivre <= ultimoTamanho) {
                        return aResultado[0].concat("...").concat(aResultado[ultimoIndice].substring((espacoLivre + 3) - ultimoTamanho));
                    } //há espaco para compactar os tokens intermediários 
                    //mantendo-se os tokens dos extremos
                    else {
                        //calcula o espaço livre após a extração do primeiro
                        //e do último token além da reticências
                        espacoLivre = ncaracteres - (aResultado[0].length() + 3 + aResultado[aResultado.length - 1].length());
                        if (espacoLivre <= 1) {
                            return aResultado[0].concat("...").concat(aResultado[aResultado.length - 1]);
                        } else {
                            //passa a string intermediaria para extração de iniciais
                            String iniciais = extraiIniciais(Arrays.copyOfRange(aResultado, 1, (aResultado.length - 1)));
                            if (iniciais.length() <= espacoLivre) {
                                return aResultado[0].concat(" ").concat(iniciais).concat("...").concat(aResultado[aResultado.length - 1]);
                            } else {
                                return aResultado[0].concat(" ").concat(iniciais.substring(0, espacoLivre)).concat("...").concat(aResultado[aResultado.length - 1]);
                            }
                        }
                    }
            }
        }
    }

    public String extraiIniciais(String[] aString) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < aString.length; i++) {
            if (i > 0) {
                sb.append(" ");
            }
            sb.append(aString[i].substring(0, 1));
        }
        return sb.toString();
    }    
}
