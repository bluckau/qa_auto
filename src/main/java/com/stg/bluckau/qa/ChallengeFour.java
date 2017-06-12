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

		CompareResorts compareResorts = new CompareResorts();
		compareResorts.pageLoad();

		int miles = compareResorts.getMilesForResort(resortName);
		System.out.printf("\nResort %s is %d miles from the closest airport.", resortName, miles);

    	Automation.quit();
    }
}
