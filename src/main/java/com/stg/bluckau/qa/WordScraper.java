package com.stg.bluckau.qa;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WordScraper
{
	private static WebDriver driver;
	private HashMap<String, Integer> wordMap;

	public WordScraper()
	{
		driver = Automation.getDriver();
		wordMap = new HashMap<String, Integer>();
	}

	public void scrapePage()
	{
		List<WebElement> elementList = driver.findElements(By.xpath("//*"));
		// System.out.println("Size of list is: " + elementList.size());
		int prevNum;
		String elemText = "";
		for (WebElement webElement : elementList)
		{
			try
			{
				elemText = webElement.getText().replace("\n", " ").trim();
			} catch (org.openqa.selenium.StaleElementReferenceException e)
			{
				System.err.println("Warning: Stale element");
			}

			String[] elemStrings = elemText.split(" ");
			for (String elem : elemStrings)
			{
				prevNum = (wordMap.containsKey(elem)) ? wordMap.get(elem) : 0;
				if (!"".equals(elem) && elem.matches("[a-zA-Z]+"))
				{
					wordMap.put(elem, ++prevNum);
				}
			}
		}
	}


	public void writeFile(String fileName)
	{
		System.out.println("Writing file of dictionary words to: " + fileName);
		try
		{
			System.out.println("Writing file " + fileName);
			OutputStream output = new FileOutputStream(fileName);
			Properties props = new Properties();

			for (String string : wordMap.keySet())
			{
				props.put(string, (wordMap.get(string)).toString());
			}

			props.store(output, null);

			output.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
