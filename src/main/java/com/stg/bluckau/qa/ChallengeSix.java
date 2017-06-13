package com.stg.bluckau.qa;

import java.util.ArrayList;
import java.util.List;

public class ChallengeSix
{
	public static void main( String[] args )
	{
		// System.out.println(args[0]);
		// if (args.length < 1)
		// {
		// System.err.println("Must specify url.");
		// System.exit(1);
		// }

		List<String> prefixes = new ArrayList<String>();
		// TODO: use regex
		prefixes.add("http://skiutah.com");
		prefixes.add("https://skiutah.com");
		prefixes.add("http://www.skiutah.com");
		prefixes.add("https://www.skiutah.com");
		System.out.println("****************setting url here for the very first time");
		WebCrawler webCrawler = new WebCrawler("https://skiutah.com", prefixes);
		webCrawler.recursivelyWalk("https://skiutah.com", 4);
		webCrawler.printBrokenLinks();


	}
}
