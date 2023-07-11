package org.vcs;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ByteArray;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.MimeType;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.logging.Logging;
import burp.api.montoya.ui.Selection;
import burp.api.montoya.ui.editor.EditorOptions;
import burp.api.montoya.ui.editor.HttpResponseEditor;
import burp.api.montoya.ui.editor.extension.EditorCreationContext;
import burp.api.montoya.ui.editor.extension.ExtensionProvidedHttpResponseEditor;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MyHttpResponseEditor implements ExtensionProvidedHttpResponseEditor {
    private final HttpResponseEditor requestEditor;
    private HttpRequestResponse requestResponse;
    private final MontoyaApi api;
    private final Logging log;
    private final MimeType[] allowMimeType = new MimeType[]{
            MimeType.HTML,
            MimeType.JSON,
            MimeType.PLAIN_TEXT,
            MimeType.SCRIPT,
            MimeType.XML,
            MimeType.YAML,
    };


    public MyHttpResponseEditor(MontoyaApi api, EditorCreationContext creationContext) {
        this.api = api;
        this.log = this.api.logging();
        this.requestEditor = api.userInterface().createHttpResponseEditor(EditorOptions.READ_ONLY);
    }
    @Override
    public HttpResponse getResponse() {
        return this.requestResponse.response();
    }

    @Override
    public void setRequestResponse(HttpRequestResponse requestResponse) {
        this.requestResponse = requestResponse;
        String body = this.requestResponse.response().bodyToString();
        try {
            body = UnicodeToVietnamese.magicBytes + BodyProcessor.convert2Vietnamese(body);
        } catch (IOException e) {
            body += "\n\n----------------------------------------------------------\nEXCEPTION, CHECK LOG";
            throw new RuntimeException(e);
        }
        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
        this.requestEditor.setResponse(this.requestResponse.response().withBody(ByteArray.byteArray(bodyBytes)));

    }

    @Override
    public boolean isEnabledFor(HttpRequestResponse requestResponse) {
//        log.logToOutput("RESPONSE TYPE: " + requestResponse.response().inferredMimeType().name());
        return Arrays.asList(allowMimeType).contains(requestResponse.response().inferredMimeType());
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
        return this.requestEditor.selection().isPresent() ? requestEditor.selection().get() : null;
    }

    @Override
    public boolean isModified() {
        return this.requestEditor.isModified();
    }
}
