package com.example.userdefinedpart;

import java.util.ArrayList;

import com.example.data.Data;
import com.example.data.DataAccessObject;

public class IntegerSensorList extends DataAccessObject{

	public static IntegerSensorList sensors;

	private IntegerSensorList(){
		super();
	}
	
	public static IntegerSensorList getIntegerSensorList(){
		if(sensors == null){
			sensors = new IntegerSensorList();
		}
		return sensors;
	}


	
}
