package graphbot.telegram

import graphbot.core.objects.BotContent
import graphbot.core.objects.BotUpdate
import graphbot.core.objects.BotUser
import graphbot.simple.contents.LocationContent
import graphbot.simple.contents.TextContent
import org.telegram.telegrambots.meta.api.objects.Update

class TelegramUpdate<T: BotUser>(from: T, content: Iterable<BotContent>): BotUpdate<T>(from,content) {


}