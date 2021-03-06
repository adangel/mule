/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.CharUtils;

/**
 * <code>StringUtils</code> contains useful methods for manipulating Strings.
 */
// @ThreadSafe
public class StringUtils extends org.apache.commons.lang.StringUtils
{
    /**
     * Like {@link org.mule.util.StringUtils#split(String, String)}, but additionally
     * trims whitespace from the result tokens.
     */
    public static String[] splitAndTrim(String string, String delim)
    {
        if (string == null)
        {
            return null;
        }

        if (isEmpty(string))
        {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }

        String[] rawTokens = split(string, delim);
        List<String> tokens = new ArrayList<String>();
        if (rawTokens != null)
        {
            for (int i = 0; i < rawTokens.length; i++)
            {
                String token = trim(rawTokens[i]);
                if (isNotEmpty(token))
                {
                    tokens.add(token);
                }
            }
        }
        return (String[]) ArrayUtils.toArrayOfComponentType(tokens.toArray(), String.class);
    }

    /**
     * Convert a hexadecimal string into its byte representation.
     * 
     * @param hex The hexadecimal string.
     * @return The converted bytes or <code>null</code> if the hex String is null.
     */
    public static byte[] hexStringToByteArray(String hex)
    {
        if (hex == null)
        {
            return null;
        }

        int stringLength = hex.length();
        if (stringLength % 2 != 0)
        {
            throw new IllegalArgumentException("Hex String must have even number of characters!");
        }

        byte[] result = new byte[stringLength / 2];

        int j = 0;
        for (int i = 0; i < result.length; i++)
        {
            char hi = Character.toLowerCase(hex.charAt(j++));
            char lo = Character.toLowerCase(hex.charAt(j++));
            result[i] = (byte) ((Character.digit(hi, 16) << 4) | Character.digit(lo, 16));
        }

        return result;
    }

    /**
     * Like {@link #repeat(String, int)} but with a single character as argument.
     */
    public static String repeat(char c, int len)
    {
        return repeat(CharUtils.toString(c), len);
    }

    /**
     * @see #toHexString(byte[])
     */
    public static String toHexString(byte[] bytes)
    {
        return StringUtils.toHexString(bytes, false);
    }

    /**
     * Convert a byte array to a hexadecimal string.
     * 
     * @param bytes The bytes to format.
     * @param uppercase When <code>true</code> creates uppercase hex characters
     *            instead of lowercase (the default).
     * @return A hexadecimal representation of the specified bytes.
     */
    public static String toHexString(byte[] bytes, boolean uppercase)
    {
        if (bytes == null)
        {
            return null;
        }

        int numBytes = bytes.length;
        StringBuffer str = new StringBuffer(numBytes * 2);

        String table = (uppercase ? HEX_CHARACTERS_UC : HEX_CHARACTERS);

        for (int i = 0; i < numBytes; i++)
        {
            str.append(table.charAt(bytes[i] >>> 4 & 0x0f));
            str.append(table.charAt(bytes[i] & 0x0f));
        }

        return str.toString();
    }

    /**
     * Matches the given value to the given pattern. Then returns the group at
     * matchIndex.
     * 
     * @param pattern the pattern to use as regexp
     * @param value the value to evaluate
     * @param matchIndex the group index to be returned
     * @return the value of the group at the given index
     * @throws IllegalArgumentException if no match found at the given index. Also if
     *             value or pattern are null
     */
    public static String match(Pattern pattern, String value, int matchIndex) throws IllegalArgumentException
    {
        if (value == null || pattern == null)
        {
            throw new IllegalArgumentException("pattern and value cannot be null");
        }
        Matcher matcher = pattern.matcher(value);
        if (matcher.find() && (matcher.groupCount() >= matchIndex))
        {
            return matcher.group(matchIndex);
        }

        throw new IllegalArgumentException(String.format(
            "String %s did not produced any match for pattern %s at index %d", value, pattern.pattern(),
            matchIndex));
    }

    // lookup tables needed for toHexString(byte[], boolean)
    private static final String HEX_CHARACTERS = "0123456789abcdef";
    private static final String HEX_CHARACTERS_UC = HEX_CHARACTERS.toUpperCase();
}
