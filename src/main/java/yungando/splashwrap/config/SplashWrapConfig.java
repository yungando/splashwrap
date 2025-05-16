package yungando.splashwrap.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;
import yungando.splashwrap.SplashWrap;

public class SplashWrapConfig {
  public int maximumLineWidth() {
    return 200;
  }

  public int minimumTextScale() {
    return 150;
  }

  @SuppressWarnings("FieldMayBeFinal")
  @Config(name = SplashWrap.MODID)
  public static class SplashWrapAutoConfig extends SplashWrapConfig implements ConfigData {
    @Tooltip
    private int maximumLineWidth = super.maximumLineWidth();
    @Tooltip
    private int minimumTextScale = super.minimumTextScale();

    @Override
    public int maximumLineWidth() {
      return maximumLineWidth;
    }

    @Override
    public int minimumTextScale() {
      return minimumTextScale;
    }
  }
}
