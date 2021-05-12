package com.fourforfour.eldanialight;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fourforfour.eldanialight.characters.Enemy;
import com.fourforfour.eldanialight.characters.Player;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

class DataParser {
    // STATIC FIELDS
    private static final String CLASS_NODE = "Classes";
    private static final String NPC_NODE = "NPCs";
    private static final String ENEMIES_NODE = "Enemies";
    private static final String ATTRIBUTES_NODE = "Attributes";
    private static final String STATS_NODE = "stats";
    private static final String TYPE_NODE = "type";
    private static final String WEAR_ITEM_NODE = "Wear Item";
    private static final String WEAPONS_NODE = "weapons";
    private static final String ARMOR_NODE = "armor";
    private static final String CONSUMABLES_NODE = "Consumables";
    private static final String UTILITY_NODE = "Utility";
    private static final String REWARDS_NODE = "Rewards";
    private static final String SHOP_INVENTORY_NODE = "Shop Inventory";
    private static final String ARMORY_LIST_NODE = "armoryList";
    private static final String MAGIC_LIST_NODE = "magicList";
    private static final String LOCATIONS_NODE = "Locations";
    private static final String DESCRIPTION_NODE = "description";
    private static final String NEIGHBOR_NODE = "neighbors";
    private static final String COMMANDS_NODE = "commands";
    private static final String SHOP_NPC_NODE = "npc";

    // FIELDS
    private ObjectMapper mapper;
    private JsonNode gameData;

    // CONSTRUCTOR
    DataParser() {
        try {
            // grabs the game dataParser file
            InputStream jsonFile = getClass().getResourceAsStream("/data.json");
            // create a mapper to read through the game data
            mapper = new ObjectMapper();
            // create the JSON starting point
            gameData = mapper.readTree(jsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // HELPER METHODS
    private List<String> getArrayAsList(JsonNode node, String childNode) {
        List<String> result = new ArrayList<>();
        node.path(childNode).forEach(val -> result.add(val.asText()));
        return result;
    }

    private List<String> getKeysAsList(JsonNode node, String childNode) {
        List<String> result = new ArrayList<>();
        node.path(childNode).fieldNames().forEachRemaining(result::add);
        return result;
    }

    // Currently only being used to grab all the NPCs from "Quest" and "Shop"
    private List<String> getNestedKeysAsList(JsonNode node, String childNode) {
        List<String> result = new ArrayList<>();
        node.path(childNode).forEach(value -> value.fieldNames().forEachRemaining(result::add));
        return result;
    }

    // METHODS
    /**
     * PLAYER CLASSES
     *
     *  getPlayerClasses()  -> returns a List of available classes
     *  isPlayerClass()     -> checks to see if a class is available in the gameData
     */
    public List<String> getPlayerClasses() {
        return getKeysAsList(gameData, CLASS_NODE);
    }

    public boolean isPlayerClass(String playerClass) {
        return getPlayerClasses().contains(playerClass);
    }

    /**
     * NPCS
     *
     *  getNPCs()   -> returns a List of available NPCs
     *  isNPC()     -> checks to see if an NPC is available in the gameData
     */
    public List<String> getNPCs() {
        return getNestedKeysAsList(gameData, NPC_NODE);
    }

    public boolean isNPC(String npc) {
        return getNPCs().contains(npc);
    }

    /**
     * ENEMIES
     *
     *  getEnemies()    -> returns a List of available NPCs
     *  isEnemy()       -> checks to see if an NPC is available in the gameData
     *  getEnemy()      -> grabs the JsonNode of the specified enemy
     */
    public List<String> getEnemies() {
        return getKeysAsList(gameData, ENEMIES_NODE);
    }

    public boolean isEnemy(String enemy) {
        return getEnemies().contains(enemy);
    }

    public JsonNode getEnemy(String enemy) {
        if (isEnemy(enemy)) {
            return gameData.path(ENEMIES_NODE).path(enemy);
        } else {
            throw new IllegalArgumentException("Please input a valid enemy");
        }
    }

    /**
     * ITEMS
     *
     *  getItemStats()  -> returns a List of available Item Stats
     *  isItemStat()    -> checks to see if an Item Stat is available in the gameData
     *  getItemTypes()  -> returns a List of available Item Types
     *  isItemType()    -> checks to see if an Item Type is available in the gameData
     */
    public List<String> getItemStats() {
        return getArrayAsList(gameData.path(ATTRIBUTES_NODE), STATS_NODE);
    }

    public boolean isItemStat(String itemStat) {
        return getItemStats().contains(itemStat);
    }

    public List<String> getItemTypes() {
        return getArrayAsList(gameData.path(ATTRIBUTES_NODE), TYPE_NODE);
    }

    public boolean isItemType(String itemTypes) {
        return getItemTypes().contains(itemTypes);
    }

    /**
     * WEAPONS
     *
     *  getWeapons()    -> returns a List of available Weapons
     *  isWeapon()      -> checks to see if a Weapon is available in the gameData
     *  getWeapon()     -> grabs the JsonNode of the specified Weapon
     */
    public List<String> getWeapons() {
        return getKeysAsList(gameData.path(WEAR_ITEM_NODE), WEAPONS_NODE);
    }

    public boolean isWeapon(String weapon) {
        return getWeapons().contains(weapon);
    }

    public JsonNode getWeapon(String weapon) {
        if (isWeapon(weapon)) {
            return gameData.path(WEAR_ITEM_NODE).path(WEAPONS_NODE).path(weapon);
        } else {
            throw new IllegalArgumentException("Please input a valid weapon");
        }
    }

    /**
     * ARMOR
     *
     *  getArmor()  -> returns a List of available Armor
     *  isArmor()   -> checks to see if a Armor is available in the gameData
     *  getArmor()  -> grabs the JsonNode of the specified Armor
     */
    public List<String> getArmor() {
        return getKeysAsList(gameData.path(WEAR_ITEM_NODE), ARMOR_NODE);
    }

    public boolean isArmor(String armor) {
        return getArmor().contains(armor);
    }

    public JsonNode getArmor(String armor) {
        if (isArmor(armor)) {
            return gameData.path(WEAR_ITEM_NODE).path(ARMOR_NODE).path(armor);
        } else {
            throw new IllegalArgumentException("Please input a valid armor");
        }
    }

    /**
     * CONSUMABLES
     *
     *  getConsumables()    -> returns a List of available Consumables
     *  isConsumable()      -> checks to see if a Armor is available in the gameData
     *  getConsumable()     -> grabs the JsonNode of the specified Armor
     */
    public List<String> getConsumables() {
        return getKeysAsList(gameData, CONSUMABLES_NODE);
    }

    public boolean isConsumable(String consumable) {
        return getConsumables().contains(consumable);
    }

    public JsonNode getConsumable(String consumable) {
        if (isConsumable(consumable)) {
            return gameData.path(CONSUMABLES_NODE).path(consumable);
        } else {
            throw new IllegalArgumentException("Please input a valid enemy");
        }
    }

    /**
     * UTILITY ITEMS
     *
     *  getUtilityItems()    -> returns a List of available Utility Items
     *  isUtilityItem()      -> checks to see if a Utility Item is available in the gameData
     */
    public List<String> getUtilityItems() {
        return getArrayAsList(gameData, UTILITY_NODE);
    }

    public boolean isUtilityItem(String utilityItem) {
        return getUtilityItems().contains(utilityItem);
    }

    /**
     * REWARD ITEMS
     *
     *  getRewardItems()    -> returns a List of available Reward Item
     *  isRewardItem()      -> checks to see if a Reward Item is available in the gameData
     */
    public List<String> getRewardItems() {
        return getArrayAsList(gameData, REWARDS_NODE);
    }

    public boolean isRewardItem(String rewardItem) {
        return getRewardItems().contains(rewardItem);
    }

    /**
     * LOCATIONS
     *
     *  getLocations()              -> returns a List of available Locations
     *  isLocation()                -> checks to see if a Location is available in the gameData
     *  getLocationType()           -> returns the Location's Type
     *  getLocationDescription()    -> returns the Location's Description
     *  getLocationNeighbors()      -> returns the Location's Neighbors
     *  getLocationCommands()       -> returns the Location's Commands
     *  getLocationNPC()            -> returns the Location's NPCs
     */
    public List<String> getLocations() {
        return getKeysAsList(gameData, LOCATIONS_NODE);
    }

    public boolean isLocation(String location) {
        return getLocations().contains(location);
    }

    public String getLocationType(String location) {
        if (isLocation(location)) {
            return gameData.path(LOCATIONS_NODE).path(location).path(TYPE_NODE).asText();
        } else {
            throw new IllegalArgumentException("Please input a valid location");
        }
    }

    public String getLocationDescription(String location) {
        if (isLocation(location)) {
            return gameData.path(LOCATIONS_NODE).path(location).path(DESCRIPTION_NODE).asText();
        } else {
            throw new IllegalArgumentException("Please input a valid location");
        }
    }

    public List<String> getLocationNeighbors(String location) {
        if (isLocation(location)) {
            return getArrayAsList(gameData.path(LOCATIONS_NODE).path(location), NEIGHBOR_NODE);
        } else {
            throw new IllegalArgumentException("Please input a valid location");
        }
    }

    public List<String> getLocationCommands(String location) {
        if (isLocation(location)) {
            return getArrayAsList(gameData.path(LOCATIONS_NODE).path(location), COMMANDS_NODE);
        } else {
            throw new IllegalArgumentException("Please input a valid location");
        }
    }

    public JsonNode getLocationNPC(String location) {
        if (isLocation(location)) {
            return gameData.path(LOCATIONS_NODE).path(location).path(SHOP_NPC_NODE);
        } else {
            throw new IllegalArgumentException("Please input a valid location");
        }
    }

    /**
     * ARMORY LIST
     *
     *  getArmoryList()    -> returns a List of the Armor Shop's available items
     */
    public List<String> getArmoryList() {
        return getArrayAsList(gameData.path(SHOP_INVENTORY_NODE), ARMORY_LIST_NODE);
    }

    /**
     * MAGIC LIST
     *
     *  getMagicList()    -> returns a List of the Magic Shop's available items
     */
    public List<String> getMagicList() {
        return getArrayAsList(gameData.path(SHOP_INVENTORY_NODE), MAGIC_LIST_NODE);
    }

    /**
     * INSTANCE CREATORS
     *
     *  createPlayerClass() -> returns an instance of a Player Class
     *  createEnemy()       -> returns an instance of an Enemy Class
     */
    public Player createPlayerClass(String classChoice) {
        // allows you to create an instance of a character straight from your JSON game data
        if (!isPlayerClass(classChoice)) {
            throw new IllegalArgumentException("Requested character class does not exist in your game data");
        }

        Player result = null;
        try {
            JsonNode characterInformation = gameData.path(CLASS_NODE).path(classChoice);
            // allows you to pass in a JsonNode and it returns a Java Object of your choosing (as long as it has the proper fields)
            result = mapper.treeToValue(characterInformation, Player.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Enemy createEnemy(String enemy) {
        // allows you to create an instance of an enemy straight from your JSON game data
        if (!isEnemy(enemy)) {
            throw new IllegalArgumentException("Requested enemy does not exist in your game data");
        }

        Enemy result = null;
        try {
            JsonNode enemyInformation = gameData.path(ENEMIES_NODE).path(enemy);
            // allows you to pass in a JsonNode and it returns a Java Object of your choosing (as long as it has the proper fields)
            result = mapper.treeToValue(enemyInformation, Enemy.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}