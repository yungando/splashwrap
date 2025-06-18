package yungando.splashwrap;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import yungando.splashwrap.config.SplashWrapConfig;
import yungando.splashwrap.config.SplashWrapConfig.SplashWrapAutoConfig;

@Environment(EnvType.CLIENT)
public class SplashWrap implements ClientModInitializer {
  public static final String MODID = "splashwrap";
  public static final boolean USE_AUTO_CONFIG = FabricLoader.getInstance().isModLoaded("cloth-config2");
  public static SplashWrapConfig config;

  @Override
  public void onInitializeClient() {
    if (USE_AUTO_CONFIG) {
      AutoConfig.register(SplashWrapAutoConfig.class, GsonConfigSerializer::new);
      config = AutoConfig.getConfigHolder(SplashWrapAutoConfig.class).getConfig();
    } else {
      config = new SplashWrapAutoConfig();
    }
  }
}
