package ninja.bytecode.shuriken.cloud.web;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({TYPE, FIELD})
public @interface ParcelDescription
{
	public String value();
}