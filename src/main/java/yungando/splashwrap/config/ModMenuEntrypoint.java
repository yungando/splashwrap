package yungando.splashwrap.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfigClient;
import yungando.splashwrap.config.SplashWrapConfig.SplashWrapAutoConfig;

public class ModMenuEntrypoint implements ModMenuApi {
  @Override
  public ConfigScreenFactory<?> getModConfigScreenFactory() {
    return parent -> AutoConfigClient.getConfigScreen(SplashWrapAutoConfig.class, parent).get();
  }
}
