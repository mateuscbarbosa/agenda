package br.com.petshoptchutchucao.agenda.infra;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

public class PasswordGeneratorPassay {

	private PasswordGenerator gen = new PasswordGenerator();
	private CharacterData lowerCaseChar = EnglishCharacterData.LowerCase;
	private CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChar);
	
	private CharacterData upperCaseChar = EnglishCharacterData.UpperCase;
	private CharacterRule upperCaseRule = new CharacterRule(upperCaseChar);
	
	private CharacterData digitChars = EnglishCharacterData.Digit;
	private CharacterRule digitRule = new CharacterRule(digitChars);
	
	private CharacterData especialChars = new CharacterData() {
		
		@Override
		public String getErrorCode() {
			return "erro";
		}
		
		@Override
		public String getCharacters() {
			return "!@#$%^&*()_+";
		}
	};
	
	private CharacterRule especialCharRule = new CharacterRule(especialChars);
	
	public String generatePassword() {
		lowerCaseRule.setNumberOfCharacters(2);
		upperCaseRule.setNumberOfCharacters(2);
		digitRule.setNumberOfCharacters(2);
		especialCharRule.setNumberOfCharacters(1);
		
		String password = gen.generatePassword(8, especialCharRule, lowerCaseRule, upperCaseRule, digitRule);
		return password;
		
	}
}
