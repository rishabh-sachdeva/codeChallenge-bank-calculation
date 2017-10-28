/*
Program defined to evaluate the amount spent on purchasing, amount credited and amount one owes(credit card bill, electricity bill,etc)
Assumptions:
Messages are available in csv format,
Each message is considered as seperate entity.
Comma is not present in text of any message.
*/


package com.csv;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadCSVfile {
	 private static Logger logger=Logger.getLogger("ReadCSVfile");

 String searchingFor;
	public void inputMapping(){
		String csvFile = "inputFile.csv";
		String searchingForCredited="credited";
		String searchingForPurchases="purchase";
		String searchingForBill = "bill";
		
	this.executeNetAmountEvaluation(csvFile,searchingForCredited);//evaluates credited amount
	this.executeNetAmountEvaluation(csvFile,searchingForPurchases);//evaluates money spent in purchasing
	this.executeNetAmountEvaluation(csvFile,searchingForBill);// evaluates amount one owes

	}
	public void executeNetAmountEvaluation(String csvFile,String searchingFor){
		BufferedReader br = null;
		String line = "";
		String commaSeperator = ",";
		String spaceSeperator = " ";
		int length = 0;
		this.searchingFor= searchingFor;
		String searchingForINR = "INR";
		int outstandingAmount = 0;
		int netAmount = 0;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while((line = br.readLine())!= null){
				
				String[] columnValues = line.split(commaSeperator);
				for(int k =0; k<columnValues.length ;k++){
				String[] msgValues = columnValues[k].split(spaceSeperator);
				int i =0;
				length = msgValues.length;
				 while(i<length){
				if(((msgValues[i]).trim()).equals(searchingFor)){
					int countForCreditedAccount = i;
					boolean INRFound= false;
					while(countForCreditedAccount!=length && !INRFound ){
					if((msgValues[countForCreditedAccount].trim()).equals((searchingForINR))){
					     outstandingAmount = Integer.parseInt(msgValues[countForCreditedAccount+1].trim());
					     netAmount =  outstandingAmount + netAmount;
					     countForCreditedAccount++;
					     i++;
					     INRFound = true;
					}
					else{
						countForCreditedAccount++;
						i++;
						
					}
				  }
				}
				else{
					i++;
				}

			}
		  } 
		}
		outputPresent(netAmount,searchingFor);
		} catch (FileNotFoundException e) {
			logger.error("File Not Found",e);
		} catch (IOException e) {
		     logger.error("IOEXception occured:", e);
		}
		
		finally{
			 
			if (br != null) {
				 
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	public void outputPresent(int netAmount,String searchingFor){
		System.out.println("*****************");

		if("credited".equals(searchingFor)){
			System.out.println("total credited amount in your account :"+netAmount +"\n");
			}
			if("purchase".equals(searchingFor)){
				System.out.println("total purchase made by you :"+netAmount +"\n");
				}
			if("bill".equals(searchingFor)){
				System.out.println("total amount you owe :"+netAmount +"\n");
				}
			System.out.println("*****************");
			
	}

	
	public static void main(String args[])
	{
		ReadCSVfile rcsv = new ReadCSVfile();
		rcsv.inputMapping();
		
	}

}
