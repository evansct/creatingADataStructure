/**
 * Your implementation of the LookupInterface. The only public methods in this
 * class should be the ones that implement the interface. You should write as
 * many other private methods as needed. Of course, you should also have a
 * public constructor.
 * 
 * @author Colin Evans
 */

public class StudentLookup implements LookupInterface {

	private ArrayMap map;

	public StudentLookup() {
		map = new ArrayMap();
	}

	@Override
	public void addString(int amount, String s) {
		if (map.containsKey(s)) {
			int count = map.get(s);
			count += amount;
			map.put(s, count);
		} else {
			map.put(s, amount);
		}

	}

	@Override
	public int lookupCount(String s) {
		if(!map.containsKey(s))
			return 0;
		return map.get(s);
	}

	@Override
	public String lookupPopularity(int n) {
		return map.mostPopular(n);
	}

	@Override
	public int numEntries() {
		return map.getSize();
	}

}
