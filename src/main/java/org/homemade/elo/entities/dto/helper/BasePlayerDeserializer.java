package org.homemade.elo.entities.dto.helper;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import org.homemade.elo.entities.dto.BasePlayer;

import java.io.IOException;

public class BasePlayerDeserializer extends KeyDeserializer {

    @Override
    public BasePlayer deserializeKey(String key, DeserializationContext ctxt) throws IOException {
        return new BasePlayer();
    }
}
