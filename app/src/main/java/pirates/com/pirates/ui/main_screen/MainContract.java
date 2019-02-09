package pirates.com.pirates.ui.main_screen;

import pirates.com.pirates.ui.BasePresenter;
import pirates.com.pirates.ui.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
public class MainContract {

    public interface View extends BaseView<Presenter> {

    }

    public  interface Presenter extends BasePresenter<View> {


    }

}
