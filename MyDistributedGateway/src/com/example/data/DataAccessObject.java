package com.example.data;

import java.util.ArrayList;

import com.example.userdefinedpart.IntegerSensorList;

public abstract class DataAccessObject{
	
	protected ArrayList<Data> dataList;
	private ArrayList<Data> bucket1;
	private ArrayList<Data> bucket2;
	protected int size = 0;
	private int numOfData = 0;
	public DataAccessObject(){
		
		bucket1 = new ArrayList<Data>();
		bucket2 = new ArrayList<Data>();
		dataList = bucket1;
		size = 100;
	}
	
	public int length(){
		return numOfData;
	}
	
	public void setSize(int size){
		this.size = size;
	}
	
	public int getMaxSize(){
		return this.size;
	}
	public int getSize(){
		return this.dataList.size();
	}

	public ArrayList<Data> getAllData() {
		// TODO Auto-generated method stub
		if(bucket1.isEmpty()){
			ArrayList temp = new ArrayList();
			for(int i = 0; i < bucket2.size(); i++){
				temp.add(bucket2.get(i).getData());
			}
			bucket2 = new ArrayList();
			flush(bucket2);
			dataList = bucket2;
			numOfData -= temp.size();
			return temp;
		} else{
			ArrayList temp = new ArrayList();
			for(int i = 0; i < bucket1.size(); i++){
				temp.add(bucket1.get(i).getData());
			}
			numOfData -= temp.size();
			bucket1 = new ArrayList();
			dataList = bucket1;
			flush(bucket1);
			return temp;
		}
	}

	public Data getData(int id) {
		// TODO Auto-generated method stub
		return dataList.get(id);
	}


	public Data discardData(int id) {
		// TODO Auto-generated method stub
		Data data = getData(id);
		dataList.remove(id);
		numOfData--;
		
		return data;
	}

	public void flush(ArrayList list){
		list = new ArrayList();
	}

	public void discardData(Data data) {
		// TODO Auto-generated method stub
		dataList.remove(data);
		numOfData--;
	}

	public void putData(Data data) {
		// TODO Auto-generated method stub
		if(dataList.size() == size){
			changeBucket();
		}
		
		numOfData++;
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
