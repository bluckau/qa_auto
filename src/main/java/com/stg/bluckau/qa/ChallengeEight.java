package com.stg.bluckau.qa;

import java.util.ArrayList;
import java.util.List;

public class ChallengeEight
{
	public static void main( String[] args )
	{
		List<String> prefixes = new ArrayList<String>();

		prefixes.add("http://skiutah.com");
		prefixes.add("https://skiutah.com");
		prefixes.add("http://www.skiutah.com");
		prefixes.add("https://www.skiutah.com");

		WebCrawler webCrawler = new WebCrawler("https://skiutah.com", prefixes, "c:\\tmp\\data.txt");
		webCrawler.recursivelyWalk("https://skiutah.com", 5, 5);
		// webCrawler.recursivelyWalk("https://skiutah.com");
		webCrawler.printBrokenLinks();
		Automation.quit();
	}
}
