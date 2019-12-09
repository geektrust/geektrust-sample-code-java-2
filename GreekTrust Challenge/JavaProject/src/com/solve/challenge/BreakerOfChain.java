package com.solve.challenge;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class BreakerOfChain {


	static List<String> messageList = Arrays.asList("Summer is coming", "a1d22n333a4444p", "oaaawaala",
			"zmzmzmzaztzozh", "Go, risk it all", "Let's swing the sword together", "Die or play the tame of thrones",
			"Ahoy! Fight for me with men and money", "Drag on Martin!",
			"When you play the tame of thrones, you win or you die.",
			"What could we say to the Lord of Death? Game on?", "Turn us away, and we will burn you first",
			"Death is so terribly final, while life is full of possibilities.", "You Win or You Die",
			"His watch is Ended", "Sphinx of black quartz, judge my dozen vows",
			"Fear cuts deeper than swords, My Lord.", "Different roads sometimes lead to the same castle.",
			"A DRAGON IS NOT A SLAVE.", "Do not waste paper", "Go ring all the bells",
			"Crazy Fredrick bought many very exquisite pearl, emerald and diamond jewels.",
			"The quick brown fox jumps over a lazy dog multiple times.",
			"We promptly judged antique ivory buckles for the next prize.", "Walar Morghulis: All men must die.");

	static List<String> kingdomList = Arrays.asList("air", "land", "water", "ice", "fire", "space");
	static int ballotCount;

	public static void main(String[] args) throws IOException {
		String[] competitors = null;
		boolean validInput = false;
		List<String> competitorsList = null;
		
		
		printTheWinnerDetails(null, "None");
		try (Scanner scanner = new Scanner(System.in)) {
			while (!validInput) {

				System.out.println("Enter the kingdoms competing to be the ruler: ");
				String kingdoms = scanner.nextLine();

				if (kingdoms.trim().length() == 0) {
					System.err.println("please provide a valid input.");
					continue;
				}

				kingdoms = kingdoms.trim().toLowerCase();
				competitors = kingdoms.split("\\s+");
				competitorsList = Arrays.asList(competitors);
				if (!kingdomList.containsAll(competitorsList)) {
					System.err.println("please provide valid competitors name,seperated by space");
					continue;
				}
				
				if(competitorsList.containsAll(kingdomList)) {
					System.err.println("All the kingdoms cannot compete");
					continue;
				}

				validInput = true;
			}
		}
		ballotCount=0;
		validate(competitorsList);
		
	}

	public static void validate(List<String> competitorsList) {
		
		++ballotCount;
		int noOfBallots = 6;
		Map<String, Alliances> allianceList = new HashMap<>();
		List<String> ballotList = new ArrayList<>();

		for (String competingKingdom : competitorsList) {
			for (String receivingKingdom : kingdomList) {
				if (!competingKingdom.equalsIgnoreCase(receivingKingdom)) {
					String message = messageList.get(generateRandomNumber(messageList.size()));
					String ballotPaper = competingKingdom + "$" + receivingKingdom + "$" + message;
					ballotList.add(ballotPaper);
				}
			}
		}

		

		List<String> anAlly = new ArrayList<>();
		Alliances alliance = null;
		System.out.print("\r\n");
		for (int counter = 0; counter < noOfBallots; counter++) {

			String ballot = ballotList.get(generateRandomNumber(ballotList.size()));
			System.out.println("The Chosen Ballot is "+ballot);
			String[] messages = ballot.split("\\$", 3);
			String kingdom = messages[0];
			String receivingKingdom = messages[1];
			String message = messages[2].toLowerCase();

			if (!competitorsList.contains(receivingKingdom) && !(anAlly.contains(receivingKingdom))) {
				if (GoldenCrown.validateAlliance(receivingKingdom, message)) {
					alliance = allianceList.get(kingdom);

					anAlly.add(receivingKingdom);
					if (alliance == null) {
						alliance = new Alliances();
						alliance.setCount(1);
						if (alliance.getAlliances() == null) {
							alliance.setAlliances(new ArrayList<>());
						} 
					} else {
						alliance.setCount(alliance.getCount() + 1);
					
					}
					alliance.getAlliances().add(receivingKingdom);
					allianceList.put(kingdom, alliance);
				}

			} 
		}
		System.out.print("\r\n");
		if (allianceList.size() == 0) {
			printBallotResult(competitorsList, allianceList, null,false);
			validate(competitorsList);
		} else {
			checkForTie(allianceList, competitorsList);
		}
	}

	public static void checkForTie(Map<String, Alliances> allianceList, List<String> competitorsList) {

		Set<String> alliances = allianceList.keySet();


		if (alliances.size() == 1) {
			printBallotResult(competitorsList, allianceList, alliances.stream().findFirst().get(),false);

		} else {
			boolean tieflag = false;
			int tieCount = 0;
			int noOfTiedCandidate = 0;
			List<String> allies = null;
			List<String> tieList = new ArrayList<>();
			String elected = null;
			int maxCount = 0;
			for (String alliance : alliances) {
				Alliances allianceDescription = allianceList.get(alliance);
				if (allianceDescription.getCount() > maxCount) {
					maxCount = allianceDescription.getCount();
					elected = alliance;
					allies = new ArrayList<>();
					allies.addAll(allianceDescription.getAlliances());
				} else if (allianceDescription.getCount() == maxCount) {
					if (!tieList.contains(elected))
						tieList.add(elected);
					if (tieCount == allianceDescription.getCount() || tieCount == 0) {
						tieCount = allianceDescription.getCount();

						tieList.add(alliance);
					} else {
						tieCount = allianceDescription.getCount();
						tieList.clear();
						tieList.add(alliance);
					}
					noOfTiedCandidate = tieList.size();

				}
			}

			if ((maxCount == tieCount && noOfTiedCandidate > 1)) {
				tieflag = true;
			} else {
				tieflag = false;
			}
			if (tieflag) {
				printBallotResult(competitorsList, allianceList, null,tieflag);
				validate(tieList);
			} else {
				printBallotResult(competitorsList, allianceList, elected,tieflag);
			}
		}

	}

	public static int generateRandomNumber(int size) {
		double randomNumber = Math.random() * size;
		return (int) Math.floor(randomNumber);
	}

	public static void printBallotResult(List<String> CompetitorsList, Map<String, Alliances> allianceList,String elected,boolean tieFlag) {
		System.out.println("Results after round " + ballotCount + " ballot count");
		int count =0;
		int size = allianceList.size();
		if (size == 0) {
			for (String competitor : CompetitorsList)
				System.out.println("Output: Allies for " + competitor + " : 0 ");
		} else if (size >= 1) { 
			for (String competitor : CompetitorsList) {
				    Alliances alliance =  allianceList.get(competitor);
				    if(alliance==null) {
				    	count =0;
				    }else {
				    	count= allianceList.get(competitor).getCount();
				    }
					System.out.println("Output: Allies for " + competitor +": "+count);
			}
			
			if(!tieFlag && elected !=null)
			printTheWinnerDetails(allianceList, elected);
		} 
	}

	public static void printTheWinnerDetails(Map<String, Alliances> allianceList, String elected) {
		
		 System.out.println("Who is the ruler of Southeros?" );
		 System.out.println("Output: " + elected);
		if(allianceList ==null) {
			System.out.println("Allies of Ruler");
			System.out.println("Output :None");
		}else if (allianceList.size() >= 1) {
				List<String> allies = allianceList.get(elected).getAlliances();
				System.out.println("Allies of Ruler? \r\n"
						+ " Output: " + allies);
			}	
		}
	}

