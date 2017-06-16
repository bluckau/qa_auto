package com.stg.bluckau.qa;

import java.util.ArrayList;
import java.util.List;

public class ChallengeEight
{
	public static void main( String[] args )
	{
		List<String> prefixes = new ArrayList<String>();
		String localSitePattern = "(http(s)?://)?(www)?skiutah.com/.*";
		WebCrawler webCrawler = new WebCrawler("https://skiutah.com", localSitePattern);

		webCrawler.recursivelyWalk("https://skiutah.com", 5, 5);
		// webCrawler.recursivelyWalk("https://skiutah.com");
		webCrawler.printBrokenLinks();
		Automation.quit();
	}
}
