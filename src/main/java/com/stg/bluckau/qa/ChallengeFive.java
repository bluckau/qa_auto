package com.stg.bluckau.qa;

public class ChallengeFive 
{
    public static void main( String[] args )
    {
		System.out.println(args[0]);
		if (args.length < 3)
		{
			System.err.println("Must specify Category, Subcategory, and Resort.");
			System.exit(1);
		}

		String category = args[0];
		String subCategory = args[1];
		String Resort = args[2];

		SearchPage searchPage = new SearchPage();
		searchPage.pageLoad();
		searchPage.searchForCombination(category, subCategory, Resort);
		searchPage.getSearchResults();
		System.out.println("Search Results");
		System.out.println();
		for (String string : searchPage.getSearchResults())
		{
			System.out.println(string);
		}
    }
}
