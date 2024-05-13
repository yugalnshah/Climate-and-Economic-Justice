# Climate and Economic Justice

The purpose of this assignment is to practice linked structures.

## Overview

In this project, I'll be working with community data from various regions to explore the intersection of climate and economic justice. The provided data includes information about racial demographics, pollution levels, flood risks, poverty rates, and more. The goal is to process this data using linked lists and analyze the social and environmental aspects of different communities.

Communities data analysis is an important tool to understand communities aspects. For example, flood risk and high PM2.5 levels to name a few.
The data can help understand which communities are susceptible to flooding, or if disadvantaged communities are more susceptible to flooding than non-disadvantage communities.
In this project, I’ll implement methods in the ClimateEconJustice.java class to create a 3-layered linked list structure consisting of states, counties, and communities. Each layer is interconnected, allowing to navigate through the data hierarchy. The methods will carry out operations on this linked structure to gain insights from the community data.

This is not the optimal data structure to hold this dataset but it will allow me to practice in linked structures.
The data we will be using was obtained from the Geoplatform screening tool. The data file provided contains only a portion of the original data set.

## Methods 

### 1. addToStateLevel

This method adds a single state to the layered linked list starting at firstState. Add to the end of the list, and if the state already exists in the list do nothing.

To complete this method: 

Given a line from the input file, split (string_name.split(“,”)) each line to extract information such as community, county, and state names, along with various attributes. The indices for all the contents required are as follows:
* Community: index 0
* County: index 1
* State: index 2
* Percent African American: index 3
* Percent Native: index 4
* Percent Asian: index 5
* Percent White: index 8
* Percent Hispanic: index 9
* Disadvantaged: index 19
* PM Level: index 49
* Chance of Flood: index 37
* Poverty Line: index 121

**This method  will ONLY use the states’ name (index 2)**
 
* Create instances of StateNode classes to store the extracted data.
* In later methods I will build the linked list structure by adding states, counties, and communities using setNext and setDown methods.

### 2. addToCountyLevel

This method reads a county and links it to its corresponding state.

* If a county already exists in the list, do nothing. 

To complete this method: 

1. Read a line the same way I did in the previous method. I will use **indices 1 and 2 for this method**.
2. Search for the state I want to link a county to, and access its county list through its down method.
3. Add to the end of the county list, doing nothing if it’s present.

### 3. addToCommunityLevel

This method reads a community and links it to its corresponding county.

To complete this method: 

1. Read a line the same way I did in the previous method. For reference, here are the indices used:
* Community: index 0
* County: index 1
* State: index 2
* Percent African American: index 3
* Percent Native: index 4
* Percent Asian: index 5
* Percent White: index 8
* Percent Hispanic: index 9
* Disadvantaged: index 19
* PM Level: index 49
* Chance of Flood: index 37
* Poverty Line: index 121

2. I will need to create a Data object to store the data from this community; it will be stored inside the CommunityNode.
3. Search for the state corresponding to this community and then the county by using the state’s down reference. Then, add the CommunityNode to the end of the community list (access through county’s down reference).

This is the expected output for TestCommunityData.csv when addToStateLayer, addToCountyLayer, and addToCommunityLayer are complete. As I complete these methods, I will build each one of these layers starting from states.

<img width="1030" alt="Screenshot 2024-05-13 at 12 02 11 AM" src="https://github.com/yugalnshah/Climate-and-Economic-Justice/assets/162384655/6bf14ef5-771a-458f-9840-f9c899eea967">

### 4. disadvantagedCommunities

This method calculates the number of communities that have a certain percentage or higher of a particular racial demographic and are identified as **disadvantaged**.  

To complete this method: 

1. Start by traversing the linked list structure layer by layer.
2. Once I reach the community layer, count the number of communities that have said percentage or higher of the racial demographic and are also identified as disadvantaged.
3. Utilize the getAdvantageStatus method to determine disadvantaged status.
4. Return the count of the communities that meet these criteria.

**Note**: getAdvantageStatus() returns the disadvantaged value that you read in addToCommunityLevel and added to each community’s Data object: it is a String that is either “True” or “False”. True indicates a community is disadvantaged, False otherwise.
 
**Note 2**: The Driver reads and passes in percentage values from 0-100 (given through the parameter userPrcntage), while the percentage values in the CSV files are from 0.0-1.0. Make sure to multiply the value read in the CSV by 100 when comparing against userPrcntage.
 
This is the expected output for this method when tested in the driver using TestCommunityData.csv. Linked structure must be created by calling createLinkedStructure in the driver before testing this method:

<img width="1028" alt="Screenshot 2024-05-13 at 12 03 09 AM" src="https://github.com/yugalnshah/Climate-and-Economic-Justice/assets/162384655/a53289d8-93e1-4bc0-8737-fbd13a043f74">

### 5. nonDisadvantagedCommunities

Similar to the previous method, this method calculates the number of communities that have a certain percentage or higher of a particular racial demographic and are identified as **non-disadvantaged**.

To complete this method: 

1. Again, traverse the linked list structure layer by layer.
2. Once I reach the community layer, count the number of communities that have said percentage or higher of the racial demographic and are also identified as non-disadvantaged.
3. Utilize the getAdvantageStatus method to determine disadvantaged status.
4. Return the count of communities meeting these criteria.

**Note**: getAdvantageStatus() returns the disadvantaged value that I read in addToCommunityLevel and added to each community’s Data object: it is a String that is either “True” or “False”. True indicates a community is disadvantaged, False otherwise.

**Note 2**: The Driver reads and passes in percentage values from 0-100 (given through the parameter userPrcntage), while the percentage values in the CSV files are from 0.0-1.0. Make sure to multiply the value read in the CSV by 100 when comparing against userPrcntage.
 
This is the expected output for this method when tested in the driver using TestCommunityData.csv. Linked structure must be created by calling createLinkedStructure in the driver before testing this method:

<img width="844" alt="Screenshot 2024-05-13 at 12 06 07 AM" src="https://github.com/yugalnshah/Climate-and-Economic-Justice/assets/162384655/e19299d7-f3be-47e1-b8d2-ddaadd86666a">

### 6. statesPMLevels

This method identifies states with pollution levels (PM2.5) equal to or exceeding a provided threshold.

To complete this method: 

1. Traverse the linked list structure.
2. Once I reach the community layer, check if each community has a recorded pollution level equal to or higher than the pollution level entered.
3. Identify states containing communities with pollution levels meeting the threshold.
4. Store these states in an ArrayList and return the list.

This is the expected output for this method when tested in the driver using TestCommunityData.csv. Linked structure must be created by calling createLinkedStructure in the driver before testing this method:

<img width="542" alt="Screenshot 2024-05-13 at 12 06 50 AM" src="https://github.com/yugalnshah/Climate-and-Economic-Justice/assets/162384655/661d68e8-d0c5-4577-8abc-b58232b02293">

### 7. chanceOfFlood

This method counts the number of communities that have a chance of flooding equal to or exceeding a provided percentage.

To complete this method:

1. Traverse the linked list structure.
2. Once I reach the community layer, count the number of communities that have a **chance of flooding** equal to or higher than the specified threshold.
3. Return the total count of communities meeting this criterion.

**Note**: The Driver reads and passes in whole number percentage values from 0-100 (given through the userPrcntage parameter)
 
This is the expected output for this method when tested in the driver using TestCommunityData.csv. Linked structure must be created by calling createLinkedStructure in the driver before testing this method:

<img width="846" alt="Screenshot 2024-05-13 at 12 07 26 AM" src="https://github.com/yugalnshah/Climate-and-Economic-Justice/assets/162384655/127da9a1-f9ac-4a0e-b7f4-73e515d2638e">

### 8. lowestIncomeCommunities

This method identifies the 10 communities with the lowest incomes within a specific state.

To complete this method:

1. Traverse the linked list structure until I reach the specified state.
2. Then, traverse the linked list connected to that state until I reach the community layer. 
3. Iterate through the communities, maintaining an ArrayList of the 10 lowest-income communities – I can use the getPercentPovertyLine method of the community’s corresponding Data object.
   * As I iterate, if there are ten communities in the ArrayList:
        * Find the community with the lowest percent poverty line. If there are multiple communities with the same (lowest) percent, use the first one.
        * Replace the lowest poverty line community with the community I am at IF the current community has a higher percent poverty line than the one with the lowest.
4. Update this list as I encounter lower income communities, until I reach the end of the communities list.

Refer to the diagram below for steps 3 and 4:

<img width="1123" alt="Screenshot 2024-05-13 at 12 08 02 AM" src="https://github.com/yugalnshah/Climate-and-Economic-Justice/assets/162384655/46248e84-a633-4c8e-967a-c1f35535440d">

**Note 1**: the ArrayList order does not matter.

**Note 2**: Use list.set(index, elementToReplaceWith) to replace an element stored at an index with the object passed through parameters in the same index.

This is the expected output for this method when tested in the driver using TestCommunityData.csv. You must create the linked structure by calling createLinkedStructure in the driver before testing this method:

<img width="840" alt="Screenshot 2024-05-13 at 12 08 21 AM" src="https://github.com/yugalnshah/Climate-and-Economic-Justice/assets/162384655/9979d9b4-247e-455a-8148-968ff3a5c09c">


