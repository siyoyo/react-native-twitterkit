
package com.netcetera.reactnative.twitterkit;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.bridge.JavaScriptModule;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

public class ReactTwitterKitPackage implements ReactPackage {


  @Nullable
  private final String consumerKey;
  @Nullable
  private final String consumerSecret;

  /**
   * Initializes the package. If you don't pass a consumer key and secret the package
   * assumes you've *already* initialized Fabric elsewhere.
   *
   * @param consumerKey the key
   * @param consumerSecret the secret
   */
  public ReactTwitterKitPackage(@Nullable String consumerKey,  @Nullable String consumerSecret) {

    this.consumerKey = consumerKey;
    this.consumerSecret = consumerSecret;
  }

  /**
   * Initializes the package *without* initializing Fabric.
   */
  public ReactTwitterKitPackage() {
    this(null, null);
  }

  public void initFabric(Context reactContext) {
    if (consumerKey == null || consumerSecret == null) {
      return;
    }

    TwitterConfig config = new TwitterConfig.Builder(reactContext)
            .logger(new DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(new TwitterAuthConfig(consumerKey, consumerSecret))
            .debug(true)
            .build();

    Twitter.initialize(config);
  }

  @Override
  public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
    return Collections.emptyList();
  }

  @Override
  public List<ViewManager> createViewManagers(
          ReactApplicationContext reactContext) {
    initFabric(reactContext);
    return Arrays.<ViewManager>asList(
            new ReactTweetViewManager()
    );
  }
}