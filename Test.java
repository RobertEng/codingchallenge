/*******************************************************************************
 * Robert Eng
 * Taboola Coding Challenge
 * Question 2: Test.java
 * 
 * This is some kind of Test class which stores a list of integers, a list of
 * strings, a date, and its name. Not very many assumptions were made about
 * this class because its higher level function is unknown.
 ******************************************************************************/



import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Test {
	private Date m_time;
	private String m_name;
	// Given what m_numbers is used for (only lookups and to identify the obj
	// in the toString) and there aren't any methods for modification of
	// m_numbers, it would save just a tad bit of memory and make this code
	// simpler if we just made it into an int array. The array won't be
	// changing, so we don't have the pain of changing an array at all.
	// The downside to this is Test would be horribly difficult to change in
	// the future. Also there is some information that is lost is m_numbers is
	// a stack or queue. Thus I'm not changing this to an int array. Either way
	// it has the same lookup time and uses same space.
	
	// Another consideration was to remove all duplicate entries in m_numbers,
	// but then realized that maybe it's important to keep duplicates because
	// I have no clue what other uses m_numbers has.
	
	private List<Integer> m_numbers;
	private List<String> m_strings;

	public Test(Date time, String name, List<Integer> numbers, List<String> strings) {
		m_time = time;
		m_name = name;
		m_numbers = numbers;
		m_strings = strings;
	}

	public boolean equals(Object obj) {
		// Must check for nulls first! Is obj null? is m_name null? are both
		// obj.m_name and m_name null? All wonderful questions to be answered
		// in if statements.
		if(obj == null) return false;
		if(((Test)obj).getName() == null) {
			if(m_name == null) return true;
			else return false;
		}

		try{
			// Getting a private data member m_name by using obj.m_name is
			// incorrect. There should be getters and setters for the private
			// data members.
			// BEFORE: return m_name.equals(((Test) obj).m_name);
			// AFTER
			return m_name.equals(((Test) obj).getName());
		} catch (NullPointerException e) { // BEFORE: Exception e
			// It is bad form to catch the generic Exception. It would be more
			// prudent to catch what the try specifically will throw ie
			// NullPointerException.
			e.printStackTrace();
		}

		// Must return something (even if try catch fails)
		return false;
	}

	public String toString() {
		// I'm a tad skeptical about this. Usually you want to be printing out
		// all the relevant data members including strings and time. Who knows
		// what test is used for, but I'd print those out too.
		// BEFORE: String out = m_name + m_numbers.toString();
		String out = m_name + " " + m_time + "\n";
		out += m_numbers.toString() + "\n";
		out += m_strings.toString() + "\n";
		return out;
	}

	public void removeString(String str) {
		// Probably when you want to remove a string, this includes all strings
		// which are equal to str.
		// BEFORE: m_strings.remove(str);
		while(m_strings.remove(str)) {
		}
	}

	public boolean containsNumber(int number) {
		return m_numbers.contains(number);
	}

	public boolean isHistoric() {
		return m_time.before(new Date());
	}

	// Getters and setters
	public String getName() {
		return m_name;
	}

	public void setName(String name) {
		m_name = name;
	}

	public Date getDate() {
		return m_time;
	}

	public void setDate(Date time) {
		m_time = time;
	}

	public List<Integer> getNumbers() {
		return m_numbers;
	}

	public void setNumbers(List<Integer> numbers) {
		m_numbers = numbers;
	}

	public List<String> getStrings() {
		return m_strings;
	}

	public void setStrings(List<String> strings) {
		m_strings = strings;
	}

	public static void main(String [] args) {
		Test t = new Test(new Date(), "yoyo", new ArrayList<Integer>(), new ArrayList<String>());
	}

}


