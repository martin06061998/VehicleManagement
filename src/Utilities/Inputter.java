/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilities;

import java.util.Scanner;

/**
 *
 * @author marti
 */
public class Inputter {

	public static String inputPatternStr(String msg, String pattern) {
		Scanner sc;
		boolean flag = false;
		String response = "";
		while (!flag) {
			sc= new Scanner(System.in); 
			if (response.matches(pattern)) {
				flag = true;
			} else if (response.isEmpty()) {
				System.out.println(msg);
				response = sc.nextLine().trim();
			} else {
				System.err.println("Wrong format!");
				response = sc.nextLine().trim();
			}
		}
		return response;
	}

	public static String inputNotBlankStr(String msg) {
		Scanner sc;
		boolean flag = false;
		String response = "";
		while (!flag) {
			System.out.println(msg);
			sc = new Scanner(System.in);
			response = sc.nextLine().trim();
			if (!response.isEmpty()) {
				flag = true;
			} else {
				System.err.println("Cannot Be Blank!");
			}
		}
		return response;
	}

	public static short inputShort(String msg) {
		Scanner sc;
		boolean flag = false;
		Short response = 0;
		String inputData;
		while (!flag) {
			try {
				System.out.println(msg);
				sc = new Scanner(System.in);
				inputData = sc.nextLine();
				response = Short.parseShort(inputData);
				flag = true;
			} catch (NumberFormatException e) {
				System.err.println("Invalid input. Please enter only number");
			}
		}
		return response;
	}

	public static boolean inputBoolean(String msg) {
		Scanner sc;
		boolean flag = false;
		boolean response = false;
		String inputData;
		while (!flag) {
			try {
				System.out.println(msg);
				sc = new Scanner(System.in);
				inputData = sc.nextLine();
				response = Boolean.parseBoolean(inputData);
				flag = true;
			} catch (NumberFormatException e) {
				System.err.println("Invalid input. Please enter true/false only");
			}
		}
		return response;
	}

	public static int inputInteger(String msg) {
		Scanner sc;
		boolean flag = false;
		int response = 0;
		String inputData;
		while (!flag) {
			try {
				System.out.println(msg);
				sc = new Scanner(System.in);
				inputData = sc.nextLine();
				response = Integer.parseInt(inputData);
				flag = true;
			} catch (NumberFormatException e) {
				System.err.println("Invalid input. Please enter only number");
			}
		}
		return response;
	}

	public static float inputFloat(String msg) {
		Scanner sc;
		boolean flag = false;
		float response = 0;
		String inputData;
		while (!flag) {
			try {
				System.out.println(msg);
				sc = new Scanner(System.in);
				inputData = sc.nextLine();
				response = Float.parseFloat(inputData);
				flag = true;
			} catch (NumberFormatException e) {
				System.err.println("Invalid input. Please enter only number");
			}
		}
		return response;
	}

	public static double inputDouble(String msg) {
		Scanner sc;
		boolean flag = false;
		double response = 0;
		String inputData;
		while (!flag) {
			try {
				System.out.println(msg);
				sc = new Scanner(System.in);
				inputData = sc.nextLine();
				response = Double.parseDouble(inputData);
				flag = true;
			} catch (NumberFormatException e) {
				System.err.println("Invalid input. Please enter only number");
			}
		}
		return response;
	}

	/*public static void pressEnterToContinue() {
		System.out.println("Press \"ENTER\" to continue...");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		//scanner.close();
	}*/
}
