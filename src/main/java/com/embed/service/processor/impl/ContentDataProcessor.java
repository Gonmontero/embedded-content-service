package com.embed.service.processor.impl;

import com.embed.builder.ContentBuilder;
import com.embed.builder.ThumbnailBuilder;
import com.embed.command.ProviderService;
import com.embed.command.impl.ProviderServiceCommandFactory;
import com.embed.command.resources.ProviderEmbeddedResponse;
import com.embed.entities.Content;
import com.embed.entities.EmbeddedContent;
import com.embed.entities.Thumbnail;
import com.embed.exceptions.ApplicationException;
import com.embed.exceptions.errors.ErrorCode;
import com.embed.service.processor.DataProcessor;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ContentDataProcessor implements DataProcessor<EmbeddedContent, String> {

    @Autowired
    private Mapper mapper;

    @Override
    public void processData(EmbeddedContent content, String url) {

        ProviderServiceCommandFactory providerService = new ProviderServiceCommandFactory(ProviderService.class,
                                                                content.getProvider().getApiEndpoint());

        ProviderEmbeddedResponse embeddedContentResponse = providerService.getEmbeddedContent(url);

        if (embeddedContentResponse == null) {
            throw new ApplicationException(ErrorCode.CONTENT_NOT_FOUND, "Content was not found at the Provider");
        }
        content.getProvider().setUrl(embeddedContentResponse.getProvider_url());
        content.setContent(ContentBuilder.Builder()
                        .withAuthorName(embeddedContentResponse.getAuthor_name())
                        .withAuthorUrl(embeddedContentResponse.getAuthor_url())
                        .withHeigth(embeddedContentResponse.getHeight())
                        .withWidth(embeddedContentResponse.getWidth())
                        .withHtml(embeddedContentResponse.getHtml())
                        .withType(embeddedContentResponse.getType())
                        .withTitle(embeddedContentResponse.getTitle())
                        .withUrl(embeddedContentResponse.getUrl())
                        .build());

        if(!StringUtils.isEmpty(embeddedContentResponse.getThumbnail_Url())) {

        content.setThumbnail(ThumbnailBuilder.Builder()
                        .withHeigth(embeddedContentResponse.getThumbnail_height())
                        .withWidth(embeddedContentResponse.getThumbnail_width())
                        .withUrl(embeddedContentResponse.getThumbnail_Url())
                        .build());
        }

        content.setVersion(embeddedContentResponse.getVersion());
    }
}
