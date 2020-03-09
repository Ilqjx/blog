package com.upfly.util;

import java.util.UUID;

public class UUIDUtil {

    private static UUID uuid;

    public static UUID getUuid() {
        return uuid;
    }

    public static void setUuid(UUID uuid) {
        UUIDUtil.uuid = uuid;
    }

}
