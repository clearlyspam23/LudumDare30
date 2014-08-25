package com.clearlyspam23.game;

import java.util.ArrayList;
import java.util.List;

/**
The MIT License (MIT)

Copyright (c) 2014 John Ader

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */

/**
 * A table representing drop rate for objects
 * intended usage is that you fill this table with several items, and their chance to drop (out of some arbitrary number, assumed to be 100), 
 * and then, whenever necessary you ask this table to give you an item, at random.

 * so for example, say you have 4 items, gold, iron, silver, and bronze, and these 4 items should have the drop rate:
 * 
 * [bronze - 25%]
 * [iron   - 10%]
 * [silver -  5%]
 * [gold   -  1%]
 * 
 * then, you would use the DropTable like:
 * 
 * DropTable<Item> table = new DropTable<Item>();
 * table.addEntry(bronze, 25);
 * table.addEntry(iron, 10);
 * table.addEntry(silver, 5);
 * table.addEntry(gold, 1);
 * 
 * and in order to grab an item out of this table, you would do:
 * 
 * Item i = table.getValue();
 * 
 * @author clearlyspam23
 *
 * @param <T> the type of item being dropped
 */
public class DropTable<T> {
	
	private List<TableEntry> table = new ArrayList<TableEntry>();
	private float max;
	
	/**
	 * constructs a new DropTable, with a default maximum chance of 100
	 */
	public DropTable()
	{
		this(100f);
	}
	
	/**
	 * constructs a new DropTable, using the given maximum chance
	 * so for instance, if you wanted drop chance to be out of 50 rather than out of 100, you would say:
	 * new DropTable(50);
	 * @param maxChance
	 */
	public DropTable(float maxChance)
	{
		this.max = maxChance;
	}
	
	/**
	 * Adds a new entry to this table, with the given chance to drop
	 * 
	 * for instance, if your game has an item called "Shotgun", and you want some monster to have a 10% chance to drop said item, you would call:
	 * DropTable table = new DropTable();
	 * table.addEntry(Shotgun, 10);
	 * 
	 * @param chance the chance for this item to be dropped (usually chance out of 100, but can be out of whatever)
	 * @param object the item to be dropped
	 */
	public void addEntry(T object, float chance)
	{
		table.add(new TableEntry(chance, object));
	}
	
	/**
	 * fetches an item out of the drop table, given a value. This value will probably be a randomly generated value.
	 * depending on the value given and the entries, there is a chance no item will be returned.
	 * 
	 * So for instance, if you added 3 items to this table (shotgun, machinegun, pistol), all with a value 10 (10% chance to drop), you would call this method like:
	 * 
	 * table.getValue(math.random()*100);
	 * 
	 * 
	 * @param value the value to try and fetch out of this table
	 * @return the item at the given value, or null if the value is greater than the combined chances to drop of all items
	 */
	public T getValue(float value)
	{
		for(TableEntry e : table)
		{
			value-=e.chance;
			if(value<=0)
				return e.item;
		}
		return null;
	}
	
	/**
	 * fetches an item out of the drop table, using the value passed in on construction (default 100)
	 * @return the item at the given value, or null if the value is greater than the combined chances to drop of all items
	 */
	public T getValue()
	{
		return getValue((float) (Math.random()*max));
	}
	
	private class TableEntry
	{
		public float chance;
		public T item;
		
		//this table assumes values between 0 and 100
		
		public TableEntry(float chance, T item)
		{
			this.item = item;
			this.chance = chance;
		}
		
	}

}
