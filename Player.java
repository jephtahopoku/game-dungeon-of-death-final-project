import java.util.Scanner;
//import java.io.*; 
public class Player { 
Scanner input = new Scanner(System.in); 
String playerName = "N/A";
int playerCoins=300; 
double healthPoints = 225; 
double attackpoints = 55; // Actual attack will range between 40-70 attack points 
Item[] playerItems = new Item[5];   
double attackStance;
double DefenseStance;
int combatChecker = 0; 
int monsterCount = 0; 






public static String getPlayerName (String name, Scanner input1) {
String pName;
System.out.println(" Young Warrior before you wander off to your journey what's is your name....");
pName = input1.nextLine();
pName = name;
System.out.println("I see, truly this is a fine name, be well on your journey " + name );
return pName; 
}






public void addItem(Item itemToAdd) {
 int i=0;
 for(i=0; i<playerItems.length;i++){
  if(playerItems[i]==null){
   playerItems[i]=itemToAdd;
   break;
  }
 }
} 























}