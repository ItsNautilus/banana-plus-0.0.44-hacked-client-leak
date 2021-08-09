/*     */ package bananaplusdevelopment.addons.modules;
/*     */ import bananaplusdevelopment.utils.Render3DUtils;
/*     */ import minegame159.meteorclient.events.render.RenderEvent;
/*     */ import minegame159.meteorclient.settings.Setting;
/*     */ import minegame159.meteorclient.systems.modules.render.Freecam;
/*     */ import minegame159.meteorclient.utils.player.PlayerUtils;
/*     */ import minegame159.meteorclient.utils.render.color.Color;
/*     */ import minegame159.meteorclient.utils.render.color.SettingColor;
/*     */ import net.minecraft.class_1007;
/*     */ import net.minecraft.class_1158;
/*     */ import net.minecraft.class_1159;
/*     */ import net.minecraft.class_1160;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_286;
/*     */ import net.minecraft.class_287;
/*     */ import net.minecraft.class_289;
/*     */ import net.minecraft.class_290;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_5498;
/*     */ import net.minecraft.class_591;
/*     */ import net.minecraft.class_630;
/*     */ 
/*     */ public class SkeletonESP extends Module {
/*  28 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*     */   private final Freecam freecam;
/*     */   
/*  32 */   private final Setting<SettingColor> skeletonColorSetting = this.sgGeneral.add((Setting)(new ColorSetting.Builder())
/*  33 */       .name("players-color")
/*  34 */       .description("The other player's color.")
/*  35 */       .defaultValue(new SettingColor(255, 255, 255))
/*  36 */       .build());
/*     */ 
/*     */   
/*     */   public SkeletonESP() {
/*  40 */     super(AddModule.BANANAMINUS, "skeleton-esp", "Looks cool as fuck");
/*  41 */     this.freecam = (Freecam)Modules.get().get(Freecam.class);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onRender(RenderEvent event) {
/*  46 */     class_4587 matrixStack = event.matrices;
/*  47 */     float g = event.tickDelta;
/*  48 */     Render3DUtils.setup3DRender(true);
/*  49 */     this.mc.field_1687.method_18112().forEach(entity -> {
/*     */           if (!(entity instanceof class_1657)) {
/*     */             return;
/*     */           }
/*     */           
/*     */           if (this.mc.field_1690.method_31044() == class_5498.field_26664 && !this.freecam.isActive() && this.mc.field_1724 == entity) {
/*     */             return;
/*     */           }
/*     */           
/*     */           Color skeletonColor = PlayerUtils.getPlayerColor((class_1657)entity, (Color)this.skeletonColorSetting.get());
/*     */           
/*     */           class_1657 playerEntity = (class_1657)entity;
/*     */           
/*     */           class_243 footPos = Render3DUtils.getEntityRenderPosition((class_1297)playerEntity, g);
/*     */           
/*     */           class_1007 livingEntityRenderer = (class_1007)this.mc.method_1561().method_3953((class_1297)playerEntity);
/*     */           
/*     */           class_591 playerEntityModel = (class_591)livingEntityRenderer.method_4038();
/*     */           
/*     */           float h = class_3532.method_17821(g, playerEntity.field_6220, playerEntity.field_6283);
/*     */           
/*     */           float j = class_3532.method_17821(g, playerEntity.field_6259, playerEntity.field_6241);
/*     */           
/*     */           float q = playerEntity.field_6249 - playerEntity.field_6225 * (1.0F - g);
/*     */           
/*     */           float p = class_3532.method_16439(g, playerEntity.field_6211, playerEntity.field_6225);
/*     */           
/*     */           float o = playerEntity.field_6012 + g;
/*     */           
/*     */           float k = j - h;
/*     */           
/*     */           float m = class_3532.method_16439(g, playerEntity.field_6004, playerEntity.field_5965);
/*     */           playerEntityModel.method_17087((class_1309)playerEntity, q, p, o, k, m);
/*     */           boolean sneaking = playerEntity.method_5715();
/*     */           class_630 head = playerEntityModel.field_3398;
/*     */           class_630 leftArm = playerEntityModel.field_3390;
/*     */           class_630 rightArm = playerEntityModel.field_3401;
/*     */           class_630 leftLeg = playerEntityModel.field_3397;
/*     */           class_630 rightLeg = playerEntityModel.field_3392;
/*     */           matrixStack.method_22904(footPos.field_1352, footPos.field_1351, footPos.field_1350);
/*     */           matrixStack.method_22907(new class_1158(new class_1160(0.0F, -1.0F, 0.0F), playerEntity.field_6283 + 180.0F, true));
/*     */           class_287 bufferBuilder = class_289.method_1348().method_1349();
/*     */           bufferBuilder.method_1328(1, class_290.field_1576);
/*     */           class_1159 matrix4f = matrixStack.method_23760().method_23761();
/*     */           bufferBuilder.method_22918(matrix4f, 0.0F, sneaking ? 0.6F : 0.7F, sneaking ? 0.23F : 0.0F).method_1336(skeletonColor.r, skeletonColor.g, skeletonColor.b, skeletonColor.a).method_1344();
/*     */           bufferBuilder.method_22918(matrix4f, 0.0F, sneaking ? 1.05F : 1.4F, 0.0F).method_1336(skeletonColor.r, skeletonColor.g, skeletonColor.b, skeletonColor.a).method_1344();
/*     */           bufferBuilder.method_22918(matrix4f, -0.37F, sneaking ? 1.05F : 1.35F, 0.0F).method_1336(skeletonColor.r, skeletonColor.g, skeletonColor.b, skeletonColor.a).method_1344();
/*     */           bufferBuilder.method_22918(matrix4f, 0.37F, sneaking ? 1.05F : 1.35F, 0.0F).method_1336(skeletonColor.r, skeletonColor.g, skeletonColor.b, skeletonColor.a).method_1344();
/*     */           bufferBuilder.method_22918(matrix4f, -0.15F, sneaking ? 0.6F : 0.7F, sneaking ? 0.23F : 0.0F).method_1336(skeletonColor.r, skeletonColor.g, skeletonColor.b, skeletonColor.a).method_1344();
/*     */           bufferBuilder.method_22918(matrix4f, 0.15F, sneaking ? 0.6F : 0.7F, sneaking ? 0.23F : 0.0F).method_1336(skeletonColor.r, skeletonColor.g, skeletonColor.b, skeletonColor.a).method_1344();
/*     */           matrixStack.method_22903();
/*     */           matrixStack.method_22904(0.0D, sneaking ? 1.0499999523162842D : 1.399999976158142D, 0.0D);
/*     */           rotate(matrixStack, head);
/*     */           matrix4f = matrixStack.method_23760().method_23761();
/*     */           bufferBuilder.method_22918(matrix4f, 0.0F, 0.0F, 0.0F).method_1336(skeletonColor.r, skeletonColor.g, skeletonColor.b, skeletonColor.a).method_1344();
/*     */           bufferBuilder.method_22918(matrix4f, 0.0F, 0.15F, 0.0F).method_1336(skeletonColor.r, skeletonColor.g, skeletonColor.b, skeletonColor.a).method_1344();
/*     */           matrixStack.method_22909();
/*     */           matrixStack.method_22903();
/*     */           matrixStack.method_22904(0.15000000596046448D, sneaking ? 0.6000000238418579D : 0.699999988079071D, sneaking ? 0.23000000417232513D : 0.0D);
/*     */           rotate(matrixStack, rightLeg);
/*     */           matrix4f = matrixStack.method_23760().method_23761();
/*     */           bufferBuilder.method_22918(matrix4f, 0.0F, 0.0F, 0.0F).method_1336(skeletonColor.r, skeletonColor.g, skeletonColor.b, skeletonColor.a).method_1344();
/*     */           bufferBuilder.method_22918(matrix4f, 0.0F, -0.6F, 0.0F).method_1336(skeletonColor.r, skeletonColor.g, skeletonColor.b, skeletonColor.a).method_1344();
/*     */           matrixStack.method_22909();
/*     */           matrixStack.method_22903();
/*     */           matrixStack.method_22904(-0.15000000596046448D, sneaking ? 0.6000000238418579D : 0.699999988079071D, sneaking ? 0.23000000417232513D : 0.0D);
/*     */           rotate(matrixStack, leftLeg);
/*     */           matrix4f = matrixStack.method_23760().method_23761();
/*     */           bufferBuilder.method_22918(matrix4f, 0.0F, 0.0F, 0.0F).method_1336(skeletonColor.r, skeletonColor.g, skeletonColor.b, skeletonColor.a).method_1344();
/*     */           bufferBuilder.method_22918(matrix4f, 0.0F, -0.6F, 0.0F).method_1336(skeletonColor.r, skeletonColor.g, skeletonColor.b, skeletonColor.a).method_1344();
/*     */           matrixStack.method_22909();
/*     */           matrixStack.method_22903();
/*     */           matrixStack.method_22904(0.3700000047683716D, sneaking ? 1.0499999523162842D : 1.350000023841858D, 0.0D);
/*     */           rotate(matrixStack, rightArm);
/*     */           matrix4f = matrixStack.method_23760().method_23761();
/*     */           bufferBuilder.method_22918(matrix4f, 0.0F, 0.0F, 0.0F).method_1336(skeletonColor.r, skeletonColor.g, skeletonColor.b, skeletonColor.a).method_1344();
/*     */           bufferBuilder.method_22918(matrix4f, 0.0F, -0.55F, 0.0F).method_1336(skeletonColor.r, skeletonColor.g, skeletonColor.b, skeletonColor.a).method_1344();
/*     */           matrixStack.method_22909();
/*     */           matrixStack.method_22903();
/*     */           matrixStack.method_22904(-0.3700000047683716D, sneaking ? 1.0499999523162842D : 1.350000023841858D, 0.0D);
/*     */           rotate(matrixStack, leftArm);
/*     */           matrix4f = matrixStack.method_23760().method_23761();
/*     */           bufferBuilder.method_22918(matrix4f, 0.0F, 0.0F, 0.0F).method_1336(skeletonColor.r, skeletonColor.g, skeletonColor.b, skeletonColor.a).method_1344();
/*     */           bufferBuilder.method_22918(matrix4f, 0.0F, -0.55F, 0.0F).method_1336(skeletonColor.r, skeletonColor.g, skeletonColor.b, skeletonColor.a).method_1344();
/*     */           matrixStack.method_22909();
/*     */           bufferBuilder.method_1326();
/*     */           class_286.method_1309(bufferBuilder);
/*     */           matrixStack.method_22907(new class_1158(new class_1160(0.0F, 1.0F, 0.0F), playerEntity.field_6283 + 180.0F, true));
/*     */           matrixStack.method_22904(-footPos.field_1352, -footPos.field_1351, -footPos.field_1350);
/*     */         });
/* 139 */     Render3DUtils.end3DRender();
/*     */   }
/*     */   
/*     */   private void rotate(class_4587 matrix, class_630 modelPart) {
/* 143 */     if (modelPart.field_3674 != 0.0F) {
/* 144 */       matrix.method_22907(class_1160.field_20707.method_23626(modelPart.field_3674));
/*     */     }
/*     */     
/* 147 */     if (modelPart.field_3675 != 0.0F) {
/* 148 */       matrix.method_22907(class_1160.field_20704.method_23626(modelPart.field_3675));
/*     */     }
/*     */     
/* 151 */     if (modelPart.field_3654 != 0.0F)
/* 152 */       matrix.method_22907(class_1160.field_20702.method_23626(modelPart.field_3654)); 
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/SkeletonESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */