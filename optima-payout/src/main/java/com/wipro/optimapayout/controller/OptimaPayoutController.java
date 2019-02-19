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
public class OptimaPayoutController {
	
	@RequestMapping(value="/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value="/landingpage")
	public String details(Model model) {
		Object obj;
		 Map<Double, List<Double>> debitMap = new HashMap<Double, List<Double>>();
		 Map<Double, List<Double>> creditMap = new HashMap<Double, List<Double>>();
		 Map<Double, List<Double>> creditresult = new HashMap<Double, List<Double>>();
		 ArrayList<Double> funds = new ArrayList<Double>();
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
				creditresult.put(Double.parseDouble((String) json.get("APR")), l);
				l = null;
			}
			totalCreditBal=0.00;
			JSONArray credit = (JSONArray) jo.get("creditDetails");
			Iterator i2 = credit.iterator();
			while (i2.hasNext()) {
				JSONObject json = (JSONObject) i2.next();
				List<Double> l = new ArrayList<Double>();
				double due = Double.parseDouble((String) json.get("minDue"));
				double out = Double.parseDouble((String) json.get("Outstanding"));
				l.add(out);
				l.add(due);
				totalCreditBal = totalCreditBal + out;
				creditMap.put(Double.parseDouble((String) json.get("APR")), l);
				l = null;
			}

			Map<Double, List<Double>> creditresult1 = new TreeMap<Double, List<Double>>(Collections.reverseOrder());
			creditresult1.putAll(creditresult);
			model.addAttribute("c",creditresult1);

			//-----------------debit sort by AER----------------------------------------
			
			Map<Double, List<Double>> debitMapSort = new TreeMap<Double, List<Double>>(debitMap);
			System.out.println(debitMapSort);
			model.addAttribute("d",debitMapSort);
			
			//-------------------credit sort by APR----------------------------------------
			
			Map<Double, List<Double>> creditMapSort = new TreeMap<Double, List<Double>>(Collections.reverseOrder());
			creditMapSort.putAll(creditMap);
			System.out.println(creditMapSort);
			
			//---------------------logic-----------------------------------------------
			if(totalDebitBal >= totalCreditBal) {
				System.out.println("scen1 d>c total debit balance :"+totalDebitBal+"total credit bal :"+totalCreditBal);
				Iterator<Map.Entry<Double, List<Double>>> itr = debitMapSort.entrySet().iterator(); 
				Iterator<Map.Entry<Double, List<Double>>> itr1 = creditMapSort.entrySet().iterator();
				 while(itr.hasNext()) 
			        { 
			             Entry<Double, List<Double>> entry = itr.next(); 
			             funds.add(entry.getValue().get(3));
			             
			        } 
				 int i=0;
					
					 while(itr1.hasNext()) {
						 Entry<Double, List<Double>> entry = itr1.next();
						 while (entry.getValue().get(0) != 0) {
				
//							System.out.println(funds.get(i)+"  "+entry.getValue().get(0));
							
							if(funds.get(i) > 0 && entry.getValue().get(0) > 0.00){
								if(funds.get(i) >= entry.getValue().get(0)) {
									funds.set(i, funds.get(i)-entry.getValue().get(0));
									entry.getValue().set(0, 0.00);
									entry.getValue().set(1, 0.00);
//									System.out.println(i+"if  :"+funds.get(i)+"  "+entry.getValue().get(0));
								}
								else {
									entry.getValue().set(0, entry.getValue().get(0)-funds.get(i));
									entry.getValue().set(1, 0.00);
									funds.set(i, 0.00);
//									System.out.println(i+"else  :"+funds.get(i)+"  "+entry.getValue().get(0));
								}
							}
							if (funds.get(i) == 0) {
								i++;
							}
//							System.out.println(i);
								
						 }
					 }
					 int k=0;
					 System.out.println("itr" + itr.hasNext());
					 Iterator<Map.Entry<Double, List<Double>>> fundBal = debitMapSort.entrySet().iterator();
						while(fundBal.hasNext()) {
							Entry<Double, List<Double>> entry = fundBal.next();
								System.out.println("funds"+funds.get(k)+" "+entry.getValue().get(1)+" "+entry.getValue().get(2));
								funds.set(k, funds.get(k)+entry.getValue().get(1)+entry.getValue().get(2));
								System.out.println(funds);
							k++;
						}
					model.addAttribute("creditresult", creditMapSort);
					System.out.println(funds);
					System.out.println(creditMapSort);
					model.addAttribute("funds", funds);
			} //-----if end-------------
			else {
				System.out.println("sc 2 c>d total debit balance :"+totalDebitBal+"total credit bal :"+totalCreditBal);
				Iterator<Map.Entry<Double, List<Double>>> itr = debitMapSort.entrySet().iterator(); 
				Iterator<Map.Entry<Double, List<Double>>> itr1 = creditMapSort.entrySet().iterator();
				
				 while(itr.hasNext()) 
			        { 
			             Entry<Double, List<Double>> entry = itr.next(); 
			             funds.add(entry.getValue().get(3));
			             
			        } 
				 int i=0;
				 while(itr1.hasNext()) {
					Entry<Double, List<Double>> entry = itr1.next();
					if(funds.get(i) >= entry.getValue().get(1)) {
						funds.set(i, funds.get(i)-entry.getValue().get(1));
						entry.getValue().set(0, entry.getValue().get(0)-entry.getValue().get(1));
						entry.getValue().set(1, 0.00);
						
					}
					else {
						funds.set(i, entry.getValue().get(1)-funds.get(i));
						entry.getValue().set(0, funds.get(i)-entry.getValue().get(1));
						entry.getValue().set(1, 0.00);
					}i++;
					if(i >= funds.size()) {
						i=0;
					}
					System.out.println(funds);
					for(int j=0;j<funds.size()-1;j++) {
						System.out.println(funds.get(i));
							if(funds.get(j) >= entry.getValue().get(0)) {
								double a = funds.get(j)-entry.getValue().get(0);
								funds.set(j, a);
								entry.getValue().set(0, 0.00);
								System.out.println("a"+a);
								a=0;
							}
							else {
								double b = entry.getValue().get(0)-funds.get(j);
								funds.set(j, 0.00);
								entry.getValue().set(0, b);
								System.out.println("b"+b);
								b=0;
							}
					
					}
				 }
				
					System.out.println(funds);
					System.out.println(creditMapSort);
					
					while(itr.hasNext()) {
						Entry<Double, List<Double>> entry = itr.next();
						for(int k=0;k<funds.size();k++) {
							funds.set(k, funds.get(k)+entry.getValue().get(1)+entry.getValue().get(2));
							System.out.println(funds);
						}
					}
					model.addAttribute("funds", funds);		
					model.addAttribute("creditresult", creditMapSort);
			}//-------else end------------
			
		
			
		        
			model.addAttribute("credit", creditMapSort);
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
