/*    */ package bananaplusdevelopment.utils;
/*    */ import minegame159.meteorclient.utils.player.Rotations;
/*    */ import net.minecraft.class_2596;
/*    */ import net.minecraft.class_2828;
/*    */ import net.minecraft.class_310;
/*    */ 
/*    */ public class RotationUtils {
/*  8 */   public static class_310 mc = class_310.method_1551();
/*    */   
/*    */   public static void packetRotate(float yaw, float pitch) {
/* 11 */     mc.field_1724.field_3944.method_2883((class_2596)new class_2828.class_2831(yaw, pitch, mc.field_1724.method_24828()));
/* 12 */     Rotations.setCamRotation(yaw, pitch);
/*    */   }
/*    */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/utils/RotationUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */