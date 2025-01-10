package MVC;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Class qui permet d'éxecuter un listener 2 fois seulement afin d'obtenir la taille en X et en Y des panes et autres
 * @param <T>
 */
public class TwoTimeChangeListener<T> implements ChangeListener<T> {
    private final ChangeListener<T> listener;
    private final Runnable actionApresDeuxFois; // Action à exécuter après deux fois
    private int count;
    public TwoTimeChangeListener(ChangeListener<T> wrappedListener, Runnable actionApresDeuxFois) {
        this.listener = wrappedListener;
        this.actionApresDeuxFois = actionApresDeuxFois;
        count = 0;
    }


    @Override
    public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
        // Appeler le listener
        listener.changed(observable, oldValue, newValue);
        count+=1;

        if (count >= 2) {
            //Après 2 fois donc après obtenir le X et Y je fait ma fonction pour creer mes fleches et je remove le listener car inutile
            actionApresDeuxFois.run();
            observable.removeListener(this);
        }
    }
}
