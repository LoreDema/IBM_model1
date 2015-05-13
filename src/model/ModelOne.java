package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModelOne {
	
	private ArrayList<SentencePair> sentence_pairs;
	private HashMap<String, HashMap<String, Double>> tEF;
	private HashMap<String, HashMap<String, Double>> cEF;
	private HashMap<String, Double> tF;
	
	
	/*
	 * initialize the model
	 */
	public ModelOne(ArrayList<ArrayList<String>> f, ArrayList<ArrayList<String>> e){
		this.sentence_pairs = new ArrayList<SentencePair>();
		for (int i = 0; i < f.size(); i++){
			SentencePair pair = new SentencePair(f.get(i), e.get(i));
			sentence_pairs.add(pair);
		}
		tEF = new HashMap<String, HashMap<String, Double>>();
		cEF = new HashMap<String, HashMap<String, Double>> ();
		tF = new HashMap<String, Double>();
	}
	
	public ArrayList<SentencePair> getSentence_pairs() {
		return sentence_pairs;
	}

	public void setSentence_pairs(ArrayList<SentencePair> sentence_pairs) {
		this.sentence_pairs = sentence_pairs;
	}
	/*
	 * compute expectation maximum
	 */
	public HashMap<String,HashMap<String,Double>> expectationMaximum(){
		
		// build e vocabulary
		ArrayList<String> e_voc = new ArrayList<String>(); 
		for (SentencePair pair : sentence_pairs){
			for (EWord e : pair.getE()){
				if(!e_voc.contains(e.getWord())){
					e_voc.add(e.getWord());
				}
			}
		}
		// initialize t(e|f) uniformly
		for(SentencePair pair : sentence_pairs){
			for(String f : pair.getF()){
				if (tEF.get(f) == null){
					HashMap<String, Double> entry = new HashMap<String, Double>();
					for (String e : e_voc){
						entry.put(e, (double) (1.0/e_voc.size()));
					}
					tEF.put(f, entry);
				}
			}
		}
		while(true){
			// set count(e|f) to 0 for all e,f and total(f) to 0 for all f
			for(SentencePair pair : sentence_pairs){
				for(String f : pair.getF()){
					tF.put(f, (double) 0);
					HashMap<String, Double> entry2 = new HashMap<String, Double>();
					for (String e : e_voc){
						entry2.put(e, (double) 0);
					}
					cEF.put(f, entry2);
				}
			}
			for (SentencePair pair : sentence_pairs){
				// compute normalization
				for (EWord e : pair.getE()){
					e.setsTotal(0);
					for (String f : pair.getF()){
						e.setsTotal(e.getsTotal() + tEF.get(f).get(e.getWord()));
					}
				}
				// collect counts
				for (EWord e : pair.getE()){
					for (String f : pair.getF()){
						cEF.get(f).put(e.getWord(), cEF.get(f).get(e.getWord()) + 
								((tEF.get(f).get(e.getWord())))/(e.getsTotal()));
						tF.put(f, tF.get(f) + 
								((tEF.get(f).get(e.getWord()))/(e.getsTotal())));
					}
				}
			}
			// save previous to check if model is converged
			HashMap<String, HashMap<String, Double>> previous = new HashMap<String, HashMap<String, Double>>(tEF);
			// estimate probabilities
			for (Map.Entry<String, HashMap<String, Double>> entry1 : tEF.entrySet()){
				HashMap<String, Double> temp1 = new HashMap<String, Double>();
				for (String entry4 : e_voc){
					temp1.put(entry4, 
							(cEF.get(entry1.getKey()).get(entry4)) /
							(tF.get(entry1.getKey())));
				}
				tEF.put(entry1.getKey(), temp1);
			}
			// Check if model is converged
			ArrayList<Double> diff = new ArrayList<Double>();
			double x = 0; 
			for (Map.Entry<String, HashMap<String, Double>> entry : tEF.entrySet()){
				for (Map.Entry<String, Double> entry1 : entry.getValue().entrySet()){
					x = Math.abs((entry1.getValue() - previous.get(entry.getKey()).get(entry1.getKey())));
					if(x > 0.0000001){
						diff.add(x);
					}
				}
			}
			if(diff.isEmpty()){
				break;
			}
		}
		return tEF;
	}

	public HashMap<String, HashMap<String, Double>> gettEF() {
		return tEF;
	}

	public void settEF(HashMap<String, HashMap<String, Double>> tEF) {
		this.tEF = tEF;
	}

	public HashMap<String, Double> gettF() {
		return tF;
	}

	public void settF(HashMap<String, Double> tF) {
		this.tF = tF;
	}

	public HashMap<String, HashMap<String, Double>> getcEF() {
		return cEF;
	}

	public void setcEF(HashMap<String, HashMap<String, Double>> cEF) {
		this.cEF = cEF;
	}
	public void print(){
		for (Map.Entry<String, HashMap<String, Double>> entry : tEF.entrySet()){
			System.out.println(entry.getKey());
			for (Map.Entry<String, Double> entry1 : entry.getValue().entrySet()){
				System.out.println("\t" + entry1.getKey() + ": " + entry1.getValue());
			}
		}
	}

}
