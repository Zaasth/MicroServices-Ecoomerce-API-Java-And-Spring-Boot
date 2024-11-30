package com.ecommerce.Util;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DataUtil {

    public String pegarDiasUteis(Integer dias){
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataCalculada = adicionarDiasUteis(dataAtual, dias);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //System.out.println("Data atual + 3 dias úteis: " + dataCalculada.format(formatter));
        return dataCalculada.format(formatter);
    }

    private LocalDate adicionarDiasUteis(LocalDate data, int diasUteis) {
        int diasAdicionados = 0;
        while (diasAdicionados < diasUteis) {
            data = data.plusDays(1);
            if (data.getDayOfWeek() != DayOfWeek.SATURDAY && data.getDayOfWeek() != DayOfWeek.SUNDAY) {
                diasAdicionados++;
            }
        }
        return data;
    }
}
