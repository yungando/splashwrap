package yungando.splashwrap.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yungando.splashwrap.SplashWrap;

import java.util.List;

@Mixin(SplashTextRenderer.class)
public class SplashTextRendererMixin {
  @Shadow @Final
  private String text;

  @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;getWidth(Ljava/lang/String;)I"))
  private int scaleSplashText(TextRenderer textRenderer, String text) {
    return Math.min(textRenderer.getWidth(this.text), SplashWrap.config.minimumTextScale());
  }

  @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawCenteredTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"))
  private void wrapSplashText(DrawContext context, int screenWidth, TextRenderer textRenderer, int alpha, CallbackInfo ci) {
    StringVisitable splashText = StringVisitable.plain(this.text);
    List<OrderedText> splashTextLines = textRenderer.wrapLines(splashText, SplashWrap.config.maximumLineWidth());

    int y = -8;

    for (OrderedText splashLine : splashTextLines) {
      context.drawCenteredTextWithShadow(textRenderer, splashLine, 0, y, 0xffff00 | alpha);
      y += 9;
    }
  }

  @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawCenteredTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"))
  private boolean cancelVanillaDraw(DrawContext instance, TextRenderer textRenderer, String text, int centerX, int y, int color) {
    return false;
  }
}
