package climate;

import java.util.ArrayList;

public class ClimateEconJustice {

    private StateNode firstState;
    
    /*
    * Constructor
    */
    public ClimateEconJustice() {
        firstState = null;
    }

    /*
    * Get method to retrieve instance variable firstState
    * 
    * @return firstState
    */ 
    public StateNode getFirstState () {
        // DO NOT EDIT THIS CODE
        return firstState;
    }

    public void createLinkedStructure (String inputFile) {
        
        StdIn.setFile(inputFile);
        StdIn.readLine(); // Skips header
        
        // Reads the file one line at a time
        while ( StdIn.hasNextLine() ) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    public void addToStateLevel (String inputLine) {

        String[] splitLine = inputLine.split(",");
        boolean exists = false;
        String stateName = splitLine[2];
        StateNode currentState = firstState;
        while (currentState != null) {
            if (currentState.getName().equals(stateName)) {// checks if the state exists or not
                exists = true;
            }
            currentState = currentState.getNext();
        }
        if (!exists){ // only runs if the state doesn't exist
            StateNode newState = new StateNode(stateName, null, null); 
            if (firstState == null){
                firstState = newState; // if the list is empty, set the new node as the first one
            } else {
                currentState = firstState;
                while (currentState.getNext() != null){
                    currentState = currentState.getNext(); 
                }
                currentState.setNext(newState);// adds the state to the first level of the linked structure
            }
        }
    }

    public void addToCountyLevel (String inputLine) {

        String[] splitLine = inputLine.split(",");
        String sName = splitLine[2];
        String cName = splitLine[1];
        StateNode currentState = firstState; // basically a pointer tht points to the state that it is currently looking at
        boolean cexists = false; // variable to see if the county exists

        while (currentState != null) {
            if (currentState.getName().equals(sName)) { // if state is found, check if the county already exists in the state's list
                CountyNode currentCounty = currentState.getDown(); // finds the county node corresponding to the state name
                while (currentCounty != null) {
                    if (currentCounty.getName().equals(cName)) {
                        cexists = true;
                    }
                    currentCounty = currentCounty.getNext();
                }
                if (!cexists) {// only runs when the county does not exist
                    CountyNode nCounty = new CountyNode(cName, null, null);
                    if (currentState.getDown() == null) {
                        currentState.setDown(nCounty); // if the county list is empty, this sets the new county as the first one
                    } 
                    else {
                        currentCounty = currentState.getDown();
                        while (currentCounty.getNext() != null) {
                            currentCounty = currentCounty.getNext();
                        }
                        currentCounty.setNext(nCounty); // adds the county to the end of the county list
                        nCounty.setName(cName);
                    }
                }
            }
            currentState = currentState.getNext();
        }
    }

    public void addToCommunityLevel (String inputLine) {

        // read in from the file
        String[] splitLine = inputLine.split(",");
        String county = splitLine[1];
        String state = splitLine[2];
        String community = splitLine[0];

        double ptAfricanAmerican = Double.parseDouble(splitLine[3]);
        double ptNative = Double.parseDouble(splitLine[4]);
        double ptAsian = Double.parseDouble(splitLine[5]);
        double ptWhite = Double.parseDouble(splitLine[8]);
        double ptHispanic = Double.parseDouble(splitLine[9]);
        String disadvantaged = (splitLine[19]);
        double ptPMlevel = Double.parseDouble(splitLine[49]);
        double ptchanceOfFlood = Double.parseDouble(splitLine[37]);
        double ptpovertyLine = Double.parseDouble(splitLine[121]);

        CommunityNode nCommunity = new CommunityNode(community, null, null); // create a CommunityNode object
        Data commData = new Data(ptAfricanAmerican, ptNative, ptAsian, ptWhite, ptHispanic, disadvantaged, ptPMlevel, ptchanceOfFlood, ptpovertyLine); // create a Data object to store the community's information
        nCommunity.setInfo(commData); // set the community information to the new data object
         
        StateNode stateptr = firstState;
        for (; stateptr != null && !stateptr.getName().equals(state); stateptr = stateptr.getNext()) {} // traverse through the state nodes to find the corresponding communities
        CountyNode countyptr = stateptr.getDown();
        for (; countyptr != null && !countyptr.getName().equals(county); countyptr = countyptr.getNext()){} // get the CountyNode corresponding to the state
                
        CommunityNode communityptr = countyptr.getDown();
        while (communityptr != null){ // check if the community already exists within the county

            if (communityptr.getName().equals(community)){
                return;
            }
            communityptr = communityptr.getNext();

        }

        if (countyptr.getDown() == null) { // if the community does not exist, add it to the end of the community list
            countyptr.setDown(nCommunity);
        } 
        else {
            for (communityptr = countyptr.getDown(); communityptr.getNext() != null; communityptr = communityptr.getNext()) {
            }
            communityptr.setNext(nCommunity);
        }
    }

    public int disadvantagedCommunities (double userPrcntage, String race) {

        int afAM = 0, naAM = 0, asAM = 0, whAM = 0, hiAM = 0; // initial counters for each racial demographic

        for (StateNode stateptr = firstState; stateptr != null; stateptr = stateptr.getNext()) { // traverse through the linked list layer by layer
            for (CountyNode countyptr = stateptr.getDown(); countyptr != null; countyptr = countyptr.getNext()) {
                for (CommunityNode communityptr = countyptr.getDown(); communityptr != null; communityptr = communityptr.getNext()) {

                    boolean checkD = communityptr.getInfo().getAdvantageStatus().equals("True"); // check if the community is disadvantaged
                    boolean checkRAF = race.equalsIgnoreCase("African American"); // check if the racial demographic matches
                    if (checkD && checkRAF && (communityptr.getInfo().getPrcntAfricanAmerican()*100 >= userPrcntage)) { // check if it meets the specified percentage criteria
                        afAM++;
                    }
                    boolean checkRNA = race.equalsIgnoreCase("Native American");
                    if (checkD && checkRNA && (communityptr.getInfo().getPrcntNative()*100 >= userPrcntage)) {
                        naAM++;
                    }
                    boolean checkRAS = race.equalsIgnoreCase("Asian American");
                    if (checkD && checkRAS && (communityptr.getInfo().getPrcntAsian()*100 >= userPrcntage)) {
                        asAM++;
                    }
                    boolean checkRWH = race.equalsIgnoreCase("White American");
                    if (checkD && checkRWH && (communityptr.getInfo().getPrcntWhite()*100 >= userPrcntage)) {
                        whAM++;
                    }
                    boolean checkRHI = race.equalsIgnoreCase("Hispanic American");
                    if (checkD && checkRHI && (communityptr.getInfo().getPrcntHispanic()*100 >= userPrcntage)) {
                        hiAM++;
                    }

                }
            }
        }
        return afAM + naAM + asAM + whAM + hiAM; // return the total
    }

    public int nonDisadvantagedCommunities (double userPrcntage, String race) {

    int afAM = 0, naAM = 0, asAM = 0, whAM = 0, hiAM = 0; // initial counters for each racial demographic

    for (StateNode stateptr = firstState; stateptr != null; stateptr = stateptr.getNext()) {  // traverse through the linked list layer by layer
        for (CountyNode countyptr = stateptr.getDown(); countyptr != null; countyptr = countyptr.getNext()) {
            for (CommunityNode communityptr = countyptr.getDown(); communityptr != null; communityptr = communityptr.getNext()) {

                boolean checkD = communityptr.getInfo().getAdvantageStatus().equals("False"); // check if the community is disadvantaged
                boolean checkRAF = race.equalsIgnoreCase("African American");  // check if the racial demographic matches
                if (checkD && checkRAF && (communityptr.getInfo().getPrcntAfricanAmerican()*100 >= userPrcntage)) { // check if it meets the specified percentage criteria
                    afAM++;
                }
                boolean checkRNA = race.equalsIgnoreCase("Native American");
                if (checkD && checkRNA && (communityptr.getInfo().getPrcntNative()*100 >= userPrcntage)) {
                    naAM++;
                }
                boolean checkRAS = race.equalsIgnoreCase("Asian American");
                if (checkD && checkRAS && (communityptr.getInfo().getPrcntAsian()*100 >= userPrcntage)) {
                    asAM++;
                }
                boolean checkRWH = race.equalsIgnoreCase("White American");
                if (checkD && checkRWH && (communityptr.getInfo().getPrcntWhite()*100 >= userPrcntage)) {
                    whAM++;
                }
                boolean checkRHI = race.equalsIgnoreCase("Hispanic American");
                if (checkD && checkRHI && (communityptr.getInfo().getPrcntHispanic()*100 >= userPrcntage)) {
                    hiAM++;
                }

            }
        }
    }
        return afAM + naAM + asAM + whAM + hiAM; // return the total
    }
    
    public ArrayList<StateNode> statesPMLevels (double PMlevel) {

        ArrayList <StateNode> statesPM = new ArrayList<>(); // arraylist that stores all the states with pollution

        for (StateNode stateptr = firstState; stateptr != null; stateptr = stateptr.getNext()) { // traverse through the linked list layer by layer
            for (CountyNode countyptr = stateptr.getDown(); countyptr != null; countyptr = countyptr.getNext()) {
                for (CommunityNode communityptr = countyptr.getDown(); communityptr != null; communityptr = communityptr.getNext()) {

                    if (communityptr.getInfo().getPMlevel() >= PMlevel){ // check if the community's pollution level meets the threshold
                        if (!statesPM.contains(stateptr)){ // if not in the list, add it
                            statesPM.add(stateptr);
                        }
                    }

                }
            }
        }
            return statesPM; // return the list wuth all the states that meet the pollution threshold
    }

    public int chanceOfFlood (double userPercntage) {

        int communities = 0; // counter for number of communities

        for (StateNode stateptr = firstState; stateptr != null; stateptr = stateptr.getNext()) { // traverse through the linked list layer by layer
            for (CountyNode countyptr = stateptr.getDown(); countyptr != null; countyptr = countyptr.getNext()) {
                for (CommunityNode communityptr = countyptr.getDown(); communityptr != null; communityptr = communityptr.getNext()) {

                    if (communityptr.getInfo().getChanceOfFlood() >= userPercntage){ // check if the community's chance of flood meets the threshold
                        communities++;
                    }

                }
            }
        }
        return communities; // return the number of communities
    }

    public ArrayList<CommunityNode> lowestIncomeCommunities (String stateName) {
        
        ArrayList <CommunityNode> lowestCommunities = new ArrayList<>();
        StateNode stateptr = firstState;
        while (stateptr != null && !stateptr.getName().equals(stateName)){
            stateptr = stateptr.getNext();
        }
            for (CountyNode countyptr = stateptr.getDown(); countyptr != null; countyptr = countyptr.getNext()) {
                for (CommunityNode communityptr = countyptr.getDown(); communityptr != null; communityptr = communityptr.getNext()) {

                    if (lowestCommunities.size() < 10) { // if there are less than 10 communites, then add the current one
                        lowestCommunities.add(communityptr);
                    }
                    else { // find the community with the lowest percent poverty 
                        CommunityNode lincComm = lowestCommunities.get(0);
                        for (int i = 0; i < lowestCommunities.size(); i++) {

                            CommunityNode currComm = lowestCommunities.get(i);
                            if (currComm.getInfo().getPercentPovertyLine() < lincComm.getInfo().getPercentPovertyLine()) {
                            lincComm = currComm;
                            }

                    }
                    if (communityptr.getInfo().getPercentPovertyLine() > lincComm.getInfo().getPercentPovertyLine()) { // if the current community has a higher percent poverty than the lowest one in the list, replace it
                        lowestCommunities.remove(lincComm);
                        lowestCommunities.add(communityptr);
                    }
                    }

                }
                }
    
        return lowestCommunities; // return 10 lowest communities
    }
}