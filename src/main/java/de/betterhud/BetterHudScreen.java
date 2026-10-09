package de.betterhud;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import java.util.*;

public class BetterHudScreen extends Screen {
 private final Screen parent;
 private int page = 0;
 private KeyBinding capture;
 private final LinkedHashSet<Integer> captured = new LinkedHashSet<>();
 private List<KeyBinding> keys;
 public BetterHudScreen(Screen parent) { super(Text.literal("Better HUD - Multi-Keybindings")); this.parent=parent; }
 @Override protected void init() {
  keys = new ArrayList<>(Arrays.asList(client.options.allKeys));
  int perPage= Math.max(1,(height-105)/24); page=Math.min(page,Math.max(0,(keys.size()-1)/perPage));
  for (int i=page*perPage; i<Math.min(keys.size(),(page+1)*perPage); i++) {
   KeyBinding k=keys.get(i); int y=38+(i%perPage)*24;
   addDrawableChild(ButtonWidget.builder(Text.literal(shortName(k)), b -> { capture=k; captured.clear(); BetterHud.recording=true; clearAndInit(); })
    .dimensions(width/2-155,y,180,20).build());
   addDrawableChild(ButtonWidget.builder(Text.literal(BetterHud.label(BetterHud.bindings.get(k.getTranslationKey()))), b -> {
    capture=k; captured.clear(); BetterHud.recording=true; clearAndInit();
   }).dimensions(width/2+30,y,125,20).build());
  }
  addDrawableChild(ButtonWidget.builder(Text.literal("<"), b -> { page=Math.max(0,page-1);clearAndInit(); }).dimensions(width/2-155,height-35,45,20).build());
  addDrawableChild(ButtonWidget.builder(Text.literal("Clear"), b -> { if(capture!=null) { BetterHud.bindings.remove(capture.getTranslationKey()); BetterHud.save(); capture=null; BetterHud.recording=false; clearAndInit(); }}).dimensions(width/2-100,height-35,65,20).build());
  addDrawableChild(ButtonWidget.builder(Text.literal("Done"), b -> close()).dimensions(width/2-30,height-35,75,20).build());
  addDrawableChild(ButtonWidget.builder(Text.literal(">"), b -> { page=Math.min((keys.size()-1)/perPage,page+1);clearAndInit(); }).dimensions(width/2+55,height-35,45,20).build());
 }
 private String shortName(KeyBinding k) { String s=Text.translatable(k.getTranslationKey()).getString(); return s.length()>27?s.substring(0,27):s; }
 @Override public boolean keyPressed(int keyCode,int scanCode,int modifiers) {
  if(capture!=null) {
   if(keyCode==GLFW.GLFW_KEY_ESCAPE) { capture=null; BetterHud.recording=false;clearAndInit(); return true; }
   if(keyCode==GLFW.GLFW_KEY_ENTER) {
    if(captured.size()>=2) { BetterHud.bindings.put(capture.getTranslationKey(),new ArrayList<>(captured)); BetterHud.save(); }
    capture=null; BetterHud.recording=false;clearAndInit(); return true;
   }
   captured.add(keyCode); return true;
  }
  return super.keyPressed(keyCode,scanCode,modifiers);
 }
 @Override public void render(DrawContext context,int mouseX,int mouseY,float delta) {
  super.render(context,mouseX,mouseY,delta);
  context.drawCenteredTextWithShadow(textRenderer,title,width/2,12,0xFFFFFF);
  if(capture!=null) context.drawCenteredTextWithShadow(textRenderer,"Recording: "+BetterHud.label(new ArrayList<>(captured))+" | Enter=Save, Esc=Cancel",width/2,height-57,0xFFFF55);
 }
 @Override public void close() { capture=null; BetterHud.recording=false; BetterHud.save(); client.setScreen(parent); }
}
