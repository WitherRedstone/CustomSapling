package com.chinaex123.custom_sapling.util;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

public final class ColorUtils {

    private ColorUtils() {}

    public static int parseColor(String input) {
        if (input == null) return -1;

        String s = input.trim();
        if (s.isEmpty()) return -1;

        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException ignored) {
        }

        String hex = s;
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        } else if (hex.startsWith("0x") || hex.startsWith("0X")) {
            hex = hex.substring(2);
        }

        if (hex.length() == 8) {
            hex = hex.substring(2);
        }

        try {
            return Integer.parseInt(hex, 16);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static String toHexString(int color) {
        return String.format("#%06X", color);
    }

    public static final Codec<Integer> CODEC = Codec.either(
            Codec.INT,
            Codec.STRING.comapFlatMap(
                    s -> {
                        int result = parseColor(s);
                        if (result < 0) {
                            return DataResult.error(() -> "[Custom Sapling]无效的颜色格式: '" + s + "'，支持格式: 十进制整数 / #RRGGBB / 0xRRGGBB");
                        }
                        return DataResult.success(result);
                    },
                    ColorUtils::toHexString
            )
    ).xmap(
            either -> either.map(i -> i, s -> s),
            Either::right
    );
}