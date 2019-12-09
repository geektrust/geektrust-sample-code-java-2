package com.solve.challenge;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class GoldenCrown {

	static Set<String> allianceList ;
	static List<String> kingdomList;
	static{ 
		allianceList =new HashSet<>();
		kingdomList = Arrays.asList("air","land","water","ice","fire");
		}
	
	public static void main(String[] args) {
	
		String contestingKing = "King Shah";
		System.out.println("Welcome "+contestingKing );
		System.out.println("Please Provide the encrypted message (FORMAT:- 'kingdomName,cryptedValue'), to form the ally with the other Kingdoms");
		System.out.println("You need to have support from three kingdoms to Rule....");
		
		
		String input = null;
		int counter =0;
		int limit =5;
		
		try(Scanner scanner = new Scanner(System.in)){
		
			while(limit > counter) {
				int chances = limit-counter;
				
				System.out.println("Do you want to send Encrypted Message ? press (Y) to continue , any  other key  to quit,  "+((chances!=1)?" you have "+chances+" chances left ":" this is the last chance"));
				String choice =	 scanner.nextLine();
				
				if(choice.equalsIgnoreCase("Y")) {
					System.out.println("Enter the Message now");
					input = scanner.nextLine();
					
					if(input.trim().length() == 0) {
						System.err.println("Input cannot be empty or only spaces.");
						continue;
					}
					input = input.toLowerCase();
					String[] kingdomMessage = input.split(",",2);
					if(kingdomMessage.length !=2) {
						System.err.println("Please provide a valid format for the input. ");
						continue;
					}
					String kingdomName =kingdomMessage[0].trim();
					if(kingdomName.equalsIgnoreCase("space")) {
						System.err.println("Space is the competing kingdom , so please provide message to the other available kingdoms.");
						continue;
					}
					
					if(!kingdomList.contains(kingdomName)) {
						System.err.println("Please provide a valid kingdom name. ");
						continue;
					}
					
					String message = kingdomMessage[1].trim();
					if(kingdomName.length()==0 || message.length()==0) {
						System.err.println("Land or crypted message cannot be empty or only spaces.");
						continue;
					}
					boolean isAnAlly =  validateAlliance(kingdomName,message);
					System.out.println("The isAlly :: "+isAnAlly);
					if(isAnAlly) {
						allianceList.add(kingdomMessage[0]);
					}
					++counter;
				}else 
					break;
			
				
			}
		}
		System.out.println("Who is the ruler of Southeros? ");
		if(allianceList.size() >=3) {
			System.out.println(contestingKing);
			System.out.println("Allies of Ruler? ");
			System.out.println(allianceList);
		}else {
			System.out.println("None");
			System.out.println("Allies of Ruler? ");
			System.out.println("None");
		}
		
	}
	
	
	public static boolean validateAlliance(String kingdom,String message) {
		boolean result = false;
		
		switch(kingdom) {
		
		case "air":{
			result = isAirKingdomAllied(message);
			break;
		}
		case "ice" :{
			result = isIceKingdomAllied(message);
			break;
		}
		
		case "land" :{
			result =isLandKingdomAllied(message);
			break;
		}
		
		
		case "fire" :{
			result = isFireKingdomAllied(message);
			break;
		}
		
		case "water" :{
			result = isWaterKingdomAllied(message);
			break;
		}
		
		case "space" :{
			result = isSpaceKingdomAllied(message);
			break;
		}
		default :
			
		}
			
		
		return result;
	}
	
	public static boolean isAirKingdomAllied(String message) {
		boolean result = false;
		if(message.contains("o") && message.contains("w") && message.contains("l"))
			result=true;
		return result;
	}
	
	public static boolean isIceKingdomAllied(String message) {
		boolean result = false;
		
		String  temp =  message.replaceAll("m", "");
		int length = message.length() - temp.length();
		if(message.contains("o") && message.contains("a") && message.contains("t") && message.contains("h") && length >=3)
			result=true;
		
		return result;
	}
	
	public static boolean isLandKingdomAllied(String message) {
		boolean result = false;
		
		if(message.contains("p") && message.contains("n") && message.contains("d") && message.indexOf("a") !=-1 && (message.indexOf("a") != message.lastIndexOf("a")))
				result = true;
		
		
		return result;
	}
	
	public static boolean isFireKingdomAllied(String message) {
		boolean result = false;
		if(message.contains("d") && message.contains("r") && message.contains("a") && message.contains("g") && message.contains("o") && message.contains("n"))
			result=true;
		
		return result;
	}
	
	public static boolean isWaterKingdomAllied(String message) {
		boolean result = false;
		
		if(message.contains("c") && message.contains("t") && message.contains("p") && message.contains("u") && message.contains("s") && message.indexOf("o") !=-1 && (message.indexOf("o") != message.lastIndexOf("o")))
				result = true;
		return result;
	}
	
	public static boolean isSpaceKingdomAllied(String message) {
		boolean result = false;
		
		if(message.contains("g") && message.contains("o") && message.contains("r") && message.contains("i") && message.contains("a") && message.indexOf("l") !=-1 && (message.indexOf("l") != message.lastIndexOf("l")))
				result = true;
		return result;
	}
	
}
