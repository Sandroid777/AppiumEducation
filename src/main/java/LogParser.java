
import org.openqa.selenium.logging.LogEntry;
import java.util.Date;
import java.util.List;

public class LogParser {

    private List<LogEntry> logEntryList;
    private Date startTime;

    LogParser(List<LogEntry> log, Date date){
        logEntryList = log;
        startTime = date;
    }

    //поиск строки по логам
    public boolean FindStringInLog(String findString ){
        //итератор для перебора массива
        int i = 0;
        //перебор массива
        for(LogEntry l :  logEntryList ){
            //фильтрую по тегу ReportManager и по времени тапа
            if(logEntryList.get(i).getTimestamp() > startTime.getTime()
                    && logEntryList.get(i).getMessage().contains("D/[Ya:ReportManager]")){
                //ищу строку
                if(logEntryList.get(i).getMessage().contains(findString)){
                    //совпадение найдено
                    return true;
                }
            }
            //не нашли идём на сл.круг
            i++;
        }
        //не нашли
        return false;
    }
}
