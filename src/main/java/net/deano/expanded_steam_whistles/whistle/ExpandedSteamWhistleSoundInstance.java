package net.deano.expanded_steam_whistles.whistle;

import static net.deano.expanded_steam_whistles.init.AllSoundEvents.*;


import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

public class ExpandedSteamWhistleSoundInstance extends AbstractTickableSoundInstance {

    private boolean active;
    private int keepAlive;
    private ExpandedSteamWhistleBlock.ExpandedWhistleSize size;

    public ExpandedSteamWhistleSoundInstance(ExpandedSteamWhistleBlock.ExpandedWhistleSize size, BlockPos worldPosition) {
        super((size == ExpandedSteamWhistleBlock.ExpandedWhistleSize.TINY ? EXPANDED_WHISTLE_SUPERHIGH
                : size == ExpandedSteamWhistleBlock.ExpandedWhistleSize.SMALL ? EXPANDED_WHISTLE_HIGH
                : size == ExpandedSteamWhistleBlock.ExpandedWhistleSize.MEDIUM ? EXPANDED_WHISTLE_MEDIUM
                : size == ExpandedSteamWhistleBlock.ExpandedWhistleSize.LARGE ? EXPANDED_WHISTLE_LOW :
                EXPANDED_WHISTLE_DEEP)
                .get(), SoundSource.RECORDS, SoundInstance.createUnseededRandom());

        //TODO the .get could cause issues
        this.size = size;
        looping = true;
        active = true;
        volume = 0.05f;
        delay = 0;
        keepAlive();
        Vec3 v = Vec3.atCenterOf(worldPosition);
        x = v.x;
        y = v.y;
        z = v.z;
    }

    public ExpandedSteamWhistleBlock.ExpandedWhistleSize getOctave() {
        return size;
    }

    public void fadeOut() {
        this.active = false;
    }

    public void keepAlive() {
        keepAlive = 2;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    @Override
    public void tick() {
        if (active) {
            volume = Math.min(1, volume + .25f);
            keepAlive--;
            if (keepAlive == 0)
                fadeOut();
            return;

        }
        volume = Math.max(0, volume - .25f);
        if (volume == 0)
            stop();
    }
    @Override
    public Attenuation getAttenuation() {
        return Attenuation.LINEAR;
    }

}