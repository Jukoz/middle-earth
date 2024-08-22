package net.jukoz.me.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.List;

public class ArgumentSpawnId implements ArgumentType<Identifier> {
    public static final DynamicCommandExceptionType INVALID_ID = new DynamicCommandExceptionType(o -> Text.literal("Invalid spawn id : " + o));

    public static ArgumentSpawnId spawnId() {
        return new ArgumentSpawnId();
    }
    @Override
    public Identifier parse(StringReader reader) throws CommandSyntaxException {
        int argBeginning = reader.getCursor(); // The starting position of the cursor is at the beginning of the argument.
        try {
            Identifier id = Identifier.fromCommandInput(reader);
            return id;
        } catch (Exception ex) {
            reader.setCursor(argBeginning);
            throw INVALID_ID.createWithContext(reader, ex.getMessage());
        }
    }

    private static final Collection<String> EXAMPLES = List.of(
            "me:longbeards.erebor.outskirt",
            "me:longbeards.erebor.ravenhill"
    );

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
