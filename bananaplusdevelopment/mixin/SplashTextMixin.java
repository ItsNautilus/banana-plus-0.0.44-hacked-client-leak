/*    */ package bananaplusdevelopment.mixin;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.class_4008;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({class_4008.class})
/*    */ public class SplashTextMixin
/*    */ {
/*    */   private boolean override = true;
/* 16 */   private final Random random = new Random();
/*    */   
/* 18 */   private final List<String> bananaPlusSplashes = getBananaPlusSplashes();
/*    */ 
/*    */   
/*    */   @Inject(method = {"get"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onApply(CallbackInfoReturnable<String> cirr) {
/* 23 */     if (this.override) cirr.setReturnValue(this.bananaPlusSplashes.get(this.random.nextInt(this.bananaPlusSplashes.size()))); 
/* 24 */     this.override = !this.override;
/*    */   }
/*    */   
/*    */   private static List<String> getBananaPlusSplashes() {
/* 28 */     return Arrays.asList(new String[] { "§6Banana+", "§6Banana-", "§6Banana devs OP", "§2Oasis", "§bTheRealYeetBird", "§fBennooo", "§8Necropho", "§dNecro", "§cFazeNecro", "§1sju", "§2100% THC", "§2monke", "§aI fucking hate green holes", "§aplz stop the green holes", "truevanilla.org", "TVE", "§9noob", "§2ezzzzz" });
/*    */   }
/*    */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/mixin/SplashTextMixin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */