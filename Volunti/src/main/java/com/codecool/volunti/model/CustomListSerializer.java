package com.codecool.volunti.model;

import com.codecool.volunti.model.enums.SocialLink;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CustomListSerializer extends StdSerializer<List<OrganisationSocialLink>> {

    public CustomListSerializer() {
        this(null);
    }

    public CustomListSerializer(Class<List<OrganisationSocialLink>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<OrganisationSocialLink> items,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        HashMap<SocialLink, String> socialLinks = new HashMap<>();
        for (OrganisationSocialLink organisationSocialLink : items) {
git            socialLinks.put(organisationSocialLink.getSocialLinkType(), organisationSocialLink.getSocialLinkUrl());
        }
        generator.writeObject(socialLinks);
    }
}