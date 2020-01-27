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
    private boolean voter;
    private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static HashMap<String, Short> voterCount;
    private String voterName;
    private String voterDate;
    private static int count = 0;
    private StringBuilder builder = new StringBuilder();


    public XMLHandlerString(){
        voterCount = new HashMap<>();
    }

    public StringBuilder getBuilder(){
        return builder;
    }
    public  HashMap<String, Short> getVoterCount(){
        return voterCount;
    }

    public int getSizeVoterCount(){
        return voterCount.size();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        try
        {
            if(qName.equals("voter") && voter == false)
            {

                Date birthDay = birthDayFormat.parse(attributes.getValue("birthDay"));
                voterName = attributes.getValue("name");
                voterDate = birthDayFormat.format(birthDay).replace(".", "-");
                voter = true;
            }
            else if(qName.equals("visit") && voter)
            {
                count++;
                builder.append((builder.length()==0 ? "" : ",") +
                        "('" + voterName + "', '" + voterDate + "',1)");
                if(count == 30000) {
                    insertData(builder);
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
            voter = false;
        }
    }

    public void DuplicatedVotersString()
    {
        System.out.println("Duplicated voters:");
        for (String voter: voterCount.keySet()){
            int count = voterCount.get(voter);
            if (count > 1){
                System.out.println("\t" +voter + " - " + count);
            }
        }
        System.out.println();
    }

    public void insertData (StringBuilder builder) throws SQLException {
        Connection connection = DBConnection.getConnection();
        Statement stmt = connection.createStatement();
        String sql = "INSERT INTO voter_count(name, birthDate, count) " +
                "VALUES" + builder.toString() +
                " ON DUPLICATE KEY UPDATE count = count + 1";
        stmt.addBatch(sql);
        int[] a = stmt.executeBatch();
        System.out.print("объем сделанных записей = " + a.length);
    }
}


