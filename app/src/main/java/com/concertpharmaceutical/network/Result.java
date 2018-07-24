package com.concertpharmaceutical.network;

import java.util.ArrayList;

public class Result {
	
	private ArrayList<Object> mResultList;
	private Object mResultObj;
	
	public Result()
	{
		
	}
	
	public Result(ArrayList<Object> mResultList) {
		super();
		this.mResultList = mResultList;
	}
	
	public Result(Object object) {
		super();
		this.mResultObj = object;
	}

	public ArrayList<Object> getResultList() {
		return mResultList;
	}

	public void setResultList(ArrayList<Object> mResultList) {
		this.mResultList = mResultList;
	}

	public Object getResultObj() {
		return mResultObj;
	}

	public void setResultObj(Object obj) {
		this.mResultObj = obj;
	} 
}
