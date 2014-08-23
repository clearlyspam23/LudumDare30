package com.clearlyspam23.game.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ValueTable {
	
	private Map<Resource, Value> valueMap = new HashMap<Resource, Value>();
	
	private static class Value{
		private int value;
		private Value parent;
		
		public Value(int amount){
			this(amount, null);
		}
		
		public Value(int amount, Value parent){
			this.value = amount;
			this.parent = parent;
		}
		
		public int getValue(){
			if(parent!=null)
				return value+parent.getValue();
			return value;
		}
		
		public void setValue(int value){
			this.value = value;
		}
	}
	
	public ValueTable(){
		
	}
	
	public ValueTable(ValueTable base){
		for(Entry<Resource, Value> e : base.getValues().entrySet()){
			valueMap.put(e.getKey(), new Value(0, e.getValue()));
		}
	}
	
	public void addResource(Resource resource, int value){
		valueMap.put(resource, new Value(value));
	}
	
	public void setValue(Resource resource, int value){
		if(valueMap.containsKey(resource))
			valueMap.get(resource).setValue(value);
	}
	
	public Map<Resource, Value> getValues(){
		return valueMap;
	}
	
	public int getValueOf(Resource resource){
		if(valueMap.containsKey(resource))
			return valueMap.get(resource).getValue();
		return 0;
	}

}
