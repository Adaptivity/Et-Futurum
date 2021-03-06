package ganymedes01.etfuturum;

import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.proxy.CommonProxy;
import ganymedes01.etfuturum.entities.ModEntityList;
import ganymedes01.etfuturum.items.ItemEntityEgg;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.recipes.ModRecipes;
import ganymedes01.etfuturum.world.OceanMonument;
import ganymedes01.etfuturum.world.SurfaceWorldGen;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUI_FACTORY_CLASS)
public class EtFuturum {

	@Instance(Reference.MOD_ID)
	public static EtFuturum instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static CreativeTabs creativeTab = new CreativeTabs(Reference.MOD_ID) {
		@Override
		public Item getTabIconItem() {
			return enablePrismarine ? ModItems.prismarine_shard : Items.skull;
		}
	};

	public static boolean enableStones = true;
	public static boolean enableIronTrapdoor = true;
	public static boolean enableMutton = true;
	public static boolean enableSponge = true;
	public static boolean enablePrismarine = true;
	public static boolean enableDoors = true;
	public static boolean enableInvertedDaylightSensor = true;
	public static boolean enableCoarseDirt = true;
	public static boolean enableRedSandstone = true;
	public static boolean enableEnchants = true;
	public static boolean enableFences = true;
	public static boolean enableSilkTouchingMushrooms = true;
	public static boolean enableBanners = true;
	public static boolean enableSlimeBlock = true;
	public static boolean enableArmourStand = true;
	public static boolean enableRabbit = false;
	public static boolean enableOldGravel = true;
	public static boolean enableRecipeForPrismarine = true;
	public static boolean enableEndermite = true;

	public static boolean enableBurnableBlocks = true;
	public static boolean enableFancySkulls = true;
	public static boolean enableSkullDrop = true;

	public static int maxStonesPerCluster = 33;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.INSTANCE.init(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MOD_ID + ".cfg"));

		GameRegistry.registerWorldGenerator(new SurfaceWorldGen(), 0);

		ModBlocks.init();
		ModItems.init();

		OceanMonument.makeMap();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);

		ModRecipes.init();

		proxy.registerEvents();
		proxy.registerEntities();
		proxy.registerRenderers();

		if (ModEntityList.hasEntitiesWithEggs()) {
			Item entity_egg = new ItemEntityEgg();
			GameRegistry.registerItem(entity_egg, "entity_egg");
			OreDictionary.registerOre("mobEgg", entity_egg);
		}
	}
}