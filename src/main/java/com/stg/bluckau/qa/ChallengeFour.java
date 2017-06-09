package com.stg.bluckau.qa;

public class ChallengeFour 
{
    public static void main( String[] args )
    {
		String resortName = null;
		if (args.length < 0)
		{
			System.err.println("Error, ski resort required");
			System.exit(1);
		}
		resortName = args[0];
		// System.err.println("resort = %s", resortName);

		CompareResorts compareResorts = new CompareResorts();
		// ChromeDriverManager.getInstance().setup();

		// System.out.println("Loading Main page");
		// mainPage.pageLoad();

		// System.out.println("Going to sub menu for \"Compare All Resorts\"");
		// mainPage.goToSubMenu("Explore", "Compare All Resorts");
		compareResorts.pageLoad();

		int miles = compareResorts.getMilesForResort(resortName);
		System.out.printf("\nResort %s is %d miles from the closest airport.", resortName, miles);

    	Automation.quit();
    }
}
