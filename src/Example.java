import model.*;

import java.util.ArrayList;

public class Example {
	public static void main(String[] args) {
		ArrayList<String> it1 = new ArrayList<String>();
		it1.add("il");
		it1.add("gatto");
		it1.add("mangia");
		it1.add("pesce");
		
		ArrayList<String> en1 = new ArrayList<String>();
		en1.add("the");
		en1.add("cat");
		en1.add("eats");
		en1.add("fish");
		
		
		ArrayList<String> it2 = new ArrayList<String>();
		it2.add("un");
		it2.add("gatto");
		it2.add("è");
		it2.add("sul");
		it2.add("tavolo");
		
		ArrayList<String> en2 = new ArrayList<String>();
		en2.add("a");
		en2.add("cat");
		en2.add("is");
		en2.add("on");
		en2.add("the");
		en2.add("table");
		
		ArrayList<String> it3 = new ArrayList<String>();
		it3.add("i");
		it3.add("fiori");
		it3.add("sono");
		it3.add("sul");
		it3.add("tavolo");
		
		ArrayList<String> en3 = new ArrayList<String>();
		en3.add("the");
		en3.add("flowers");
		en3.add("are");
		en3.add("on");
		en3.add("the");
		en3.add("table");
		
		
		ArrayList<ArrayList<String>> it = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> en = new ArrayList<ArrayList<String>>();
		
		it.add(it1);
		it.add(it2);
		it.add(it3);
	
		en.add(en1);
		en.add(en2);
		en.add(en3);
		
		ModelOne model = new ModelOne(it, en);
		model.expectationMaximum();
		model.print();
	}
}
