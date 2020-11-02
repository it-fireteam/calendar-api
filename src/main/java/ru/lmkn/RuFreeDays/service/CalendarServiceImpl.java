package ru.lmkn.RuFreeDays.service;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.lmkn.RuFreeDays.model.Day;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Log4j2
public class CalendarServiceImpl implements CalendarService {

    private final String URL;

    public CalendarServiceImpl(@Value("${url.consultant}") String url){
        this.URL = url;
    }

    @Override
    public List<Day> findWeekends(String year) {
        List<Day> days = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.connect(String.format("%s/%s/", URL, year))
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("")
                    .get();
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }

        Elements yearLi = doc.select("ul.list-year");
        final String yearItem = yearLi.select("strong").text().length()>0 ? yearLi.select("strong").text() : yearLi.select("b").text() ;

        doc.select("table.cal")
                .not("td.inactively")
                .select("th.month")
                .forEach(m -> m.parent()
                .parent()
                .parent()
                .select("td.weekend")
                        .stream()
                        .map(w -> mapToDay(w.text(), m.text(), yearItem, w.className()))
                        .forEach(days::add));

        return days;
    }

    private Day mapToDay(String day, String month, String year, String type) {
        Day cday = new Day();
        cday.setDate(LocalDate.parse(String.format("%02d.%s.%s",
                Integer.valueOf(day),
                stringMonthToDigit(month),
                year), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        cday.setType(type);
        return cday;
    }

    private String stringMonthToDigit (String month) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat("MMM").parse(month));
        } catch (ParseException e) {
            log.error(e.getMessage());
            return null;
        }
        int monthInt = cal.get(Calendar.MONTH) + 1;

        return String.format("%02d", monthInt);
    }
}
