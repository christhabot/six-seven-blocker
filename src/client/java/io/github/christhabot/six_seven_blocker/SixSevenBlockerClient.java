package io.github.christhabot.six_seven_blocker;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SixSevenBlockerClient implements ClientModInitializer
{

	private static final Pattern BAD_NUMBER_PATTERN = Pattern.compile(
			"(?i)(?>6(?:[\\s\\S]{0,30}?)7|[s5$]++(?:[\\W_0-9]*+[i1!|l]++)[\\W_0-9]*+[x×*]++[\\W_0-9]*+[s5$]++[\\W_0-9]*+[e3€]++[\\W_0-9]*+[v\\\\/]+[\\W_0-9]*+[e3€]++[\\W_0-9]*+n++)",
			Pattern.CASE_INSENSITIVE
	);


	@Override
	public void onInitializeClient() {
		ClientReceiveMessageEvents.ALLOW_CHAT.register((message, signedMessage, sender, params, receptionTimestamp) -> {
			String raw = message.getString();
			Matcher matcher = BAD_NUMBER_PATTERN.matcher(raw);

			if (matcher.find()) {
				String replaced = matcher.replaceAll("bad number");

				MutableText newText = Text.literal(replaced);

				MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(newText);

				return false;
			}

			return true;
		});
	}
}
