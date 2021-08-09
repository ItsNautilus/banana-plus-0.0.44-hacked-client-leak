/*    */ package bananaplusdevelopment.mixin;
/*    */ 
/*    */ import minegame159.meteorclient.systems.config.Config;
/*    */ import minegame159.meteorclient.utils.render.color.Color;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_437;
/*    */ import net.minecraft.class_442;
/*    */ import net.minecraft.class_4587;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({class_442.class})
/*    */ public class TitleScrenMixin
/*    */   extends class_437 {
/* 17 */   private final int WHITE = Color.fromRGBA(255, 255, 255, 255);
/* 18 */   private final int GRAY = Color.fromRGBA(175, 175, 175, 255);
/* 19 */   private final int YELLOW = Color.fromRGBA(255, 193, 0, 255);
/*    */   
/*    */   private String textB1;
/*    */   
/*    */   private int text1BLength;
/*    */   
/*    */   private String textB2;
/*    */   
/*    */   private int text2BLength;
/*    */   
/*    */   private String textB3;
/*    */   
/*    */   private int text3BLength;
/*    */   
/*    */   private String textB4;
/*    */   private int text4BLength;
/*    */   private String textB5;
/*    */   private int text5BLength;
/*    */   private int fullBLength;
/*    */   private int prevBWidth;
/* 39 */   private int heightT = 3;
/* 40 */   private int heightD = 14;
/*    */   private int heightTD;
/*    */   
/*    */   public TitleScrenMixin(class_2561 title) {
/* 44 */     super(title);
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"init"}, at = {@At("TAIL")})
/*    */   private void onInit(CallbackInfo info) {
/* 50 */     this.textB1 = "Banana+";
/* 51 */     this.textB2 = " by ";
/* 52 */     this.textB3 = "Bennooo";
/* 53 */     this.textB4 = " & ";
/* 54 */     this.textB5 = "Necro";
/*    */     
/* 56 */     this.text1BLength = this.field_22793.method_1727(this.textB1);
/* 57 */     this.text2BLength = this.field_22793.method_1727(this.textB2);
/* 58 */     this.text3BLength = this.field_22793.method_1727(this.textB3);
/* 59 */     this.text4BLength = this.field_22793.method_1727(this.textB4);
/* 60 */     this.text5BLength = this.field_22793.method_1727(this.textB5);
/*    */ 
/*    */     
/* 63 */     this.fullBLength = this.text1BLength + this.text2BLength + this.text3BLength + this.text4BLength + this.text5BLength;
/* 64 */     this.prevBWidth = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"render"}, at = {@At("TAIL")})
/*    */   private void onRender(class_4587 mat, int mouseX, int mouseY, float delta, CallbackInfo info) {
/* 70 */     if (!(Config.get()).titleScreenCredits) { this.heightTD = this.heightT; } else { this.heightTD = this.heightD; }
/* 71 */      this.prevBWidth = 0;
/* 72 */     this.field_22793.method_1720(mat, this.textB1, (this.field_22789 - this.fullBLength - 3), this.heightTD, this.YELLOW);
/* 73 */     this.prevBWidth += this.text1BLength;
/* 74 */     this.field_22793.method_1720(mat, this.textB2, (this.field_22789 - this.fullBLength + this.prevBWidth - 3), this.heightTD, this.WHITE);
/* 75 */     this.prevBWidth += this.text2BLength;
/* 76 */     this.field_22793.method_1720(mat, this.textB3, (this.field_22789 - this.fullBLength + this.prevBWidth - 3), this.heightTD, this.GRAY);
/* 77 */     this.prevBWidth += this.text3BLength;
/* 78 */     this.field_22793.method_1720(mat, this.textB4, (this.field_22789 - this.fullBLength + this.prevBWidth - 3), this.heightTD, this.WHITE);
/* 79 */     this.prevBWidth += this.text4BLength;
/* 80 */     this.field_22793.method_1720(mat, this.textB5, (this.field_22789 - this.fullBLength + this.prevBWidth - 3), this.heightTD, this.GRAY);
/* 81 */     this.prevBWidth += this.text5BLength;
/*    */   }
/*    */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/mixin/TitleScrenMixin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */