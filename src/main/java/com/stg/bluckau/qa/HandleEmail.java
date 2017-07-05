package com.stg.bluckau.qa;

public interface HandleEmail
{
	// Dispatch email. This could mean sending via home grown smtp call or it
	// could mean saving a file with options for Jenkins (etc.)
	public void dispatchEmail(EmailProperties p);
}
