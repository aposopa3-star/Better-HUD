package de.betterhud;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
public class BetterHudMenu implements ModMenuApi {
 @Override public ConfigScreenFactory<?> getModConfigScreenFactory() { return BetterHudScreen::new; }
}
