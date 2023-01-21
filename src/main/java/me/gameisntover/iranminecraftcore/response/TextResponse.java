package me.gameisntover.iranminecraftcore.response;

import com.cryptomorin.xseries.XSound;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;

import java.util.Optional;

public class TextResponse extends APIResponse<String> {
    protected Optional<Sound> sound;
    protected Optional<ClickEvent> clickEvent;
    protected Optional<HoverEvent> hoverEvent = null;

    public TextResponse(String text, XSound sound, String hoverText, ClickEvent clickEvent) {
        super(text, true);
        this.sound = Optional.of(sound.parseSound());
        this.clickEvent = Optional.of(clickEvent);
        if (hoverText != null)
            this.hoverEvent = Optional.of(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(hoverText)));
    }

    public Optional<Sound> getSound() {
        return sound;
    }

    public Optional<ClickEvent> getClickEvent() {
        return clickEvent;
    }

    public Optional<HoverEvent> getHoverEvent() {
        return hoverEvent;
    }
}
