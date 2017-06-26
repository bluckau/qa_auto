package com.stg.bluckau.qa;



import io.github.bonigarcia.wdm.ChromeDriverManager;

public class ChallengeThree 
{
    public static void main( String[] args )
    {
    	MainPage mp = new MainPage();

    	ChromeDriverManager.getInstance().setup();

		System.out.println("Loading Main page");
  		mp.pageLoad();

		String title = Automation.getPageTitle();

   		System.out.println("Title of Page is: " + title);

		System.out.println("Going to sub menu for \"Photos\"");
		mp.goToSubMenu("Plan Your Trip", "Photos");

		title = Automation.getPageTitle();
		System.out.println("Title of Page is: " + title);

    	Automation.quit();
    }
}
