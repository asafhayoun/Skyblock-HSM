package hsm.hotshirtlessmen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record SbItem(String uuid) {
    public static final MapCodec<SbItem> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.STRING.fieldOf("uuid").forGetter(SbItem::uuid)
            ).apply(instance, SbItem::new)
    );

//    public static final Codec<SbItem> CODEC = MAP_CODEC.codec();
}
