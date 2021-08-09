/*    */ package bananaplusdevelopment.addons.modules;
/*    */ import bananaplusdevelopment.addons.AddModule;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Timer;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import minegame159.meteorclient.events.Cancellable;
/*    */ import minegame159.meteorclient.settings.BoolSetting;
/*    */ import minegame159.meteorclient.settings.Setting;
/*    */ import minegame159.meteorclient.settings.SettingGroup;
/*    */ import minegame159.meteorclient.systems.friends.Friends;
/*    */ import minegame159.meteorclient.utils.player.ChatUtils;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_1802;
/*    */ 
/*    */ public class ArmorMessage extends Module {
/* 18 */   private final SettingGroup sgGeneral = this.settings.createGroup("General");
/*    */   
/* 20 */   private final Setting<Integer> armorThreshhold = this.sgGeneral.add((Setting)(new IntSetting.Builder())
/* 21 */       .name("Armor Threshhold")
/* 22 */       .description("The minimum armor% before notifying.")
/* 23 */       .defaultValue(20)
/* 24 */       .min(0)
/* 25 */       .sliderMax(100)
/* 26 */       .build());
/*    */ 
/*    */   
/* 29 */   private final Setting<Boolean> notifySelf = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/* 30 */       .name("Notify yourself")
/* 31 */       .description("Notify yourself when your armor is low.")
/* 32 */       .defaultValue(true)
/* 33 */       .build());
/*    */ 
/*    */   
/* 36 */   private final Setting<Boolean> notification = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/* 37 */       .name("Notification")
/* 38 */       .description("Notication.")
/* 39 */       .defaultValue(true)
/* 40 */       .build());
/*    */ 
/*    */   
/* 43 */   private final Map<class_1657, Integer> entityArmorArraylist = new HashMap<>();
/* 44 */   private final Timer timer = new Timer();
/*    */ 
/*    */   
/*    */   public ArmorMessage() {
/* 48 */     super(AddModule.BANANAPLUS, "Armor Message", "Automatically notify friends when armour is low.");
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(Cancellable event) {
/* 54 */     for (class_1657 player : this.mc.field_1687.method_18456()) {
/* 55 */       if (!player.method_5805() || !Friends.get().isFriend(player))
/* 56 */         continue;  for (class_1799 stack : player.field_7514.field_7548) {
/* 57 */         if (stack == class_1799.field_8037)
/* 58 */           continue;  int percent = ((Integer)this.armorThreshhold.get()).intValue();
/* 59 */         if (percent <= ((Integer)this.armorThreshhold.get()).intValue() && !this.entityArmorArraylist.containsKey(player)) {
/* 60 */           if (player == this.mc.field_1687.method_18456() && ((Boolean)this.notifySelf.get()).booleanValue()) {
/* 61 */             ChatUtils.warning(" Watchout your " + getArmorPieceName(stack) + " low! | Banana+", new Object[] { this.notification.get() });
/*    */           } else {
/* 63 */             this.mc.field_1724.method_3142("/msg " + player.method_5477() + " " + player.method_5477() + " watchout your " + getArmorPieceName(stack) + " low! | Banana+");
/*    */           } 
/* 65 */           this.entityArmorArraylist.put(player, Integer.valueOf(player.field_7514.field_7548.indexOf(stack)));
/*    */         } 
/* 67 */         if (!this.entityArmorArraylist.containsKey(player) || ((Integer)this.entityArmorArraylist.get(player)).intValue() != player.field_7514.field_7548.indexOf(stack) || percent <= ((Integer)this.armorThreshhold.get()).intValue())
/*    */           continue; 
/* 69 */         this.entityArmorArraylist.remove(player);
/*    */       } 
/* 71 */       if (!this.entityArmorArraylist.containsKey(player) || player.field_7514.field_7548.get(((Integer)this.entityArmorArraylist.get(player)).intValue()) != class_1799.field_8037)
/*    */         continue; 
/* 73 */       this.entityArmorArraylist.remove(player);
/*    */     } 
/*    */   }
/*    */   
/*    */   private String getArmorPieceName(class_1799 stack) {
/* 78 */     if (stack.method_7909() == class_1802.field_22027 || stack.method_7909() == class_1802.field_8805 || stack.method_7909() == class_1802.field_8862 || stack.method_7909() == class_1802.field_8743 || stack.method_7909() == class_1802.field_8283 || stack.method_7909() == class_1802.field_8267) {
/* 79 */       return "helmet is";
/*    */     }
/* 81 */     if (stack.method_7909() == class_1802.field_22028 || stack.method_7909() == class_1802.field_8058 || stack.method_7909() == class_1802.field_8678 || stack.method_7909() == class_1802.field_8523 || stack.method_7909() == class_1802.field_8873 || stack.method_7909() == class_1802.field_8577) {
/* 82 */       return "chestplate is";
/*    */     }
/* 84 */     if (stack.method_7909() == class_1802.field_22029 || stack.method_7909() == class_1802.field_8348 || stack.method_7909() == class_1802.field_8416 || stack.method_7909() == class_1802.field_8396 || stack.method_7909() == class_1802.field_8218 || stack.method_7909() == class_1802.field_8570) {
/* 85 */       return "leggings are";
/*    */     }
/* 87 */     return "boots are";
/*    */   }
/*    */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/ArmorMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */