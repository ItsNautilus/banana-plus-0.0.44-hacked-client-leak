/*     */ package bananaplusdevelopment.addons.modules;
/*     */ 
/*     */ import bananaplusdevelopment.addons.AddModule;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.UUID;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import minegame159.meteorclient.events.game.GameJoinedEvent;
/*     */ import minegame159.meteorclient.events.packets.PacketEvent;
/*     */ import minegame159.meteorclient.events.world.TickEvent;
/*     */ import minegame159.meteorclient.settings.BoolSetting;
/*     */ import minegame159.meteorclient.settings.Setting;
/*     */ import minegame159.meteorclient.settings.SettingGroup;
/*     */ import minegame159.meteorclient.settings.StringSetting;
/*     */ import minegame159.meteorclient.systems.friends.Friends;
/*     */ import minegame159.meteorclient.systems.modules.Module;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_2663;
/*     */ 
/*     */ public class AutoEz
/*     */   extends Module
/*     */ {
/*  25 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  27 */   private final Setting<String> ezed = this.sgGeneral.add((Setting)(new StringSetting.Builder())
/*  28 */       .name("text")
/*  29 */       .description("The text you want to send when you ez someone.")
/*  30 */       .defaultValue("Monke Down! {victim} | Banana+")
/*  31 */       .build());
/*     */ 
/*     */   
/*  34 */   private final Setting<Boolean> IgnoreOwn = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  35 */       .name("Ignore self")
/*  36 */       .description("Ignore self.")
/*  37 */       .defaultValue(true)
/*  38 */       .build());
/*     */ 
/*     */   
/*  41 */   private final Setting<Boolean> IgnoreFriends = this.sgGeneral.add((Setting)(new BoolSetting.Builder())
/*  42 */       .name("Ignore friends")
/*  43 */       .description("Ignore friends.")
/*  44 */       .defaultValue(true)
/*  45 */       .build());
/*     */   
/*     */   private int timer;
/*     */   
/*     */   private int said;
/*     */   
/*  51 */   private final Object2IntMap<UUID> totemPops = (Object2IntMap<UUID>)new Object2IntOpenHashMap();
/*  52 */   private final Object2IntMap<UUID> chatIds = (Object2IntMap<UUID>)new Object2IntOpenHashMap();
/*     */   
/*     */   public AutoEz() {
/*  55 */     super(AddModule.BANANAPLUS, "auto-ez", "Sends a chat message when a player dies near you.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/*  60 */     this.timer = 0;
/*  61 */     this.said = 0;
/*  62 */     this.totemPops.clear();
/*  63 */     this.chatIds.clear();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onGameJoin(GameJoinedEvent event) {
/*  68 */     this.said = 0;
/*  69 */     this.totemPops.clear();
/*  70 */     this.chatIds.clear();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onReceivePacket(PacketEvent.Receive event) {
/*  75 */     if (!(event.packet instanceof class_2663))
/*     */       return; 
/*  77 */     class_2663 p = (class_2663)event.packet;
/*  78 */     if (p.method_11470() != 35)
/*     */       return; 
/*  80 */     class_1297 entity = p.method_11469((class_1937)this.mc.field_1687);
/*     */     
/*  82 */     synchronized (this.totemPops) {
/*  83 */       int pops = this.totemPops.getOrDefault(entity.method_5667(), 0);
/*  84 */       this.totemPops.put(entity.method_5667(), ++pops);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Post event) {
/*  91 */     if (this.timer <= 0) {
/*  92 */       this.timer = 20;
/*     */     } else {
/*  94 */       this.timer--;
/*     */       
/*     */       return;
/*     */     } 
/*  98 */     synchronized (this.totemPops) {
/*  99 */       for (class_1657 player : this.mc.field_1687.method_18456()) {
/* 100 */         if (!this.totemPops.containsKey(player.method_5667()))
/* 101 */           continue;  if (player.method_5739((class_1297)this.mc.field_1724) > 8.0F) {
/* 102 */           this.said = 0;
/*     */           
/*     */           continue;
/*     */         } 
/* 106 */         if ((this.said == 0 && player.field_6213 > 0) || player.method_6032() <= 0.0F) {
/* 107 */           String fart = farte(this.ezed, player);
/* 108 */           this.mc.field_1724.method_3142(fart);
/* 109 */           this.said = 1;
/* 110 */           this.chatIds.removeInt(player.method_5667());
/*     */         } 
/*     */         
/* 113 */         if (player.equals(this.mc.field_1724) && ((Boolean)this.IgnoreOwn.get()).booleanValue())
/* 114 */           return;  if (Friends.get().isFriend(player) && ((Boolean)this.IgnoreFriends.get()).booleanValue())
/*     */           return; 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private String farte(Setting<String> line, class_1657 player) {
/* 120 */     if (((String)line.get()).length() > 0)
/* 121 */       return ((String)line.get()).replace("{player}", getName()).replace("{victim}", player.method_7334().getName()); 
/* 122 */     return null;
/*     */   }
/*     */   
/*     */   private String getName() {
/* 126 */     return this.mc.field_1724.method_7334().getName();
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/AutoEz.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */