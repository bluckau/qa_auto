package com.stg.bluckau.qa;

import java.util.ArrayList;
import java.util.List;

public class ChallengeSix
{
	public static void main( String[] args )
	{
		List<String> prefixes = new ArrayList<String>();

		// TODO: use regex
		prefixes.add("http://skiutah.com");
		prefixes.add("https://skiutah.com");
		prefixes.add("http://www.skiutah.com");
		prefixes.add("https://www.skiutah.com");

		WebCrawler webCrawler = new WebCrawler("https://skiutah.com", prefixes);
		webCrawler.recursivelyWalk("https://skiutah.com", 0, 0);
		webCrawler.printBrokenLinks();
	}
}
