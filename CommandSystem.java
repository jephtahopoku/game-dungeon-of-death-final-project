/*
CommandSystem.java
For use in the Final project for COSC 236.
Based on starter code first developed by Prof. Dastyni Loksa

This class is the primary logic class for the system. It defines what commands are valid, 
and what happens when those commands are executed.  
*/

import java.util.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
public class CommandSystem {
    private static int DISPLAY_WIDTH = 80;
    private GameState state;

    private List<String> verbs = new ArrayList<String>();
    private List<String> verbDescription = new ArrayList<String>();
    private List<String> nouns = new ArrayList<String>();

    /*
     * Constructor should defines all verbs that can be used in the commands and all
     * nouns the user can interact with.
     * 
     * Suggestion: These could all be loaded from a file.
     * 
     * Verb descriptions are stored in a parallel Arraylist with the Verbs and are
     * used when printing out the help menu (using the ? command).
     */
    public CommandSystem(GameState state) {
        this.state = state;
        DISPLAY_WIDTH = GameState.DISPLAY_WIDTH;
        // Assign verbs and descriptions here
        addVerb("?", "Show this help screen.");
        addVerb("look",
                "Use the look command by itself to look in your current area. \nYou can also look at a person or object by ntyping look and the name of what you want to look at.\nExample: look book");
        addVerb("l", "Same as the look command.");
        addVerb("quit", "Quit the game."); // NOTE: In the starter code, this is handeled by the client code - not the
        addVerb("enter", "Allows you to enter a location.");
        addVerb("attack", "Allows you to attack, only when monsters are near by");
        addVerb("a", "Same as attack");
        addVerb("attack stance", "Allows you to attack with more damage but you take more damage ");
        addVerb("defense stance", "Allows you to attack with less damage but take less damage");
        addVerb("defense", "Lets you brace for impact");
        addVerb("buy", "Allows you to buy only if you in the in the store");
        addVerb("use", "Allows you use items you have by adding the item name to use");
        // CommandSystem.
    }

    // When a command is only one Verb this method controls the result.
    public void executeVerb(String verb) {

        switch (verb) {
            case "l":
            case "look": // will show the description of the current room (stored in the state object)
                System.out.println("You look around.");
                System.out.println(formatStringToScreenWidth(state.currentLocation.description));
                break;
            case "?":
                this.printHelp();
                break;
            case "enter":
                System.out.println("You tried entering");
                break;
            case "defense":
                System.out.println("You braced for impact");
                break;

            case "buy":
                if (state.currentLocation.pathName == "The Old Cave store") {
                    System.out.println("Welcome what do you wish to buy");
                    System.out.println("Spells:: "
                            + "EarthQuake Rumble:  200 Coins   Fireball: 250 Coins      WaterFall:  270 Coins");
                    System.out.println("Potions:: Health: 150");
                } else
                    System.out.println("There is nothing to buy here warrior");
                break;
        
                
            case "attack":
            case "a":

                if (state.currentPlayer.monsterCount >= 5) {
                    System.out.println(state.currentLocation.endingText);
                    System.exit(0);
                }

                if (state.currentLocation.monster[state.currentPlayer.monsterCount] != null) {
                    System.out.println("YOU HAVE ENTERED BATTLE");
                    System.out.println(state.currentLocation.monster[state.currentPlayer.monsterCount].monName + " :: "
                            + state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth);
                    System.out.println("--------vs---------");
                    System.out.println("Health ::" + state.currentPlayer.healthPoints + "       Coins: "
                            + state.currentPlayer.playerCoins);
                    double damageDealt = state.currentPlayer.attackpoints;
                    double damageTaken = state.currentLocation.monster[state.currentPlayer.monsterCount].monAttackpoints;
                    state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth -= damageDealt;
                    state.currentPlayer.healthPoints -= damageTaken;
                    System.out.println("You strike and dealt " + damageDealt + " to "
                            + state.currentLocation.monster[state.currentPlayer.monsterCount].monName);
                    System.out.println("You took " + damageTaken + " points of damage");

                    if (state.currentPlayer.healthPoints < 1) {
                        System.out.println("You have taken to much damage");
                        System.out.println("YOU HAVE FALLEN");
                        System.exit(0);
                    }
                    if (state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth <= 0) {
                        System.out.println("You have beaten the "
                                + state.currentLocation.monster[state.currentPlayer.monsterCount].monName);

                        int newCoins = (int) (Math.random() * 80 + 20);
                        state.currentPlayer.playerCoins += newCoins;
                        System.out.println("You picked up " + newCoins + " coins");
                        state.currentPlayer.monsterCount += 1;
                        System.out.println("A new monster appeared, it might be good to [look] at it");
                        break;
                    }
                } else
                    System.out.println("There are no monsters here to attack");

        }
    }

    // When a command is a Verb followed by a noun, this method controls the result.
    public void executeVerbNoun(String verb, String noun) {
        // Initilize the string that we will use as a response to player input.
        String resultString = "";
        // Random rand = new Random();

        switch (verb) { // Decides what to do based on each verb
            case "use":
                switch (noun) {
                    case "fireball":
                        int hasItemFireball = 0;
                        for (int i = 0; i < state.currentPlayer.playerItems.length; i++) {
                            if (state.currentPlayer.playerItems[i] == null) {
                                continue;
                            }
                            if (state.currentPlayer.playerItems[i].itemName == state.fireball.itemName) {
                                hasItemFireball++;

                            }

                        }
                        if (hasItemFireball >= 1) {
                            if (state.currentPlayer.monsterCount >= 5) {
                                System.out.println(state.currentLocation.endingText);
                                System.exit(0);
                            }
                            if (state.currentLocation.monster[state.currentPlayer.monsterCount] != null) {
                                System.out.println("YOU USED THE FIREBALL SPELL");
                                System.out.println(
                                        state.currentLocation.monster[state.currentPlayer.monsterCount].monName + " :: "
                                                + state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth);
                                System.out.println("--------vs---------");
                                System.out.println("Health ::" + state.currentPlayer.healthPoints + "       Coins: "
                                        + state.currentPlayer.playerCoins);
                                double damageDealt = state.currentPlayer.attackpoints;
                                double damageTaken = state.currentLocation.monster[state.currentPlayer.monsterCount].monAttackpoints;
                                state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth -= damageDealt;
                                state.currentPlayer.healthPoints -= damageTaken;
                                damageDealt += state.fireball.itemStats;
                                damageTaken -= 10;
                                System.out.println("You casted a fireball and it dealt " + damageDealt + " to "
                                        + state.currentLocation.monster[state.currentPlayer.monsterCount].monName);
                                System.out.println(
                                        "The monster was burnt so you only took " + damageTaken + " points of damage");

                                if (state.currentPlayer.healthPoints < 1) {
                                    System.out.println("You have taken to much damage");
                                    System.out.println("YOU HAVE FALLEN");
                                    System.exit(0);
                                }
                                if (state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth <= 0) {
                                    System.out.println("You have beaten the "
                                            + state.currentLocation.monster[state.currentPlayer.monsterCount].monName);

                                    int newCoins = (int) (Math.random() * 80 + 20);
                                    state.currentPlayer.playerCoins += newCoins;
                                    System.out.println("You picked up " + newCoins + " coins");
                                    state.currentPlayer.monsterCount += 1;
                                    System.out.println("A new monster appeared, it might be good to [look] at it");

                                    break;
                                }
                            } else
                                System.out.println("There are no monsters here to [attack]");

                        } else
                            System.out.println("You don't have fireball");
                        break;
                    case "waterFall":
                        int hasItemWaterFall = 0;
                        for (int i = 0; i < state.currentPlayer.playerItems.length; i++) {
                            if (state.currentPlayer.playerItems[i] == null) {
                                continue;
                            }
                            if (state.currentPlayer.playerItems[i].itemName == state.waterFall.itemName) {
                                hasItemWaterFall++;

                            }

                        }
                        if (hasItemWaterFall >= 1) {

                            if (state.currentPlayer.monsterCount >= 5) {
                                System.out.println(state.currentLocation.endingText);
                                System.exit(0);
                            }

                            if (state.currentLocation.monster[state.currentPlayer.monsterCount] != null) {
                                System.out.println("YOU USED THE WATERFALL SPELL ");
                                System.out.println(
                                        state.currentLocation.monster[state.currentPlayer.monsterCount].monName + " :: "
                                                + state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth);
                                System.out.println("--------vs---------");
                                System.out.println("Health ::" + state.currentPlayer.healthPoints + "       Coins: "
                                        + state.currentPlayer.playerCoins);
                                double damageDealt = state.currentPlayer.attackpoints;
                                double damageTaken = state.currentLocation.monster[state.currentPlayer.monsterCount].monAttackpoints;
                                state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth -= damageDealt;
                                state.currentPlayer.healthPoints -= damageTaken;
                                damageDealt += state.waterFall.itemStats;
                                damageTaken -= 15;
                                System.out.println("You casted a waterFall and it dealt " + damageDealt + " to "
                                        + state.currentLocation.monster[state.currentPlayer.monsterCount].monName);
                                System.out.println("You took " + damageTaken + " points of damage");

                                if (state.currentPlayer.healthPoints < 1) {
                                    System.out.println("You have taken to much damage");
                                    System.out.println("YOU HAVE FALLEN");
                                    System.exit(0);
                                }
                                if (state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth <= 0) {
                                    System.out.println("You have beaten the "
                                            + state.currentLocation.monster[state.currentPlayer.monsterCount].monName);
                                    System.out.println("test 0 " + state.currentPlayer.monsterCount);
                                    int newCoins = (int) (Math.random() * 80 + 20);
                                    state.currentPlayer.playerCoins += newCoins;
                                    System.out.println("You picked up " + newCoins + " coins");
                                    state.currentPlayer.monsterCount += 1;
                                    System.out.println("A new monster appeared, it might be good to [look] at it");
                                    break;
                                }
                            } else
                                System.out.println("There are no monsters here to [attack]");

                        } else
                            System.out.println("You don't have waterFall");
                        break;
                    case "sword":
                        int hasItemSword = 0;
                        for (int i = 0; i < state.currentPlayer.playerItems.length; i++) {
                            if (state.currentPlayer.playerItems[i] == null) {
                                continue;
                            }
                            if (state.currentPlayer.playerItems[i].itemName == state.sword.itemName) {
                                hasItemSword++;

                            }

                        }
                        if (hasItemSword >= 1) {
                            if (state.currentLocation.monster[state.currentPlayer.monsterCount] != null) {
                                System.out.println("YOU USED A SWORD");
                                System.out.println(
                                        state.currentLocation.monster[state.currentPlayer.monsterCount].monName + " :: "
                                                + state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth);
                                System.out.println("--------vs---------");
                                System.out.println("Health ::" + state.currentPlayer.healthPoints + "       Coins: "
                                        + state.currentPlayer.playerCoins);
                                double damageDealt = state.currentPlayer.attackpoints;
                                double damageTaken = state.currentLocation.monster[state.currentPlayer.monsterCount].monAttackpoints;
                                state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth -= damageDealt;
                                state.currentPlayer.healthPoints -= damageTaken;
                                damageDealt += state.sword.itemStats;
                                damageTaken -= 30;
                                System.out.println("You used a sword and it dealt " + damageDealt + " to "
                                        + state.currentLocation.monster[state.currentPlayer.monsterCount].monName);
                                System.out.println("You took " + damageTaken + " points of damage");

                                if (state.currentPlayer.healthPoints < 1) {
                                    System.out.println("You have taken to much damage");
                                    System.out.println("YOU HAVE FALLEN");
                                    System.exit(0);
                                }
                                if (state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth <= 0) {
                                    System.out.println("You have beaten the "
                                            + state.currentLocation.monster[state.currentPlayer.monsterCount].monName);

                                    int newCoins = (int) (Math.random() * 80 + 20);
                                    state.currentPlayer.playerCoins += newCoins;
                                    System.out.println("You picked up " + newCoins + " coins");
                                    state.currentPlayer.monsterCount += 1;
                                    System.out.println("A new monster appeared, it might be good to [look] at it");
                                    break;
                                }
                            } else
                                System.out.println("There are no monsters here to attack");

                        } else
                            System.out.println("You don't have a sword");
                        break;
                    case "health":
                        int hasItemHealth = 0;
                        for (int i = 0; i < state.currentPlayer.playerItems.length; i++) {
                            if (state.currentPlayer.playerItems[i] == null) {
                                continue;
                            }
                            if (state.currentPlayer.playerItems[i].itemName == state.health.itemName) {
                                hasItemHealth++;

                            }

                        }
                        if (hasItemHealth >= 1) {
                            if (state.currentLocation.monster[state.currentPlayer.monsterCount] != null) {
                                System.out.println("YOU USED HEALTH");
                                if (state.currentPlayer.healthPoints + state.health.itemStats > 250) {
                                    state.currentPlayer.healthPoints = 250;
                                } else
                                    state.currentPlayer.healthPoints += state.health.itemStats;
                            }
                        } else
                            System.out.println("You don't have a sword");
                        break;
                    case "earthquake":
                        int hasItemearthQuake = 0;
                        for (int i = 0; i < state.currentPlayer.playerItems.length; i++) {
                            if (state.currentPlayer.playerItems[i] == null) {
                                continue;
                            }
                            if (state.currentPlayer.playerItems[i].itemName == state.earthQuakeRumbleSpell.itemName) {
                                hasItemearthQuake++;

                            }

                        }
                        if (hasItemearthQuake >= 1) {
                            if (state.currentPlayer.monsterCount >= 5) {
                                System.out.println(state.currentLocation.endingText);
                                System.exit(0);
                            }
                            if (state.currentLocation.monster[state.currentPlayer.monsterCount] != null) {
                                System.out.println("YOU USED THE EARTHQUAKE SPELL");
                                System.out.println(
                                        state.currentLocation.monster[state.currentPlayer.monsterCount].monName + " :: "
                                                + state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth);
                                System.out.println("--------vs---------");
                                System.out.println("Health ::" + state.currentPlayer.healthPoints + "       Coins: "
                                        + state.currentPlayer.playerCoins);
                                double damageDealt = state.currentPlayer.attackpoints;
                                double damageTaken = state.currentLocation.monster[state.currentPlayer.monsterCount].monAttackpoints;
                                state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth -= damageDealt;
                                state.currentPlayer.healthPoints -= damageTaken;
                                damageDealt += state.earthQuakeRumbleSpell.itemStats;
                                damageTaken -= 15;
                                System.out.println("You casted an Earthquake Spell and it dealt " + damageDealt + " to "
                                        + state.currentLocation.monster[state.currentPlayer.monsterCount].monName);
                                System.out.println(
                                        "The monster was shaken up and only did " + damageTaken + " points of damage");

                                if (state.currentPlayer.healthPoints < 1) {
                                    System.out.println("You have taken to much damage");
                                    System.out.println("YOU HAVE FALLEN");
                                    System.exit(0);
                                }
                                if (state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth <= 0) {
                                    System.out.println("You have beaten the "
                                            + state.currentLocation.monster[state.currentPlayer.monsterCount].monName);
                                    int newCoins = (int) (Math.random() * 80 + 20);
                                    state.currentPlayer.playerCoins += newCoins;
                                    System.out.println("You picked up " + newCoins + " coins");
                                    state.currentPlayer.monsterCount += 1;

                                    break;
                                }
                            } else
                                System.out.println("There are no monsters here to attack");

                        } else
                            System.out.println("You don't have earthQuake");
                        break;

                }
            case "l":
            case "look":
                switch (noun) { // for the given verb, decide what to do based on what noun was entered
                    case "":
                        /*
                         * This is extremely simple and hard coded. You should figure out a way to get
                         * the description from the mat itself and print that out here.
                         */
                        resultString += "You look at the welcome mat. You see nothing special.";
                        break;
                    case "monster":
                        if (state.currentLocation.monster[0] == null) {
                            System.out.println("There are no monsters around");
                            break;
                        } else
                            resultString += "You look at the near by monsters";
                        System.out.println(state.currentLocation.monster[0].monDescript);
                        // You cound design a way to look at any item without having to specify how to
                        // deal with each of them.
                        // That way you can code special cases for some items, and others would just use
                        // default behavior.
                        // This is HIGHLY encouraged. (It will save time and headaches!)
                    default:
                }
                break;
            case "enter":

                switch (noun) {
                    case "earth":
                        resultString += "Your have entered the EarthQuake Carven, you should try looking around";
                        state.currentLocation = state.location[0];
                        break;
                    case "fire":
                        resultString += "Your have entered the Flameveil Hollow, you should try looking around ";
                        state.currentLocation = state.location[2];
                        break;
                    case "water":
                        resultString += "Your have entered the Water Cave Whirlpool, you should try looking around";
                        state.currentLocation = state.location[1];
                        break;
                    case "store":
                        resultString += "You have enter the store, buy as you wish for the right price";
                        state.currentLocation = state.location[3];

                }

            case "defense":
                switch (noun) {
                    case "stance":
                        if (state.currentPlayer.monsterCount >= 5) {
                            System.out.println(state.currentLocation.endingText);
                            System.exit(0);
                        }
                        if (state.currentLocation.monster[state.currentPlayer.monsterCount] != null) {
                            System.out.println("YOU HAVE ENTERED BATTLE WITH DEFENSE STANCE");
                            System.out.println(state.currentLocation.monster[state.currentPlayer.monsterCount].monName
                                    + " :: "
                                    + state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth);
                            System.out.println("--------vs---------");
                            System.out.println("Health ::" + state.currentPlayer.healthPoints + "       Coins: "
                                    + state.currentPlayer.playerCoins);
                            double damageDealt = state.currentPlayer.attackpoints;
                            damageDealt -= 25;
                            double damageTaken = state.currentLocation.monster[state.currentPlayer.monsterCount].monAttackpoints;
                            damageTaken -= 30;
                            state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth -= damageDealt;
                            state.currentPlayer.healthPoints -= damageTaken;
                            System.out.println(
                                    "You strike lightly with weakened strength and dealt " + damageDealt + " to "
                                            + state.currentLocation.monster[state.currentPlayer.monsterCount].monName);
                            System.out.println("You took an reduced " + damageTaken + " points of damage");
                            if (state.currentPlayer.healthPoints < 1) {
                                System.out.println("You have taken to much damage");
                                System.out.println("YOU HAVE FALLEN");
                                System.exit(0);
                            }
                            if (state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth <= 0) {
                                System.out.println(
                                        "You have beaten the "
                                                + state.currentLocation.monster[state.currentPlayer.monsterCount].monName);

                                int newCoins = (int) (Math.random() * 80 + 20);
                                state.currentPlayer.playerCoins += newCoins;
                                System.out.println("You picked up " + newCoins + " coins");
                                System.out.println("A new monster appeared, it might be good to [look] at it.");

                                break;
                            }
                        } else
                            System.out.println("There are no monsters nearby");

                }
                break;

            case "attack":
                switch (noun) {
                    case "stance":

                        if (state.currentLocation.monster[state.currentPlayer.monsterCount] != null) {
                            System.out.println("YOU HAVE ENTERED BATTLE WITH ATTACK STANCE");
                            System.out.println(state.currentLocation.monster[state.currentPlayer.monsterCount].monName
                                    + " :: "
                                    + state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth);
                            System.out.println("--------vs---------");
                            System.out.println("Health ::" + state.currentPlayer.healthPoints + "       Coins: "
                                    + state.currentPlayer.playerCoins);
                            double damageDealt = state.currentPlayer.attackpoints;
                            damageDealt += 35;
                            double damageTaken = state.currentLocation.monster[state.currentPlayer.monsterCount].monAttackpoints;
                            damageTaken += 30;
                            state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth -= damageDealt;
                            state.currentPlayer.healthPoints -= damageTaken;
                            System.out.println("You strike HARD with increased damage and dealt " + damageDealt + " to "
                                    + state.currentLocation.monster[state.currentPlayer.monsterCount].monName);
                            System.out.println("You took an increased " + damageTaken + " points of damage");
                            if (state.currentPlayer.healthPoints < 1) {
                                System.out.println("You have taken to much damage");
                                System.out.println("YOU HAVE FALLEN");
                                System.exit(0);
                            }
                            if (state.currentLocation.monster[state.currentPlayer.monsterCount].monHealth <= 0) {
                                System.out.println(
                                        "You have beaten the "
                                                + state.currentLocation.monster[state.currentPlayer.monsterCount].monName);

                                int newCoins = (int) (Math.random() * 80 + 20);
                                state.currentPlayer.playerCoins += newCoins;
                                System.out.println("You picked up " + newCoins + " coins");
                                System.out.println("A new monster appered, it might be good to [look] at it");

                                break;
                            }
                        } else
                            System.out.println("There are no monsters nearby");
                }
                break;

            case "buy":
                switch (noun) {

                    case "earthquake":
                        if (state.currentPlayer.playerCoins >= state.earthQuakeRumbleSpell.itemCost) {
                            state.currentPlayer.playerCoins -= state.earthQuakeRumbleSpell.itemCost;
                            state.currentPlayer.addItem(state.earthQuakeRumbleSpell);
                            System.out.println("You brought EarthQuake Rumble Spell");
                        } else
                            System.out.println("Not enough coins");
                        break;

                    case "fireball":
                        if (state.currentPlayer.playerCoins >= state.fireball.itemCost) {
                            state.currentPlayer.playerCoins -= state.fireball.itemCost;
                            state.currentPlayer.addItem(state.fireball);
                            System.out.println("You brought fireball");
                        } else
                            System.out.println("Not enough coins");
                        break;

                    case "waterFall":
                        if (state.currentPlayer.playerCoins >= state.waterFall.itemCost) {
                            state.currentPlayer.playerCoins -= state.waterFall.itemCost;
                            state.currentPlayer.addItem(state.waterFall);
                            System.out.println("You brought waterFall");
                        } else
                            System.out.println("Not enough coins");
                        break;
                    case "sword":
                        if (state.currentPlayer.playerCoins >= state.sword.itemCost) {
                            state.currentPlayer.playerCoins -= state.sword.itemCost;
                            state.currentPlayer.addItem(state.sword);
                            System.out.println("You brought sword");
                        } else
                            System.out.println("Not enough coins");
                        break;
                    case "health":
                        if (state.currentPlayer.playerCoins >= state.health.itemCost) {
                            state.currentPlayer.playerCoins -= state.health.itemCost;
                            state.currentPlayer.addItem(state.health);
                            System.out.println("You brought health");
                        } else
                            System.out.println("Not enough coins");
                        break;

                }
                break;
        }
        System.out.println(formatStringToScreenWidth(resultString));
    }

    /*
     * private void buyItem(String noun) {
     * switch (noun) {
     * case "rocks":
     * //if (state.currentPlayer.playerCoins >= state.rocks.itemCost) {
     * //state.currentPlayer.playerCoins -= state.rocks.itemCost;
     * //state.currentPlayer.addItem(state.rocks);
     * 
     * }
     * }
     * 
     * }
     * 
     * // When a command is a Verb followed by two nouns, this method controls the
     * // result.
     * public void executeVerbNounNoun(String string, String string2, String
     * string3) {
     * 
     * }
     * 
     * /*
     * Prints out the help menu. Goes through all verbs and verbDescriptions
     * printing a list of all commands the user can use.
     */
    public void printHelp() {
        String s1 = "";
        while (s1.length() < DISPLAY_WIDTH)
            s1 += "-";

        String s2 = "";
        while (s2.length() < DISPLAY_WIDTH) {
            if (s2.length() == (DISPLAY_WIDTH / 2 - 10)) {
                s2 += " Commands ";
            } else {
                s2 += " ";
            }
        }

        System.out.println("\n\n" + s1 + "\n" + s2 + "\n" + s1 + "\n");
        for (String v : verbs) {
            // System.out.printp(v + " --> " + verbDescription.get(verbs.indexOf(v)));
            System.out.printf("%-8s  %s", v, formatMenuString(verbDescription.get(verbs.indexOf(v))));
        }
    }

    // Allows the client code to check to see if a verb is in the game.
    public boolean hasVerb(String string) {
        return verbs.contains(string);
    }

    // Allows the client code to check to see if a noun is in the game.
    public boolean hasNoun(String string) {
        return nouns.contains(string);
    }

    // Used to format the help menu
    public String formatMenuString(String longString) {
        String result = "";
        Scanner chop = new Scanner(longString);
        int charLength = 0;

        while (chop.hasNext()) {
            String next = chop.next();
            charLength += next.length();
            result += next + " ";
            if (charLength >= (DISPLAY_WIDTH - 30)) {
                result += "\n          ";
                charLength = 0;
            }
        }
        chop.close();
        return result + "\n\n";
    }

    // formats a string to DISPLAY_WIDTH character width.
    // Used when getting descriptions from items/locations and printing them to the
    // screen.
    // use [nl] for a newline in a string in a description etc.
    public String formatStringToScreenWidth(String longString) {

        Scanner chop = new Scanner(longString);
        String result = "";
        int charLength = 0;
        boolean addSpace = true;

        while (chop.hasNext()) {

            // Get our next word in the string.
            String next = chop.next();

            // Add the legnth to our charLength.
            charLength += next.length() + 1;

            // Find and replace any special newline characters [nl] with \n.
            if (next.contains("[nl]")) {
                // Find the index after our [nl] characters.
                int secondHalf = next.indexOf("[nl]") + 4;

                // Set charLength to the number of characters after the [nl],
                // because that will be the beginnig of a new line.
                if (secondHalf < next.length()) {
                    charLength = secondHalf;
                } else {
                    charLength = 0;
                    addSpace = false; // Do not add space after if this ended with a newline character.
                }

                // Now actually replace the [nl] with the newline character
                next = next.replace("[nl]", "\n");

            }

            // Add the word to the result.
            result += next;

            // Only add a space if our special case did not happen.
            if (addSpace)
                result += " ";

            // Normally we add a space after a word, prepare for that.
            addSpace = true;

            if (charLength >= DISPLAY_WIDTH) {
                result += "\n";
                charLength = 0;
            }
        }
        chop.close();
        return result;
    }

    // Adds a noun to the noun list
    // lets the command system know this is something you an interact with.
    public void addNoun(String string) {
        if (!nouns.contains(string.toLowerCase()))
            nouns.add(string.toLowerCase());
    }

    // Adds a verb to the verb list and the description to the parallel description
    // list
    // Adding a verb lets the command system know you want this to be a command.
    public void addVerb(String verb, String description) {
        verbs.add(verb.toLowerCase());
        verbDescription.add(description.toLowerCase());
    }

}
