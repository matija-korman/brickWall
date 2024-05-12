import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Main {
	
	public record Pair<K, V>(K left, V right) {}
	
	static int width;
	static int height;
	
	static Map<Integer, List<Integer>> mapAllPossibleRowLayouts = new HashMap<>();
	static Map<Pair<Integer,Integer>, Boolean> mapRowPairsCompatibility = new HashMap<>();
	static int numOfGoodWallConfigurations = 0;
	
    public static void main(String[] args) {
    	try {
        	width = Integer.valueOf(args[0]);
        	height = Integer.valueOf(args[1]);
            System.out.println("Width: " + width + " Height: " + height );
    	}
    	catch(Exception ex) {
            System.out.println("Invalid input. Terminating program."); 
            System. exit(0);
    	}

    	createAllDifferentBrickRowLayouts();
        System.out.println("Number of possible row layouts: " + mapAllPossibleRowLayouts.size());

        createMapOfRowPairsCompatibility();
        calculateNumOfGoodWallConfigurations();
        System.out.println("Number of good wall configurations: " + numOfGoodWallConfigurations); 
    }
    
    static public void createAllDifferentBrickRowLayouts() {
    	addBrickToRow(new ArrayList<Integer>());
    }
    
    static public void addBrickToRow(List<Integer> list) {
    	List<Integer> list_1  = new ArrayList<>();
    	List<Integer> list_2  = new ArrayList<>();

    	if(list==null || list.isEmpty()) {
    		 list = new ArrayList<Integer>();
    	}
    	else {
    		list_1.addAll(list);
    		list_2.addAll(list);	
    	}
    	
    	if(!list_1.isEmpty() && list_1.get(list_1.size()-1) + 2 == width) {
    		mapAllPossibleRowLayouts.put(mapAllPossibleRowLayouts.size() + 1, list_1);
    	}
    	else if (list_1.isEmpty()) {
    		list_1.add(2);
    		addBrickToRow(list_1);
    	}
    	else if (list_1.get(list_1.size() - 1)  + 2 < width) {
    		list_1.add(list_1.get(list_1.size()-1) + 2);
    		addBrickToRow(list_1);
    	}
    	
    	if(!list_2.isEmpty() && list_2.get(list_2.size()-1) + 3 == width) {
    		mapAllPossibleRowLayouts.put(mapAllPossibleRowLayouts.size() + 1, list_2);
    	}
    	else if (list_2.isEmpty()) {
    		list_2.add(3);
    		addBrickToRow(list_2);
    	}
    	else if (list_2.get(list_2.size() - 1)  + 3 < width) {
    		list_2.add(list_2.get(list_2.size()-1) + 3);
    		addBrickToRow(list_2);
    	}
    }
    
    static public void createMapOfRowPairsCompatibility() {
    	for(int i = 1; i <= mapAllPossibleRowLayouts.size(); i++) {
        	for(int j = 1; j <= mapAllPossibleRowLayouts.size(); j++) {
        		List<Integer> list_1 = mapAllPossibleRowLayouts.get(i);
        		List<Integer> list_2 = mapAllPossibleRowLayouts.get(j);
           		boolean hasRunningCrack = list_1.stream().anyMatch(it -> list_2.contains(it));
           		
           		mapRowPairsCompatibility.put(new Pair<Integer, Integer>(i,j), hasRunningCrack);
        	}
    	}
    }
    
    static public void calculateNumOfGoodWallConfigurations() {
    	for(int i = 1; i <= mapAllPossibleRowLayouts.size(); i++) {
    		List<Integer> list_1 = mapAllPossibleRowLayouts.get(i);
    		compareRows(list_1, 1, i);
    	}
    }

    
    static public void compareRows(List<Integer> row, int level, int index) {
        for(int j = 1; j <= mapAllPossibleRowLayouts.size(); j++) { 
       		if(index == j) {
       			continue;
       		}
       		boolean hasRunningCrack = mapRowPairsCompatibility.get(new Pair<Integer,Integer>(index,j));
       		if(hasRunningCrack) {
       			continue;
        	}
        	else if(level==(height-1)) {
       			numOfGoodWallConfigurations++;
       		}
       		else {
           		List<Integer> list_2 = mapAllPossibleRowLayouts.get(j);
       			compareRows(list_2, level + 1, j);
       		}
       	}
    }
}