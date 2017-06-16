package com.stg.bluckau.qa;

public class ChallengeEight
{
	public static void main( String[] args )
	{
		String localSitePattern = "(http(s)?://)?(www)?skiutah.com/.*";
		WebCrawler webCrawler = new WebCrawler(localSitePattern);

		webCrawler.recursivelyWalk("https://skiutah.com");
		// webCrawler.recursivelyWalk("https://skiutah.com");

		Automation.quit();
	}
}
