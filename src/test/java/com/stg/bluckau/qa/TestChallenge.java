package com.stg.bluckau.qa;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public abstract class TestChallenge
{

	@BeforeClass
	public static void beforeClass()
	{
		ChromeDriverManager.getInstance().setup();
		System.err.println("	Running beforeClass");
	}

	@AfterClass
	public static void Afterclass()
	{
		System.err.println("	running afterClass");
		Automation.quit();
		Automation.driver = null;
	}
}
