package com.dequeue.rsbot.scripts.herblore;

import org.powerbot.script.Manifest;
import org.powerbot.script.PollingScript;

/**
 * Created with IntelliJ IDEA.
 * User: Mayfield
 * Date: 7/28/13
 * Time: 3:59 AM
 */

@Manifest(authors = {"Psi"}, name = "ΨHerblore", description = "AIO Herblore Script")
public class PsiHerblore extends PollingScript {

    public enum HERB {
        GUAM("Guam", 199, 249, 91),
        TARROMIN("Tarromin", 203, 253, 95),
        MARRENTILL("Marrentill", 201, 251, 93),
        HARRALANDER("Harralander", 205, 255, 97),
        RANARR("Ranarr", 207, 257, 99),
        TOADFLAX("Toadflax", 3049, 2998, 3002),
        SPIRIT_WEED("Spirit Weed", 12174, 12172, 12181),
        IRIT("Irit", 209, 259, 101),
        WERGALI("Wergali", 14836, 14854, 14856),
        AVANTOE("Avantoe", 211, 261, 103),
        KWUARM("Kwuarm", 213, 263, 105),
        SNAPDRAGON("Snapdragon", 3051, 3000, 3004),
        CADANTINE("Cadantine", 215, 265, 107),
        LANTADYME("Lantadyme", 2485, 2481, 2483),
        DWARF_WEED("Dwarf Weed", 217, 267, 109),
        TORSTOL("Torstol", 219, 269, 111),
        FELLSTALK("Fellstalk", 21626, 21624, 21628);

        private final String name;
        private final int grimyID, cleanID, unfID;

        private HERB(String name, int grimyID, int cleanID, int unfID) {
            this.name = name;
            this.grimyID = grimyID;
            this.cleanID = cleanID;
            this.unfID = unfID;
        }
    }

    public enum POTION {
        ATTACK("Attack potion", 121, new int[]{91, 221}, true),
        RANGING("Ranging potion", 14251, new int[]{91, 1951}, true),
        //MAGIC("Magic potion", 14271, new int[]{95, 1476 or 1474 or 1472 or 1470}),
        DEFENCE("Defence potion", 133, new int[]{93, 948}, true),
        STRENGTH("Strength potion", 115, new int[]{95, 225}, true),
        ANTIPOISON("Antipoison", 175, new int[]{93, 235}, true),
        SERUM_207("Serum 207", 3410, new int[]{95, 592}, true),
        GUTHIX_REST("Guthix rest", 4419, new int[]{97, 251}, true),
        //GUAM_TAR("Guam tar", 10142, new int[]{}),
        RESTORE("Restore potion", 127, new int[]{97, 223}, true),
        GUTHIX_BALANCE("Guthix balance", 7662, new int[]{127, 1550, 7650}, false),
        ENERGY("Energy potion", 3010, new int[]{97, 1975}, true),
        //MARRENTILL_TAR("Marrentill tar", 10143, new int[]{}),
        AGILITY("Agility potion", 3034, new int[]{3002, 2152}, true),
        COMBAT("Combat potion", 9741, new int[]{97, 9736}, true),
        PRAYER("Prayer potion", 139, new int[]{99, 231}, true),
        //TARROMIN_TAR("Tarromin tar", 10144, new int[]{}),
        SUMMONING("Summoning potion", 12142, new int[]{12181, 12109}, true),
        CRAFTING("Crafting potion", 14840, new int[]{14856, 5004}, true),
        //HARRALANDER_TAR("Harralander tar", 10145, new int[]{}),
        SUPER_ATTACK("Super attack", 145, new int[]{101, 221}, true),
        SUPER_ANTIPOISON("Super antipoison", 181, new int[]{221, 235}, true),
        FISHING("Fishing potion", 151, new int[]{103, 231}, true),
        SUPER_ENERGY("Super energy", 3018, new int[]{103, 2970}, true),
        HUNTER("Hunter potion", 10000, new int[]{103, 10111}, true),
        SUPER_STRENGTH("Super strength", 157, new int[]{105, 225}, true),
        FLETCHING("Fletching potion", 14849, new int[]{14856, 11525}, true),
        WEAPON_POISON("Weapon poison", 187, new int[]{105, 241}, true),
        SUPER_RESTORE("Super restore", 3026, new int[]{3004, 223}, true),
        //SANFEW_SERUM("Sanfew serum", 10927, new int[]{}),
        SUPER_DEFENCE("Super defence", 163, new int[]{107, 239}, true),
        ANTIPOISON_PLUS("Antipoison+", 5945, new int[]{3002, 6049}, true),
        ANTIFIRE("Antifire", 2454, new int[]{2483, 241}, true),
        SUPER_RANGING("Super ranging potion", 169, new int[]{109, 245}, true),
        WEAPON_POISON_PLUS("Weapon poison+", 5937, new int[]{5935, 6016, 223}, false),
        SUPER_MAGIC("Super magic potion", 3042, new int[]{2483, 3138}, true),
        ZAMORAK_BREW("Zamorak brew", 189, new int[]{111, 247}, true),
        ANTIPOISION_PLUS_PLUS("Antipoision++", 5954, new int[]{5935, 101, 6051}, false),
        SARADOMIN_BREW("Saradomin brew", 6687, new int[]{3002, 6693}, true),
        WEAPON_POISON_PLUS_PLUS("Weapon poison++", 5941, new int[]{5935, 2398, 6018}, false),
        ADRENALINE("Adrenaline potion", 15301, new int[]{3018, 5972}, false),
        SUPER_ANTIFIRE("Super antifire", 15305, new int[]{2454, 4621}, false),
        EXTREME_ATTACK("Extreme attack", 15309, new int[]{145, 261}, false),
        EXTREME_STRENGTH("Extreme strength", 15313, new int[]{157, 267}, false),
        EXTREME_DEFENCE("Extreme defence", 15317, new int[]{163, 2481}, false),
        EXTREME_MAGIC("Extreme magic", 15321, new int[]{3042, 9594}, false),
        EXTREME_RANGING("Extreme ranging", 15325, new int[]{169, 12539}, false),
        SUPER_SARADOMIN_BREW("Super Saradomin brew", 28193, new int[]{6687, 28256}, false),
        SUPER_ZAMORAK_BREW("Super Zamorak brew", 28201, new int[]{189, 245}, false),
        SUPER_GUTHIX_REST("Super Guthix rest", 28209, new int[]{4419, 28253}, false),
        //SUPER_PRAYER("Super prayer", 15329, new int[]{139, }, false),
        PRAYER_RENEWAL("Prayer renewal", 21632, new int[]{21628, 21622}, true),
        OVERLOAD("Overload", 15333, new int[]{15309, 15313, 15317, 15321, 15325, 269}, false);

        private final String name;
        private final int potID;
        private final int[] ingredients;
        private final boolean water;

        private POTION(String name, int potID, int[] ingredients, boolean water) {
            this.name = name;
            this.potID = potID;
            this.ingredients = ingredients;
            this.water = water;

        }

    }

    private HERB herb;
    private POTION potion;

    public PsiHerblore() {
        getExecQueue(State.START).offer(new Runnable() {
            @Override
            public void run() {
                System.out.println("Script initialized");
            }
        });
    }

    @Override
    public int poll() {
        return 0;
    }
}
