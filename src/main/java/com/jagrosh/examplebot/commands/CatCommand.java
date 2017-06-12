/*
 * Copyright 2017 John Grosh (jagrosh).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jagrosh.examplebot.commands;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.awt.Color;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;

/**
 *
 * @author John Grosh (john.a.grosh@gmail.com)
 */
public class CatCommand extends Command
{
    public CatCommand()
    {
        this.name = "cat";
        this.help = "shows a random cat";
        this.category = new Category("Fun");
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event)
    {
        // use Unirest to poll an API
        Unirest.get("http://random.cat/meow").asJsonAsync(new Callback<JsonNode>(){
            
            // The API call was successful
            @Override
            public void completed(HttpResponse<JsonNode> hr)
            {
                event.reply(new EmbedBuilder()
                        .setColor(event.isFromType(ChannelType.TEXT) ? event.getSelfMember().getColor() : Color.GREEN)
                        .setImage(hr.getBody().getObject().getString("file"))
                        .build());
            }

            // The API call failed
            @Override
            public void failed(UnirestException ue)
            {
                event.reactError();
            }

            // The API call was cancelled (this should never happen)
            @Override
            public void cancelled()
            {
                event.reactError();
            }
        });
    }
}
