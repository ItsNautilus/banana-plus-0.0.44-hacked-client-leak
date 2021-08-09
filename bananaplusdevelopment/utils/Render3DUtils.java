/*     */ package bananaplusdevelopment.utils;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import minegame159.meteorclient.utils.render.color.Color;
/*     */ import net.minecraft.class_1159;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_238;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_287;
/*     */ import net.minecraft.class_289;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_4184;
/*     */ import net.minecraft.class_4493;
/*     */ import net.minecraft.class_4587;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Render3DUtils {
/*  19 */   private static final class_310 mc = class_310.method_1551();
/*     */   
/*     */   public static class_243 getEntityRenderPosition(class_1297 entity, double partial) {
/*  22 */     double x = entity.field_6014 + (entity.method_23317() - entity.field_6014) * partial - ((mc.method_1561()).field_4686.method_19326()).field_1352;
/*  23 */     double y = entity.field_6036 + (entity.method_23318() - entity.field_6036) * partial - ((mc.method_1561()).field_4686.method_19326()).field_1351;
/*  24 */     double z = entity.field_5969 + (entity.method_23321() - entity.field_5969) * partial - ((mc.method_1561()).field_4686.method_19326()).field_1350;
/*  25 */     return new class_243(x, y, z);
/*     */   }
/*     */   
/*     */   public static class_243 getRenderPosition(double x, double y, double z) {
/*  29 */     double minX = x - ((mc.method_1561()).field_4686.method_19326()).field_1352;
/*  30 */     double minY = y - ((mc.method_1561()).field_4686.method_19326()).field_1351;
/*  31 */     double minZ = z - ((mc.method_1561()).field_4686.method_19326()).field_1350;
/*  32 */     return new class_243(minX, minY, minZ);
/*     */   }
/*     */   
/*     */   public static class_243 getRenderPosition(class_243 vec3d) {
/*  36 */     double minX = vec3d.method_10216() - ((mc.method_1561()).field_4686.method_19326()).field_1352;
/*  37 */     double minY = vec3d.method_10214() - ((mc.method_1561()).field_4686.method_19326()).field_1351;
/*  38 */     double minZ = vec3d.method_10215() - ((mc.method_1561()).field_4686.method_19326()).field_1350;
/*  39 */     return new class_243(minX, minY, minZ);
/*     */   }
/*     */   
/*     */   public static class_243 getRenderPosition(class_2338 blockPos) {
/*  43 */     double minX = blockPos.method_10263() - ((mc.method_1561()).field_4686.method_19326()).field_1352;
/*  44 */     double minY = blockPos.method_10264() - ((mc.method_1561()).field_4686.method_19326()).field_1351;
/*  45 */     double minZ = blockPos.method_10260() - ((mc.method_1561()).field_4686.method_19326()).field_1350;
/*  46 */     return new class_243(minX, minY, minZ);
/*     */   }
/*     */   
/*     */   public static void fixCameraRots() {
/*  50 */     class_4184 camera = (mc.method_1561()).field_4686;
/*  51 */     GL11.glRotated(-class_3532.method_15338(camera.method_19330() + 180.0D), 0.0D, 1.0D, 0.0D);
/*  52 */     GL11.glRotated(-class_3532.method_15393(camera.method_19329()), 1.0D, 0.0D, 0.0D);
/*     */   }
/*     */   
/*     */   public static void applyCameraRots() {
/*  56 */     class_4184 camera = (mc.method_1561()).field_4686;
/*  57 */     GL11.glRotated(class_3532.method_15393(camera.method_19329()), 1.0D, 0.0D, 0.0D);
/*  58 */     GL11.glRotated(class_3532.method_15338(camera.method_19330() + 180.0D), 0.0D, 1.0D, 0.0D);
/*     */   }
/*     */   
/*     */   public static void setup3DRender(boolean disableDepth) {
/*  62 */     RenderSystem.disableTexture();
/*  63 */     RenderSystem.enableBlend();
/*  64 */     RenderSystem.blendFuncSeparate(class_4493.class_4535.field_22541, class_4493.class_4534.field_22523, class_4493.class_4535.field_22534, class_4493.class_4534.field_22527);
/*  65 */     if (disableDepth)
/*  66 */       RenderSystem.disableDepthTest(); 
/*  67 */     RenderSystem.depthMask(class_310.method_29611());
/*  68 */     RenderSystem.enableCull();
/*     */   }
/*     */   
/*     */   public static void end3DRender() {
/*  72 */     RenderSystem.enableTexture();
/*  73 */     RenderSystem.disableCull();
/*  74 */     RenderSystem.disableBlend();
/*  75 */     RenderSystem.enableDepthTest();
/*  76 */     RenderSystem.depthMask(true);
/*     */   }
/*     */   
/*     */   public static void drawSphere(class_4587 matrixStack, float radius, int gradation, Color color, boolean testDepth, class_243 pos) {
/*  80 */     class_1159 matrix4f = matrixStack.method_23760().method_23761();
/*  81 */     float PI = 3.141592F;
/*     */     
/*  83 */     setup3DRender(!testDepth); float alpha;
/*  84 */     for (alpha = 0.0F; alpha < Math.PI; alpha += 3.141592F / gradation) {
/*  85 */       class_287 bufferBuilder = class_289.method_1348().method_1349();
/*  86 */       bufferBuilder.method_1328(1, class_290.field_1576); float beta;
/*  87 */       for (beta = 0.0F; beta < 6.314601203754922D; beta += 3.141592F / gradation) {
/*  88 */         float x = (float)(pos.method_10216() + radius * Math.cos(beta) * Math.sin(alpha));
/*  89 */         float y = (float)(pos.method_10214() + radius * Math.sin(beta) * Math.sin(alpha));
/*  90 */         float z = (float)(pos.method_10215() + radius * Math.cos(alpha));
/*  91 */         class_243 renderPos = getRenderPosition(x, y, z);
/*  92 */         bufferBuilder.method_22918(matrix4f, (float)renderPos.field_1352, (float)renderPos.field_1351, (float)renderPos.field_1350).method_1336(color.r, color.g, color.b, color.a).method_1344();
/*  93 */         x = (float)(pos.method_10216() + radius * Math.cos(beta) * Math.sin((alpha + 3.141592F / gradation)));
/*  94 */         y = (float)(pos.method_10214() + radius * Math.sin(beta) * Math.sin((alpha + 3.141592F / gradation)));
/*  95 */         z = (float)(pos.method_10215() + radius * Math.cos((alpha + 3.141592F / gradation)));
/*  96 */         renderPos = getRenderPosition(x, y, z);
/*  97 */         bufferBuilder.method_22918(matrix4f, (float)renderPos.field_1352, (float)renderPos.field_1351, (float)renderPos.field_1350).method_1336(color.r, color.g, color.b, color.a).method_1344();
/*     */       } 
/*  99 */       bufferBuilder.method_1326();
/* 100 */       class_286.method_1309(bufferBuilder);
/*     */     } 
/* 102 */     end3DRender();
/*     */   }
/*     */   
/*     */   public static void drawBox(class_4587 matrixStack, class_238 bb, Color color) {
/* 106 */     setup3DRender(true);
/* 107 */     drawFilledBox(matrixStack, bb, color);
/* 108 */     RenderSystem.lineWidth(1.0F);
/* 109 */     drawOutlineBox(matrixStack, bb, color);
/* 110 */     end3DRender();
/*     */   }
/*     */   
/*     */   public static void drawBoxOutline(class_4587 matrixStack, class_238 bb, Color color) {
/* 114 */     setup3DRender(true);
/* 115 */     RenderSystem.lineWidth(1.0F);
/* 116 */     drawOutlineBox(matrixStack, bb, color);
/* 117 */     end3DRender();
/*     */   }
/*     */   
/*     */   public static void drawBoxInside(class_4587 matrixStack, class_238 bb, Color color) {
/* 121 */     setup3DRender(true);
/* 122 */     drawFilledBox(matrixStack, bb, color);
/* 123 */     end3DRender();
/*     */   }
/*     */   
/*     */   public static void drawEntityBox(class_4587 matrixStack, class_1297 entity, float partialTicks, Color color) {
/* 127 */     class_243 renderPos = getEntityRenderPosition(entity, partialTicks);
/* 128 */     drawEntityBox(matrixStack, entity, renderPos.field_1352, renderPos.field_1351, renderPos.field_1350, color);
/*     */   }
/*     */   
/*     */   public static void drawEntityBox(class_4587 matrixStack, class_1297 entity, double x, double y, double z, Color color) {
/* 132 */     float yaw = class_3532.method_17821(mc.method_1488(), entity.field_5982, entity.field_6031);
/* 133 */     setup3DRender(true);
/* 134 */     matrixStack.method_22904(x, y, z);
/* 135 */     matrixStack.method_22907(new class_1158(new class_1160(0.0F, -1.0F, 0.0F), yaw, true));
/* 136 */     matrixStack.method_22904(-x, -y, -z);
/*     */     
/* 138 */     class_238 bb = new class_238(x - entity.method_17681() + 0.25D, y, z - entity.method_17681() + 0.25D, x + entity.method_17681() - 0.25D, y + entity.method_17682() + 0.1D, z + entity.method_17681() - 0.25D);
/* 139 */     if (entity instanceof net.minecraft.class_1542) {
/* 140 */       bb = new class_238(x - 0.15D, y + 0.10000000149011612D, z - 0.15D, x + 0.15D, y + 0.5D, z + 0.15D);
/*     */     }
/* 142 */     drawFilledBox(matrixStack, bb, color);
/* 143 */     RenderSystem.lineWidth(1.0F);
/* 144 */     drawOutlineBox(matrixStack, bb, color);
/*     */     
/* 146 */     end3DRender();
/* 147 */     matrixStack.method_22904(x, y, z);
/* 148 */     matrixStack.method_22907(new class_1158(new class_1160(0.0F, 1.0F, 0.0F), yaw, true));
/* 149 */     matrixStack.method_22904(-x, -y, -z);
/*     */   }
/*     */   
/*     */   public static double interpolate(double now, double then, double percent) {
/* 153 */     return then + (now - then) * percent;
/*     */   }
/*     */   
/*     */   public static void drawFilledBox(class_4587 matrixStack, class_238 bb, Color color) {
/* 157 */     class_1159 matrix4f = matrixStack.method_23760().method_23761();
/*     */     
/* 159 */     class_287 bufferBuilder = class_289.method_1348().method_1349();
/* 160 */     bufferBuilder.method_1328(7, class_290.field_1576);
/* 161 */     float minX = (float)bb.field_1323;
/* 162 */     float minY = (float)bb.field_1322;
/* 163 */     float minZ = (float)bb.field_1321;
/* 164 */     float maxX = (float)bb.field_1320;
/* 165 */     float maxY = (float)bb.field_1325;
/* 166 */     float maxZ = (float)bb.field_1324;
/*     */     
/* 168 */     bufferBuilder.method_22918(matrix4f, minX, minY, minZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/* 169 */     bufferBuilder.method_22918(matrix4f, maxX, minY, minZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/* 170 */     bufferBuilder.method_22918(matrix4f, maxX, minY, maxZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/* 171 */     bufferBuilder.method_22918(matrix4f, minX, minY, maxZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/*     */     
/* 173 */     bufferBuilder.method_22918(matrix4f, minX, maxY, minZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/* 174 */     bufferBuilder.method_22918(matrix4f, minX, maxY, maxZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/* 175 */     bufferBuilder.method_22918(matrix4f, maxX, maxY, maxZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/* 176 */     bufferBuilder.method_22918(matrix4f, maxX, maxY, minZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/*     */     
/* 178 */     bufferBuilder.method_22918(matrix4f, minX, minY, minZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/* 179 */     bufferBuilder.method_22918(matrix4f, minX, maxY, minZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/* 180 */     bufferBuilder.method_22918(matrix4f, maxX, maxY, minZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/* 181 */     bufferBuilder.method_22918(matrix4f, maxX, minY, minZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/*     */     
/* 183 */     bufferBuilder.method_22918(matrix4f, maxX, minY, minZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/* 184 */     bufferBuilder.method_22918(matrix4f, maxX, maxY, minZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/* 185 */     bufferBuilder.method_22918(matrix4f, maxX, maxY, maxZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/* 186 */     bufferBuilder.method_22918(matrix4f, maxX, minY, maxZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/*     */     
/* 188 */     bufferBuilder.method_22918(matrix4f, minX, minY, maxZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/* 189 */     bufferBuilder.method_22918(matrix4f, maxX, minY, maxZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/* 190 */     bufferBuilder.method_22918(matrix4f, maxX, maxY, maxZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/* 191 */     bufferBuilder.method_22918(matrix4f, minX, maxY, maxZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/*     */     
/* 193 */     bufferBuilder.method_22918(matrix4f, minX, minY, minZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/* 194 */     bufferBuilder.method_22918(matrix4f, minX, minY, maxZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/* 195 */     bufferBuilder.method_22918(matrix4f, minX, maxY, maxZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/* 196 */     bufferBuilder.method_22918(matrix4f, minX, maxY, minZ).method_1336(color.r, color.g, color.b, color.a).method_1344();
/* 197 */     bufferBuilder.method_1326();
/* 198 */     class_286.method_1309(bufferBuilder);
/*     */   }
/*     */   
/*     */   public static void drawOutlineBox(class_4587 matrixStack, class_238 bb, Color color) {
/* 202 */     class_1159 matrix4f = matrixStack.method_23760().method_23761();
/*     */     
/* 204 */     class_287 bufferBuilder = class_289.method_1348().method_1349();
/* 205 */     bufferBuilder.method_1328(1, class_290.field_1576);
/*     */     
/* 207 */     class_265 shape = class_259.method_1078(bb);
/* 208 */     shape.method_1104((x1, y1, z1, x2, y2, z2) -> {
/*     */           bufferBuilder.method_22918(matrix4f, (float)x1, (float)y1, (float)z1).method_1336(color.r, color.g, color.b, color.a).method_1344();
/*     */           
/*     */           bufferBuilder.method_22918(matrix4f, (float)x2, (float)y2, (float)z2).method_1336(color.r, color.g, color.b, color.a).method_1344();
/*     */         });
/* 213 */     bufferBuilder.method_1326();
/* 214 */     class_286.method_1309(bufferBuilder);
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/utils/Render3DUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */