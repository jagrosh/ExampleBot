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

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

/**
 *
 * @author John Grosh (jagrosh)
 */
public class ChooseCommand extends Command
{

    public ChooseCommand()
    {
        this.name = "choose";
        this.help = "make a decision";
        this.arguments = "<item> <item> ...";
        this.guildOnly = false;
    }
    
    @Override
    protected void execute(CommandEvent event)
    {
        // check that the user provided choices
        if(event.getArgs().isEmpty())
        {
            event.replyWarning("You didn't give me any choices!");
        }
        else
        {
            // split the choices on all whitespace
            String[] items = event.getArgs().split("\\s+");
            
            // if there is only one option, have a special reply
            if(items.length==1)
                event.replyWarning("You only gave me one option, `"+items[0]+"`");
            
            // otherwise, pick a random response
            else
            {
                event.replySuccess("I choose `"+items[(int)(Math.random()*items.length)]+"`");
            }
        }
    }
    
}
