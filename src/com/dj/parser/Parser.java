package com.dj.parser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class Parser {
	public String ReadFile(String path){
	    File file = new File(path);
	    BufferedReader reader = null;
	    String laststr = "";
	    try {
	     //System.out.println("Start reading line by line：");
	     reader = new BufferedReader(new FileReader(file));
	     String tempString = null;
	     int line = 1;
	     //until end of file
	     while ((tempString = reader.readLine()) != null) {
	      //display line number
	      System.out.println("line " + line + ": " + tempString);
	      laststr = laststr + tempString;
	      line++ ;
	     }
	     reader.close();
	    } catch (IOException e) {
	     e.printStackTrace();
	    } finally {
	     if (reader != null) {
	      try {
	       reader.close();
	      } catch (IOException e1) {
	      }
	     }
	    }
	    return laststr;
	}
	
	public void writeFile(String filePath, String json) throws IOException {
	    FileWriter fw = new FileWriter(filePath);
	    PrintWriter out = new PrintWriter(fw);
	    out.write(json);
	    out.println();
	    fw.close();
	    out.close();
	   }
	
	public List<Object> findOrders(String path) {
		String sets = ReadFile(path);
		JSONObject jo=JSONObject.fromObject(sets);
		JSONArray array = jo.getJSONArray("data");
		List<Object> list = new ArrayList<Object>();
		for (int i=0; i<array.size(); i++){
			JSONObject orderJsonObj = array.getJSONObject(i);
			Order order = new Order();
			order.setCount(orderJsonObj.getString("status"));
			order.setOrderId(orderJsonObj.getString("id"));
			order.setOrderTime(orderJsonObj.getString("completion_time"));
			order.setPayment(orderJsonObj.getString("pay_type"));
			order.setTotalPrice("order_amount");
			order.setUserId(orderJsonObj.getString("user_id"));
			list.add(order);
		}
		return list;
	}
	
	public String listToJson(List<Object> list) {
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray.toString();
	}
	
	public List<Object> findOrderDetail(String path) {
		List<Object> list = new ArrayList<Object>();
		String sets = ReadFile(path);
		JSONObject jo=JSONObject.fromObject(sets);
		JSONArray array = jo.getJSONArray("data");
		for (int i=0; i<array.size(); i++){
			JSONObject orderJsonObj = array.getJSONObject(i);
			String orderId = orderJsonObj.getString("id");
			JSONArray orderDetails = JSONArray.fromObject(orderJsonObj.getJSONArray("order_goods"));
			for (int j=0; j<orderDetails.size(); j++){
				JSONArray subOrderDetails = orderDetails.getJSONArray(j);
				for (int k=0; k<subOrderDetails.size(); k++) {
					OrderDetail orderDetail = new OrderDetail();
					JSONObject orderDetailObject = subOrderDetails.getJSONObject(k);
					orderDetail.setGoodsId(orderDetailObject.get("goods_id").toString());
					orderDetail.setItemId(orderDetailObject.get("id").toString());
					orderDetail.setPrice(orderDetailObject.get("real_price").toString());
					orderDetail.setQuantity(orderDetailObject.get("goods_nums").toString());
					orderDetail.setOrdersId(orderId);
					list.add(orderDetail);
				}
			}
		}
		return list;
	}
	
	
}
