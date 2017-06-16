package com.stg.bluckau.qa;

public class ChallengeSeven
{
	public static void main( String[] args )
	{

		String localSitePattern = "(http(s)?://)?(www)?skiutah.com/.*";

		WebCrawler webCrawler = new WebCrawler("https://skiutah.com", localSitePattern);

		// webCrawler.recursivelyWalk("https://skiutah.com", 5, 5);
		webCrawler.recursivelyWalk("https://skiutah.com");

		Automation.quit();
	}
}
