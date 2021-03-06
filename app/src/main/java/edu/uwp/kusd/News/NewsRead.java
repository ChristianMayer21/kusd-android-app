package edu.uwp.kusd.News;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import static edu.uwp.kusd.R.id.text;

public class NewsRead {
    private List<NewsItems> newsItems= new ArrayList<NewsItems>();
    private NewsItems newsItem;
    private String title;
    private String date;
    private String desc;
    private String text;


    public List<NewsItems> getEmployees() {
        return newsItems;
    }

    public List<NewsItems> parse(InputStream is) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser  parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("node")) {
                            // create a new instance of employee
                            newsItem = new NewsItems(title,date,desc);
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("node")) {
                            // add employee object to list
                            newsItems.add(newsItem);
                        } else if (tagname.equalsIgnoreCase("title")) {
                            newsItem.setNewsItem(text);
                        }else if (tagname.equalsIgnoreCase("date")) {
                            newsItem.setNewsDate(text);
                        }  else if (tagname.equalsIgnoreCase("story")) {

                            newsItem.setNewsContent(text.replaceAll("<[^>]+>",""));
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}

        return newsItems;
    }

}

