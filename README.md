
## Library

A data-driven implementation of the MVVM software architecture pattern based on Android Architecture Components (kotlin), incorporating RxJava2, Retrofit2, DataBinding and other open source frameworks

## Base

### BaseActivity

must cover the following methods

1. getContentViewId


Some extra features

- you can cover `initStatusBar()` with empty implementation to cancel immersion status bar or cover `initStatusBar()` with return true to achieve a light status bar

- you can setOnClickListener with this can be avoid continuous double clicks
- cover initViewObservable and get LiveData by ViewModel subscrible some observer

### BaseBindActivity


extends BaseActivity and must cover the following methods

1. getContentViewId
2. initDataBinding
3. getVariableId

### BaseViewModelImpl

if your use RxJava,you need subscribe by `subscribe(disposable: Disposable)`

### BaseModelImpl


if your use RxJava and subscribe in this class,you need subscribe by `subscribe(disposable: Disposable)`