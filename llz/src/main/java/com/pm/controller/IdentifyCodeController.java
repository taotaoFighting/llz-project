package com.pm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aliyuncs.exceptions.ClientException;
import com.pm.service.IdentifyCodeService;
import com.pm.util.Status;

@RestController
@RequestMapping(value="/sms")
public class IdentifyCodeController {
	
	@Autowired
	IdentifyCodeService identifyCodeService;
	
	@RequestMapping(value="/snedSmsCode",method=RequestMethod.POST)
	public Status sendSmsCode() throws ClientException {
		
		Status status = new Status();
		status.setMsg("sucess");
		status.setStatus(200);
		
		identifyCodeService.sendSms();
		
		return status;
	}

}
