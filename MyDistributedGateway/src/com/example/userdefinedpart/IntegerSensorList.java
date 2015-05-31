package com.example.userdefinedpart;

import java.util.ArrayList;

import com.example.data.Data;
import com.example.data.DataAccessObject;

public class IntegerSensorList extends DataAccessObject{

	public static IntegerSensorList sensors;

	public static IntegerSensorList getIntegerSensorList(){
		if(sensors == null){
			return new IntegerSensorList();
		}
		return sensors;
	}


	
}
