package org.isd.ihs.decasims.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The Class PasswordGenerator.
 */
public class PasswordGenerator {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		// type in raw plain text password to encode
		String rawPasswordToEncode = "admin";
		// String rawPasswordToEncode = "deca";

		String encodedPassword = encoder.encode(rawPasswordToEncode);
		System.out.println("Encoded pwd: " + encodedPassword);
	}

}
