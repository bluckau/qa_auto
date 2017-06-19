package com.stg.bluckau.qa;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.UrlValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebCrawler
{
	private static WebDriver driver;
	private Set<String> brokenLinks = new HashSet<String>();
	private Set<String> brokenImages = new HashSet<String>();
	private Set<String> visitedLinks = new HashSet<String>();
	private Set<String> visitedImages = new HashSet<String>();
	// private Queue<String> linksToVisit = new LinkedList<String>();

	// do we need, we are not doing multithreaded ... yet...
	private Queue<String> linksToVisit = new LinkedBlockingQueue<String>();

	private WordScraper wordScraper = new WordScraper();
	private String startingUrl;
	private String sitePattern;
	private int linksLimit = 0;
	private String textFileName = "";
	private static UrlValidator urlValidator;

	// needs refinement
	private static final String imagePattern = ".*(jpg|jpeg|gif|png|svn).*";

	WebCrawler(String url, String sitePattern)
	{
		this(url, sitePattern, "");
	}

	WebCrawler(String url, String sitePattern, String fileName)
	{
		startingUrl = url;
		this.sitePattern = sitePattern;
		driver = Automation.getDriver();
		brokenLinks = new HashSet<String>();
		brokenImages = new HashSet<String>();
		textFileName = fileName;
		urlValidator = new UrlValidator();
	}

	public void setLinksLimit(int limit)
	{
		// limit to how many links each page returns, mainly for testing
		// purposes
		linksLimit = limit;
	}

	public List<String> getCurrentLinks()
	{
		return getCurrentLinks(0);
	}

	public List<String> getCurrentLinks(int limit)
	{
		// System.err.println("GCL: Links LIMIT = " + limit);
		List<String> currentLinks = new ArrayList<String>();
		List<WebElement> urlsList = driver.findElements(By.tagName("a"));
		// System.out.println("Size of list is: " + urlsList.size());
		int count = 0;
		for (WebElement webElement : urlsList)
		{
			// only enforce links limit if it is greater than zero
			if (limit > 0 && count == limit)
			{
				return currentLinks;
			}

			String linkText = webElement.getAttribute("href");
			// System.err.println("GCL: Link Text: " + linkText);
			if (linkText != null && !"".equals(linkText))
			{
				currentLinks.add(linkText);
			}
			count++;
		}

		return currentLinks;
	}// End getCurrentLinks


	public static boolean linkWorks(String url)
	{
		if (url == null || "".equals(url))
		{
			System.err.println("WARNING: Got a null or empty url to check");
			return false;
		}
		if (urlValidator.isValid(url))
		{
			System.err.print("valid url ");
			System.err.println("LW: Checking link: " + url);
			int responseCode = 0;
			try
			{
				HttpURLConnection theConnection = (HttpURLConnection) new URL(url).openConnection();
				responseCode = theConnection.getResponseCode();
				System.err.println("LW: the response code is: " + responseCode);
				// still need some work with handling the response codes
				return responseCode > 199 && responseCode < 400;
			} catch (IOException e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else
		{
			System.err.print("!NOT valid url! ");
			return false;
		}
	}// end linkworks


	public boolean urlIsLocal(String url)
	{
		Pattern pattern = Pattern.compile(sitePattern);
		return pattern.matcher(url).matches();
	}


	public boolean urlIsImage(String url)
	{

		if (url == null || "".equals(url))
		{
			System.err.println("Warning, got a null or empty image url to check!");
			return false;
		}
		Matcher m = Pattern.compile(imagePattern).matcher(url);
		if (m.matches())
		{
			return true;

		}
		return false;
	}


	public static boolean checkImageLoad(WebElement imageElement)
	{

		// System.out.println("image url = " +
		// imageElement.getAttribute("src"));
		Boolean imageIsPresent = (Boolean) ((JavascriptExecutor) driver).executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
				imageElement);
		if (imageIsPresent)
		{
			System.out.println("Image Displayed Successfully");
			return true;
		} else
		{
			System.out.println("Image NOT DISPLAYED");
		}
		return false;
	}


	public void printBrokenLinks()
	{
		System.out.println("********************************************************");
		System.out.println("********* Broken Links:");
		for (String s : brokenLinks)
		{
			System.out.println(s);
		}
	}


	public void printBrokenImages()
	{
		System.out.println("********************************************************");
		System.out.println("********* Broken Images:");
		for (String s : brokenImages)
		{
			System.out.println(s);
		}
	}


	public void walkSite()
	{
		System.out.println("WS: got sURL of " + startingUrl);
		if (textFileName == null || "".equals(textFileName))
		{
			textFileName = "c:\\tmp\\output.txt";
		}
		driver.get(startingUrl);
		linksToVisit.add(startingUrl);

		String currentUrl;
		// Begin consumer loop

		while (true)
		{
			currentUrl = linksToVisit.poll();
			if (currentUrl == null)
			{
				break;
			}

			checkAllImages();
			wordScraper.scrapePage();

			// Parse through links
			System.err.println("RW: getting current links");
			List<String> links = getCurrentLinks(linksLimit);

			for (String thisLink : links)
			{
				if (visitedLinks.contains(thisLink))
				{
					System.err.println("already visited.");
				} else if (brokenLinks.contains(thisLink))
				{
					System.err.println("link was marked broken");
				} else if (!linkWorks(thisLink))
				{
					brokenLinks.add(thisLink);
				} else
				{
					if (urlIsLocal(thisLink))
					{
						linksToVisit.add(thisLink);
					}
				}
			}
		}
		// end consumer loop

		// Wrap up after the last one finishes
		wordScraper.scrapePage();
		wordScraper.writeFile(textFileName);
		printBrokenLinks();
		printBrokenImages();
	}


	protected void checkAllImages()
	{
		List<WebElement> images = driver.findElements(By.tagName("img"));
		String url;
		for (WebElement imageElement : images)
		{
			// check the response code
			url = imageElement.getAttribute("src");

			if (url == null || !"".equals(url))
			{
				continue;
			}

			if (visitedLinks.contains(url))
			{
				System.out.println("Already visited");
				continue;
			}
			else if (brokenLinks.contains(url))
			{
				System.out.println("Already marked broken");
				continue;
			}
			if (urlIsImage(url) && (linkWorks(url) && checkImageLoad(imageElement)))
			{
				visitedImages.add(url);
			} else
			{
				brokenImages.add(url);
			}
		}
	}
}