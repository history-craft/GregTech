package gregtech;

import codechicken.lib.CodeChickenLib;
import gregtech.api.GTValues;
import gregtech.api.capability.SimpleCapabilityManager;
import gregtech.api.items.MetaItemUIFactory;
import gregtech.api.metatileentity.MetaTileEntityUIFactory;
import gregtech.api.model.ResourcePackHook;
import gregtech.api.net.NetworkHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GTLog;
import gregtech.api.worldgen.config.WorldGenRegistry;
import gregtech.common.CommonProxy;
import gregtech.common.ConfigHolder;
import gregtech.common.MetaFluids;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.modelfactories.BlockCompressedFactory;
import gregtech.common.blocks.modelfactories.BlockFrameFactory;
import gregtech.common.blocks.modelfactories.BlockOreFactory;
import gregtech.common.command.GregTechCommand;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.common.multipart.GTMultipartFactory;
import gregtech.common.worldgen.WorldGenRubberTree;
import gregtech.loaders.postload.DungeonLootLoader;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = GTValues.MODID,
     name = "GregTech",
     acceptedMinecraftVersions = "[1.12,1.13)",
     dependencies = CodeChickenLib.MOD_VERSION_DEP + "after:forestry;after:forgemultipartcbe;after:jei@[4.8.6,);")
public class GregTechMod {

    static {
        FluidRegistry.enableUniversalBucket();
        if(FMLCommonHandler.instance().getSide().isClient()) {
            ResourcePackHook.init();
            BlockOreFactory.init();
            BlockCompressedFactory.init();
            BlockFrameFactory.init();
        }
    }

    @Mod.Instance(GTValues.MODID)
    public static GregTechMod instance;

    @SidedProxy(modId = GTValues.MODID, clientSide = "gregtech.common.ClientProxy", serverSide = "gregtech.common.CommonProxy")
    public static CommonProxy gregtechproxy;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        GTLog.init(event.getModLog());

        NetworkHandler.init();
        MetaTileEntityUIFactory.INSTANCE.init();
        MetaItemUIFactory.INSTANCE.init();
        SimpleCapabilityManager.init();
        OreDictUnifier.init();

        //freeze material registry before processing items, blocks and fluids
        Material.freezeRegistry();

        MetaBlocks.init();
        MetaItems.init();
        MetaFluids.init();
        MetaTileEntities.init();

        gregtechproxy.onPreLoad();
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        gregtechproxy.onLoad();

        if (RecipeMap.foundInvalidRecipe) {
            GTLog.logger.fatal("Seems like invalid recipe was found. Loading will not continue.");
            throw new LoaderException("Found at least one invalid recipe. Please read the log above for more details.");
        }

        if(Loader.isModLoaded(GTValues.MODID_FMP)) {
            GTLog.logger.info("ForgeMultiPart found. Enabling integration...");
            registerForgeMultipartCompat();
        }

        WorldGenRegistry.INSTANCE.initializeRegistry();
        if(!ConfigHolder.disableRubberTreeGeneration) {
            GameRegistry.registerWorldGenerator(new WorldGenRubberTree(), 10000);
        }

        DungeonLootLoader.init();
    }

    private void registerForgeMultipartCompat() {
        GTMultipartFactory.registerFactory();
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        gregtechproxy.onPostLoad();
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new GregTechCommand());
    }
}