package ninja.bytecode.shuriken.bukkit.api.sql;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface UniversalType
{
	Class<?> value();
}
