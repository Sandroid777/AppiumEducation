package ru.sandroid.appiumeducations;

import org.openqa.selenium.logging.LogEntry;
import java.util.Date;
import java.util.List;

class LogParser {

    private List<LogEntry> logEntryList;
    private Date startTime;

    //Конструктор принимает массив лога и дату с кототорой начать поиск
    LogParser(List<LogEntry> log, Date date){
        logEntryList = log;
        startTime = date;
    }

    //Поиск строки по логам
    public boolean FindStringInLog(String findString ){

        //перебор массива
        for(LogEntry l :  logEntryList ){
            //фильтрую по тегу ReportManager и по времени тапа
            if(l.getTimestamp() > startTime.getTime()
                    && l.getMessage().contains("D/[Ya:ReportManager]")){
                //ищу строку
                if(l.getMessage().contains(findString)){
                    //совпадение найдено, отдаём на выходе true
                    return true;
                }
            }
         }
        //не нашли
        return false;
    }
}
