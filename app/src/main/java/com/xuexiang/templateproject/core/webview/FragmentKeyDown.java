
package com.xuexiang.templateproject.core.webview;

import android.view.KeyEvent;

/**
 *
 *
 * @author xuexiang
 * @since 2019/1/4 下午11:32
 */
public interface FragmentKeyDown {

    /**
     * fragment按键监听
     * @param keyCode
     * @param event
     * @return
     */
    boolean onFragmentKeyDown(int keyCode, KeyEvent event);
}
