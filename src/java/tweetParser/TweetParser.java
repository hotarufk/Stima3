package tweetParser;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class TweetParser {
	private int sentiment;						//Overall sentimen
	private List<String> keywords;  			//List keyword untuk pencarian tweet
	private List<String> positiveWord;  		//List kata2 positif
	private List<String> negativeWord;  		//List kata2 negatif
	private List<List<Integer> >kmpTable1;   	//Tabel border function KMP untuk keywords
	private List<List<Integer> >kmpTable2;	 	//Tabel border function KMP untuk positiveWord
	private List<List<Integer> >kmpTable3;	 	//Tabel border function KMP untuk negativeWord
	
	private List<Map<Character, Integer> >bmTable1;   	//Tabel border function KMP untuk keywords
	private List<Map<Character, Integer> >bmTable2;	 	//Tabel border function KMP untuk positiveWord
	private List<Map<Character, Integer> >bmTable3;	 	//Tabel border function KMP untuk negativeWord
	boolean mode;								//true = KMP, false = BM
	
	/*
	Constructor.
	*/
	public TweetParser(){
		keywords = new ArrayList<String>();
		positiveWord = new ArrayList<String>();
		negativeWord = new ArrayList<String>();
		kmpTable1 = new ArrayList<List<Integer> >();
		kmpTable2 = new ArrayList<List<Integer> >();
		kmpTable3 = new ArrayList<List<Integer> >();
		bmTable1 = new ArrayList<Map<Character, Integer> >();
		bmTable2 = new ArrayList<Map<Character, Integer> >();
		bmTable3 = new ArrayList<Map<Character, Integer> >();
		sentiment = 0;
	}
	
	/*
	Reset object. Fungsi digunakan untuk set keywords dan mode pencarian.
	*/
	public void Reset(List<String> key, List<String> pos, List<String> neg, boolean mod){
		Clear();
		for (String k: key){
			keywords.add(k.toLowerCase());
		}
		for (String p: pos){
			positiveWord.add(p.toLowerCase());
		}
		for (String n: neg){
			negativeWord.add(n.toLowerCase());
		}
		mode = mod;
		if (mode){
			BuildKMPTables();
		}
		else {
			BuildBMTables();
		}
	}
	
	/*
	Preprocessing keywords untuk membentuk KMP border function table.
	table 1 = keywords
	table 2 = positiveWord
	table 3 = negativeWord
	*/
	private void BuildKMPTables(){
		int index = -1;
		for (String key : keywords){
			int pos = 2;
			int cnd = 0;
			index++;
			kmpTable1.add(new ArrayList<Integer>(key.length()));
			kmpTable1.get(index).add(0,-1);
			kmpTable1.get(index).add(1,0);
			while (pos < key.length()){
				if (key.charAt(pos - 1) == key.charAt(cnd)){
					cnd++;
					kmpTable1.get(index).add(pos, cnd);
					pos++;
				}
				else if (cnd > 0){
					cnd = kmpTable1.get(index).get(cnd);
				}
				else{
					kmpTable1.get(index).add(pos, 0);
					pos++;
				}
			}
		}
		index = -1;
		for (String key : positiveWord){
			int pos = 2;
			int cnd = 0;
			index++;
			kmpTable2.add(new ArrayList<Integer>(key.length()));
			kmpTable2.get(index).add(0,-1);
			kmpTable2.get(index).add(1,0);
			while (pos < key.length()){
				if (key.charAt(pos - 1) == key.charAt(cnd)){
					cnd++;
					kmpTable2.get(index).add(pos, cnd);
					pos++;
				}
				else if (cnd > 0){
					cnd = kmpTable2.get(index).get(cnd);
				}
				else{
					kmpTable2.get(index).add(pos, 0);
					pos++;
				}
			}
		}
		index = -1;
		for (String key : negativeWord){
			int pos = 2;
			int cnd = 0;
			index++;
			kmpTable3.add(new ArrayList<Integer>(key.length()));
			kmpTable3.get(index).add(0,-1);
			kmpTable3.get(index).add(1,0);
			while (pos < key.length()){
				if (key.charAt(pos - 1) == key.charAt(cnd)){
					cnd++;
					kmpTable3.get(index).add(pos, cnd);
					pos++;
				}
				else if (cnd > 0){
					cnd = kmpTable3.get(index).get(cnd);
				}
				else{
					kmpTable3.get(index).add(pos, 0);
					pos++;
				}
			}
		}
	}
	
	private void BuildBMTables(){
		for (String key: keywords){
			Map<Character, Integer> tempTable = new HashMap<Character, Integer>();
			for (int i = 0; i < key.length(); i++){
				char c = key.charAt(i);
				tempTable.put(c, i);
			}
			bmTable1.add(tempTable);
		}
		for (String key: positiveWord){
			Map<Character, Integer> tempTable = new HashMap<Character, Integer>();
			for (int i = 0; i < key.length(); i++){
				char c = key.charAt(i);
				tempTable.put(c, i);
			}
			bmTable2.add(tempTable);
		}
		for (String key: negativeWord){
			Map<Character, Integer> tempTable = new HashMap<Character, Integer>();
			for (int i = 0; i < key.length(); i++){
				char c = key.charAt(i);
				tempTable.put(c, i);
			}
			bmTable3.add(tempTable);
		}
	}
	
	/*Search keyword pencarian. Akan mengembalikan string dimana kata yang dicari akan dibungkus dengan HTML tag bold.
	Jika tidak ada match, akan mengembalikan string dengan panjang = 0.
	
	*/
	public String SearchKey(String tweet){
		int index = -1;
		List<Integer> matchPos = new ArrayList<Integer>();
		if (tweet.length() == 0 || (kmpTable1.size() == 0 && bmTable1.size() == 0)) return new String();
		for (String key : keywords){
			index++;
			if (mode){
				int m = MatchKMP(tweet.toLowerCase(), key, kmpTable1.get(index));
				if (m < tweet.length()){
					matchPos.add(m);
				}
				else {
					return new String();
				}
			}
			else{
				int m = MatchBM(tweet.toLowerCase(), key, bmTable1.get(index));
				if (m < tweet.length()){
					matchPos.add(m);
				}
				else {
					return new String();
				}
			}
		}
		String result = new String(tweet);
		for (int i = 0; i<matchPos.size(); i++){
			int start = matchPos.get(i) + i * 7;
			int end = start + keywords.get(i).length();
			result = result.substring(0,start) + "<b>" + result.substring(start,end) + "</b>" + result.substring(end);
		}
		return result;
	}
	
	/*Pencarian keyword2 positif dan negatif.
	Untuk tiap kata positif, akan menambah nilai sentimen dr tweet tsb.
	Untuk tiap kata positif, akan mengurangi nilai sentimen dr tweet tsb.
	Jika sentimen tweet > 0, overall sentimen bertambah 1.
	Jika sentimen tweet < 0, overall sentimen berkurang 1.
	*/
	public int SearchSentiment(String tweet){
		int index = -1;
		int localSentiment = 0;
		for (String key: positiveWord){
			index++;
			if (mode){
				if (MatchKMP(tweet, key, kmpTable2.get(index)) < tweet.length()){
					localSentiment++;
				}
			}
			else{
				if (MatchBM(tweet, key, bmTable2.get(index)) < tweet.length()){
					localSentiment++;
				}
			}
		}
		index = -1;
		for (String key: negativeWord){
			index++;
			if (mode){
				if (MatchKMP(tweet, key, kmpTable3.get(index)) < tweet.length()){
					localSentiment--;
				}
			}
			else{
				if (MatchBM(tweet, key, bmTable3.get(index)) < tweet.length()){
					localSentiment--;
				}
			}
		}
		if (localSentiment > 0) sentiment++;
		else if (localSentiment < 0) sentiment--;
		
		return localSentiment;
	}
	/*
	Pencocokan menggunakan KMP
	*/
	private int MatchKMP(String s, String p, List<Integer> table){
		if (s.length() < p.length()) return s.length();
		int m = 0;
		int i = 0;
		while (m+i < s.length()){
			if (p.charAt(i) == s.charAt(m+i)){
				if (i == p.length() - 1){
					return m;
				}
				else {
					i++;
				}
			}
			else {
				m = m + i - table.get(i);
				if (table.get(i) > -1){
					i = table.get(i);
				}
				else {
					i = 0;
				}
			}
		
		}
		return s.length();
	}
	
	private int LastBM(char c, Map<Character, Integer> table){
		if (table.containsKey(c)){
			return table.get(c);
		}
		else {
			return -1;
		}
	}
	
	private int MatchBM(String s, String p, Map<Character, Integer> table){
		if (s.length() < p.length()) return s.length();
		int m = p.length() - 1;
		int i = m;
		do{
			if (s.charAt(m) == p.charAt(i)){
				if (i == 0){
					return m;
				}
				else{
					m--;
					i--;
				}
			}
			else{
				m = m + p.length() - Math.min(i, 1+LastBM(s.charAt(m),table));
				i = p.length() - 1;
			}
		} while (m < s.length());
		return s.length();
	}
	
	//Overall sentimen
	public int Result(){
		return sentiment;
	}
	
	//Menghapus seluruh isi object.
	public void Clear(){
		keywords.clear();
		positiveWord.clear();
		negativeWord.clear();
		kmpTable1.clear();
		kmpTable2.clear();
		kmpTable3.clear();
		bmTable1.clear();
		bmTable2.clear();
		bmTable3.clear();
		sentiment = 0;
	}
}