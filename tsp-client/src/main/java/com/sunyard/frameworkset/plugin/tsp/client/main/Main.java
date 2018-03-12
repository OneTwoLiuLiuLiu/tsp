package com.sunyard.frameworkset.plugin.tsp.client.main;


import com.sunyard.frameworkset.plugin.tsp.client.TspNettyClient;

public class Main {
	public static void main(String args[]) throws Exception {
		TspNettyClient client = TspNettyClient.getInst();
		client.init();		
	}
}
