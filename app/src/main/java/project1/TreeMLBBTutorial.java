package project1;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class TreeMLBBTutorial {
    static class itemNode {
        String name;
        String node;
        List<itemNode> children;

        // node constructor
        itemNode(String name, String node) {
            this.name = name;
            this.node = node;
            this.children = new ArrayList<>();
        }

        // add children
        public void addChild(itemNode child) {
            children.add(child);
        }
    }

    // build tree
    public static itemNode buildTree() {
        itemNode root = new itemNode("Item Shop", "Root");

        return root;
    }

    // create tree
    static void printTree(itemNode node, int level) {
        if (node == null) {
            return;
        }

        String indent = "".repeat(level);
        System.out.println(indent + "-" + node.name + "->" + node.node);
        for (itemNode child : node.children) {
            printTree(child, level + 1);
        }
    }

    // print build paths
    static void printAllBuildPaths(itemNode node, List<String> path) {
        if (node == null) {
            return;
        }
        path.add(node.name);

        if (node.children.isEmpty()) {
            System.out.println(String.join("->", path));
        } else {
            for (itemNode child : node.children) {
                printAllBuildPaths(child, path);
            }
        }
        path.remove(path.size() - 1);
    }

    // basic operations
    static int countNodes(itemNode node) {
        if (node == null) {
            return 0;
        }
        int count = 1;
        for (itemNode child : node.children) {
            count += countNodes(child);
        }
        return count;
    }

    static int countLeaf(itemNode node) {
        if (node == null) {
            return 0;
        }
        if (node.children.isEmpty()) {
            return 1;
        }
        int count = 0;
        for (itemNode child : node.children) {
            count += countLeaf(child);
        }
        return count;
    }

    static int height(itemNode node) {
        if (node == null) {
            return 0;
        }
        if (node.children.isEmpty()) {
            return 1;
        }
        int maxChildHeight = 0;
        for (itemNode child : node.children) {
            maxChildHeight = Math.max(maxChildHeight, height(child));
        }
        return maxChildHeight + 1;
    }

    static boolean findPath(itemNode node, String target, List<String> path) {
        if (node == null) {
            return false;
        }
        path.add(node.name);
        if (node.name.equals(target)) {
            return true;
        }
        for (itemNode child : node.children) {
            if (findPath(child, target, path)) {
                return true;
            }
        }
        path.remove(path.size() - 1);
        return false;
    }

    static int countItemOccurences(itemNode node, String target) {
        if (node == null)
            return 0;

        // Cek apakah node ini adalah target
        int count = node.name.equals(target) ? 1 : 0;

        // Tambahkan hasil hitungan dari semua anak-anaknya
        for (itemNode child : node.children) {
            count += countItemOccurences(child, target);
        }
        return count;
    }

    static void printPathsEndingWith(itemNode node, String target, List<String> path) {
        if (node == null) {
            return;
        }
        path.add(node.name);
        if (node.name.equals(target)) {
            System.out.println(String.join("->", path));
        }
        for (itemNode child : node.children) {
            printPathsEndingWith(child, target, path);
        }

        // backtracking
        path.remove(path.size() - 1);
    }

    // method ini dibantu menggunakan chatgpt karena lumayan makan waktu untuk
    // mengimplementasikan semua item defense di MLBB, tapi saya paham kok logika di
    // baliknya
    public static void main(String[] args) {
        itemNode root = new itemNode("Defense Shop", "Pilih komponen dasar");

        // ==========================================
        // 1. TIER 1 (KOMPONEN DASAR / MENTAHAN UTAMA)
        // ==========================================
        itemNode vitalityCrystal = new itemNode("Vitality Crystal", "Tier 1: Base HP");
        itemNode leatherJerkin = new itemNode("Leather Jerkin", "Tier 1: Base Physical Def");
        itemNode magicResistCloak = new itemNode("Magic Resist Cloak", "Tier 1: Base Magic Def");
        itemNode heroRing = new itemNode("Hero's Ring", "Tier 1: Base Utility");

        // (Task 1: Menambah cabang utama yang berbeda)
        root.addChild(vitalityCrystal);
        root.addChild(leatherJerkin);
        root.addChild(magicResistCloak);
        root.addChild(heroRing);

        // ==========================================
        // 2. TIER 2 (KOMPONEN MENENGAH)
        // ==========================================
        // Jalur Vitality Crystal
        itemNode aresBelt = new itemNode("Ares Belt", "Tier 2");
        itemNode moltenEssence = new itemNode("Molten Essence", "Tier 2");
        itemNode silenceRobe = new itemNode("Silence Robe", "Tier 2");
        vitalityCrystal.addChild(aresBelt);
        vitalityCrystal.addChild(moltenEssence);
        vitalityCrystal.addChild(silenceRobe);

        // Jalur Leather Jerkin
        itemNode dreadnaughtArmor = new itemNode("Dreadnaught Armor", "Tier 2");
        itemNode steelLegplates = new itemNode("Steel Legplates", "Tier 2");
        itemNode aresBelt2 = new itemNode("Ares Belt", "Tier 2 (Jalur Jerkin)"); // Duplikasi untuk validasi
        leatherJerkin.addChild(dreadnaughtArmor);
        leatherJerkin.addChild(steelLegplates);
        leatherJerkin.addChild(aresBelt2);

        // Jalur Hero's Ring
        itemNode expertGloves = new itemNode("Expert Gloves", "Tier 2");
        heroRing.addChild(expertGloves);

        // Jalur Tambahan Komponen Spesifik
        itemNode blackIceShield = new itemNode("Black Ice Shield", "Tier 2");
        leatherJerkin.addChild(blackIceShield);

        // ==========================================
        // 3. TIER 3 (FINAL ITEMS) & PENYUSUNAN
        // ==========================================
        // Dari Ares Belt (Jalur Vitality Crystal)
        aresBelt.addChild(new itemNode("Chastise Pauldron", "Tier 3"));
        aresBelt.addChild(new itemNode("Antique Cuirass", "Tier 3"));
        aresBelt.addChild(new itemNode("Brute Force Breastplate", "Tier 3"));
        aresBelt.addChild(new itemNode("Immortality", "Tier 3")); // Immortality ke-1
        aresBelt.addChild(new itemNode("Thunder Belt", "Tier 3"));
        aresBelt.addChild(new itemNode("Guardian Helmet", "Tier 3"));
        aresBelt.addChild(new itemNode("Queen's Wings", "Tier 3"));

        // Dari Ares Belt 2 (Jalur Leather Jerkin)
        aresBelt2.addChild(new itemNode("Immortality", "Tier 3")); // Immortality ke-2

        // Dari Molten Essence
        moltenEssence.addChild(new itemNode("Cursed Helmet", "Tier 3"));

        // Dari Silence Robe
        silenceRobe.addChild(new itemNode("Athena's Shield", "Tier 3"));
        silenceRobe.addChild(new itemNode("Radiant Armor", "Tier 3"));

        // Dari Dreadnaught Armor
        dreadnaughtArmor.addChild(new itemNode("Antique Cuirass", "Tier 3")); // Antique Cuirass ke-2

        // Dari Steel Legplates
        steelLegplates.addChild(new itemNode("Chastise Pauldron", "Tier 3"));
        itemNode bladeArmor = new itemNode("Blade Armor", "Tier 3");
        steelLegplates.addChild(bladeArmor);

        // Dari Black Ice Shield
        blackIceShield.addChild(new itemNode("Dominance Ice", "Tier 3"));

        // Dari Hero's Ring / Expert Gloves
        expertGloves.addChild(new itemNode("Fleeting Time", "Tier 3"));
        heroRing.addChild(new itemNode("Oracle", "Tier 3"));

        // ==========================================
        // 4. TIER 4 (TASK 5: ADD ONE MORE LEVEL)
        // ==========================================
        // Menambahkan satu level lagi (Level Awakening / Upgrade akhir)
        itemNode awakenedBladeArmor = new itemNode("Awakened Blade Armor", "Tier 4: Refleksi 200%");
        bladeArmor.addChild(awakenedBladeArmor);

        // ==========================================
        // EKSEKUSI OUTPUT TUGAS (TAKE HOME TASKS)
        // ==========================================
        System.out.println("=== COMPREHENSIVE MLBB DEFENSE TREE ===");
        printTree(root, 0);

        System.out.println("\n=== TREE STATISTICS ===");
        System.out.println("Total Nodes: " + countNodes(root));
        System.out.println("Leaf Nodes: " + countLeaf(root));
        System.out.println("Tree Height: " + height(root)); // Output: 5 (Karena ada Tier 4)

        // --- TASK 2: COUNT OCCURRENCES ---
        String targetItem = "Immortality";
        System.out.println("\n=== TASK 2: OCCURRENCE COUNT ===");
        System.out.println("Item '" + targetItem + "' muncul sebanyak: " + countItemOccurences(root, targetItem)
                + " kali (Karena komponen utamanya ada 2 jalur)");

        // --- TASK 3: PATHS ENDING WITH TARGET ---
        System.out.println("\n=== TASK 3: ALL CRAFTING PATHS TO '" + targetItem.toUpperCase() + "' ===");
        List<String> task3Path = new ArrayList<>();
        printPathsEndingWith(root, targetItem, task3Path);

        // --- TASK 4: SCANNER (DYNAMIC SEARCH) ---
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n=== TASK 4: CARI ITEM (e.g., Antique Cuirass) ===\nInput nama item: ");
        String userInput = scanner.nextLine();

        List<String> userPath = new ArrayList<>();
        if (findPath(root, userInput, userPath)) {
            System.out.println("Jalur Tercepat: " + String.join(" -> ", userPath));
        } else {
            System.out.println("Error: Item tidak ditemukan dalam Tree.");
        }

        scanner.close();
    }
}