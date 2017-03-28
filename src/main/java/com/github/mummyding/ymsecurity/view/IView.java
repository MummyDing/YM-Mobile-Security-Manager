package com.github.mummyding.ymsecurity.view;

/**
 * Created by dingqinying on 17/2/22.
 */

public interface IView<T> {

    void bindViewModel(T viewModel);

    void update();
}
