package co.edu.upb.discoverchat.data.web.base;

import java.util.ArrayList;

import co.edu.upb.discoverchat.models.Image;

/**
 * Created by hatsumora on 1/05/15.
 */
public abstract class UIUpdater {
    public abstract void  updateUI();

    public void updateUI(ArrayList<Image> images){}
}
