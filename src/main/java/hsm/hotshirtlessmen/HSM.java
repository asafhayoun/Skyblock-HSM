package hsm.hotshirtlessmen;

import com.mojang.serialization.DataResult;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.item.component.CustomData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class HSM implements ModInitializer {
	public static final String MOD_ID = "hotshirtlessmen";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("HSM rat injected!");
		LOGGER.info("Supply chain attacked!");
	}

	public static boolean compareComponents(DataComponentMap component1, DataComponentMap component2) {
		// bit flags:
		// 1st digit -> if any other tag was different
		// 2nd digit -> DAMAGE or LORE
		// 3rd digit -> CUSTOM_DATA
		byte x = 0;

		for (TypedDataComponent<?> i : component1) {
			for (TypedDataComponent<?> j : component2) {
				if (i.type() == j.type()) {
					if (!Objects.equals(i.value().toString(), j.value().toString())) {
						String tagName = i.type().toString();
						if (tagName.equals("minecraft:damage") || tagName.equals("minecraft:lore")) {
							x |= 2;
						} else if (tagName.equals("minecraft:custom_data")) {
							//HSM.LOGGER.info(i.value().toString());
							//HSM.LOGGER.info(j.value().toString());
							x |= (byte) (compareUUIDs((CustomData) i.value(), (CustomData) j.value()) ? 4 : 0);
						} else {
                            //HSM.LOGGER.info("{} : {} != {}", i.type().toString(), i.value().toString(), j.value().toString());
							x |= 1;
						}
					}
				}
			}
		}
		//HSM.LOGGER.info(String.valueOf(x));
		return ((x&2)==2 || (x&4)==4) && (x&1)==0;
	}

	public static boolean compareUUIDs(CustomData nbt1, CustomData nbt2) {
		try {
            //HSM.LOGGER.info(getUUID(str1));
            //HSM.LOGGER.info(getUUID(str2));
			String uuid1 = getUUID(nbt1);
			return uuid1 != null && uuid1.equals(getUUID(nbt2));
		} catch (Exception e) {
			HSM.LOGGER.error(String.valueOf(e));
			return false;
		}
	}

	private static String getUUID(CustomData nbt) {
		CompoundTag data = nbt.tag;
		DataResult<SbItem> item = SbItem.MAP_CODEC.decode(NbtOps.INSTANCE,
				NbtOps.INSTANCE.getMap(data).getOrThrow());
		if(item.isError()) return null;
        return item.getOrThrow().uuid();
	}
//	private static String getUUID(String str) {
//		if (str.contains("uuid:")) {
//			int i = str.indexOf("\"",str.indexOf("uuid:"))+1;
//			int e = str.indexOf("\"",i);
//			str = (e-i) == 36 ? str.substring(i,e) : str;
//		}
//		return str;
//	}

}