package ua.juv4uk.swiftpocketguide

import android.content.Context
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

private class SwiftEditorKeyboardBridge(
    private val webView: WebView,
    private val inputMethodManager: InputMethodManager
) {
    @JavascriptInterface
    fun onEditorFocus() {
        webView.post {
            webView.requestFocus()
            inputMethodManager.showSoftInput(webView, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}

@Composable
fun SwiftPlaygroundScreen() {
    val context = LocalContext.current
    val webView = remember {
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            isFocusable = true
            isFocusableInTouchMode = true
            descendantFocusability = ViewGroup.FOCUS_AFTER_DESCENDANTS
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.loadsImagesAutomatically = true
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = false
            webChromeClient = WebChromeClient()
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            addJavascriptInterface(
                SwiftEditorKeyboardBridge(this, inputMethodManager),
                "AndroidKeyboard"
            )
            setOnTouchListener { view, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    view.requestFocusFromTouch()
                }
                false
            }
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean = request.url.scheme != "https"

                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    view.evaluateJavascript(
                        """
                        (function () {
                          var styleId = 'swift-pocket-guide-mobile';
                          if (!document.getElementById(styleId)) {
                            var style = document.createElement('style');
                            style.id = styleId;
                            style.textContent = `
                              html, body {
                                width: 100% !important;
                                height: 100% !important;
                                min-height: 0 !important;
                                overflow: hidden !important;
                              }
                              body > .container-fluid.vh-100 {
                                height: 100vh !important;
                                height: 100dvh !important;
                                min-height: 0 !important;
                                padding: 0 !important;
                              }
                              header .row > .col {
                                display: flex !important;
                                flex-wrap: wrap !important;
                                align-items: center !important;
                                gap: 4px !important;
                                padding: 5px !important;
                              }
                              header .row > .col > .d-inline-block:first-child {
                                display: none !important;
                              }
                              header .row > .col > .d-inline-block:nth-child(2) {
                                display: flex !important;
                                flex-wrap: wrap !important;
                                align-items: center !important;
                                gap: 4px !important;
                                width: 100% !important;
                              }
                              header .row > .col > .d-inline-block:nth-child(2) > * {
                                margin: 2px !important;
                              }
                              #stop-button, #clear-console-button, #format-button,
                              #lang-server-status, #share-button, #settings-button,
                              #help-button, footer {
                                display: none !important;
                              }
                              #run-button {
                                min-width: 84px !important;
                                min-height: 38px !important;
                                font-size: 15px !important;
                              }
                              main.row {
                                display: flex !important;
                                flex-direction: column !important;
                                flex-wrap: nowrap !important;
                                flex: 1 1 auto !important;
                                min-height: 0 !important;
                                overflow: hidden !important;
                              }
                              main.row > div {
                                width: 100% !important;
                                max-width: 100% !important;
                                min-height: 0 !important;
                              }
                              main.row > div:first-child {
                                flex: 0 0 58% !important;
                              }
                              main.row > div:last-child {
                                flex: 1 1 42% !important;
                              }
                              #editor-container, #terminal-container {
                                width: 100% !important;
                                height: 100% !important;
                                min-height: 0 !important;
                              }
                            `;
                            document.head.appendChild(style);
                          }

                          if (window.__swiftPocketKeyboardHook) return;
                          window.__swiftPocketKeyboardHook = true;

                          function notifyEditorFocus() {
                            var active = document.activeElement;
                            if (active && (
                              active.matches('textarea.inputarea, textarea, input') ||
                              active.isContentEditable
                            )) {
                              if (window.AndroidKeyboard) {
                                window.AndroidKeyboard.onEditorFocus();
                              }
                            }
                          }

                          document.addEventListener('focusin', notifyEditorFocus, true);
                          document.addEventListener('pointerdown', function (event) {
                            var target = event.target;
                            var editor = target && target.closest && target.closest('.monaco-editor');
                            if (editor) window.setTimeout(notifyEditorFocus, 120);
                          }, true);
                          document.addEventListener('touchend', function (event) {
                            var target = event.target;
                            var editor = target && target.closest && target.closest('.monaco-editor');
                            if (editor) window.setTimeout(notifyEditorFocus, 120);
                          }, true);
                        })();
                        """.trimIndent(),
                        null
                    )
                }
            }
            loadUrl("https://swiftfiddle.com/")
        }
    }

    DisposableEffect(webView) {
        onDispose {
            webView.stopLoading()
            webView.removeJavascriptInterface("AndroidKeyboard")
            webView.destroy()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                "Справжній Swift-компілятор",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                "Торкнися поля коду — клавіатура відкриється автоматично. Кнопка Run запускає програму онлайн; потрібен інтернет.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        AndroidView(
            factory = { webView },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}
