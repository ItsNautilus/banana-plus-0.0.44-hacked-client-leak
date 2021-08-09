/*     */ package bananaplusdevelopment.addons.modules;
/*     */ import bananaplusdevelopment.addons.AddModule;
/*     */ import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
/*     */ import java.util.ArrayList;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import minegame159.meteorclient.events.packets.PacketEvent;
/*     */ import minegame159.meteorclient.events.world.TickEvent;
/*     */ import minegame159.meteorclient.settings.BoolSetting;
/*     */ import minegame159.meteorclient.settings.DoubleSetting;
/*     */ import minegame159.meteorclient.settings.EntityTypeListSetting;
/*     */ import minegame159.meteorclient.settings.EnumSetting;
/*     */ import minegame159.meteorclient.settings.IntSetting;
/*     */ import minegame159.meteorclient.settings.Setting;
/*     */ import minegame159.meteorclient.settings.SettingGroup;
/*     */ import minegame159.meteorclient.systems.friends.Friends;
/*     */ import minegame159.meteorclient.systems.modules.combat.KillAura;
/*     */ import minegame159.meteorclient.utils.entity.SortPriority;
/*     */ import minegame159.meteorclient.utils.entity.Target;
/*     */ import minegame159.meteorclient.utils.entity.TargetUtils;
/*     */ import minegame159.meteorclient.utils.player.PlayerUtils;
/*     */ import minegame159.meteorclient.utils.player.Rotations;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1299;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1934;
/*     */ 
/*     */ public class CAFix extends Module {
/*  31 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  32 */   private final SettingGroup sgTargeting = this.settings.createGroup("Targeting");
/*  33 */   private final SettingGroup sgDelay = this.settings.createGroup("Delay");
/*     */ 
/*     */ 
/*     */   
/*  37 */   private final Setting<KillAura.RotationMode> rotation = this.sgGeneral.add((Setting)(new EnumSetting.Builder())
/*  38 */       .name("rotate")
/*  39 */       .description("Determines when you should rotate towards the target.")
/*  40 */       .defaultValue((Enum)KillAura.RotationMode.Always)
/*  41 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   private final Setting<Object2BooleanMap<class_1299<?>>> entities = this.sgTargeting.add((Setting)(new EntityTypeListSetting.Builder())
/*  48 */       .name("entities")
/*  49 */       .description("Entities to attack.")
/*  50 */       .defaultValue((Object2BooleanMap)new Object2BooleanOpenHashMap(0))
/*  51 */       .onlyAttackable()
/*  52 */       .build());
/*     */ 
/*     */   
/*  55 */   private final Setting<Double> range = this.sgTargeting.add((Setting)(new DoubleSetting.Builder())
/*  56 */       .name("range")
/*  57 */       .description("The maximum range the entity can be to attack it.")
/*  58 */       .defaultValue(6.0D)
/*  59 */       .min(0.0D)
/*  60 */       .sliderMax(6.0D)
/*  61 */       .build());
/*     */ 
/*     */   
/*  64 */   private final Setting<Double> wallsRange = this.sgTargeting.add((Setting)(new DoubleSetting.Builder())
/*  65 */       .name("walls-range")
/*  66 */       .description("The maximum range the entity can be attacked through walls.")
/*  67 */       .defaultValue(6.0D)
/*  68 */       .min(0.0D)
/*  69 */       .sliderMax(6.0D)
/*  70 */       .build());
/*     */ 
/*     */   
/*  73 */   private final Setting<SortPriority> priority = this.sgTargeting.add((Setting)(new EnumSetting.Builder())
/*  74 */       .name("priority")
/*  75 */       .description("How to filter targets within range.")
/*  76 */       .defaultValue((Enum)SortPriority.LowestDistance)
/*  77 */       .build());
/*     */ 
/*     */   
/*  80 */   private final Setting<Integer> maxTargets = this.sgTargeting.add((Setting)(new IntSetting.Builder())
/*  81 */       .name("max-targets")
/*  82 */       .description("How many entities to target at once.")
/*  83 */       .defaultValue(1)
/*  84 */       .min(1).max(10)
/*  85 */       .sliderMin(1).sliderMax(5)
/*  86 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   private final Setting<Boolean> smartDelay = this.sgDelay.add((Setting)(new BoolSetting.Builder())
/*  93 */       .name("smart-delay")
/*  94 */       .description("Uses the vanilla cooldown to attack entities.")
/*  95 */       .defaultValue(false)
/*  96 */       .build());
/*     */ 
/*     */   
/*  99 */   private final Setting<Integer> hitDelay = this.sgDelay.add((Setting)(new IntSetting.Builder())
/* 100 */       .name("hit-delay")
/* 101 */       .description("How fast you hit the entity in ticks.")
/* 102 */       .defaultValue(0)
/* 103 */       .min(0)
/* 104 */       .sliderMax(60)
/* 105 */       .visible(() -> !((Boolean)this.smartDelay.get()).booleanValue())
/* 106 */       .build());
/*     */ 
/*     */ 
/*     */   
/* 110 */   private final List<class_1297> targets = new ArrayList<>();
/*     */   private int hitDelayTimer;
/*     */   private int switchTimer;
/*     */   
/*     */   public CAFix() {
/* 115 */     super(AddModule.BANANAPLUS, "CA Fix", "Attacks crystals around you.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/* 120 */     this.hitDelayTimer = 0;
/* 121 */     this.targets.clear();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/* 126 */     if (!this.mc.field_1724.method_5805() || PlayerUtils.getGameMode() == class_1934.field_9219)
/*     */       return; 
/* 128 */     TargetUtils.getList(this.targets, this::entityCheck, (SortPriority)this.priority.get(), ((Integer)this.maxTargets.get()).intValue());
/*     */     
/* 130 */     if (this.targets.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 134 */     class_1297 primary = this.targets.get(0);
/*     */     
/* 136 */     if (this.rotation.get() == KillAura.RotationMode.Always) rotate(primary, (Runnable)null);
/*     */ 
/*     */     
/* 139 */     if (delayCheck()) this.targets.forEach(this::attack);
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onSendPacket(PacketEvent.Send event) {}
/*     */   
/*     */   private boolean entityCheck(class_1297 entity) {
/* 148 */     if (entity.equals(this.mc.field_1724) || entity.equals(this.mc.field_1719)) return false; 
/* 149 */     if ((entity instanceof class_1309 && ((class_1309)entity).method_29504()) || !entity.method_5805()) return false; 
/* 150 */     if (PlayerUtils.distanceTo(entity) > ((Double)this.range.get()).doubleValue()) return false; 
/* 151 */     if (!((Object2BooleanMap)this.entities.get()).getBoolean(entity.method_5864())) return false; 
/* 152 */     if (!PlayerUtils.canSeeEntity(entity) && PlayerUtils.distanceTo(entity) > ((Double)this.wallsRange.get()).doubleValue()) return false; 
/* 153 */     if (entity instanceof class_1657) {
/* 154 */       if (((class_1657)entity).method_7337()) return false; 
/* 155 */       if (!Friends.get().shouldAttack((class_1657)entity)) return false; 
/*     */     } 
/* 157 */     return (!(entity instanceof class_1429) || !((class_1429)entity).method_6109());
/*     */   }
/*     */   
/*     */   private boolean delayCheck() {
/* 161 */     if (this.switchTimer > 0) {
/* 162 */       this.switchTimer--;
/* 163 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 167 */     if (((Boolean)this.smartDelay.get()).booleanValue()) return (this.mc.field_1724.method_7261(0.5F) >= 1.0F);
/*     */     
/* 169 */     if (this.hitDelayTimer >= 0) {
/* 170 */       this.hitDelayTimer--;
/* 171 */       return false;
/*     */     } 
/*     */     
/* 174 */     this.hitDelayTimer = ((Integer)this.hitDelay.get()).intValue();
/*     */ 
/*     */ 
/*     */     
/* 178 */     return true;
/*     */   }
/*     */   
/*     */   private void attack(class_1297 target) {
/* 182 */     if (this.rotation.get() == KillAura.RotationMode.OnHit) {
/* 183 */       rotate(target, () -> hitEntity(target));
/*     */     } else {
/* 185 */       hitEntity(target);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void hitEntity(class_1297 target) {
/* 192 */     this.mc.field_1761.method_2918((class_1657)this.mc.field_1724, target);
/* 193 */     this.mc.field_1724.method_6104(class_1268.field_5808);
/*     */   }
/*     */   
/*     */   private void rotate(class_1297 target, Runnable callback) {
/* 197 */     Rotations.rotate(Rotations.getYaw(target), Rotations.getPitch(target, Target.Body), callback);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getInfoString() {
/* 203 */     if (!this.targets.isEmpty()) {
/* 204 */       class_1297 targetFirst = this.targets.get(0);
/* 205 */       if (targetFirst instanceof class_1657) return targetFirst.method_5820(); 
/* 206 */       return targetFirst.method_5864().method_5897().getString();
/*     */     } 
/* 208 */     return null;
/*     */   }
/*     */   
/*     */   public class_1297 getTarget() {
/* 212 */     if (!this.targets.isEmpty()) return this.targets.get(0); 
/* 213 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/modules/CAFix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */