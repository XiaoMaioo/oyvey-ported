package today.slopta.dev.features.commands.impl;

import today.slopta.dev.Slopta;
import today.slopta.dev.features.commands.Command;
import net.minecraft.util.Formatting;

public class HelpCommand
        extends Command {
    public HelpCommand() {
        super("help");
    }

    @Override
    public void execute(String[] commands) {
        HelpCommand.sendMessage("Commands: ");
        for (Command command : Slopta.commandManager.getCommands()) {
            StringBuilder builder = new StringBuilder(Formatting.GRAY.toString());
            builder.append(Slopta.commandManager.getPrefix());
            builder.append(command.getName());
            builder.append(" ");
            for (String cmd : command.getCommands()) {
                builder.append(cmd);
                builder.append(" ");
            }
            HelpCommand.sendMessage(builder.toString());
        }
    }
}