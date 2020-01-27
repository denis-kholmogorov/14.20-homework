import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class XMLHandlerString extends DefaultHandler
{
    private boolean isVoter;
    private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private String voterName;
    private String voterDate;
    private static int count = 0;
    private StringBuilder builder = new StringBuilder();

    public StringBuilder getBuilder(){
        return builder;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        try
        {
            if(qName.equals("voter") && isVoter == false)
            {

                Date birthDay = birthDayFormat.parse(attributes.getValue("birthDay"));
                voterName = attributes.getValue("name");
                voterDate = birthDayFormat.format(birthDay).replace(".", "-");
                isVoter = true;
            }
            else if(qName.equals("visit") && isVoter)
            {
                count++;
                builder.append((builder.length()==0 ? "" : ",") +
                        "('" + voterName + "', '" + voterDate + "',1)");
                if(count == 200000) {
                    DBConnection.executeMultiInsert(builder);
                    builder = new StringBuilder();
                    count=0;
                }
            }
            else{
                throw new RuntimeException();
            }
        }catch (ParseException | RuntimeException | SQLException e){
            e.getMessage();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
    {
        if(qName.equals("voter"))
        {
            isVoter = false;
        }
    }

}


