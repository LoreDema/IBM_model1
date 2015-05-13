package model;

import java.util.ArrayList;

public class SentencePair {
	
	private ArrayList<EWord> e;
	private ArrayList<String> f;
	
	public SentencePair(ArrayList<String> f, ArrayList<String> e){
		this.e = new ArrayList<EWord>();
		for (String word : e){
			EWord eWord = new EWord();
			eWord.setWord(word);
			eWord.setsTotal(0);
			this.e.add(eWord);
		}
		this.setF(f);
	}

	public ArrayList<EWord> getE() {
		return e;
	}

	public void setE(ArrayList<EWord> e) {
		this.e = e;
	}

	public ArrayList<String> getF() {
		return f;
	}

	public void setF(ArrayList<String> f) {
		this.f = f;
	}

}
