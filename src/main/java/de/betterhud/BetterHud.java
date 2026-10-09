package de.betterhud;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class BetterHud implements ClientModInitializer {
 public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
 public static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("betterhud.json");
 public static final Map<String, List<Integer>> bindings = new LinkedHashMap<>();
 private static final Set<Integer> held = new HashSet<>();
 private static final Set<String> active = new HashSet<>();
 public static boolean recording = false;
 @Override public void onInitializeClient() { load(); }
 public static void load() {
  try { if (Files.exists(FILE)) {
   Data data = GSON.fromJson(Files.readString(FILE), Data.class);
   bindings.clear(); if (data != null && data.bindings != null) bindings.putAll(data.bindings);
  }} catch (Exception e) { System.err.println("Better HUD: " + e); }
 }
 public static void save() {
  try { Files.createDirectories(FILE.getParent()); Data data = new Data(); data.bindings = bindings; Files.writeString(FILE,GSON.toJson(data)); }
  catch (Exception e) { System.err.println("Better HUD: " + e); }
 }
 public static class Data { Map<String,List<Integer>> bindings = new LinkedHashMap<>(); }
 public static void key(int code, boolean down) {
  if (code < 0) return;
  if (down) held.add(code); else held.remove(code);
  MinecraftClient client = MinecraftClient.getInstance();
  if (recording || client.player == null || client.currentScreen != null) {
   for (KeyBinding binding : client.options.allKeys) {
    if (active.remove(binding.getTranslationKey())) binding.setPressed(false);
   }
   return;
  }
  for (KeyBinding binding : client.options.allKeys) {
   String id = binding.getTranslationKey();
   List<Integer> combo = bindings.get(id);
   boolean match = combo != null && combo.size() >= 2 && held.containsAll(combo);
   if (match && active.add(id)) { binding.setPressed(true); KeyBinding.onKeyPressed(binding.getBoundKey()); }
   else if (!match && active.remove(id)) { binding.setPressed(false); }
  }
 }
 public static String label(List<Integer> keys) {
  if (keys == null || keys.isEmpty()) return "Nicht belegt";
  List<String> names = new ArrayList<>();
  for (int key : keys) {
   String s = GLFW.glfwGetKeyName(key,0);
   if (key == GLFW.GLFW_KEY_LEFT_CONTROL || key == GLFW.GLFW_KEY_RIGHT_CONTROL) s = "STRG";
   else if (key == GLFW.GLFW_KEY_LEFT_SHIFT || key == GLFW.GLFW_KEY_RIGHT_SHIFT) s = "SHIFT";
   else if (key == GLFW.GLFW_KEY_LEFT_ALT || key == GLFW.GLFW_KEY_RIGHT_ALT) s = "ALT";
   names.add(s == null ? "Taste " + key : s.toUpperCase(Locale.ROOT));
  }
  return String.join(" + ",names);
 }
}
