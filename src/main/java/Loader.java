import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Loader
{
    private static StringBuilder builder = new StringBuilder();
    private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    private static HashMap<Voter, Integer> voterCounts = new HashMap<>();

    public static void main(String[] args) throws Exception
    {
        String fileName = "res/data-18M.xml";
        System.out.println("Начало цикла");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandlerString handlerString = new XMLHandlerString();

        // Оптимизтрованный
        long usageS = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        parser.parse(new File(fileName), handlerString);
        handlerString.insertData(handlerString.getBuilder());
        usageS = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usageS)/1024/1024;
        System.out.println(usageS + " MB занимает парсер SAXParser после оптимизации \n");
       // String [] nameAndDate;
       /* if(handlerString.getSizeVoterCount() > 0) {
            for (String key : handlerString.getVoterCount().keySet()) {
                nameAndDate = key.split(" \\| ");
                builder.append((builder.length()==0 ? "" : ",") +
                        "('" + nameAndDate[0] + "', '" + nameAndDate[1] + "',1)");
                //DBConnection.countVoter(nameAndDate[0], nameAndDate[1]);
            }
        }*/
        long a = System.currentTimeMillis();
       // DBConnection.executeMultiInsert(builder);
        System.out.println(System.currentTimeMillis() - a);
        handlerString.DuplicatedVotersString();
        DBConnection.printVoterCounts();

    }
}


/*Duplicated voters:
        Данильчик Галий | 1959-03-01 - 3
        Сенник Досифей | 1989-01-20 - 2
        Вадбольский Витим | 1975-03-17 - 2
        Несмелов Марин | 1972-03-26 - 4
        Агашин Илиодор | 1932-05-01 - 2
        Язвицкий Калистрат | 1986-01-04 - 2
        Рябухин Ермий | 1929-07-08 - 2
        Цысырев Димитриан | 1966-06-08 - 2
        Стаднюк Орест | 1965-12-09 - 3
        Чернопятов Аврелий | 1979-01-09 - 2
        Покровов Аника | 1923-09-30 - 4
        Кубасов Вацлав | 1962-06-02 - 3
        Хомчук Ефрем | 1991-04-27 - 2
        Лапшинов Кирин | 1921-06-02 - 3
        Дарюсин Амфиан | 1996-11-03 - 2*/

//149123