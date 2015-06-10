package org.molgenis.data.semanticsearch.string;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class StemmerTest
{
	private final Stemmer customPorterStemmer = new Stemmer("EN");

	@Test
	public void replaceIllegalCharacter()
	{
		assertEquals(customPorterStemmer.replaceIllegalCharacter("Hello__world!"), "Hello world");
		assertEquals(customPorterStemmer.replaceIllegalCharacter("Hello__world! 1234"), "Hello world 1234");
		assertEquals(customPorterStemmer.replaceIllegalCharacter("Hello_#45_world! 1234"), "Hello 45 world 1234");
	}

	@Test
	public void stemPhrase()
	{
		assertEquals(customPorterStemmer.cleanStemPhrase("i like smoking!"), "i like smoke");
		assertEquals(customPorterStemmer.cleanStemPhrase("it`s not possibilities!"), "it s not possibl");
	}

	@Test
	public void stem()
	{
		assertEquals(customPorterStemmer.stem("use"), "use");
		assertEquals(customPorterStemmer.stem("hypertension"), "hypertens");
	}
}
