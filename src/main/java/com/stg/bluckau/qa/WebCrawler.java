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
		System.out.println("LIMIT = " + limit);

		List<String> currentLinks = new ArrayList<String>();

		List<WebElement> urlsList = driver.findElements(By.tagName("a"));
		System.out.println("Size is: " + urlsList.size());
		int count = 0;
		for (WebElement e : urlsList)
		{
			if (limit > 0 && count == limit)
			{
				// TODO: Take this out or make it configurable
				System.out.println("QUIITING AFTER FINDING " + limit);
				System.out.println("Going to return a list of: " + currentLinks.size() + "links");
				return currentLinks;
			}

			String linkText = e.getAttribute("href");
			System.out.println("Link Text: " + linkText);
			currentLinks.add(linkText);
			count++;
		}
		System.out.println("Going to return a list of: " + currentLinks.size() + "links");
		return currentLinks;
	}

	private boolean linkWorks(String url)
	{
		HttpURLConnection theConnection;
		System.err.println("Checking link: " + url);

		try
		{
			theConnection = (HttpURLConnection) new URL(url).openConnection();
			return theConnection.getResponseCode() == 200;
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e)
		{
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

	// public void recursivelyWalk()
	// {
	// System.out.println("running recursively walk without limit");
	// recursivelyWalk(URL, 0);
	// }

	public void recursivelyWalk(String sURL, int limit)
	{
		System.out.println("Running the basic recursively walk function against URL: " + sURL);

		System.out.println("*********  Going to GET: " + sURL);
		driver.get(sURL);
		visitedLinks.add(sURL);
		level++;
		System.out.println("                                                 currentLevel = " + level);
		System.out.println("getting current links");
		List<String> links = getCurrentLinks(limit);
		System.out.println("Done getting current links");
		System.out.println("Strings to process for next recusion: " + links.size());
		Boolean prefixFound;
		for (String thisLink : links)
		{
			System.out.println("PROCESSING LINK");
			prefixFound = false;
			System.out.println("Checking for prefixes for " + sURL);
			for (String p : prefixes)
			{
				// System.out.println("s is: " + s);
				// System.out.println("p is: " + p);
				if (sURL.startsWith(p))
				{
					System.out.println("match found");
					prefixFound = true;
				}
			}

			if (prefixFound == false)
			{
				System.out.println("Web site is outside of range");
			}
			else if (visitedLinks.contains(thisLink))
			{
				System.out.println("already visited.");
			}
			else if (brokenLinks.contains(thisLink))
			{
				System.out.println("link was marked broken");
			}

			else if (linkWorks(thisLink))
			{
				System.out.println("Going to call the next recursivelyWalk with" + thisLink);
				recursivelyWalk(thisLink, limit);
			}
		}
		level--;
	}

}
