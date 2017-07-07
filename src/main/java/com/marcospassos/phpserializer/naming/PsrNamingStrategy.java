package com.marcospassos.phpserializer.naming;

import java.lang.reflect.Field;
import com.marcospassos.phpserializer.NamingStrategy;

/**
 * Translates names of classes and methods from Java to PHP PSR-1 standard.
 *
 * @author Marcos Passos
 * @see <a href="http://www.php-fig.org/psr/psr-1/">PHP PSR-1</a>
 * @since 1.0
 */
public class PsrNamingStrategy implements NamingStrategy
{
    @Override
    public String getClassName(Class type)
    {
        String className = type.getCanonicalName();
        StringBuilder adapted = new StringBuilder();

        for (String part : className.split("\\.")) {
            adapted.append("\\");

            adapted.append(part.substring(0, 1).toUpperCase());
            adapted.append(part.substring(1));
        }

        return adapted.substring(1);
    }

    @Override
    public String getFieldName(Field field)
    {
        return field.getName();
    }
}
