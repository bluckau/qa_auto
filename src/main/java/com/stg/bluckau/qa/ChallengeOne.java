package com.stg.bluckau.qa;


public class ChallengeOne 
{
    public static void main( String[] args )
    {
    	MainPage mp = new MainPage();
  		System.out.println("Loading Main page");
  		mp.pageLoad();

		String title = Automation.getPageTitle();
   		System.out.println("Title of Page is: " + title);
    	Automation.quit();
    }
}
