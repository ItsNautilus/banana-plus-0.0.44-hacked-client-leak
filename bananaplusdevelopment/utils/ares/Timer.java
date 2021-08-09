/*    */ package bananaplusdevelopment.utils.ares;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Timer
/*    */ {
/*  9 */   private long nanoTime = -1L;
/*    */   
/*    */   public void reset() {
/* 12 */     this.nanoTime = System.nanoTime();
/*    */   }
/*    */   
/*    */   public void setTicks(long ticks) {
/* 16 */     this.nanoTime = System.nanoTime() - convertTicksToNano(ticks);
/*    */   } public void setNano(long time) {
/* 18 */     this.nanoTime = System.nanoTime() - time;
/*    */   } public void setMicro(long time) {
/* 20 */     this.nanoTime = System.nanoTime() - convertMicroToNano(time);
/*    */   } public void setMillis(long time) {
/* 22 */     this.nanoTime = System.nanoTime() - convertMillisToNano(time);
/*    */   } public void setSec(long time) {
/* 24 */     this.nanoTime = System.nanoTime() - convertSecToNano(time);
/*    */   }
/*    */   public long getTicks() {
/* 27 */     return convertNanoToTicks(this.nanoTime);
/*    */   } public long getNano() {
/* 29 */     return this.nanoTime;
/*    */   } public long getMicro() {
/* 31 */     return convertNanoToMicro(this.nanoTime);
/*    */   } public long getMillis() {
/* 33 */     return convertNanoToMillis(this.nanoTime);
/*    */   } public long getSec() {
/* 35 */     return convertNanoToSec(this.nanoTime);
/*    */   }
/*    */   public boolean passedTicks(long ticks) {
/* 38 */     return passedNano(convertTicksToNano(ticks));
/*    */   } public boolean passedNano(long time) {
/* 40 */     return (System.nanoTime() - this.nanoTime >= time);
/*    */   } public boolean passedMicro(long time) {
/* 42 */     return passedNano(convertMicroToNano(time));
/*    */   } public boolean passedMillis(long time) {
/* 44 */     return passedNano(convertMillisToNano(time));
/*    */   } public boolean passedSec(long time) {
/* 46 */     return passedNano(convertSecToNano(time));
/*    */   }
/*    */   
/* 49 */   public long convertMillisToTicks(long time) { return time / 50L; }
/* 50 */   public long convertTicksToMillis(long ticks) { return ticks * 50L; }
/* 51 */   public long convertNanoToTicks(long time) { return convertMillisToTicks(convertNanoToMillis(time)); } public long convertTicksToNano(long ticks) {
/* 52 */     return convertMillisToNano(convertTicksToMillis(ticks));
/*    */   }
/*    */   
/* 55 */   public long convertSecToMillis(long time) { return time * 1000L; }
/* 56 */   public long convertSecToMicro(long time) { return convertMillisToMicro(convertSecToMillis(time)); } public long convertSecToNano(long time) {
/* 57 */     return convertMicroToNano(convertMillisToMicro(convertSecToMillis(time)));
/*    */   }
/* 59 */   public long convertMillisToMicro(long time) { return time * 1000L; } public long convertMillisToNano(long time) {
/* 60 */     return convertMicroToNano(convertMillisToMicro(time));
/*    */   } public long convertMicroToNano(long time) {
/* 62 */     return time * 1000L;
/*    */   }
/*    */   
/* 65 */   public long convertNanoToMicro(long time) { return time / 1000L; }
/* 66 */   public long convertNanoToMillis(long time) { return convertMicroToMillis(convertNanoToMicro(time)); } public long convertNanoToSec(long time) {
/* 67 */     return convertMillisToSec(convertMicroToMillis(convertNanoToMicro(time)));
/*    */   }
/* 69 */   public long convertMicroToMillis(long time) { return time / 1000L; } public long convertMicroToSec(long time) {
/* 70 */     return convertMillisToSec(convertMicroToMillis(time));
/*    */   } public long convertMillisToSec(long time) {
/* 72 */     return time / 1000L;
/*    */   }
/*    */ }


/* Location:              /home/ezzer29/Documents/MCClients/banana-plus-0.0.44.jar!/bananaplusdevelopment/utils/ares/Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */