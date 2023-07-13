package org.vcs;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.core.ToolType;
import burp.api.montoya.http.message.ContentType;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.logging.Logging;
import burp.api.montoya.ui.Selection;
import burp.api.montoya.ui.editor.EditorOptions;
import burp.api.montoya.ui.editor.HttpRequestEditor;
import burp.api.montoya.ui.editor.extension.EditorCreationContext;
import burp.api.montoya.ui.editor.extension.ExtensionProvidedHttpRequestEditor;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MyHttpRequestEditor implements ExtensionProvidedHttpRequestEditor {
    private final HttpRequestEditor requestEditor;
    private final EditorCreationContext creationContext;
    private HttpRequestResponse requestResponse;
    private final MontoyaApi api;
    private final Logging log;
    private final ContentType[] allowType = new ContentType[]{
            ContentType.JSON,
            ContentType.URL_ENCODED
    };

    public MyHttpRequestEditor(MontoyaApi api, EditorCreationContext creationContext) {
        this.api = api;
        this.log = this.api.logging();
        this.requestEditor = this.api.userInterface().createHttpRequestEditor(EditorOptions.READ_ONLY);
        this.creationContext = creationContext;
    }

    @Override
    public HttpRequest getRequest() {
        return this.requestResponse.request() ;
    }

    @Override
    public void setRequestResponse(HttpRequestResponse httpRequestResponse) {
        this.requestResponse = httpRequestResponse;
        String body = this.requestResponse.request().bodyToString();
        try {
            body = BodyProcessor.convert2Vietnamese(body);
        } catch (IOException e) {
            body += "\n\n----------------------------------------------------------\nEXCEPTION, CHECK LOG";
            throw new RuntimeException(e);
        }
        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
        this.requestEditor.setRequest(this.requestResponse.request().withBody(ByteArray.byteArray(bodyBytes)));
    }

    @Override
    public boolean isEnabledFor(HttpRequestResponse httpRequestResponse) {
        return Arrays.asList(allowType).contains(httpRequestResponse.request().contentType()) &&
                !this.creationContext.toolSource().isFromTool(ToolType.EXTENSIONS);
    }

    @Override
    public String caption() {
        return "Unicode to Vietnamese";
    }

    @Override
    public Component uiComponent() {
        return this.requestEditor.uiComponent();
    }

    @Override
    public Selection selectedData() {
        return requestEditor.selection().isPresent() ? requestEditor.selection().get() : null;
    }

    @Override
    public boolean isModified() {
        return requestEditor.isModified();
    }
}
