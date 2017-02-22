package com.github.mummyding.ymsecurity.ui;

/**
 * Created by dingqinying on 17/2/22.
 */

public interface IView<T> {

    void bindViewModel(T viewModel);

    void update();
}
