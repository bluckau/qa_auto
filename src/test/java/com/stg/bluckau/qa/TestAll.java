package com.stg.bluckau.qa;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestChallengeOne.class, TestChallengeTwo.class, TestChallengeThree.class,
		TestChallengeFour.class, TestChallengeFive.class, TestChallengeSix.class, TestChallengeSeven.class,
		TestChallengeEight.class })

public class TestAll
{
	// the class remains empty,
	// used only as a holder for the above annotations
}
