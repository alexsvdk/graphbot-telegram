package graphbot.telegram

import graphbot.core.objects.BotUser

open class TelegramUser(
        chatID: String,

): BotUser(chatID)