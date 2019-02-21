package com.wipro.optimapayout.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject; 
import org.json.simple.parser.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/payOutPlanService")
public class PayoutController {

	@RequestMapping(value="/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value="/landingpage")
	public String details(Model model) {
		Object obj;
		 Map<Double, List<Double>> debitMap = new HashMap<Double, List<Double>>();
		 Map<Double, List<Double>> creditMap = new HashMap<Double, List<Double>>();
		 Map<Double, List<Double>> creditDuplicate = new HashMap<Double, List<Double>>();
		 ArrayList<Double> funds = new ArrayList<Double>();
		 ArrayList<Double> outstanding = new ArrayList<Double>();
		 ArrayList<Double> mindue = new ArrayList<Double>();
		 try {
			//-----------------debit----------------------------------------
			obj = new JSONParser().parse(new FileReader("src/main/resources/data.json"));
			JSONObject jo = (JSONObject) obj;
			JSONArray debit = (JSONArray) jo.get("debitDetails");
			double totalDebitBal = 0.00;
			double totalCreditBal = 0.00;
			Iterator i1 = debit.iterator();			
			while (i1.hasNext()) {
				JSONObject json = (JSONObject) i1.next();
				double bal = Double.parseDouble((String) json.get("currentBal"));
				double min = Double.parseDouble((String) json.get("minBal"));
				double stand = Double.parseDouble((String) json.get("standing"));
				totalDebitBal =totalDebitBal + (bal-(min+stand));
				List<Double> l = new ArrayList<Double>();
				l.add(bal);
				l.add(min);
				l.add(stand);
				l.add(bal-min-stand);
				debitMap.put((Double.parseDouble((String) json.get("AER"))), l);
				l = null;
			}
			//-------------------credit----------------------------------------
			JSONArray credit1 = (JSONArray) jo.get("creditDetails");
			Iterator i3 = credit1.iterator();
			while (i3.hasNext()) {
				JSONObject json = (JSONObject) i3.next();
				List<Double> l = new ArrayList<Double>();
				double due = Double.parseDouble((String) json.get("minDue"));
				double out = Double.parseDouble((String) json.get("Outstanding"));
				l.add(out);
				l.add(due);
				totalCreditBal = totalCreditBal + out;
				creditDuplicate.put(Double.parseDouble((String) json.get("APR")), l);
				l = null;
			}
			JSONArray credit2 = (JSONArray) jo.get("creditDetails");
			Iterator i4 = credit2.iterator();
			while (i4.hasNext()) {
				JSONObject json = (JSONObject) i4.next();
				List<Double> l = new ArrayList<Double>();
				double due = Double.parseDouble((String) json.get("minDue"));
				double out = Double.parseDouble((String) json.get("Outstanding"));
				l.add(out);
				l.add(due);
				totalCreditBal = totalCreditBal + out;
				creditMap.put(Double.parseDouble((String) json.get("APR")), l);
				l = null;
			}

			//-----------------debit sort by AER----------------------------------------
			
			Map<Double, List<Double>> debitMapSort = new TreeMap<Double, List<Double>>(debitMap);
			System.out.println(debitMapSort);
			model.addAttribute("d",debitMapSort);

			
			//-------------------credit sort by APR----------------------------------------
			
			Map<Double, List<Double>> creditMapSort = new TreeMap<Double, List<Double>>(Collections.reverseOrder());
			creditMapSort.putAll(creditMap);
			System.out.println(creditMapSort);
			model.addAttribute("c", creditMapSort);
			Map<Double, List<Double>> creditMapSortResult = new TreeMap<Double, List<Double>>(Collections.reverseOrder());
			creditMapSortResult.putAll(creditDuplicate);
			System.out.println(creditMapSortResult);
			
			//---------------------logic-----------------------------------------------
				Iterator<Map.Entry<Double, List<Double>>> itr = debitMapSort.entrySet().iterator(); 
				Iterator<Map.Entry<Double, List<Double>>> itr1 = creditMapSortResult.entrySet().iterator();
				
				 while(itr.hasNext())  { 
			             Entry<Double, List<Double>> entry = itr.next(); 
			             funds.add(entry.getValue().get(3));
			        } 
				 while(itr1.hasNext()) {
					 	Entry<Double, List<Double>> entry = itr1.next();
					 	outstanding.add(entry.getValue().get(0));
					 	mindue.add(entry.getValue().get(1));
				 }
				 System.out.println("mindue"+mindue);
			 		System.out.println("funds"+funds);
			 		System.out.println("outstanding"+outstanding);
				 for(int c=0;c<mindue.size();c++) {
				 		for(int d=0;d<funds.size();d++) {
				 			if(funds.get(d) >= mindue.get(c)) {
				 				System.out.println("Before If" + funds.get(d)+" "+mindue.get(c)+" "+d+" "+c);
				 				funds.set(d, funds.get(d)-mindue.get(c));
								outstanding.set(c, outstanding.get(c)-mindue.get(c));
								mindue.set(c, 0.00);
								System.out.println("After If" + funds.get(d)+" "+mindue.get(c)+" "+d+" "+c);
							}
							else {
								System.out.println("Before else" + funds.get(d)+" "+mindue.get(c)+" "+d+" "+c);
								outstanding.set(c, outstanding.get(c)-funds.get(d));
								mindue.set(c, mindue.get(c)-funds.get(d));
								funds.set(d, 0.00);
								System.out.println("After else" + funds.get(d)+" "+mindue.get(c)+" "+d+" "+c);

							}
				 		}
				 		System.out.println("mindue"+mindue);
				 		System.out.println("funds"+funds);
				 		System.out.println("outstanding"+outstanding);
				 }
			 	for(int c=0;c<outstanding.size();c++) {
			 		for(int d=0;d<funds.size();d++) {
						System.out.println("In main if" + funds.get(d)+" "+outstanding.get(c)+"d :"+d+"c :"+c);
						if(funds.get(d) >= outstanding.get(c)) {
							funds.set(d, funds.get(d)-outstanding.get(c));
							outstanding.set(c, 0.00);
							System.out.println("In if" + funds.get(d)+" "+outstanding.get(c)+"d :"+d+"c :"+c);			
						}
						else {
							System.out.println("In else before deducting" + funds.get(d)+" "+outstanding.get(c)+"d :"+d+"c :"+c);
							outstanding.set(c, outstanding.get(c)-funds.get(d));
							funds.set(d, 0.00);
							System.out.println("In else after deducting" + funds.get(d)+" "+outstanding.get(c)+"d :"+d+"c :"+c);
						}
					}
				 }
				System.out.println("end of for funds");
				
					System.out.println(funds);
					System.out.println("creditMapSortResult"+creditMapSortResult);
					System.out.println("credit map sort"+creditMapSort);
					System.out.println("debitmap sort"+debitMapSort);
					
					Iterator<Map.Entry<Double, List<Double>>> itrDebit = debitMapSort.entrySet().iterator();
					int k=0;
					while(itrDebit.hasNext()) {
						Entry<Double, List<Double>> entry2 = itrDebit.next();
							funds.set(k, funds.get(k)+entry2.getValue().get(1)+entry2.getValue().get(2));
							k++;
					}
					
					System.out.println(funds+"funds ");
					System.out.println("creditmapsort"+creditMapSort);
					System.out.println("outstanding"+outstanding);
					model.addAttribute("funds", funds);	
					model.addAttribute("out", outstanding);
					model.addAttribute("mindue", mindue);
					model.addAttribute("credit", creditMapSortResult);
					
					
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "landingpage";
		
	}
	
	@RequestMapping(value="/optimize")
	public String optimize(Model model) {
		
		return "optimize";
	}
	
		
}
