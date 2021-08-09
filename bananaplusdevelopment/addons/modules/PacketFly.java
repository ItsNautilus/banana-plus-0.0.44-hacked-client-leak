/*     */ package bananaplusdevelopment.addons.modules;
/*     */ import bananaplusdevelopment.addons.AddModule;
/*     */ import io.netty.util.internal.ConcurrentSet;
/*     */ import java.util.Set;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import minegame159.meteorclient.events.entity.player.PlayerMoveEvent;
/*     */ import minegame159.meteorclient.events.packets.PacketEvent;
/*     */ import minegame159.meteorclient.mixin.PlayerPositionLookS2CPacketAccessor;
/*     */ import minegame159.meteorclient.settings.BoolSetting;
/*     */ import minegame159.meteorclient.settings.DoubleSetting;
/*     */ import minegame159.meteorclient.settings.IntSetting;
/*     */ import minegame159.meteorclient.settings.Setting;
/*     */ import minegame159.meteorclient.settings.SettingGroup;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2708;
/*     */ import net.minecraft.class_2793;
/*     */ import net.minecraft.class_2828;
/*     */ 
/*     */ public class PacketFly extends Module {
/*  23 */   private final Set<class_2828> packets = (Set<class_2828>)new ConcurrentSet();
/*  24 */   private final SettingGroup sgMovement = this.settings.createGroup("Movement");
/*  25 */   private final SettingGroup sgClient = this.settings.createGroup("Client");
/*  26 */   private final SettingGroup sgBypass = this.settings.createGroup("Bypass");
/*     */   
/*  28 */   private final Setting<Double> horizontalSpeed = this.sgMovement.add((Setting)(new DoubleSetting.Builder())
/*  29 */       .name("Horizontal Speed")
/*  30 */       .description("Horizontal speed in blocks per second.")
/*  31 */       .defaultValue(5.2D)
/*  32 */       .min(0.0D)
/*  33 */       .max(20.0D)
/*  34 */       .sliderMin(0.0D)
/*  35 */       .sliderMax(20.0D)
/*  36 */       .build());
/*     */ 
/*     */   
/*  39 */   private final Setting<Double> verticalSpeed = this.sgMovement.add((Setting)(new DoubleSetting.Builder())
/*  40 */       .name("Vertical Speed")
/*  41 */       .description("Vertical speed in blocks per second.")
/*  42 */       .defaultValue(1.24D)
/*  43 */       .min(0.0D)
/*  44 */       .max(5.0D)
/*  45 */       .sliderMin(0.0D)
/*  46 */       .sliderMax(20.0D)
/*  47 */       .build());
/*     */ 
/*     */   
/*  50 */   private final Setting<Boolean> sendTeleport = this.sgMovement.add((Setting)(new BoolSetting.Builder())
/*  51 */       .name("Teleport")
/*  52 */       .description("Sends teleport packets.")
/*  53 */       .defaultValue(true)
/*  54 */       .build());
/*     */ 
/*     */   
/*  57 */   private final Setting<Boolean> setYaw = this.sgClient.add((Setting)(new BoolSetting.Builder())
/*  58 */       .name("Set Yaw")
/*  59 */       .description("Sets yaw client side.")
/*  60 */       .defaultValue(true)
/*  61 */       .build());
/*     */ 
/*     */   
/*  64 */   private final Setting<Boolean> setMove = this.sgClient.add((Setting)(new BoolSetting.Builder())
/*  65 */       .name("Set Move")
/*  66 */       .description("Sets movement client side.")
/*  67 */       .defaultValue(false)
/*  68 */       .build());
/*     */ 
/*     */   
/*  71 */   private final Setting<Boolean> setPos = this.sgClient.add((Setting)(new BoolSetting.Builder())
/*  72 */       .name("Set Pos")
/*  73 */       .description("Sets position client side.")
/*  74 */       .defaultValue(false)
/*  75 */       .build());
/*     */ 
/*     */   
/*  78 */   private final Setting<Boolean> setID = this.sgClient.add((Setting)(new BoolSetting.Builder())
/*  79 */       .name("Set ID")
/*  80 */       .description("Updates teleport id when a position packet is received.")
/*  81 */       .defaultValue(false)
/*  82 */       .build());
/*     */ 
/*     */   
/*  85 */   private final Setting<Boolean> noClip = this.sgClient.add((Setting)(new BoolSetting.Builder())
/*  86 */       .name("NoClip")
/*  87 */       .description("Makes the client ignore walls.")
/*  88 */       .defaultValue(false)
/*  89 */       .build());
/*     */ 
/*     */   
/*  92 */   private final Setting<Boolean> antiKick = this.sgBypass.add((Setting)(new BoolSetting.Builder())
/*  93 */       .name("Anti Kick")
/*  94 */       .description("Moves down occasionally to prevent kicks.")
/*  95 */       .defaultValue(true)
/*  96 */       .build());
/*     */ 
/*     */   
/*  99 */   private final Setting<Integer> downDelay = this.sgBypass.add((Setting)(new IntSetting.Builder())
/* 100 */       .name("Down Delay")
/* 101 */       .description("How often you move down when not flying upwards. (ticks)")
/* 102 */       .defaultValue(4)
/* 103 */       .sliderMin(1)
/* 104 */       .sliderMax(30)
/* 105 */       .min(1)
/* 106 */       .max(30)
/* 107 */       .build());
/*     */ 
/*     */   
/* 110 */   private final Setting<Integer> downDelayFlying = this.sgBypass.add((Setting)(new IntSetting.Builder())
/* 111 */       .name("Down Delay (Flying)")
/* 112 */       .description("How often you move down when flying upwards. (ticks)")
/* 113 */       .defaultValue(10)
/* 114 */       .sliderMin(1)
/* 115 */       .sliderMax(30)
/* 116 */       .min(1)
/* 117 */       .max(30)
/* 118 */       .build());
/*     */ 
/*     */   
/* 121 */   private final Setting<Boolean> invalidPacket = this.sgBypass.add((Setting)(new BoolSetting.Builder())
/* 122 */       .name("Invalid Packet")
/* 123 */       .description("Sends invalid movement packets.")
/* 124 */       .defaultValue(false)
/* 125 */       .build());
/*     */ 
/*     */   
/* 128 */   private int flightCounter = 0;
/* 129 */   private int teleportID = 0;
/*     */   
/*     */   public PacketFly() {
/* 132 */     super(AddModule.BANANAPLUS, "packet-fly", "Fly using packets.");
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onSendMovementPackets(SendMovementPacketsEvent.Pre event) {
/* 137 */     this.mc.field_1724.method_18800(0.0D, 0.0D, 0.0D);
/* 138 */     double speed = 0.0D;
/* 139 */     boolean checkCollisionBoxes = checkHitBoxes();
/*     */     
/* 141 */     speed = (this.mc.field_1724.field_3913.field_3904 && (checkCollisionBoxes || (this.mc.field_1724.field_3913.field_3905 == 0.0D && this.mc.field_1724.field_3913.field_3907 == 0.0D))) ? ((((Boolean)this.antiKick.get()).booleanValue() && !checkCollisionBoxes) ? (resetCounter(((Integer)this.downDelayFlying.get()).intValue()) ? -0.032D : (((Double)this.verticalSpeed.get()).doubleValue() / 20.0D)) : (((Double)this.verticalSpeed.get()).doubleValue() / 20.0D)) : (this.mc.field_1724.field_3913.field_3903 ? (((Double)this.verticalSpeed.get()).doubleValue() / -20.0D) : (!checkCollisionBoxes ? (resetCounter(((Integer)this.downDelay.get()).intValue()) ? (((Boolean)this.antiKick.get()).booleanValue() ? -0.04D : 0.0D) : 0.0D) : 0.0D));
/*     */     
/* 143 */     class_243 horizontal = PlayerUtils.getHorizontalVelocity(((Double)this.horizontalSpeed.get()).doubleValue());
/*     */     
/* 145 */     this.mc.field_1724.method_18800(horizontal.field_1352, speed, horizontal.field_1350);
/* 146 */     sendPackets((this.mc.field_1724.method_18798()).field_1352, (this.mc.field_1724.method_18798()).field_1351, (this.mc.field_1724.method_18798()).field_1350, ((Boolean)this.sendTeleport.get()).booleanValue());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onMove(PlayerMoveEvent event) {
/* 151 */     if (((Boolean)this.setMove.get()).booleanValue() && this.flightCounter != 0) {
/* 152 */       event.movement = new class_243((this.mc.field_1724.method_18798()).field_1352, (this.mc.field_1724.method_18798()).field_1351, (this.mc.field_1724.method_18798()).field_1350);
/* 153 */       if (((Boolean)this.noClip.get()).booleanValue() && checkHitBoxes()) {
/* 154 */         this.mc.field_1724.field_5960 = true;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPacketSent(PacketEvent.Send event) {
/* 161 */     if (event.packet instanceof class_2828 && !this.packets.remove(event.packet)) {
/* 162 */       event.setCancelled(true);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 168 */     if (event.packet instanceof class_2708 && this.mc.field_1724 != null && this.mc.field_1687 != null) {
/* 169 */       class_2338 pos = new class_2338((this.mc.field_1724.method_19538()).field_1352, (this.mc.field_1724.method_19538()).field_1351, (this.mc.field_1724.method_19538()).field_1350);
/* 170 */       class_2708 packet = (class_2708)event.packet;
/* 171 */       if (((Boolean)this.setYaw.get()).booleanValue()) {
/* 172 */         ((PlayerPositionLookS2CPacketAccessor)event.packet).setPitch(this.mc.field_1724.field_5965);
/* 173 */         ((PlayerPositionLookS2CPacketAccessor)event.packet).setYaw(this.mc.field_1724.field_6031);
/*     */       } 
/* 175 */       if (((Boolean)this.setID.get()).booleanValue()) {
/* 176 */         this.teleportID = packet.method_11737();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean checkHitBoxes() {
/* 182 */     return (this.mc.field_1687.method_20812((class_1297)this.mc.field_1724, this.mc.field_1724.method_5829().method_1009(-0.0625D, -0.0625D, -0.0625D)).count() != 0L);
/*     */   }
/*     */   
/*     */   private boolean resetCounter(int counter) {
/* 186 */     if (++this.flightCounter >= counter) {
/* 187 */       this.flightCounter = 0;
/* 188 */       return true;
/*     */     } 
/* 190 */     return false;
/*     */   }
/*     */   
/*     */   private void sendPackets(double x, double y, double z, boolean teleport) {
/* 194 */     class_243 vec = new class_243(x, y, z);
/* 195 */     class_243 position = this.mc.field_1724.method_19538().method_1019(vec);
/* 196 */     class_243 outOfBoundsVec = outOfBoundsVec(vec, position);
/* 197 */     packetSender((class_2828)new class_2828.class_2829(position.field_1352, position.field_1351, position.field_1350, this.mc.field_1724.method_24828()));
/* 198 */     if (((Boolean)this.invalidPacket.get()).booleanValue()) {
/* 199 */       packetSender((class_2828)new class_2828.class_2829(outOfBoundsVec.field_1352, outOfBoundsVec.field_1351, outOfBoundsVec.field_1350, this.mc.field_1724.method_24828()));
/*     */     }
/* 201 */     if (((Boolean)this.setPos.get()).booleanValue()) {
/* 202 */       this.mc.field_1724.method_23327(position.field_1352, position.field_1351, position.field_1350);
/*     */     }
/* 204 */     teleportPacket(position, teleport);
/*     */   }
/*     */   
/*     */   private void teleportPacket(class_243 pos, boolean shouldTeleport) {
/* 208 */     if (shouldTeleport) {
/* 209 */       this.mc.field_1724.field_3944.method_2883((class_2596)new class_2793(++this.teleportID));
/*     */     }
/*     */   }
/*     */   
/*     */   private class_243 outOfBoundsVec(class_243 offset, class_243 position) {
/* 214 */     return position.method_1031(0.0D, 1500.0D, 0.0D);
/*     */   }
/*     */   
/*     */   private void packetSender(class_2828 packet) {
/* 218 */     this.packets.add(packet);
/* 219 */     this.mc.field_1724.field_3944.method_2883((class_2596)packet);
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/PacketFly.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */