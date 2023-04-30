package me.plugin.teleportcommands.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;

public class HomeStorage {
    private static ArrayList<Home> array = new ArrayList<>();
    public static Home createHome(Player p, Location l, String name) {
        Home home = new Home (p, l, name);
        array.add(home);
        /*try {
            saveNotes();
        }catch (IOException e){
            e.printStackTrace();
        }*/
        return home;
    }

    public static ArrayList<Home> getHomes() {
        return array;
    }

    public static void deleteHome(String id) {
        for (Home home: array) {
            if (home.getId().equals(id)) {
                array.remove(home);
            }
        }
        /*try {
            saveNotes();
        }catch (IOException e){
            e.printStackTrace();
        }*/

    }
    public static void deleteHome(Player p, String name) {
        array.removeIf(home -> home.getPlayer().equals(p) && home.getName().equals(name));
        /*try {
            saveNotes();
        }catch (IOException e){
            e.printStackTrace();
        }*/

    }

    public static Home findHome(String id) {
        for (Home home: array) {
            if (home.getId().equals(id)) {
                return home;
            }
        }
        return null;
    }

    public static Home findHome(Player p, String name) {
        for(Home home: array) {
            if (home.getPlayer().equals(p) && home.getName().equals(name)) {
                return home;
            }
        }
        return null;
    }
    public static Home updateHome(String id, Home newHome) {
        for (Home home: array) {
            if (home.getId().equals(id)) {
                home.setName(newHome.getName());
                home.setPlayer(newHome.getPlayer());
                home.setLocation(newHome.getLocation());
                /*try {
                    saveNotes();
                }catch (IOException e){
                    e.printStackTrace();
                }*/
                return home;
            }
        }
        return null;
    }

    private static void saveNotes() throws IOException {

    }

    public static void loadHomes() {

    }
}
