package org.vcs;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

public class UnicodeToVietnamese implements BurpExtension {
    private String ExtName = "U2V";

    @Override
    public void initialize(MontoyaApi api) {
        api.extension().setName(this.ExtName);
        api.userInterface().registerHttpRequestEditorProvider(new MyHttpRequestEditorProvider(api));
        api.userInterface().registerHttpResponseEditorProvider(new MyHttpResponseEditorProvider(api));
    }
}
