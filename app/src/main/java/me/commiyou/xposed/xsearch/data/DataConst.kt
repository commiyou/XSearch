package me.commiyou.xposed.xsearch.data

object DataConst {
    const val CHROME_PKGNAME = "com.android.chrome"
    const val TemplateUrlClass = "org.chromium.components.search_engines.TemplateUrl"
    //const val ClassLoaderClass = "com.google.android.gms.dynamite.DynamiteModule\$DynamiteLoaderClassLoader"
    const val ClassLoaderClass = "org.chromium.WrappedClassLoaderClass"
    val ChromeActivityClasses = setOf(
            "org.chromium.chrome.browser.ChromeTabbedActivity",
            "org.chromium.chrome.browser.document.DocumentActivity",
            "org.chromium.chrome.browser.document.IncognitoDocumentActivity",
            "org.chromium.chrome.browser.customtabs.CustomTabActivity",
            "com.google.android.apps.chrome.ChromeTabbedActivity",
            "com.google.android.apps.chrome.document.DocumentActivity",
            "com.google.android.apps.chrome.document.IncognitoDocumentActivity",
            "com.google.android.apps.chrome.Main"
    )
}