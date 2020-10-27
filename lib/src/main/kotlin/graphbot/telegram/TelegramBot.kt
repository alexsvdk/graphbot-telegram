package graphbot.telegram

import graphbot.core.bot.Bot
import graphbot.core.objects.BotContent
import graphbot.core.objects.BotNode
import graphbot.core.storage.Storage
import graphbot.core.storage.TempStorage
import graphbot.simple.contents.*
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.GetFile
import org.telegram.telegrambots.meta.api.objects.Update

class TelegramBot<T: TelegramUser>(
        mainNode: BotNode<T,TelegramUpdate<T>>,
        botToken: String,
        storage: Storage<T> = TempStorage(),
        botName: String = "Bot"
): Bot<T, TelegramUpdate<T>>(mainNode,storage) {

    init {
        ApiContextInitializer.init()
    }

    private val bot = object: TelegramLongPollingBot() {

        override fun getBotToken(): String = botToken
        override fun getBotUsername(): String = botName

        override fun onUpdateReceived(update: Update?) {
            if (update==null) return
            val content = mutableListOf<BotContent>()
            if (update.hasMessage()) {
                if (update.message.hasText())
                    content.add(TextContent(update.message.text))
                if (update.message.hasLocation())
                    content.add(LocationContent(
                            update.message.location.latitude.toDouble(),
                            update.message.location.longitude.toDouble()
                    ))
                if (update.message.hasPhoto()) {
                    val id = update.message.photo.lastOrNull()?.fileUniqueId
                    val url = this.execute(GetFile().also { it.fileId=id }).getFileUrl(botToken)
                    content.add(ImageContent(url))
                }
                if (update.message.hasVoice()){
                    val id = update.message.voice.fileUniqueId
                    val url = this.execute(GetFile().also { it.fileId=id }).getFileUrl(botToken)
                    content.add(VoiceContent(url))
                }
                if (update.message.hasVideo()){
                    val id = update.message.video.fileUniqueId
                    val url = this.execute(GetFile().also { it.fileId=id }).getFileUrl(botToken)
                    content.add(VideoContent(url))
                }
                if (update.message.hasDocument()){
                    val id = update.message.document.fileId
                    val url = this.execute(GetFile().also { it.fileId=id }).getFileUrl(botToken)
                    content.add(FileContent(url))
                }
            }
        }


    }

    override fun sendContent(to: T, content: BotContent) {
        TODO("Not yet implemented")
    }


}