package com.reactnativenavigation.viewcontrollers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.reactnativenavigation.BaseTest;
import com.reactnativenavigation.mocks.SimpleViewController;
import com.reactnativenavigation.mocks.TitleBarReactViewCreatorMock;
import com.reactnativenavigation.mocks.TopBarBackgroundViewCreatorMock;
import com.reactnativenavigation.mocks.TopBarButtonCreatorMock;
import com.reactnativenavigation.parse.FabOptions;
import com.reactnativenavigation.parse.Options;
import com.reactnativenavigation.parse.params.Text;
import com.reactnativenavigation.utils.CommandListenerAdapter;
import com.reactnativenavigation.viewcontrollers.topbar.TopBarBackgroundViewController;
import com.reactnativenavigation.viewcontrollers.topbar.TopBarController;
import com.reactnativenavigation.views.Fab;
import com.reactnativenavigation.views.FabMenu;
import com.reactnativenavigation.views.StackLayout;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class FloatingActionButtonTest extends BaseTest {

    private final static int CHILD_FAB_COUNT = 3;

    private StackController stackController;
    private SimpleViewController childFab;
    private SimpleViewController childNoFab;
    private Activity activity;

    @Override
    public void beforeEach() {
        super.beforeEach();
        activity = newActivity();
        stackController = new StackControllerBuilder(activity)
                .setTopBarButtonCreator(new TopBarButtonCreatorMock())
                .setTitleBarReactViewCreator(new TitleBarReactViewCreatorMock())
                .setTopBarBackgroundViewController(new TopBarBackgroundViewController(activity, new TopBarBackgroundViewCreatorMock()))
                .setTopBarController(new TopBarController())
                .setId("stackController")
                .setInitialOptions(new Options())
                .createStackController();
        Options options = getOptionsWithFab();
        childFab = new SimpleViewController(activity, "child1", options);
        childNoFab = new SimpleViewController(activity, "child2", new Options());
    }

    @NonNull
    private Options getOptionsWithFab() {
        Options options = new Options();
        FabOptions fabOptions = new FabOptions();
        fabOptions.id = new Text("FAB");
        options.fabOptions = fabOptions;
        return options;
    }

    @NonNull
    private Options getOptionsWithFabActions() {
        Options options = new Options();
        FabOptions fabOptions = new FabOptions();
        fabOptions.id = new Text("FAB");
        for (int i = 0; i < CHILD_FAB_COUNT; i++) {
            FabOptions childOptions = new FabOptions();
            childOptions.id = new Text("fab" + i);
            fabOptions.actionsArray.add(childOptions);
        }
        options.fabOptions = fabOptions;
        return options;
    }

    private boolean hasFab() {
        StackLayout stackLayout = stackController.getStackLayout();
        for (int i = 0; i < stackLayout.getChildCount(); i++) {
            if (stackLayout.getChildAt(i) instanceof Fab) {
                return true;
            }
            if (stackLayout.getChildAt(i) instanceof FabMenu) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void showOnPush() {
        stackController.push(childFab, new CommandListenerAdapter());
        childFab.onViewAppeared();
        assertThat(hasFab()).isTrue();
    }

    @Test
    public void hideOnPush() {
        stackController.push(childFab, new CommandListenerAdapter());
        childFab.onViewAppeared();
        assertThat(hasFab()).isTrue();
        stackController.push(childNoFab, new CommandListenerAdapter());
        childNoFab.onViewAppeared();
        assertThat(hasFab()).isFalse();
    }

    @Test
    public void hideOnPop() {
        disablePushAnimation(childNoFab, childFab);
        stackController.push(childNoFab, new CommandListenerAdapter());
        stackController.push(childFab, new CommandListenerAdapter());
        childFab.onViewAppeared();
        assertThat(hasFab()).isTrue();
        stackController.pop(new CommandListenerAdapter());
        childNoFab.onViewAppeared();
        assertThat(hasFab()).isFalse();
    }

    @Test
    public void showOnPop() {
        disablePushAnimation(childFab, childNoFab);
        stackController.push(childFab, new CommandListenerAdapter());
        stackController.push(childNoFab, new CommandListenerAdapter());
        childNoFab.onViewAppeared();
        assertThat(hasFab()).isFalse();
        stackController.pop(new CommandListenerAdapter());
        childFab.onViewAppeared();
        assertThat(hasFab()).isTrue();
    }

    @Test
    public void hasChildren() {
        childFab = new SimpleViewController(activity, "child1", getOptionsWithFabActions());
        stackController.push(childFab, new CommandListenerAdapter());
        childFab.onViewAppeared();
        assertThat(hasFab()).isTrue();
        assertThat(containsActions()).isTrue();
    }

    private boolean containsActions() {
        StackLayout stackLayout = stackController.getStackLayout();
        for (int i = 0; i < stackLayout.getChildCount(); i++) {
            View child = stackLayout.getChildAt(i);
            if (child instanceof FabMenu) {
                return ((ViewGroup) child).getChildCount() == CHILD_FAB_COUNT + 2;
            }
        }
        return false;
    }
}
