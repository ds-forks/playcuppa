package com.appknox.playsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App
{
    public static void main( String[] args )
    {

		Document doc;
		try {
			System.out.println("Enter the App Name to Get Info ::");
			String queryPram = "", baseUrl = "https://play.google.com";
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			queryPram = br.readLine();
			System.out.println("TOP 10 LIST OF " + queryPram + " APPS");
			System.out.println("=========================");
			int count=0;
			doc = Jsoup.connect(baseUrl + "/store/search?q=" + queryPram + "&c=apps").get();
			Set<String> myLinks = new LinkedHashSet<String>();
			Elements links = doc.getElementsByClass("card-click-target");

			for (Element link : links) {
				String alink=link.attr("href");
				myLinks.add(baseUrl + alink);
			}
			Iterator<String> iterator = myLinks.iterator();

			while (iterator.hasNext() && count <10){
				String lnk = iterator.next();
				try{
					doc = Jsoup.connect(lnk).get();
					Elements appTitles = doc.getElementsByClass("id-app-title");
					Element appTitle = appTitles.get(0);
					System.out.println(appTitle.html());
					Elements appDevelopers = doc.getElementsByClass("dev-link");
					Element appDeveloper = appDevelopers.get(1);
					System.out.println(appDeveloper.attr("href").replaceAll("mailto:", ""));
					Elements appScores = doc.getElementsByClass("score");
					Element appScore = appScores.get(0);
					System.out.println(appScore.html());
				}catch(Exception ex){
					System.out.println(ex.getMessage());
				}
				System.out.println("=========================");
				count++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
