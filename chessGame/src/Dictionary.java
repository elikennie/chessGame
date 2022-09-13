// CS2210
// Elijah Kennie
// Student Number 251163208

import java.util.LinkedList;

public class Dictionary implements DictionaryADT{
	
	private LinkedList<Layout>[] hashTable; // the hashTable that will be used, each index will be a linked list
	private int size; // size of array
	private Layout nodeData; // information from a new node that has been accessed from an index in the hash table
	
	
	public Dictionary(int size) {
		hashTable = (LinkedList<Layout>[]) new LinkedList[size]; // define the size of hashTable
		
		// populate hash table with each entry being a new LinkedList 
		for(int c = 0; c < size; c++) {
			hashTable[c] = new LinkedList<Layout>();	
		}
		
		this.size = size; // set instance variable size to initialized size of array
	}
	
	 // put layout object into the dictionary
	public int put(Layout data) throws DictionaryException {
		int key = convertToIndex(data.getBoardLayout());
		// if hashTable is populated
		if(hashTable[key].size() > 0) {
			
			for (int i = 0; i < hashTable[key].size(); i++) {
				nodeData = hashTable[key].get(i); // get the data from the hash table
				
				if(nodeData.getBoardLayout() == data.getBoardLayout()) { // throw an exception if collision occurs (same key)
					throw new DictionaryException("Collision Occurred");
				}
			}
			hashTable[key].add(data); // insert node in linked list after previous node
			return 1;  // returning 0 signaling a collision has occurred 
		}
		
		else {
			hashTable[key].add(data); // insert node at beginning of linked list if there is no other items
			return 0; // return 0 signaling no collision has occurred
		}
	}
	
	 // removes object with key boardLayout from dictionary
	public void remove(String boardLayout) throws DictionaryException {
		int key = convertToIndex(boardLayout);
		boolean removed = false;
		
		for(int i = 0; i < hashTable[key].size(); i++) { // iterate through index where key is located
			
			if(hashTable[key].get(i).getBoardLayout().compareTo(boardLayout) == 0) { // compare boardLayouts for equality
				hashTable[key].remove(i);
				removed = true;
			}
		}
		if(removed != true) { // if removed is no longer true throw an exception
			throw new DictionaryException("Node not found");
		}
	}
	
	// returns the score stored in the object with key boardLayout, return -1 otherwise 
	public int getScore(String boardLayout) {
		int key = convertToIndex(boardLayout);
		
		for(int i = 0; i < hashTable[key].size(); i++) { // iterate through index where key is located
			
			if(hashTable[key].get(i).getBoardLayout().compareTo(boardLayout) == 0) { // compare boardLayouts for equality
				return hashTable[key].get(i).getScore(); // return the score of the node if found 
			}
		}
		return -1; // if no object has this key, return -1
	}
	
	// function to hash the given key 
	private int convertToIndex(String key) {
		
		int hashVal = 0;
		
		for(int i = 0; i < key.length(); i++) {
			hashVal = ((hashVal * 23) + key.charAt(i)) % size; // perform hash based on algorithm given in lecture 
		}	
		
		return hashVal;
	}
}
	

