package br.jus.trt23.nucleo.handlers;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Tempo {

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
        return quantidade;
    }

    public static Long dias(final Date dataInicio, final Date dataFim) {
        long quantidade = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(dataInicio);
        while (between(c.getTime(), dataInicio, dataFim)) {
            quantidade++;
            c.add(Calendar.DATE, 1);
        }
        return quantidade;
    }

    public static Long diasDesconsiderandoInicioFimIgual(final Date dataInicio, final Date dataFim) {
        long quantidade = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(dataInicio);
        while (betweenDesconsiderandoDataIguais(c.getTime(), dataInicio, dataFim)) {
            quantidade++;
            c.add(Calendar.DATE, 1);
        }
        return quantidade;
    }
    
    //para entender a diferenca entre as funções que comparam períodos de 
    //vigência: 
    //isVigentePlenamenteEntre: não interessa se o início da vigência  
    //do objeto corrente é anterior a do período de teste ou se o términdo da
    //vigência do objeto seja posterior ao final do período, mas se, em todo
    //o período de teste, o objeto esteve vigente.
    //Se periodo está contido o.vigencia
    //isVigenteParcialmente: se o objeto corrente tiver sua vigência coincidindo
    //com qualquer parte do período teste, retorna verdadeiro.  
    //Se existe (o.vigencia intersecção periodo)
    //isVigenteEstritamenteEntre: a vigência do objeto corrente deve estar
    //compreendida no período de teste.
    //Se o.vigencia está contido periodo.
    //retorna verdadeiro se a entidade tiver vigencia em todo o período
    //informado
    public Boolean isP1VigentePlenamenteEmP2(LocalDate p1Inicio, LocalDate p1Fim,
            LocalDate p2Inicio, LocalDate p2Fim) {
        return (p1Inicio.compareTo(p2Inicio) <= 0 && (null == p1Fim
                || p1Fim.compareTo(p2Fim) >= 0));
    }

    //retorna verdadeiro se a entidade tiver vigencia que abranja a data 
    //informada
    public Boolean isP1VigenteParcialmenteEmP2(LocalDate p1Inicio, LocalDate p1Fim,
            LocalDate p2Inicio, LocalDate p2Fim) {
        //início posterior
        if ((p1Inicio.compareTo(p2Fim) <= 0) && (null == p1Fim || p1Fim.compareTo(p2Inicio) >= 0)) {
            return true;
        } else {
        }
        return false;
    }

    public Boolean isP1VigenteEstritamenteEmP2(LocalDate p1Inicio, LocalDate p1Fim,
            LocalDate p2Inicio, LocalDate p2Fim) {
        //o inicio do objeto corrente deve ser igual ou superior ao inicio do 
        //período testado
        if (p1Inicio.compareTo(p2Inicio) >= 0) {
            //se o período testado estiver em aberto, fim==null, retorna true,
            //contudo, se posteriormente for definida uma vigência final para 
            //o objeto pai, poderá gerar inconsistência            
            if (null == p2Fim) {
                return true;
            }
            //se o período de teste tiver uma data de término definida,
            //mas o objeto corrente não a tiver, acusa incompatibilidade de 
            //vigência
            if (null == p1Fim) {
                return false;
            }

            //se o objeto corrente possui vigência até a data final do período
            //de teste retorna verdadeiro, senão, o contrário
            return p1Fim.compareTo(p2Fim) <= 0;
        }
        return false;
    }
    
}
