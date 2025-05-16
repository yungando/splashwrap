package yungando.splashwrap.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import yungando.splashwrap.SplashWrap;
import yungando.splashwrap.config.SplashWrapConfig.SplashWrapAutoConfig;

public class ModMenuEntrypoint implements ModMenuApi {
  private static final ConfigScreenFactory<?> FACTORY = SplashWrap.USE_AUTO_CONFIG
      ? parent -> AutoConfig.getConfigScreen(SplashWrapAutoConfig.class, parent).get()
      : parent -> null;

  @Override
  public ConfigScreenFactory<?> getModConfigScreenFactory() {
    return FACTORY;
  }
}
