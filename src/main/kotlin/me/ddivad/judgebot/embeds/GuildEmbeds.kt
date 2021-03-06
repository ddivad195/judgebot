package me.ddivad.judgebot.embeds

import com.gitlab.kordlib.core.entity.Guild
import com.gitlab.kordlib.rest.Image
import com.gitlab.kordlib.rest.builder.message.EmbedBuilder
import me.ddivad.judgebot.arguments.validConfigParameters
import me.ddivad.judgebot.dataclasses.GuildConfiguration
import me.ddivad.judgebot.util.timeToString
import me.jakejmattson.discordkt.api.extensions.toSnowflake
import java.awt.Color

suspend fun EmbedBuilder.createConfigEmbed(config: GuildConfiguration, guild: Guild) {
    title = "Configuration"
    color = Color.MAGENTA
    thumbnail {
        url = guild.getIconUrl(Image.Format.PNG) ?: ""
    }

    field {
        name = "**General:**"
        value = "Bot Prefix: ${config.prefix} \n" +
                "Admin Roles: ${config.adminRoles.map{ guild.getRoleOrNull(it.toSnowflake())?.mention }} \n" +
                "Staff Roles: ${config.staffRoles.map{ guild.getRoleOrNull(it.toSnowflake())?.mention }} \n" +
                "Moderator Roles: ${config.moderatorRoles.map{ guild.getRoleOrNull(it.toSnowflake())?.mention }} \n" +
                "Mute Role: ${guild.getRoleOrNull(config.mutedRole.toSnowflake())?.mention} \n"
    }

    field {
        name = "**Infractions:**"
        value = "Point Ceiling: ${config.infractionConfiguration.pointCeiling} \n" +
                "Strike points: ${config.infractionConfiguration.strikePoints} \n" +
                "Warn Points: ${config.infractionConfiguration.warnPoints} \n" +
                "Point Decay / Week: ${config.infractionConfiguration.pointDecayPerWeek}"
    }

    field {
        name = "**Reactions**"
        value = "Enabled: ${config.reactions.enabled} \n" +
                "Gag: ${config.reactions.gagReaction} \n" +
                "History: ${config.reactions.historyReaction} \n" +
                "Delete Message: ${config.reactions.deleteMessageReaction} \n" +
                "Flag Message: ${config.reactions.flagMessageReaction}"
    }

    field {
        name = "**Punishments:**"
        config.punishments.forEach {
            value += "Punishment Type: ${it.punishment} \n" +
                    "Point Threshold: ${it.points} \n" +
                    "Punishment Duration: ${if (it.duration !== null) timeToString(it.duration!!) else "indefinite"}" + "\n\n"
        }
    }

    field {
        name = "**Logging:**"
        value = "Logging Channel: ${guild.getChannelOrNull(config.loggingConfiguration.loggingChannel.toSnowflake())?.mention} \n" +
                "Alert Channel: ${guild.getChannelOrNull(config.loggingConfiguration.alertChannel.toSnowflake())?.mention}"
    }
    footer {
        icon = guild.getIconUrl(Image.Format.PNG) ?: ""
        text = guild.name
    }
}

fun EmbedBuilder.createConfigOptionsEmbed(config: GuildConfiguration, guild: Guild) {
    title = "Available Configuration Options"
    color = Color.MAGENTA
    field {
        name = "Usage: `${config.prefix}configuration <option>`"
        value = "```css\n${validConfigParameters.joinToString("\n")}\n```"
    }
    footer {
        icon = guild.getIconUrl(Image.Format.PNG) ?: ""
        text = guild.name
    }
}