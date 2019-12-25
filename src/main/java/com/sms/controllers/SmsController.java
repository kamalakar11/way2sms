package com.sms.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.sms.pojo.Account;
import com.sms.pojo.Msg;
import com.sms.pojo.SmsRequest;
import com.sms.pojo.TextMessage;

@Controller
public class SmsController {

	@RequestMapping(value = "/sendSms")
	public String sendSms(@RequestParam("mobile") String mobile, @RequestParam("text") String text, Model model) {
		System.out.println("executing SmsController :: sendSms");
		System.out.println("Mobile nubmers : " + mobile);
		System.out.println("Content to send : " + text);
		// hit smsgatewayhub service
		StringBuilder url = new StringBuilder("https://www.smsgatewayhub.com/api/mt/SendSMS?");
		url.append("APIKey=").append("lWkH7i8o50Odr0gE9VYU5w").append("&");
		url.append("senderid=").append("SMSTST").append("&");
		url.append("channel=").append("1").append("&");
		url.append("DCS=").append("0").append("&");
		url.append("flashsms=").append("0").append("&");
		url.append("number=").append(mobile).append("&");
		url.append("text=").append(text).append("&");
		url.append("route=").append("1").append("&");
		System.out.println(url);
		
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<String> result = rt.exchange(url.toString(), HttpMethod.GET, entity, String.class);
		
		String responseBody = result.getBody();
		System.out.println("Response from smsgatewayhub : " + result.getBody());
		Gson gson = new Gson();
		TextMessage textMessage = gson.fromJson(responseBody, TextMessage.class);
		model.addAttribute("msg", textMessage.getErrorMessage());
		return "home";
	}
	
	@RequestMapping(value="/sendSmsByJson", method=RequestMethod.POST)
	public String sendSmsByJson(@RequestParam("mobile") String mobile, @RequestParam("text") String text, Model model) {
		
		StringBuilder url = new StringBuilder("https://www.smsgatewayhub.com/api/mt/SendSMS");
		SmsRequest smsReq = prepareRequest(mobile, text);
		
		Gson gson = new Gson();
		String smsReqJson = gson.toJson(smsReq);
		System.out.println("Reques in json format : " + smsReqJson);
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(smsReqJson,headers);
		
		ResponseEntity<String> result = rt.exchange(url.toString(), HttpMethod.POST, entity, String.class);
		
		String responseBody = result.getBody();
		System.out.println("Response from smsgatewayhub : " + result.getBody());
		TextMessage textMessage = gson.fromJson(responseBody, TextMessage.class);
		model.addAttribute("msg", textMessage.getErrorMessage());
		
		return "home";
	}
	
	public SmsRequest prepareRequest(String mobile, String text) {
		SmsRequest smsReq = new SmsRequest();
		
		Account acc = new Account();
		acc.setUser("venugopal");
		acc.setPassword("606173");
		acc.setChannel("1");
		acc.setDCS("0");
		acc.setSenderId("SMSTST");
		
		List<Msg> msgList = new ArrayList<Msg>();
		Msg msg = new Msg();
		msg.setNumber(mobile);
		msg.setText(text);
		msgList.add(msg);
		
		smsReq.setAccount(acc);
		smsReq.setMessages(msgList);
		
		return smsReq;
	}
	
	public static void main(String[] args) {
		StringBuilder url = new StringBuilder("https://www.smsgatewayhub.com/api/mt/SendSMS?");
		url.append("APIKey=").append("lWkH7i8o50Odr0gE9VYU5w").append("&");
		url.append("senderid=").append("SMSTST").append("&");
		url.append("channel=").append("1").append("&");
		url.append("DCS=").append("0").append("&");
		url.append("flashsms=").append("0").append("&");
		url.append("number=").append("9545772676").append("&");
		url.append("text=").append("teset").append("&");
		url.append("route=").append("1").append("&");
		System.out.println(url);
		
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<String> result = rt.exchange(url.toString(), HttpMethod.POST, entity, String.class);
		System.out.println(result.getBody());
		
	}
}
