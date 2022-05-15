package com.demo.stock;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.demo.stock.service.StorageService;


@Component
public class StockCmdLRunner implements CommandLineRunner {
	
	@Autowired
	private ApplicationContext ctx;	
	
	@Autowired
	private StorageService storageService;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("");
		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName: beanNames) {
			//if (beanName.length()<25)
				System.out.println(beanName);
		}	
		//*************
		storageService.init();
	}

}
