package com.stg.bluckau.qa;



import io.github.bonigarcia.wdm.ChromeDriverManager;

public class ChallengeTwo 
{
    public static void main( String[] args )
    {
    	MainPage mp = new MainPage();

    	ChromeDriverManager.getInstance().setup();

  		System.out.println("Loading Main page");
  		mp.pageLoad();

		String title = Automation.getPageTitle();
   		System.out.println("Title of Page is: " + title);

		System.out.println("Going to sub menu for \"Plan Your Trip\"");
   		mp.goToMenu("Plan Your Trip");

		title = Automation.getPageTitle();
		System.out.println("Title of Page is: " + title);

    	Automation.quit();
    }
}
