/**
 * Data structure used to complete project 8 for CSE 274 at Miami University
 * 
 * Professor: Norm Krumpe
 * TA: Laura Paganini
 * @author Colin Evans
 * Class: CSE 274 
 *
 */
public class ArrayMap {

	private StringNode[] dictionary;
	private final static int DEFAULT_CAPACITY = (int) 2.6E6;
	private int arraySize;
	private int numEntries;
	private boolean sorted;

	/**
	 * Default constructor of an ArrayMap, creates an array of StringNodes (inner
	 * class) and sets the size and num of entries to their perspective amounts.
	 */
	public ArrayMap() {
		dictionary = new StringNode[DEFAULT_CAPACITY];
		arraySize = DEFAULT_CAPACITY;
		numEntries = 0;
		sorted = false;
	}

	/**
	 * Adds an item to the dictionary (array)
	 * 
	 * @param key
	 *            The string that is being stored (the word from the document)
	 * @param value
	 *            The number of times that a word is in the document
	 * @return The old number that was stored in that location.
	 */
	public int put(String key, int value) {
		// Loops through the array of entries, comparing
		if (numEntries != 0) {
			int index = 0;
			for (StringNode n : dictionary) {
				if (index >= numEntries)
					break;
				// If the key being added is found
				if (n.key.equals(key)) {
					int old = n.value;
					n.value = value;
					if (numEntries == arraySize)
						resize();
					return old;
				}
				index++;
			}
		}
		// Got here? The entry wasn't found
		StringNode add = new StringNode(key, value);
		dictionary[numEntries] = add;
		numEntries++;
		sorted = false;
		if (numEntries == arraySize)
			resize();
		return 0;
	}

	/**
	 * Returns how many entries are in the dictionary so far
	 * 
	 * @return The number of entries
	 */
	public int getSize() {
		return numEntries;
	}

	/**
	 * Gets the value stored at a specified key
	 * 
	 * @param key
	 *            the key you are looking for a value for
	 * @return the value (how many times a word has appeared)
	 */
	public int get(String key) {
		int index = 0;
		for (StringNode n : dictionary) {
			if (index >= numEntries)
				break;
			// When you find the specified key, return the value of it.
			if (n.key.equals(key)) {
				return n.value;
			}
			index++;
		}
		return 0; // Should never reach because method won't be called on a key that isn't in the
		// array.
	}

	/**
	 * Checks the dictionary to see if a given key is a member of the dictionary
	 * 
	 * @param key
	 *            The key being searched for
	 * @return True if the given string is in the dictionary, else false.
	 */
	public boolean containsKey(String key) {
		// If the array is empty, it obviously isn't there!
		if (numEntries == 0) {
			return false;
		}
		int index = 0;
		for (StringNode n : dictionary) {
			if (index >= numEntries)
				break;
			// True if it is in there
			if (n.key.equals(key)) {
				return true;
			}
			index++;
		}
		// Else, false
		return false;
	}

	public String mostPopular(int howPopular) {
		if(!sorted) {
			//quickSort(dictionary, 0, numEntries-1);
			mergeSort(dictionary, 0, numEntries-1);
			sorted = true;
		}
		return dictionary[howPopular].key;
	}

	// Merges two subarrays of arr[].
    // First subarray is arr[l..m]
    // Second subarray is arr[m+1..r]
	/**
	 * Code base borrowed from http://www.geeksforgeeks.org/merge-sort/
	 * Changed to fit my array
	 * Merges two sub-arrays
	 * 
	 * @param arr The array that is being sorted
	 * @param l The front of the array
	 * @param m A middle index to be the end of the left array
	 * @param r the end of the array 
	 */
    private void merge(StringNode arr[], int l, int m, int r)
    {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;
 
        /* Create temp arrays */
        StringNode L[] = new StringNode [n1];
        StringNode R[] = new StringNode [n2];
 
        /*Copy data to temp arrays*/
        for (int i=0; i<n1; ++i)
            L[i] = arr[l + i];
        for (int j=0; j<n2; ++j)
            R[j] = arr[m + 1+ j];
 
 
        /* Merge the temp arrays */
 
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
 
        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2)
        {
        	//Does the sorting
            if (L[i].value > R[j].value)
            {
                arr[k] = L[i];
                i++;
            }
            //If the value is the same, needs to be sorted by alphabetic order
            else if(L[i].value == R[j].value) {
            	if(L[i].key.compareTo(R[j].key) < 0) {
            		arr[k] = L[i];
            		i++;
            	} else {
            		arr[k] = R[j];
            		j++;
            	}
            	
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
 
        /* Copy remaining elements of L[] if any */
        while (i < n1)
        {
            arr[k] = L[i];
            i++;
            k++;
        }
 
        /* Copy remaining elements of R[] if any */
        while (j < n2)
        {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
 
    // Main function that sorts arr[l..r] using
    // merge()
    /**
     * framework taken from http://www.geeksforgeeks.org/merge-sort/
     * Sorts the dictionary using mergeSort so that you can find the most popular
     * 
     * @param arr The array to be sorted
     * @param l The front index of the array
     * @param r The back index of the array
     */
    private void mergeSort(StringNode arr[], int l, int r)
    {
        if (l < r)
        {
            // Find the middle point
            int m = (l+r)/2;
 
            // Sort first and second halves
            mergeSort(arr, l, m);
            mergeSort(arr , m+1, r);
 
            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }
	
	/**
	 * resizes the array if it gets full, makes it 2 times bigger.
	 */
	private void resize() {
		StringNode[] temp = new StringNode[arraySize * 2];
		for (int i = 0; i < dictionary.length; i++) {
			temp[i] = dictionary[i];
		}
		dictionary = temp;
		arraySize *= 2;
	}

	/**
	 * Inner class used to create the dictionary, the "node" stores a string and an
	 * int so that you can look up how many times each word shows up in whatever
	 * document.
	 * 
	 * @author Colin Evans
	 *
	 */
	private class StringNode {
		private String key;
		private int value;

		/**
		 * Constructor for the class, pretty simple, just stores the things in the
		 * things..
		 * 
		 * @param key
		 * @param value
		 */
		private StringNode(String key, int value) {
			this.key = key;
			this.value = value;
		}
	}
}