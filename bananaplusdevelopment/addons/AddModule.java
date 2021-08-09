/*    */ package bananaplusdevelopment.addons;
/*    */ import bananaplusdevelopment.addons.modules.AntiCrystal;
/*    */ import bananaplusdevelopment.addons.modules.NewChunks;
/*    */ import bananaplusdevelopment.addons.modules.Twerk;
/*    */ import minegame159.meteorclient.MeteorAddon;
/*    */ import minegame159.meteorclient.systems.modules.Category;
/*    */ import minegame159.meteorclient.systems.modules.Module;
/*    */ import minegame159.meteorclient.systems.modules.Modules;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ 
/*    */ public class AddModule extends MeteorAddon {
/* 12 */   public static final Logger LOG = LogManager.getLogger();
/* 13 */   public static final Category BANANAPLUS = new Category("Banana+ Combat");
/* 14 */   public static final Category BANANAMINUS = new Category("Banana+ Misc");
/*    */ 
/*    */   
/*    */   public void onInitialize() {
/* 18 */     LOG.info("Initializing Meteor Addon");
/* 19 */     Modules.get().add((Module)new Twerk());
/* 20 */     Modules.get().add((Module)new Glide());
/* 21 */     Modules.get().add((Module)new SkeletonESP());
/* 22 */     Modules.get().add((Module)new OldCA());
/* 23 */     Modules.get().add((Module)new AutoBedTrap());
/* 24 */     Modules.get().add((Module)new AntiSpawnpoint());
/* 25 */     Modules.get().add((Module)new Gravity());
/* 26 */     Modules.get().add((Module)new NewChunks());
/* 27 */     Modules.get().add((Module)new AutoHighway());
/* 28 */     Modules.get().add((Module)new PacketFly());
/* 29 */     Modules.get().add((Module)new CAFix());
/* 30 */     Modules.get().add((Module)new CevBreaker());
/* 31 */     Modules.get().add((Module)new BoatGlitch());
/* 32 */     Modules.get().add((Module)new BoatPhase());
/* 33 */     Modules.get().add((Module)new AutoWither());
/* 34 */     Modules.get().add((Module)new SurroundPlus());
/* 35 */     Modules.get().add((Module)new AutoCityPlus());
/* 36 */     Modules.get().add((Module)new CityESPPlus());
/* 37 */     Modules.get().add((Module)new AntiCrystal());
/* 38 */     Modules.get().add((Module)new MonkeWalk());
/* 39 */     Modules.get().add((Module)new HoleESPPlus());
/* 40 */     Modules.get().add((Module)new AntiCevBreaker());
/* 41 */     Modules.get().add((Module)new AutoEz());
/* 42 */     Modules.get().add((Module)new ArmorMessage());
/* 43 */     Modules.get().add((Module)new MonkeCA());
/* 44 */     Modules.get().add((Module)new StrafePlus());
/* 45 */     Modules.get().add((Module)new AnchorPlus());
/* 46 */     Modules.get().add((Module)new ReloadSoundSystem());
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRegisterCategories() {
/* 51 */     Modules.registerCategory(BANANAPLUS);
/* 52 */     Modules.registerCategory(BANANAMINUS);
/*    */   }
/*    */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/addons/AddModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */