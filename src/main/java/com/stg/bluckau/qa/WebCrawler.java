package com.stg.bluckau.qa;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebCrawler
{
	private static WebDriver driver;
	private static WebDriverWait wait;
	private Set<String> brokenLinks = new HashSet<String>();
	private Set<String> visitedLinks = new HashSet<String>();
	private List<String> prefixes;
	private int level = 0; // default to zero

	public WebCrawler(String url, List<String> prefixes)
	{
		this.prefixes = prefixes;
		driver = Automation.getDriver();
		wait = Automation.getWait();
		brokenLinks = new HashSet<String>();
	}

	public List<String> getCurrentLinks(int limit)
	{
		System.out.println("GCL: LIMIT = " + limit);

		List<String> currentLinks = new ArrayList<String>();

		List<WebElement> urlsList = driver.findElements(By.tagName("a"));
		System.out.println("Size of list is: " + urlsList.size());
		int count = 0;
		for (WebElement webElement : urlsList)
		{
			if (limit > 0 && count == limit)
			{
				// TODO: Take limit out or make it configurable
				return currentLinks;
			}

			String linkText = webElement.getAttribute("href");
			System.err.println("GCL: Link Text: " + linkText);
			currentLinks.add(linkText);
			count++;
		}

		return currentLinks;
	}

	private boolean linkWorks(String url)
	{
		System.err.println("LW: Checking link: " + url);
		int responseCode = 0;
		try
		{
			HttpURLConnection theConnection = (HttpURLConnection) new URL(url).openConnection();
			responseCode = theConnection.getResponseCode();
			System.err.println("LW: the response code is: " + responseCode);
			return responseCode == 200;
		} catch (MalformedURLException e)
		{
			System.err.println("Error: The url was malformed");
			return false;
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public void printBrokenLinks()
	{
		System.out.println("********************************************************");
		System.out.println("********* Broken Links:");
		for (String s : brokenLinks )
		{
			System.out.println(s);
		}
	}

	public void recursivelyWalk(String sURL, int limit)
	{
		driver.get(sURL);
		visitedLinks.add(sURL);

		level++;
		System.out.println("                                                 currentLevel = " + level);
		System.err.println("RC: getting current links");
		List<String> links = getCurrentLinks(limit);

		Boolean prefixFound = false;
		for (String thisLink : links)
		{
			prefixFound = false;
			//Check for the prefix to start with the right thing
			for (String prefix : prefixes)
			{
				if (sURL.startsWith(prefix))
					prefixFound = true;
			}

			if (prefixFound == false)
				System.out.println("Web site is outside of range");
			else if (visitedLinks.contains(thisLink))
				System.out.println("already visited.");
			else if (brokenLinks.contains(thisLink))
				System.out.println("link was marked broken");
			else if (linkWorks(thisLink))
				recursivelyWalk(thisLink, limit);

		}//end outer for loop

		level--;
		System.out.println("                                                 currentLevel = " + level);
	} // end recursivelyWalk
}// end class webCrawler
