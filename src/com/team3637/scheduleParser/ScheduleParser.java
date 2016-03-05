package com.team3637.scheduleParser;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ben Goldberg on 2/26/16.
 */
public class ScheduleParser {
    public static void main(String[] args) {
        PrintWriter out = null;
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File("matches.xml");
        int index  = 1;
        try {
            out = new PrintWriter("schedule.csv");
            Document document = builder.build(xmlFile);
            Element channel = document.getRootElement().getChild("channel");
            List list = channel.getChildren("item");

            for (int i = 0; i < list.size(); i++) {
                Element node = (Element) list.get(i);
                String data = node.getChildText("description");
                List<String> teamNums = new ArrayList<>();
                Matcher matcher = Pattern.compile(">[0-9]+").matcher(data);
                while (matcher.find())
                    teamNums.add(matcher.group().substring(1));
                if(teamNums.size() == 6) {
                    out.printf("%d,%d,%s,%s,%s,%s,%s,%s\n",
                            index,
                            index++,
                            teamNums.get(3),
                            teamNums.get(4),
                            teamNums.get(5),
                            teamNums.get(0),
                            teamNums.get(1),
                            teamNums.get(2));
                } else {
                    System.out.printf("Line %d Invalid\n", i);
                }
            }
        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        } finally {
            if(out != null) {
                out.close();
            }
        }
    }
}
