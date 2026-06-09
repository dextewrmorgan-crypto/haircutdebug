package com.nnpg.glazed.modules.esp;

import com.nnpg.glazed.GlazedAddon;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.IntSetting.Builder;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnchantingTableBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.WorldChunk;

public class BetterStorageESP extends Module {
   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
   private final SettingGroup sgColors = this.settings.createGroup("Colors");
   private final Setting<Integer> alpha = this.sgGeneral
      .add(
         ((Builder)((Builder)((Builder)new Builder().name("alpha")).description("The alpha value for all ESP boxes.")).defaultValue(125))
            .min(0)
            .max(255)
            .sliderMax(255)
            .build()
      );
   private final Setting<Boolean> tracers = this.sgGeneral
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                  .name("tracers"))
               .defaultValue(false))
            .build()
      );
   private final Setting<ShapeMode> shapeMode = this.sgGeneral
      .add(
         ((meteordevelopment.meteorclient.settings.EnumSetting.Builder)((meteordevelopment.meteorclient.settings.EnumSetting.Builder)new meteordevelopment.meteorclient.settings.EnumSetting.Builder()
                  .name("shape-mode"))
               .defaultValue(ShapeMode.Both))
            .build()
      );
   private final Setting<Boolean> chests = this.sgGeneral
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                  .name("chests"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> enderChests = this.sgGeneral
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                  .name("ender-chests"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> shulkerBoxes = this.sgGeneral
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                  .name("shulker-boxes"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> spawners = this.sgGeneral
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                  .name("spawners"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> furnaces = this.sgGeneral
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                  .name("furnaces"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> barrels = this.sgGeneral
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                  .name("barrels"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> enchantingTables = this.sgGeneral
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                  .name("enchanting-tables"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> pistons = this.sgGeneral
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                  .name("pistons"))
               .defaultValue(true))
            .build()
      );
   private final Setting<Boolean> hoppers = this.sgGeneral
      .add(
         ((meteordevelopment.meteorclient.settings.BoolSetting.Builder)((meteordevelopment.meteorclient.settings.BoolSetting.Builder)new meteordevelopment.meteorclient.settings.BoolSetting.Builder()
                  .name("hoppers"))
               .defaultValue(true))
            .build()
      );
   private final Setting<SettingColor> chestColor = this.sgColors
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder().name("chest-color"))
            .defaultValue(new SettingColor(156, 91, 0))
            .build()
      );
   private final Setting<SettingColor> enderChestColor = this.sgColors
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
               .name("ender-chest-color"))
            .defaultValue(new SettingColor(117, 0, 255))
            .build()
      );
   private final Setting<SettingColor> shulkerBoxColor = this.sgColors
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
               .name("shulker-box-color"))
            .defaultValue(new SettingColor(134, 0, 158))
            .build()
      );
   private final Setting<SettingColor> spawnerColor = this.sgColors
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
               .name("spawner-color"))
            .defaultValue(new SettingColor(138, 126, 166))
            .build()
      );
   private final Setting<SettingColor> furnaceColor = this.sgColors
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
               .name("furnace-color"))
            .defaultValue(new SettingColor(100, 100, 100))
            .build()
      );
   private final Setting<SettingColor> barrelColor = this.sgColors
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder().name("barrel-color"))
            .defaultValue(new SettingColor(100, 75, 50))
            .build()
      );
   private final Setting<SettingColor> enchantColor = this.sgColors
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder()
               .name("enchanting-table-color"))
            .defaultValue(new SettingColor(200, 0, 0))
            .build()
      );
   private final Setting<SettingColor> pistonColor = this.sgColors
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder().name("piston-color"))
            .defaultValue(new SettingColor(0, 255, 0))
            .build()
      );
   private final Setting<SettingColor> hopperColor = this.sgColors
      .add(
         ((meteordevelopment.meteorclient.settings.ColorSetting.Builder)new meteordevelopment.meteorclient.settings.ColorSetting.Builder().name("hopper-color"))
            .defaultValue(new SettingColor(80, 80, 80))
            .build()
      );

   public BetterStorageESP() {
      super(ZincAddon.esp, "better-storage-esp", "Advanced storage ESP with internal boxes and customizable filters.");
   }

   @EventHandler
   private void onRender(Render3DEvent event) {
      if (this.mc.world != null && this.mc.player != null) {
         Vec3d playerPos = this.mc.player.method_30950(event.tickDelta);
         int chunkX = this.mc.player.method_31476().x;
         int chunkZ = this.mc.player.method_31476().z;
         int viewDistChunks = (Integer)this.mc.options.getViewDistance().getValue();
         double s = 0.15;

         for (int x = chunkX - viewDistChunks; x <= chunkX + viewDistChunks; x++) {
            for (int z = chunkZ - viewDistChunks; z <= chunkZ + viewDistChunks; z++) {
               WorldChunk chunk = this.mc.world.method_8497(x, z);
               if (chunk != null) {
                  for (BlockPos pos : chunk.method_12021()) {
                     BlockEntity blockEntity = chunk.method_8321(pos);
                     if (blockEntity != null) {
                        SettingColor settingColor = this.getBlockEntityColor(blockEntity);
                        if (settingColor != null) {
                           Color color = new Color(settingColor.r, settingColor.g, settingColor.b, (Integer)this.alpha.get());
                           event.renderer
                              .box(
                                 pos.method_10263() + s,
                                 pos.method_10264() + s,
                                 pos.method_10260() + s,
                                 pos.method_10263() + 1 - s,
                                 pos.method_10264() + 1 - s,
                                 pos.method_10260() + 1 - s,
                                 color,
                                 color,
                                 (ShapeMode)this.shapeMode.get(),
                                 0
                              );
                           if ((Boolean)this.tracers.get()) {
                              Vec3d blockCenter = Vec3d.ofCenter(pos);
                              Vec3d startPos;
                              if (this.mc.options.getPerspective().isFirstPerson()) {
                                 Vec3d lookDirection = this.mc.player.method_5720();
                                 startPos = new Vec3d(
                                    playerPos.x + lookDirection.x * 0.5,
                                    playerPos.y + this.mc.player.method_18381(this.mc.player.method_18376()) + lookDirection.y * 0.5,
                                    playerPos.z + lookDirection.z * 0.5
                                 );
                              } else {
                                 startPos = new Vec3d(playerPos.x, playerPos.y + this.mc.player.method_18381(this.mc.player.method_18376()), playerPos.z);
                              }

                              event.renderer.line(startPos.x, startPos.y, startPos.z, blockCenter.x, blockCenter.y, blockCenter.z, color);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private SettingColor getBlockEntityColor(BlockEntity blockEntity) {
      if (blockEntity instanceof ChestBlockEntity && (Boolean)this.chests.get()) {
         return (SettingColor)this.chestColor.get();
      } else if (blockEntity instanceof EnderChestBlockEntity && (Boolean)this.enderChests.get()) {
         return (SettingColor)this.enderChestColor.get();
      } else if (blockEntity instanceof ShulkerBoxBlockEntity && (Boolean)this.shulkerBoxes.get()) {
         return (SettingColor)this.shulkerBoxColor.get();
      } else if (blockEntity instanceof MobSpawnerBlockEntity && (Boolean)this.spawners.get()) {
         return (SettingColor)this.spawnerColor.get();
      } else if (blockEntity instanceof AbstractFurnaceBlockEntity && (Boolean)this.furnaces.get()) {
         return (SettingColor)this.furnaceColor.get();
      } else if (blockEntity instanceof BarrelBlockEntity && (Boolean)this.barrels.get()) {
         return (SettingColor)this.barrelColor.get();
      } else if (blockEntity instanceof EnchantingTableBlockEntity && (Boolean)this.enchantingTables.get()) {
         return (SettingColor)this.enchantColor.get();
      } else if (blockEntity instanceof PistonBlockEntity && (Boolean)this.pistons.get()) {
         return (SettingColor)this.pistonColor.get();
      } else {
         return blockEntity instanceof HopperBlockEntity && this.hoppers.get() ? (SettingColor)this.hopperColor.get() : null;
      }
   }
}
