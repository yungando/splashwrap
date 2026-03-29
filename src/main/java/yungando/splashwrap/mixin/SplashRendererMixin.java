package yungando.splashwrap.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yungando.splashwrap.SplashWrap;

import java.util.List;

@Mixin(SplashRenderer.class)
public class SplashRendererMixin {
  @Shadow @Final
  private Component splash;

  @Redirect(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;width(Lnet/minecraft/network/chat/FormattedText;)I"))
  private int scaleSplashText(Font instance, FormattedText formattedText) {
    return Math.min(instance.width(this.splash), SplashWrap.config.minimumTextScale());
  }

  @Inject(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ActiveTextCollector;accept(Lnet/minecraft/client/gui/TextAlignment;IILnet/minecraft/client/gui/ActiveTextCollector$Parameters;Lnet/minecraft/network/chat/Component;)V"))
  private void wrapSplashText(GuiGraphicsExtractor guiGraphicsExtractor, int screenWidth, Font font, float alpha, CallbackInfo ci, @Local(name = "renderParameters") ActiveTextCollector.Parameters parameters, @Local(name = "textRenderer") ActiveTextCollector activeTextCollector) {
    FormattedText splashText = this.splash;
    List<FormattedCharSequence> splashTextLines = font.split(splashText, SplashWrap.config.maximumLineWidth());

    int y = -8;

    for (FormattedCharSequence splashLine : splashTextLines) {
      activeTextCollector.accept(TextAlignment.LEFT, -font.width(splashLine) / 2, y, parameters, splashLine);
      y += 9;
    }
  }

  @WrapWithCondition(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ActiveTextCollector;accept(Lnet/minecraft/client/gui/TextAlignment;IILnet/minecraft/client/gui/ActiveTextCollector$Parameters;Lnet/minecraft/network/chat/Component;)V"))
  private boolean cancelVanillaDraw(ActiveTextCollector instance, TextAlignment textAlignment, int i, int j, ActiveTextCollector.Parameters parameters, Component component) {
    return false;
  }
}
