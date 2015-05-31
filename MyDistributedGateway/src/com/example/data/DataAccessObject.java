package com.example.data;

import java.util.ArrayList;

import com.example.userdefinedpart.IntegerSensorList;

public abstract class DataAccessObject{
	
	protected ArrayList<Data> dataList;
	private ArrayList<Data> bucket1;
	private ArrayList<Data> bucket2;
	protected int size = 0;
	
	public DataAccessObject(){
		dataList = new ArrayList<Data>();
		bucket1 = new ArrayList<Data>();
		bucket2 = new ArrayList<Data>();
		int size = 10;
	}
	
	public void setSize(int size){
		this.size = size;
	}
	
	public int getSize(){
		return this.size;
	}
	
	private static IntegerSensorList dataListObject;
	

	public ArrayList<Data> getAllData() {
		// TODO Auto-generated method stub
		return dataList;
	}

	public Data getData(int id) {
		// TODO Auto-generated method stub
		return dataList.get(id);
	}


	public void discardData(int id) {
		// TODO Auto-generated method stub
		dataList.remove(id);
	}


	public void discardData(Data data) {
		// TODO Auto-generated method stub
		dataList.remove(data);
	}

	public void putData(Data data) {
		// TODO Auto-generated method stub
		if(dataList.size() == size){
			changeBucket();
		}
		this.dataList.add(data);
	}

	private void changeBucket(){
		if(dataList == bucket1){
			dataList = bucket2;
		}else{
			dataList = bucket1;
		}
	}
}
