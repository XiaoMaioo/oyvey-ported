package today.slopta.dev.features.commands.impl;

import today.slopta.dev.Slopta;
import today.slopta.dev.features.commands.Command;
import net.minecraft.util.Formatting;

public class FriendCommand
        extends Command {
    public FriendCommand() {
        super("friend", new String[]{"<add/del/name/clear>", "<name>"});
    }

    @Override
    public void execute(String[] commands) {
        if (commands.length == 1) {
            if (Slopta.friendManager.getFriends().isEmpty()) {
                FriendCommand.sendMessage("Friend list empty D:.");
            } else {
                StringBuilder f = new StringBuilder("Friends: ");
                for (String friend : Slopta.friendManager.getFriends()) {
                    try {
                        f.append(friend).append(", ");
                    } catch (Exception exception) {
                    }
                }
                FriendCommand.sendMessage(f.toString());
            }
            return;
        }
        if (commands.length == 2) {
            if (commands[0].equals("reset")) {
                Slopta.friendManager.getFriends().clear();
                FriendCommand.sendMessage("Friends got reset.");
                return;
            }
            FriendCommand.sendMessage(commands[0] + (Slopta.friendManager.isFriend(commands[0]) ? " is friended." : " isn't friended."));
            return;
        }
        if (commands.length >= 2) {
            switch (commands[0]) {
                case "add" -> {
                    Slopta.friendManager.addFriend(commands[1]);
                    FriendCommand.sendMessage(Formatting.GREEN + commands[1] + " has been friended");
                    return;
                }
                case "del", "remove" -> {
                    Slopta.friendManager.removeFriend(commands[1]);
                    FriendCommand.sendMessage(Formatting.RED + commands[1] + " has been unfriended");
                    return;
                }
            }
            FriendCommand.sendMessage("Unknown Command, try friend add/del (name)");
        }
    }
}