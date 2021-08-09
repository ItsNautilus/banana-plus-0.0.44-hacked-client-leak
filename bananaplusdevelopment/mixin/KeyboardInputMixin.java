/*    */ package bananaplusdevelopment.mixin;
/*    */ 
/*    */ import bananaplusdevelopment.addons.modules.Twerk;
/*    */ import minegame159.meteorclient.systems.modules.Modules;
/*    */ import net.minecraft.class_743;
/*    */ import net.minecraft.class_744;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_743.class})
/*    */ public class KeyboardInputMixin
/*    */   extends class_744
/*    */ {
/*    */   @Inject(method = {"tick"}, at = {@At("TAIL")})
/*    */   private void isPressed(boolean slowDown, CallbackInfo ci) {
/* 21 */     if (((Twerk)Modules.get().get(Twerk.class)).doVanilla()) this.field_3903 = true; 
/*    */   }
/*    */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/mixin/KeyboardInputMixin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */