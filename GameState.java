 /*
GameState.java
For use in the Final project for COSC 236.
Based on starter code first developed by Prof. Dastyni Loksa

This is the class to hold the state of the running game and allows easy
passing of important information to methods that require data from the
state of the game.

This starter code is designed for the verbs to be stored in the commandSystem.

*/


public class GameState {
    Location currentLocation;
    CommandSystem commandSystem;
    Location location [] = new Location [4];
    Player currentPlayer;
    Item earthQuakeRumbleSpell;
    Item fireball;
    Item health;
    Item waterFall;
    Item sword;
    public static int DISPLAY_WIDTH = 80;

    /*
        GameState Constructor

        Ideally, your game will be fully loaded and ready to play once this constructor has finished running.

        How things have been done here are just a rudementry setup to link the other classes and have the 
        bare bones example of the command system. This is not a great way to initilize your project.

        You should do better!
    */
    public GameState(){
        commandSystem = new CommandSystem(this);
        // Create first (starting) location 
        // (and store it in currentLocation so I can always referece where the player is easily)
        currentPlayer = new Player();
        currentLocation = new Location();
        currentLocation.pathName = " Mystic Cave Entry Points ";
        currentLocation.description = "You have been send to the Mystic Caves you must [enter] one of the three diffrent paths to fight within.[Earth], [Fire], and [Water] choose one and be send off to your journey. You can also [enter] a [store] using your mind  ";
        
        location[0] = new Location();
        location[0].pathName = "Earthquake Cavern";
        location[0].description = "You are in the Earthquake Cavern. This place is filled with jagged rocks and stones that seem to light up. The monsters here seems sturdy and heavy. Every few steps you can feel the ground slightly rumble. ";
        location[0].monster[0] = new Monster();
        location[0].monster[0].monHealth = 250;
        location[0].monster[0].monName = "Spike Rock Worm";
        location[0].monster[0].monAttackpoints = 35;
        location[0].monster[0].monDescript = "This beast is slow and heavy their life force is huge and so is their body. These worms seem to be around 100 feet in length but their size makes it hard for them to attack and like worms they are slow "; 
        location[0].monster[1] = new Monster(); 
        location[0].monster[1].monHealth = 350;
        location[0].monster[1].monName = "Iron Horned Beetle";
        location[0].monster[1].monAttackpoints = 35;// Odd interaction between location array and monster attack unable to currently resolve 
        location[0].monster[1].monDescript = "This beast is nearly 2 tons and it's horns are a clad iron. It also seems to make a odd cracking noise when it moves "; 
        location[0].monster[2] = new Monster(); 
        location[0].monster[2].monHealth = 225;
        location[0].monster[2].monAttackpoints = 40; 
        location[0].monster[2].monName = "Steel Storm Roach";
        location[0].monster[2].monDescript = "This monster is quick but lacks the same armour the other earth beasts have. Still this beast spiked legs does much more damage than the other earth beasts ";
        location[0].monster[3] = new Monster (); 
        location[0].monster[3].monHealth = 200;
        location[0].monster[3].monAttackpoints = 45; 
        location[0].monster[3].monName = "Sliver Spider";
        location[0].monster[3].monDescript = " This monster has a sliver body and it's eyes are dark blue. It's very keen with it's strikes but it's lack size means it's life force is low."; 
        location[0].monster[4] = new Monster(); 
        location[0].monster[4].monName = "BOSS: METAL MANTIS";
        location[0].monster[4].monHealth = 350; 
        location[0].monster[4].monAttackpoints = 50;
        location[0].monster[4].monDescript = "This is the strongest within the Earth Cavern. Its claws deal good damage and its life force is massive. This mantis holds power that's hard to imagine. ";
        location[0].endingText = " GOOD ENDING:: You have beaten every monster in the Earthquake Cavern and you have become it's keeper and king. " ; 
        
        
        



        location[1] = new Location();
        location[1].pathName = "Water Cave Whirlpool";
        location[1].description = "You are in the Water Cave Whirlpool. This place has water up to your waist and waterfalls are everywhere, like the name suggests there are also whirlpools around the place. The monsters here seem quick and powerful but their bodies are transparent and seem weak." ;
        location[1].monster[0] = new Monster(); 
        location[1].monster[0].monName = "Clear Body Water Serpent"; 
        location[1].monster[0].monHealth = 140; 
        location[1].monster[0].monAttackpoints = 60; 
        location[1].monster[0].monDescript = "It's fins are spiked up and it's teeth are sharped. Not to mention that it's attack is great, and it's clear body makes it hard to see in the water. Funny enough these monster can't take a punch "; 
        location[1].monster[1] = new Monster(); 
        location[1].monster[1].monName = "Crystal Sea Urchin  "; 
        location[1].monster[1].monHealth = 120; 
        location[1].monster[1].monAttackpoints = 65; 
        location[1].monster[1].monDescript = "The tips of its spikes are clear and hollow. High pressured water also shoots out the tips of the spikes. Its crystal body means that its life force is on the lower side. "; 
        location[1].monster[2] = new Monster(); 
        location[1].monster[2].monName = "Toxic Sponge"; 
        location[1].monster[2].monHealth = 110; 
        location[1].monster[2].monAttackpoints = 70; 
        location[1].monster[2].monDescript = "It seems to be a living rock filled with toxins. One touch can be very damaging. But it seems to be frail. "; 
        location[1].monster[3] = new Monster(); 
        location[1].monster[3].monName = "Clear Claw Crab"; 
        location[1].monster[3].monHealth = 70; 
        location[1].monster[3].monAttackpoints = 70; 
        location[1].monster[3].monDescript = "  Its claws do loads of damage despite looking frail. A well-timed strike can kill it in one attack."; 
        location[1].monster[4] = new Monster(); 
        location[1].monster[4].monName = " BOSS: Killer King Orca"; 
        location[1].monster[4].monHealth = 200; 
        location[1].monster[4].monAttackpoints = 75; 
        location[1].monster[4].monDescript = "This monster is Massive and its damage is unreal. It's a golden color with crystal teeth that are soaked in toxins. "; 
        location[1].endingText = "GOOD ENDING: You have beaten every monster in the Water Cave Whirlpool. You are now the water spirit and must guard over the seas."; 







        location[2] = new Location();
        location[2].pathName = "Flameveil Hollow";
        location[2].description = "You are in the Flameveil Hollow. This place is blazing hot, the heat here seems to burn everything in sight but oddly enough some plants seem to bear the heat and in fact, they seem to produce heat.  The monsters here are constantly on fire and don't seem much concerned about fighting or really anything....... ";
        location[2].monster[0] = new Monster(); 
        location[2].monster[0].monName = "Blazing Flame Ape"; 
        location[2].monster[0].monHealth = 200; 
        location[2].monster[0].monAttackpoints = 40; 
        location[2].monster[0].monDescript = "This monster is a great flaming ape, It does moderate damage and has moderate life force but it's flames seem to inflict burn"; 
        location[2].monster[1] = new Monster ();
        location[2].monster[1].monName = "Heat Flash Chimp"; 
        location[2].monster[1].monHealth = 185; 
        location[2].monster[1].monAttackpoints = 40; 
        location[2].monster[1].monDescript = "It's a odd monster that seems to be weaker than the last.";
        location[2].monster[2] = new Monster(); 
        location[2].monster[2].monName = " Blaze Back Gorilla"; 
        location[2].monster[2].monHealth = 210; 
        location[2].monster[2].monAttackpoints = 45; 
        location[2].monster[2].monDescript = "A monster with decent strength and a good life force. It has a warrior spirit similar to mine. ";
        location[2].monster[3] = new Monster(); 
        location[2].monster[3].monName = " Raging Flame Spider Monkey"; 
        location[2].monster[3].monHealth = 210; 
        location[2].monster[3].monAttackpoints = 55; 
        location[2].monster[3].monDescript = "This monster has great strength and a great life force. This monster is a true balanced warrior even more so than myself.";
        location[2].monster[4] = new Monster(); 
        location[2].monster[4].monName = "BOSS: Scorching King Orangutan"; 
        location[2].monster[4].monHealth = 210; 
        location[2].monster[4].monAttackpoints = 55; 
        location[2].monster[4].monDescript = "This monster is the ULTIMATE balanced warrior. Its hands are on constantly on fire. The heat produced from its body alone could destroy lesser warriors."; 
        location[1].endingText = "GOOD ENDING: You have beaten every monster the Flameveil Hollow. You are now a greater warrior and the King of Flameveil Hollow. ";

        
       
        location[3] = new Location(); 
        location[3].pathName = "The Old Cave store"; 
        location[3].description = "An old mystic store, that I can enter through using my mind as a medium but I need to focus to enter so I can't enter during combat. There is an old clerk who covered in a dark robe with his face hidden"; 
        
     
        // Create a demo item.
        health = new Item();
        health.itemName = "health";
        health.description = "This is a health potion, you can use it to heal 100 health.";
        health.itemCost = 150;
        health.itemStats = 100; 
        fireball = new Item();
        fireball.itemName = "fireball";
        fireball.description = "This a fireball it inflicts burns on monsters";
        fireball.itemCost= 250;
        fireball.itemStats = 20; 
        waterFall = new Item();
        waterFall.itemName = "waterFall";
        waterFall.description = "This is a waterFall it causes a drowning effect";
        waterFall.itemCost = 300;
        waterFall.itemStats = 25;
        sword = new Item();
        sword.itemName = "sword";
        sword.description = "This is a sword it is used to attack a monster";
        sword.itemCost  = 500;
        sword.itemStats = 40;
        earthQuakeRumbleSpell = new Item();
        earthQuakeRumbleSpell.itemName = "EarthQuake";
        earthQuakeRumbleSpell.description = "This is a rock it can be thrown at monsters or released as a rockfall on the monster";
        earthQuakeRumbleSpell.itemCost = 200;
        earthQuakeRumbleSpell.itemStats = 18;

        //Add item to list of nouns so our command system knows it exists.
        commandSystem.addNoun(health.itemName);
        commandSystem.addNoun(earthQuakeRumbleSpell.itemName);
        commandSystem.addNoun(fireball.itemName);
        commandSystem.addNoun(sword.itemName);
        commandSystem.addNoun(waterFall.itemName);
        commandSystem.addNoun("earth");
        commandSystem.addNoun("fire");
        commandSystem.addNoun("water");
        commandSystem.addNoun( "store");
        commandSystem.addNoun( "attack");
        commandSystem.addNoun("monster");
        commandSystem.addNoun("buy");
        commandSystem.addNoun("stance"); 
        commandSystem.addNoun("defense"); 
        /* 
            Once the commandSystem knows about the item, we need to code what happens with each of the commands that can happen with the item.
            See CommandSystem line 64 for what happens if you currently "look mat"
        */
    }
}