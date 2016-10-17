package br.jus.trt23.nucleo.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Util {

    @SuppressWarnings("unchecked")
    public static <T> List<T> castList(List<? extends T> lista, Class<T> tipo) {
        return (List<T>) lista;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Cloneable> List<T> deepCloneList(List<T> original) {
        if (original == null || original.size() < 1) {
            return new ArrayList<>();
        }

        try {
            int originalSize = original.size();
            Method cloneMethod = original.get(0).getClass().getDeclaredMethod("clone");
            List<T> clonedList = new ArrayList<>();

            for (int i = 0; i < originalSize; i++) {
                clonedList.add((T) cloneMethod.invoke(original.get(i)));
            }
            return clonedList;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            return new ArrayList<>();
        }
    }

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

    public static boolean isSabado(final Date dia) {
        if (dia != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(dia);
            int diaSemana = c.get(Calendar.DAY_OF_WEEK);
            return (diaSemana == Calendar.SATURDAY);
        }
        return false;
    }

    public static boolean isDomingo(final Date dia) {
        if (dia != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(dia);
            int diaSemana = c.get(Calendar.DAY_OF_WEEK);
            return (diaSemana == Calendar.SUNDAY);
        }
        return false;
    }

    public static boolean isFinalDeSemana(final Date dia) {
        if (dia != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(dia);
            int diaSemana = c.get(Calendar.DAY_OF_WEEK);
            return (diaSemana == Calendar.SUNDAY || diaSemana == Calendar.SATURDAY);
        }
        return false;
    }

    /**
     * Recebe como parâmetro horário no formato HH:mm
     *
     * @param horas
     * @return minutos
     */
    public static int tranformarMinutos(final String horas) {
        int resultado = 0;
        boolean isNegativo = false;
        if (horas != null) {
            String horasTexto = horas;
            if (horasTexto.startsWith("-")) {
                isNegativo = true;
                horasTexto = horas.substring(1);
            }
            String[] arrayHoraMinuto = horasTexto.split(":");
            String hora = arrayHoraMinuto[0];
            String minuto = arrayHoraMinuto[1];

            resultado = (new Integer(hora) * 60) + new Integer(minuto);
            if (isNegativo) {
                resultado = resultado * -1;
            }
        }
        return resultado;
    }

    /**
     * Recebe como parâmetro um horário no formato HH:mm e verifica se o mesmo é
     * válido.
     *
     * @param horaMinuto
     * @return boolean
     */
    public static boolean isHoraMinutoValido(final String horaMinuto) {
        if (horaMinuto != null) {
            String[] arrayHoraMinuto = horaMinuto.split(":");
            return (arrayHoraMinuto[0] != null && new Integer(arrayHoraMinuto[0]) < 24 && arrayHoraMinuto[1] != null && new Integer(arrayHoraMinuto[1]) < 60);
        }
        return false;
    }

    /**
     * Recebe como parâmetro um horário no formato HH:mm e verifica se o mesmo é
     * válido.
     *
     * @param horaMinuto
     * @return boolean
     */
    public static boolean isMinutoValido(final String horaMinuto) {
        if (horaMinuto != null) {
            String[] arrayHoraMinuto = horaMinuto.split(":");
            return (arrayHoraMinuto[1] != null && new Integer(arrayHoraMinuto[1]) < 60);
        }
        return false;
    }

    /**
     * Recebe como par�metro quantidade em mintuos e transforma em hor�rio no
     * formato HH:mm
     *
     * @param minutos
     * @return
     */
    public static String tranformarHoraMinuto(final long minutos) {
        NumberFormat nfDoisdigitos = NumberFormat.getInstance();
        nfDoisdigitos.setMinimumIntegerDigits(2);

        long hora = 0;
        long minuto = 0;

        boolean isNegativo = false;

        if (minutos > 0) {
            hora = minutos / 60;
            minuto = minutos % 60;
        } else if (minutos < 0) {
            isNegativo = true;
            hora = minutos / 60 * -1;
            minuto = minutos % 60 * -1;
        }
        String resultado = nfDoisdigitos.format(hora) + ":" + nfDoisdigitos.format(minuto);
        return isNegativo ? ("-" + resultado) : resultado;
    }

    public static Date trunc(final Date data) {
        if (data != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(data);
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            return c.getTime();
        }
        return null;
    }

    public static Date primeiroDiaMes(final Date data) {
        if (data != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(data);
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.getActualMinimum(Calendar.DAY_OF_MONTH), 0, 0, 0);
            return c.getTime();
        }
        return null;
    }

    public static Date primeiroDiaAno(final Date data) {
        if (data != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(data);
            c.set(c.get(Calendar.YEAR), Calendar.JANUARY, 1, 0, 0, 0);
            return c.getTime();
        }
        return null;
    }

    public static Date ultimoDiaMes(final Date data) {
        if (data != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(data);
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
            return c.getTime();
        }
        return null;
    }

    public static Date dataUltimoMinutoSegundo(final Date data) {
        if (data != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(data);
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
            return c.getTime();
        }
        return null;
    }

    public static Date ultimoDiaAno(final Date data) {
        if (data != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(data);
            c.set(c.get(Calendar.YEAR), Calendar.DECEMBER, 31, 23, 59, 59);
            return c.getTime();
        }
        return null;
    }

    public static Date adicionarDia(final Date data, int quantidade) {
        if (data != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(data);
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            c.add(Calendar.DATE, quantidade);
            return c.getTime();
        }
        return null;
    }

    public static Date adicionarMes(final Date data, int quantidade) {
        if (data != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(data);
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            c.add(Calendar.MONTH, quantidade);
            return c.getTime();
        }
        return null;
    }

    public static Date adicionarAno(final Date data, int quantidade) {
        if (data != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(data);
            c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            c.add(Calendar.YEAR, quantidade);
            return c.getTime();
        }
        return null;
    }

    public static boolean between(final Date data, final Date inicio, final Date fim) {
        if (data != null && inicio != null && fim != null) {
            return data.compareTo(inicio) >= 0 && data.compareTo(fim) <= 0;
        }
        return false;
    }

    public static boolean betweenDesconsiderandoDataIguais(final Date data, final Date inicio, final Date fim) {
        if (data != null && inicio != null && fim != null) {
            return data.compareTo(inicio) >= 0 && data.compareTo(fim) < 0;
        }
        return false;
    }

    /**
     * Recebe como parâmetro um valor do tipo Date
     *
     * @param data
     * @return data
     */
    public static Date proximoDiaUtil(final Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        while (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            c.add(Calendar.DATE, 1);
        }
        return c.getTime();
    }

    public static Long diasUteis(Date dataInicio, Date dataFim) {
        long quantidade = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(dataInicio);
        while (between(c.getTime(), dataInicio, dataFim)) {
            if (!isFinalDeSemana(c.getTime())) {
                quantidade++;
            }
            c.add(Calendar.DATE, 1);
        }
        return Long.valueOf(quantidade);
    }

    public static Long dias(final Date dataInicio, final Date dataFim) {
        long quantidade = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(dataInicio);
        while (between(c.getTime(), dataInicio, dataFim)) {
            quantidade++;
            c.add(Calendar.DATE, 1);
        }
        return Long.valueOf(quantidade);
    }

    public static Long diasDesconsiderandoInicioFimIgual(final Date dataInicio, final Date dataFim) {
        long quantidade = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(dataInicio);
        while (betweenDesconsiderandoDataIguais(c.getTime(), dataInicio, dataFim)) {
            quantidade++;
            c.add(Calendar.DATE, 1);
        }
        return Long.valueOf(quantidade);
    }

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
