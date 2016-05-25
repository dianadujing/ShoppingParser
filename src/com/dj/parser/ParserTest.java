package com.dj.parser;

import java.io.IOException;
import java.util.List;

public class ParserTest {
	public static void main(String[] args) {
		Parser parser = new Parser();
		List<Object> orderList = parser.findOrders("./src/com/obj/data/MyOrders.json");
		try {
			parser.writeFile("./src/com/des/data/order.json", parser.listToJson(orderList));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
