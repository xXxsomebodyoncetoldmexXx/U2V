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
    public static String magicBytes = "\u5e46\u52dc\uea4c\uc3ea\ud2a9\ucffb\u5b82\u564e\uefce\u6bd3\uc3a8\u8ba3\u1cb0\uae90\u7f85\ua2d4\nVCS_DUCK\n\n";
}
