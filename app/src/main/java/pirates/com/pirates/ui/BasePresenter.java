package pirates.com.pirates.ui;

public interface BasePresenter<T> {

    void attachView(T view);

    void detachView();
}